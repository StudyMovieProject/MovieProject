package project.movie.movie.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import project.movie.common.domain.Base;
import project.movie.theater.domain.Screen;
import project.movie.theater.domain.Theater;

import java.sql.Time;
import java.util.Date;

@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "schedules")
@Entity
@Getter
@Slf4j
@ToString
public class Schedule extends Base {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "schedule_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "movie_id")
    private Movie movie;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "theater_id")
    private Theater theater;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "screen_id")
    private Screen screen;

    @Column(name = "schedule_date")
    private Date scheduleDate;

    @Column(name = "start_at")
    private Time startAt;

    @Column(name = "end_at")
    private Time endAt;

    private String code;

    @Builder
    public Schedule(Long id, Movie movie, Theater theater, Screen screen, Date scheduleDate, Time startAt, Time endAt, String code) {
        this.id = id;
        this.movie = movie;
        this.theater = theater;
        this.screen = screen;
        this.scheduleDate = scheduleDate;
        this.startAt = startAt;
        this.endAt = endAt;
        this.code = code;
    }
}
