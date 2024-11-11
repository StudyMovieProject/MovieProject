package project.movie.movie.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import project.movie.movie.domain.Movie;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@Builder
public class MovieResDto {
    private Long id;
    // 한글 제목
    private String title;
    // 영어 제목
    private String titleEn;
    // 트레일러
    private String trailer;
    // 줄거리
    private String plot;
    // 포스터 이미지 경로
    private String posterImage;
    // 배너 이미지 경로
    private String backdropImage;
    // 개봉일
    private Integer productYear;
    private String productDate;
    // 상영 시간
    private Integer showTime;
    // 상영일
    private LocalDate startDate;
    // 종영일
    private LocalDate endDate;
    // 인기도
    private Integer popularity;
    // 영화 코드
    private Integer movieCd;

    public MovieResDto(Long id, String title, String titleEn, String trailer, String plot, String posterImage, String backdropImage, Integer productYear, String productDate, Integer showTime, LocalDate startDate, LocalDate endDate, Integer popularity, Integer movieCd) {
        this.id = id;
        this.title = title;
        this.titleEn = titleEn;
        this.trailer = trailer;
        this.plot = plot;
        this.posterImage = posterImage;
        this.backdropImage = backdropImage;
        this.productYear = productYear;
        this.productDate = productDate;
        this.showTime = showTime;
        this.startDate = startDate;
        this.endDate = endDate;
        this.popularity = popularity;
        this.movieCd = movieCd;
    }

    public MovieResDto(Movie movie) {
        this.id = movie.getId();
        this.title = movie.getTitle();
        this.titleEn = movie.getTitleEn();
        this.trailer = movie.getTrailer();
        this.plot = movie.getPlot();
        this.posterImage = movie.getPosterImage();
        this.backdropImage = movie.getBackdropImage();
        this.productYear = movie.getProductYear();
        this.productDate = movie.getProductDate();
        this.showTime = movie.getShowTime();
        this.startDate = movie.getStartDate();
        this.endDate = movie.getEndDate();
        this.popularity = movie.getPopularity();
        this.movieCd = movie.getMovieCd();
    }

    public static MovieResDto from(Movie movie) {
        return MovieResDto.builder()
                .id(movie.getId())
                .title(movie.getTitle())
                .titleEn(movie.getTitleEn())
                .trailer(movie.getTrailer())
                .plot(movie.getPlot())
                .posterImage(movie.getPosterImage())
                .backdropImage(movie.getBackdropImage())
                .productYear(movie.getProductYear())
                .productDate(movie.getProductDate())
                .showTime(movie.getShowTime())
                .startDate(movie.getStartDate())
                .endDate(movie.getEndDate())
                .popularity(movie.getPopularity())
                .movieCd(movie.getMovieCd())
                .build();
    }
}
