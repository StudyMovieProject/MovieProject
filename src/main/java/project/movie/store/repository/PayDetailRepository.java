package project.movie.store.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.movie.store.domain.PayDetail;

public interface PayDetailRepository extends JpaRepository<PayDetail, Integer> {
}
