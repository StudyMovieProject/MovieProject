package project.movie.store.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import project.movie.member.domain.Member;

import java.time.LocalDateTime;

@Entity
@Table(name="pay")
@Getter
@Setter
public class Pay {

    @Id
    @Column(name="pay_code", nullable = false, length = 20)
    private String payCode;

    @ManyToOne
    @JoinColumn(name="customer_id", nullable = false)
    private Member member;

    @Column(name="pay_type", nullable = false, length = 50)
    private String payType;

    @Column(name="pay_price", nullable = false)
    private Integer pay_price;

    @Column(name="pay_date", nullable = false)
    private LocalDateTime payDate;

    @Column(name="cancel_date")
    private LocalDateTime cancelDate;

    @Column(name="pay_status", nullable = false)
    private Integer payStatus;

    protected Pay(){};
}
