package project.movie.store.web;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import project.movie.common.web.response.ResponseDto;
import project.movie.store.domain.coupon.Coupon;
import project.movie.store.service.CouponService;

import java.util.List;

@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("/api/coupon/{memberId}")
public class CouponController {

    private final CouponService couponService;

    @GetMapping
    public ResponseEntity<?> couponList(@PathVariable String memberId){
        List<Coupon> coupons = couponService.couponList(memberId);
        return new ResponseEntity<>(new ResponseDto<>(1, "쿠폰 조회 성공", coupons), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCoupon(@PathVariable Integer id){
        Coupon coupon = couponService.getCoupon(id);
        return new ResponseEntity<>(new ResponseDto<>(1, "쿠폰 개별 조회 성공", coupon), HttpStatus.OK);
    }


    //쿠폰 사용 추가해야함

    @PutMapping("/{couponId}/use")
    public ResponseEntity<?> useCoupon(@PathVariable Integer couponId){
        couponService.useCoupon(couponId);
        return new ResponseEntity<>(new ResponseDto<>(1, "쿠폰 사용 완료", null), HttpStatus.OK);
    }

    //쿠폰삭제
    @DeleteMapping("/{couponId}")
    public ResponseEntity<?> deleteCoupon(@PathVariable Integer couponId){
        couponService.deleteCoupon(couponId);
        return new ResponseEntity<>(new ResponseDto<>(1, "쿠론 삭제 완료", null), HttpStatus.OK);
    }

    //쿠폰만료
    @PutMapping("/{couponId}/expire")
    public ResponseEntity<?> expireCoupon(@PathVariable Integer couponId){
//        couponService.expireCoupon();
        return new ResponseEntity<>(new ResponseDto<>(1, "쿠폰 만료 완료", null), HttpStatus.OK);
    }


}
