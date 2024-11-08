package project.movie.store.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name="cart")
@Getter
@Setter
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="cart_code", nullable = false)
    private Integer cartCode;

    @ManyToOne
    @JoinColumn(name="item_code", nullable = false)
    private Item item;

    @Column(name="cart_qty", nullable = false)
    private Integer cartQty;

    @Column(name="cart_status", nullable = false)
    private Integer cartStatus;

    @Column(name="cart_date", nullable = false)
    private LocalDateTime cartDate;

    protected Cart(){}




}
