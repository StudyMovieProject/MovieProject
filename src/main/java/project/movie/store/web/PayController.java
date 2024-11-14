package project.movie.store.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import project.movie.common.web.response.ResponseDto;
import project.movie.store.domain.pay.Pay;
import project.movie.store.dto.PG.IamportResponseDto;
import project.movie.store.dto.PG.PaymentCompleteDto;
import project.movie.store.dto.PG.PaymentRequestDto;
import project.movie.store.dto.cart.CartPurchaseDto;
import project.movie.store.dto.cart.PurchaseByOneDto;
import project.movie.store.service.PayService;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/pay")
public class PayController {

    private final PayService payService;

    //결제 구매하기 전 결제 구매 생성 (장바구니 구매)
    @PostMapping("/cart/purchase/create")
    public ResponseEntity<?> purchaseByCart(@RequestBody List<CartPurchaseDto> cartPurchaseDtos, @AuthenticationPrincipal UserDetails userDetails){
        PaymentRequestDto paymentRequestDto = payService.payCreate(cartPurchaseDtos, userDetails.getUsername());
//        PaymentRequestDto paymentRequestDto = payService.payCreate(cartPurchaseDtos, "test10");
        return new ResponseEntity<>(new ResponseDto<>(1, "주문번호 생성 완료", paymentRequestDto), HttpStatus.OK);
    }

    //결제 구매하기 전 결제 구매 생성 (바로 구매)
    @PostMapping("/direct/purchase/create")
    public ResponseEntity<?> purchaseByOne(@RequestBody PurchaseByOneDto purchaseByOneDto, @AuthenticationPrincipal UserDetails userDetails){
        PaymentRequestDto paymentRequestDto = payService.payCreateByOne(purchaseByOneDto, userDetails.getUsername());
//        PaymentRequestDto paymentRequestDto = payService.payCreateByOne(purchaseByOneDto, "test10");
        return new ResponseEntity<>(new ResponseDto<>(1, "선택된 상품 구매하기 조회", paymentRequestDto), HttpStatus.OK);
    }

    //결제 완료 확인
    @PostMapping("/payment/complete")
    public ResponseEntity<?> payCheck(@RequestBody PaymentCompleteDto dto){
        IamportResponseDto response =  payService.verifyResponse(dto);
        return new ResponseEntity<>(new ResponseDto<>(1, "결제 성공", response), HttpStatus.OK);
    }


    @GetMapping("/{payCode}")
    public ResponseEntity<?> paidPage(@PathVariable String orderId ){
        Pay pay = payService.getPayInfo(orderId);
        return new ResponseEntity<>(new ResponseDto<>(1,"결제 완료 페이지 조회 성공", pay), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> getPayInfoAllByMember(@AuthenticationPrincipal UserDetails userDetails){
        List<Pay> pays = payService.getPayInfoAllByMember(userDetails.getUsername());
//        List<Pay> pays = payService.getPayInfoAllByMember("test10");
        payService.cancelCheck(pays);

        return new ResponseEntity<>(new ResponseDto<>(1,"결제 정보 리스트 조회 성공", pays),HttpStatus.OK);
    }

    /**
     * 임시코드 (추후 PG API 연결시 수정)
     * Pending -> Cancel (3일이후)
     * Cancel -> 일주일후 (삭제)
     */
    @PutMapping("/cancel/{payCode}")
    public ResponseEntity<?> cancelPayment(@PathVariable String payCode){
        payService.cancelPayment(payCode);
        Pay pay = payService.getFindByPayCode(payCode);
        return new ResponseEntity<>(new ResponseDto<>(1, "결제 취소 성공", pay),HttpStatus.OK);
    }



}
