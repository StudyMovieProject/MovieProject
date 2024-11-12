package project.movie.movie.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import project.movie.movie.domain.Movie;
import project.movie.movie.domain.Schedule;
import project.movie.theater.domain.Screen;
import project.movie.theater.domain.Theater;

import java.sql.Time;
import java.util.Date;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class ScheduleSaveDto {
    @NotNull
    private Movie movie;
    @NotNull
    private Theater theater;
    @NotNull
    private Screen screen;
    @NotNull
    private Date scheduleDate;
    @NotNull
    private Time startAt;
    @NotNull
    private Time endAt;
    @NotNull
    private String code;

    @Builder
    public ScheduleSaveDto(Movie movie, Theater theater, Screen screen, Date scheduleDate, Time startAt, Time endAt, String code) {
        this.movie = movie;
        this.theater = theater;
        this.screen = screen;
        this.scheduleDate = scheduleDate;
        this.startAt = startAt;
        this.endAt = endAt;
        this.code = code;
    }

    public Schedule to() {
        return Schedule.builder()
                .movie(movie)
                .theater(theater)
                .screen(screen)
                .scheduleDate(scheduleDate)
                .startAt(startAt)
                .endAt(endAt)
                .code(code)
                .build();
    }
}
