package project.movie.theater.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@Table(name = "seats")
@Entity
@Getter
@ToString
public class Seat {
    @Schema(description = "좌석코드", required = true, example = "1")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "seat_id")
    private Integer id;

    @Schema(description = "상영관 코드", required = true, example = "1")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "screen_id")
    private Screen screen;

    @Schema(description = "영화관 코드", required = true, example = "1")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "theater_id")
    private Theater theater;

    @Schema(description = "좌석 그룹", required = true, example = "A")
    @Column(name = "seat_group", length = 10)
    private String seatGroup;

    @Schema(description = "좌석 번호", required = true, example = "1")
    @Column(name = "seat_no")
    private Integer seatNo;

    @Schema(description = "좌석 줄번호", required = true, example = "1")
    @Column(name = "seat_line_no")
    private Integer seatLineNo;
}
