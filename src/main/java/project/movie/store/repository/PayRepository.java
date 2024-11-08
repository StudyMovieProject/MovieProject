package project.movie.store.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.movie.member.domain.Member;
import project.movie.store.domain.Pay;

import java.time.LocalDateTime;
import java.util.List;

public interface PayRepository extends JpaRepository<Pay,String> {
    List<Pay> findByMember(Member member);
    List<Pay> findByPayType(String payType);
    List<Pay> findByPayStatus(Integer payStatus);
    List<Pay> findByPayDataAfter(LocalDateTime payDate);
}
