package project.movie.store.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.movie.store.domain.coupon.Coupon;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface CouponRepository extends JpaRepository<Coupon, String> {
    List<Coupon> findByMemberId(String memberId);
    Optional<Coupon> findByCpCode(Integer cpCode);
    List<Coupon> findByCpDateBeforeAndCpStatusNot(LocalDateTime expirationDate, int status);
}
