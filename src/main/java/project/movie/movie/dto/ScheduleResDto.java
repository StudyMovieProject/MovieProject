package project.movie.movie.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import project.movie.movie.domain.Schedule;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@ToString
@Builder
public class ScheduleResDto {
    @Schema(description = "스케쥴 고유 번호", required = true, example = "1")
    private Long id;

    @Schema(description = "영화 정보", required = true, example = "java.lang.Object")
    private MovieResDto movieResDto;

    @Schema(description = "영화관 정보", required = true, example = "java.lang.Object")
    private TheaterResDto theaterResDto;

    @Schema(description = "상영관 정보", required = true, example = "java.lang.Object")
    private ScreenResDto screenResDto;

    @Schema(description = "상영 일자", required = true, example = "2024-11-14")
    private LocalDate scheduleDate;

    @Schema(description = "상영 시작 시간", required = true, example = "09:19:00.000000")
    private LocalTime startAt;

    @Schema(description = "상영 종료 시간", required = true, example = "09:19:00.000000")
    private LocalTime endAt;

    @Schema(description = "위치 코드 정보", required = true, example = "java.lang.Object")
    private String code;

    public static ScheduleResDto from(Schedule schedule) {
        return ScheduleResDto.builder()
                .id(schedule.getId())
                .movieResDto(MovieResDto.from(schedule.getMovie()))
                .theaterResDto(TheaterResDto.from(schedule.getTheater()))
                .screenResDto(ScreenResDto.from(schedule.getScreen()))
                .scheduleDate(schedule.getScheduleDate())
                .startAt(schedule.getStartAt())
                .endAt(schedule.getEndAt())
                .code(schedule.getCode())
                .build();
    }
}
