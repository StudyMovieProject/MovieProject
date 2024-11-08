package project.movie.store.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="paydetail")
@Getter
@Setter
public class PayDetail {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="payde_code", nullable = false)
    private Integer paydeCode;

    @ManyToOne
    @JoinColumn(name="pay_code", nullable = false)
    private Pay pay;

    @ManyToOne
    @JoinColumn(name="item_code", nullable = false)
    private Item item;

    @Column(name="cart_qty")
    private Integer cartQty;

    protected PayDetail(){}
}
