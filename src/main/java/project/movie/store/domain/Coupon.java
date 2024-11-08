package project.movie.store.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name="coupon")
@Getter
@Setter
public class Coupon {

    @Id
    @Column(name="cp_code", nullable = false, length = 20)
    private String cpCode;

    @ManyToOne
    @JoinColumn(name="pay_code", nullable = false)
    private Pay pay;

    @ManyToOne
    @JoinColumn(name="item_code", nullable = false)
    private Item item;

    @Column(name="customer_id", nullable = false, length = 30)
    private String customerId;

    @Column(name="cp_date")
    private LocalDateTime cpDate;

    @Column(name="cp_status")
    private Integer cpStatus;

    protected Coupon(){}
}
