package project.movie.theater.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import project.movie.member.domain.Member;
import project.movie.movie.domain.PaymentMethod;
import project.movie.theater.domain.Reservation;
import project.movie.theater.domain.Schedule;
import project.movie.theater.domain.Seat;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Builder
public class ReservationResDto {
    @Schema(description = "예약 계정", required = true, example = "java.lang.Object")
    private Member member;

    @Schema(description = "영화 일정", required = true, example = "java.lang.Object")
    private Schedule schedule;

    @Schema(description = "좌석", required = true, example = "java.lang.Object")
    private Seat seat;

    @Schema(description = "인원수", required = true, example = "1")
    private Integer headCount; // 인원수

    @Schema(description = "계산 금액", required = true, example = "70000")
    private Long price;

    @Schema(description = "결재 수단", required = false, example = "CREDIT_CARD | MOBILE")
    private PaymentMethod paymentMethod;

    @Schema(description = "카드 번호", required = false, example = "7281928329392")
    private String cardNumber;

    @Schema(description = "결재유무체크", required = false, example = "TRUE | FALSE")
    private Boolean isPaymentConfirmed;

    @Schema(description = "결재일", required = false, example = "TRUE | FALSE")
    private LocalDateTime paidAt;

    public ReservationResDto(Member member, Schedule schedule, Seat seat, Integer headCount, Long price, PaymentMethod paymentMethod, String cardNumber, Boolean isPaymentConfirmed, LocalDateTime paidAt) {
        this.member = member;
        this.schedule = schedule;
        this.seat = seat;
        this.headCount = headCount;
        this.price = price;
        this.paymentMethod = paymentMethod;
        this.cardNumber = cardNumber;
        this.isPaymentConfirmed = isPaymentConfirmed;
        this.paidAt = paidAt;
    }

    public static ReservationResDto from(Reservation reservation) {
        return ReservationResDto.builder()
                .member(reservation.getMember())
                .schedule(reservation.getSchedule())
                .seat(reservation.getSeat())
                .headCount(reservation.getHeadCount())
                .price(reservation.getPrice())
                .paymentMethod(reservation.getPaymentMethod())
                .cardNumber(reservation.getCardNumber())
                .isPaymentConfirmed(reservation.getIsPaymentConfirmed())
                .build();
    }
}
