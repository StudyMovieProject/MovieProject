package project.movie.store.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.movie.store.domain.Coupon;

public interface CouponRepository extends JpaRepository<Coupon, String> {
}
