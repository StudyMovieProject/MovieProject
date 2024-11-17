package project.movie.theater.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import project.movie.theater.domain.Seat;

@Getter
@Setter
@ToString
@Builder
public class SeatAvailableResDto {
    private Seat seat;
    private boolean isNotAvailable;

    public SeatAvailableResDto(Seat seat) {
        this.seat = seat;
    }

    public SeatAvailableResDto(Seat seat, boolean isNotAvailable) {
        this.seat = seat;
        this.isNotAvailable = isNotAvailable;
    }
}
