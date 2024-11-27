package project.movie.store.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.movie.common.handler.exception.CustomApiException;
import project.movie.common.web.response.ResponseDto;
import project.movie.member.domain.Member;
import project.movie.member.service.MemberService;

import project.movie.store.domain.PG.IamPortClient;
import project.movie.store.domain.cart.Cart;
import project.movie.store.domain.item.Item;
import project.movie.store.domain.pay.Pay;
import project.movie.store.domain.pay.PayDetail;
import project.movie.store.domain.pay.PayStatus;
import project.movie.store.dto.PG.IamportResponseDto;
import project.movie.store.dto.PG.PaymentCompleteDto;
import project.movie.store.dto.PG.PaymentRequestDto;
import project.movie.store.dto.cart.CartPurchaseDto;
import project.movie.store.dto.cart.PurchaseByOneDto;
import project.movie.store.dto.pay.PayRespDto;
import project.movie.store.repository.PayRepository;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class PayService {


    private final PayRepository payRepository;
    private final MemberService memberService;
    private final CouponService couponService;
    private final IamPortClient iamPortClient;
    private final ItemService itemService;
    private final CartService cartService;


    @Transactional
    public PaymentRequestDto payCreate(List<CartPurchaseDto> cartPurchaseDtos, String memberId){
        Member findMember = memberService.getByMemberId(memberId);
        int totalPrice = 0;

        Pay pay = new Pay();
        pay.generatePayCode();
        pay.setMember(findMember);
        pay.setPayDate(LocalDateTime.now());
        pay.setPayStatus(PayStatus.CART_CREATE);


        for( CartPurchaseDto cartPurchaseDto : cartPurchaseDtos){
            Cart findCart =  cartService.findByCartCode(cartPurchaseDto.getCartCode());
            PayDetail payDetail = new PayDetail();
            payDetail.setPay(pay);
            payDetail.setItem(findCart.getItem());
            payDetail.setCartQty(findCart.getCartQty());
            totalPrice += ((findCart.getItem().getPrice() - findCart.getItem().getSalePrice()) * findCart.getCartQty());
        }

        pay.setPayPrice(totalPrice);


        PaymentRequestDto paymentRequestDto = new PaymentRequestDto();
        paymentRequestDto.setPayCode(pay.getPayCode());
        paymentRequestDto.setTotalPrice(pay.getPayPrice());
        paymentRequestDto.setStatus(PayStatus.CART_CREATE);
        paymentRequestDto.setMemberUsername(pay.getMember().getUsername());
        paymentRequestDto.setImpUid(iamPortClient.getImpPortUid());

        return paymentRequestDto;
    }

    @Transactional
    public PaymentRequestDto payCreateByOne(PurchaseByOneDto purchaseByOneDto, String memberId){
        Member findMember = memberService.getByMemberId(memberId);
        int totalPrice = 0;

        Pay pay = new Pay();
        pay.generatePayCode();
        pay.setMember(findMember);
        pay.setPayDate(LocalDateTime.now());
        pay.setPayStatus(PayStatus.DIRECT_CREATE);


        Item findItem =  itemService.itemFindByItemCode(purchaseByOneDto.getItem().getItemCode());
        PayDetail payDetail = new PayDetail();
        payDetail.setPay(pay);
        payDetail.setItem(findItem);
        payDetail.setCartQty(purchaseByOneDto.getItemQty());
        totalPrice += ((findItem.getPrice() - findItem.getSalePrice()) * purchaseByOneDto.getItemQty());


        pay.setPayPrice(totalPrice);


        PaymentRequestDto paymentRequestDto = new PaymentRequestDto();
        paymentRequestDto.setPayCode(pay.getPayCode());
        paymentRequestDto.setTotalPrice(pay.getPayPrice());
        paymentRequestDto.setStatus(PayStatus.DIRECT_CREATE);
        paymentRequestDto.setMemberUsername(pay.getMember().getUsername());
        paymentRequestDto.setImpUid(iamPortClient.getImpPortUid());

        return paymentRequestDto;
    }


    public IamportResponseDto verifyResponse(PaymentCompleteDto dto){
        IamportResponseDto response = iamPortClient.paymentByImpUid(dto.getImpUid());

        if("paid".equals(response.getResponse().getStatus())){
            Pay findPay = getFindByPayCode(response.getResponse().getPayCode());
            if(response.getResponse().getPayPrice().equals(findPay.getPayPrice())){

                if (findPay.getPayStatus().equals(PayStatus.CART_CREATE)){
                    cartService.paidCart(findPay);
                }
                findPay.setPayStatus(PayStatus.PAID);
                findPay.setImpCode(dto.getImpUid());
                couponService.couponSave(findPay);
                return response;
            }else{
                cancelPGResponse(dto.getImpUid(), "결제 금액 불일치로 인한 취소");
                throw new CustomApiException("결제 금액 오류");
            }
        }else{
            throw new CustomApiException("결제 오류");
        }

    }

    private void cancelPGResponse(String impUid, String reason){
        try{
            IamportResponseDto response = iamPortClient.cancelPaymentByImpUid(impUid, reason);

            if(!"cancelled".equals(response.getResponse().getStatus())){
                throw new CustomApiException("결제 취소 실패 : PG서버 응답 오류");
            }
        }catch (Exception e){
            throw new CustomApiException("결제 취소중 오류" + e.getMessage());
        }
    }


    public List<Pay> getPayInfoAllByMember(String memberId){
        return payRepository.findByMember_memberId(memberId)
                .orElseThrow(() -> new CustomApiException("해당 회원이 존재하지 않습니다."));
    }


    //결제정보 조회
    public PayRespDto getPayInfo(String payCode){
        Pay pay = payRepository.findById(payCode)
                .orElseThrow(() -> new CustomApiException("결제코드가 존재하지 않습니다"));

        return PayRespDto.from(pay);
    }

    public List<PayRespDto> convertToDtos(List<Pay> pays){
        List<PayRespDto> payRespDtos = new ArrayList<>();
        for(Pay pay : pays){
            payRespDtos.add(PayRespDto.from(pay));
        }
        return payRespDtos;
    }




    @Transactional
    //결제정보 업데이트 ( 결제 취소 )
    public ResponseDto<?> cancelPayment(String payCode){
        Optional<Pay> payOptional = payRepository.findById(payCode);
        if(payOptional.isPresent()){
            Pay pay = payOptional.get();
            if(!payDateOlderOneWeek(pay.getPayDate())){
                cancelPGResponse(pay.getImpCode(), "결제 취소 요청");

                couponService.cancelPayAndCoupon(pay.getPayCode());
                deletePayment(pay.getPayCode());
                return new ResponseDto<>(1,"취소 성공", null);
            }
            PayRespDto dto = PayRespDto.from(pay);
            return new ResponseDto<>(1, "결제 후 일주일 지난 결제 취소 불가", dto);
        }else{
            throw new CustomApiException("결제 정보를 찾을 수 없습니다");
        }
    }

    //결제정보 삭제
    public void deletePayment(String payCode){
        if (payRepository.existsById(payCode)){
            payRepository.deleteById(payCode);
        }else{
            throw new CustomApiException("결제 정보를 찾을 수 없습니다");
        }
    }


    public Pay getFindByPayCode(String payCode){
        return payRepository.findById(payCode)
                .orElseThrow(() -> new CustomApiException("결제 정보가 존재하지 않습니다."));
    }

    private boolean payDateOlderOneWeek(LocalDateTime payDate){
        LocalDateTime now = LocalDateTime.now();
        long daysBetween = ChronoUnit.DAYS.between(payDate, now);
        return daysBetween > 7;
    }
}
