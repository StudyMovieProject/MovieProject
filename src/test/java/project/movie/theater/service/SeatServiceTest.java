package project.movie.theater.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.transaction.annotation.Transactional;
import project.movie.member.domain.Member;
import project.movie.member.service.MemberService;
import project.movie.movie.domain.PaymentMethod;
import project.movie.theater.domain.*;
import project.movie.theater.dto.ReservationSaveReqDto;
import project.movie.theater.dto.SeatAvailableResDto;
import project.movie.theater.dto.SeatReqDto;
import project.movie.theater.repository.ScreenRepository;
import project.movie.theater.repository.TheaterRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

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
class SeatServiceTest {

    @Autowired
    private SeatService seatService;
//    @Autowired
//    private ReservationService reservationService;
//    @Autowired
//    private ScheduleService scheduleService;
//    @Autowired
//    private MemberService memberService;
//    @Autowired
//    private ScreenRepository screenRepository;
//    @Autowired
//    private TheaterRepository theaterRepository;

//    @BeforeEach
//    public void init() {
//        Schedule byId = scheduleService.get(1L);
//
//        Member byMemberId = memberService.getByMemberId("net1506");
//
//        Screen screen = screenRepository.getById(3L);
//
//        Theater theater = theaterRepository.findById(2L).get();
//
//        System.out.println(screen);
//        System.out.println(theater);
//
//        // 1번쨰 좌석 예약
//        Seat seat = Seat.builder()
//                .id(172L)
//                .theater(theater)
//                .screen(screen)
//                .seatNo(1)
//                .seatLineNo(1)
//                .seatGroup("A")
//                .isBookable(false)
//                .build();
//
//        ReservationSaveReqDto reservationSaveReqDto = ReservationSaveReqDto.builder()
//                .memberId(byMemberId.getMemberId())
//                .scheduleId((byId.getId()))
//                .seatId(seat.getId())
//                .headCount(4)
//                .price(750000L)
//                .paymentMethod(PaymentMethod.CREDIT_CARD.toString())
//                .cardNumber("1234123412341234")
//                .isPaymentConfirmed(false)
//                .paidAt(LocalDateTime.now())
//                .build();
//
//        reservationService.create(reservationSaveReqDto);
//    }

    // END 는 자동 완성 후 커서 위치
    @Test
    @Transactional
    @DisplayName("예약 가능/불가능 좌석에 대한 유효성 체크")
    public void findAvailable_Seats_test() throws Exception {
        // given
        SeatReqDto seatReqDto = SeatReqDto.builder()
                .theaterId(2L)
                .screenId(3L)
                .scheduleDate(LocalDate.of(2024, 11, 18))
                .startAt(LocalTime.of(7, 30))
                .build();

        // when
        List<SeatAvailableResDto> seatAvailableResDtos = seatService.findAvailableSeats(seatReqDto);
        SeatAvailableResDto seatAvailableResDto1 = seatAvailableResDtos.get(0);
        SeatAvailableResDto seatAvailableResDto2 = seatAvailableResDtos.get(1);

        // then
        assertThat(seatAvailableResDto1.isAvailable()).isFalse(); // 이미 예약된 좌석
        assertThat(seatAvailableResDto2.isAvailable()).isTrue(); // 예약 가능한 좌석
    }

}