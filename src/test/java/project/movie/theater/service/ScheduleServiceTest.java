package project.movie.theater.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import project.movie.theater.dto.ScheduleReqDto;
import project.movie.theater.dto.ScheduleResDto;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@SqlGroup({
        @Sql(value = "/sql/schedule-service-test-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
        @Sql(value = "/sql/delete-all-data.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
})
class ScheduleServiceTest {

    @Autowired
    ScheduleService scheduleService;

    // END 는 자동 완성 후 커서 위치
    @Test
    @DisplayName("상영중인 시간표는 1개 이상이다.")
    public void list_test() throws Exception {
        // given
        ScheduleReqDto scheduleReqDto = new ScheduleReqDto();
        scheduleReqDto.setBookingDate(String.valueOf(LocalDate.now()));
        scheduleReqDto.setTheaterId("2"); // 영화관
        scheduleReqDto.setMovieId("21"); // 영화 정보

        // when
        List<ScheduleResDto> showTimes = scheduleService.findShowTimes(scheduleReqDto);

        // then
        assertThat(showTimes).hasSizeGreaterThan(0);
    }

}