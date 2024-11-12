package project.movie.movie.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import project.movie.common.domain.Base;

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
    @Column(name = "schedule_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "movie_id")
    private Movie movie;

    @Column(name = "schedule_date")
    private Date scheduleDate;

    @Column(name = "start_at")
    private Time startAt;

    @Column(name = "end_at")
    private Time endAt;

    private String code;
}
