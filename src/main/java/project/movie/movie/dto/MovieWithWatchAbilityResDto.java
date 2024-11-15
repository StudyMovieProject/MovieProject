package project.movie.movie.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import project.movie.movie.domain.Movie;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class MovieWithWatchAbilityResDto {
    private Movie movie;
    private boolean isNotWatchable;

    public MovieWithWatchAbilityResDto(Movie movie) {
        this.movie = movie;
    }

    public MovieWithWatchAbilityResDto(Movie movie, boolean isNotWatchable) {
        this.movie = movie;
        this.isNotWatchable = isNotWatchable;
    }
}