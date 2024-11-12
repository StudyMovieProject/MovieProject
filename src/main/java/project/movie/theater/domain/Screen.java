package project.movie.theater.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@Table(name = "screens")
@Entity
@Getter
@ToString
public class Screen {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "screen_id")
    private Long id;

    @Schema(description = "상영관번호", required = true, example = "1")
    @Column(name = "screen_number")
    private Integer screenNumber;

    @Schema(description = "영화관코드", required = true, example = "1")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "theater_id")
    private Theater theater;

    @Schema(description = "좌석 갯수", required = true, example = "138")
    @Column(name = "seat_count")
    private Integer seatCount; // 좌석 수
}
