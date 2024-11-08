package project.movie.store.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="card")
@Getter
@Setter
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="card_code", nullable = false)
    private Integer cardCode;

    @ManyToOne
    @JoinColumn(name="pay_code", nullable = false)
    private Pay pay;

    @Column(name="card_num", length = 20)
    private String cardNum;

    @Column(name="card_month")
    private Integer cardMonth;

    @Column(name="card_year")
    private Integer cardYear;

    @Column(name="card_pw")
    private Integer cardPw;

    @Column(name="card_birth")
    private Integer cardBirth;

    @Column(name="pay_ins")
    private Integer payIns;

    protected Card(){}

}
