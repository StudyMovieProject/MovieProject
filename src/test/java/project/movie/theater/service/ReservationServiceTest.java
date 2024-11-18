package project.movie.theater.service;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.transaction.annotation.Transactional;
import project.movie.movie.domain.PaymentMethod;
import project.movie.theater.domain.Reservation;
import project.movie.theater.dto.ReservationSaveReqDto;
import project.movie.theater.repository.ReservationRepository;

import java.time.LocalDateTime;

import static java.time.LocalDate.now;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@SqlGroup({
        @Sql(value = "/sql/member-service-test-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
        @Sql(value = "/sql/schedule-service-test-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
        @Sql(value = "/sql/reservation-service-test-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
        @Sql(value = "/sql/delete-all-data.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD),
        @Sql(value = "/sql/delete-member-data.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
})
class ReservationServiceTest {

    @Autowired
    ReservationService reservationService;
    @Autowired
    ReservationRepository reservationRepository;
    @Autowired
    EntityManager entityManager;

    // END 는 자동 완성 후 커서 위치
    @Test
    @DisplayName("영화 예매 테스트")
    public void create_test() throws Exception {
        // given
        ReservationSaveReqDto reservationRequest = ReservationSaveReqDto.builder()
                .memberId("net1506")
                .scheduleId(1L)
                .seatId(173L)
                .headCount(1)
                .price(13000L)
                .paymentMethod("CREDIT_CARD")
                .cardNumber("1234567890123456")
                .isPaymentConfirmed(false)
                .paidAt(LocalDateTime.now())
                .build();

        // when
        Reservation reservation = reservationService.create(reservationRequest);

        // then
        assertThat(reservation.getMember().getMemberId()).isEqualTo("net1506");
        assertThat(reservation.getSchedule().getScheduleDate()).isEqualTo(now());
        assertThat(reservation.getHeadCount()).isEqualTo(1);
        assertThat(reservation.getPaymentMethod()).isEqualTo(PaymentMethod.CREDIT_CARD);
    }

    @Test
    @DisplayName("영화 예매 결재 여부 변경 테스트")
    @Transactional
    public void update_test() throws Exception {
        // given
        Long reservationId = 1L;

        // when
        Reservation beforeReservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new RuntimeException("Reservation not found"));
        assertThat(beforeReservation.getIsPaymentConfirmed()).isFalse();

        reservationService.update(reservationId, true);

        // 영속성 컨텍스트 초기화
        entityManager.flush();
        entityManager.clear();

        // then
        Reservation afterReservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new RuntimeException("Reservation not found"));
        assertThat(afterReservation.getIsPaymentConfirmed()).isTrue();
    }

}