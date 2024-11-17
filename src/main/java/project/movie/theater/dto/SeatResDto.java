package project.movie.theater.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import project.movie.theater.domain.Screen;
import project.movie.theater.domain.Seat;
import project.movie.theater.domain.Theater;

@Getter
@Setter
@ToString
@Builder
public class SeatResDto {
    @Schema(description = "좌석코드", required = true, example = "1")
    private Long id;

    @Schema(description = "상영관 코드", required = true, example = "1")
    private Screen screen;

    @Schema(description = "영화관 코드", required = true, example = "1")
    private Theater theater;

    @Schema(description = "좌석 그룹", required = true, example = "A")
    private String seatGroup;

    @Schema(description = "좌석 번호", required = true, example = "1")
    private Integer seatNo;

    @Schema(description = "좌석 줄번호", required = true, example = "1")
    private Integer seatLineNo;

    @Schema(description = "예매 가능 여부", required = true, example = "true | false")
    private Boolean isBookable; // 예매 가능 여부

    public SeatResDto(Long id, Screen screen, Theater theater, String seatGroup, Integer seatNo, Integer seatLineNo, Boolean isBookable) {
        this.id = id;
        this.screen = screen;
        this.theater = theater;
        this.seatGroup = seatGroup;
        this.seatNo = seatNo;
        this.seatLineNo = seatLineNo;
        this.isBookable = isBookable;
    }

    public SeatResDto(Seat seat) {
        this.id = seat.getId();
        this.screen = seat.getScreen();
        this.theater = seat.getTheater();
        this.seatGroup = seat.getSeatGroup();
        this.seatNo = seat.getSeatNo();
        this.seatLineNo = seat.getSeatLineNo();
        this.isBookable = seat.getIsBookable();
    }

    public static SeatResDto from(Seat seat) {
        return new SeatResDto(seat);
    }
}
