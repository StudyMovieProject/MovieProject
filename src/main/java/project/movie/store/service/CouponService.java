package project.movie.store.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.movie.common.handler.exception.CustomApiException;
import project.movie.store.domain.coupon.Coupon;
import project.movie.store.domain.coupon.CouponStatus;
import project.movie.store.domain.pay.Pay;
import project.movie.store.domain.pay.PayDetail;
import project.movie.store.repository.CouponRepository;


import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class CouponService {

    private final CouponRepository couponRepository;

    public void couponSave(Pay pay){

        List<PayDetail> payDetails = pay.getPayDetails();
        for (PayDetail payDetail : payDetails) {
            Coupon coupon = new Coupon();
            coupon.setPay(payDetail.getPay());
            coupon.setCpDate(LocalDateTime.now());
            coupon.setMemberId(pay.getMember().getMemberId());
            coupon.setCpStatus(CouponStatus.AVAILABLE);
            coupon.setItem(payDetail.getItem());


            couponRepository.save(coupon);
        }


    }


    public List<Coupon> couponList(String memberId){
        return couponRepository.findByMemberId(memberId);
    }

    public Coupon getCoupon(Integer id){
        return couponRepository.findByCpCode(id)
                .orElseThrow(() -> new CustomApiException("존재하지 않는 쿠폰입니다."));
    }

    //쿠폰 사용
    @Transactional
    public void useCoupon(Integer couponId){
        Coupon coupon = couponRepository.findByCpCode(couponId)
                .orElseThrow(()->new CustomApiException("존재하지 않는 쿠폰입니다."));

        if (coupon.getCpStatus() == CouponStatus.USED){
            throw new CustomApiException("이미 사용된 쿠폰입니다.");
        }

        coupon.setCpStatus(CouponStatus.USED);
        couponRepository.save(coupon);
    }


    //쿠폰 사용시 삭제
    @Transactional
    public void deleteCoupon(Integer couponId){
        Coupon coupon = couponRepository.findByCpCode(couponId)
                .orElseThrow(() -> new CustomApiException("존재하지 않는 쿠폰입니다."));

        couponRepository.delete(coupon);
    }

    //쿠폰 만료
    @Scheduled(cron = "0 0 0 * * ?") // 매일 자정에 실행
    public void expireCoupon(){
        LocalDateTime expirationDate = LocalDateTime.now().minusDays(120);

        List<Coupon> expiredCoupons = couponRepository.findByCpDateBeforeAndCpStatusNot(expirationDate, CouponStatus.USED);

        for (Coupon coupon : expiredCoupons) {
            coupon.setCpStatus(CouponStatus.EXPIRED);
            couponRepository.save(coupon);
        }
    }
}
