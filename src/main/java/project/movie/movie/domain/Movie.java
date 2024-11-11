package project.movie.movie.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.util.StringUtils;
import project.movie.common.domain.Base;
import project.movie.movie.constants.MovieSyncConstants;
import project.movie.movie.dto.MovieDateGeneratorDTO;
import project.movie.movie.dto.MovieDetailSyncDTO;
import project.movie.movie.dto.MovieSyncDTO;

import java.nio.file.Paths;
import java.time.LocalDate;

@Slf4j
@Entity
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "movies")
@Getter
@ToString
public class Movie extends Base {
    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    // 한글 제목
    @NotBlank(message = "영화 제목은 공백 일 수 없습니다.")
    private String title;

    // 영어 제목
    @Column(name = "title_en")
    private String titleEn;

    // 트레일러
    private String trailer;

    // 줄거리
    @Lob
    @Column(name = "plot", columnDefinition="BLOB")
    private String plot;

    // 포스터 이미지 경로
    @Column(name = "poster_image")
    private String posterImage;

    // 배너 이미지 경로
    @Column(name = "backdrop_image")
    private String backdropImage;

    // 개봉년도
    @Column(name = "product_yaer")
    private Integer productYear;

    // 개봉일
    @Column(name = "product_date")
    private String productDate;

    // 상영 시간
    @NotNull(message = "상영 시간은 Null 일 수 없습니다.")
    @Column(name = "show_time")
    private Integer showTime;

    // 상영일
    @NotNull(message = "상영일은 Null 일 수 없습니다.")
    @Column(name = "start_date")
    private LocalDate startDate;

    // 종영일
    @Column(name = "end_date")
    private LocalDate endDate;

    // 인기도
    @Column
    private Integer popularity;

    // 영화 코드
    @Column
    private Integer movieCd;

    @Builder
    public Movie(Long id, String title, String titleEn, String trailer, String plot, String posterImage, String backdropImage, Integer productYear, String productDate, Integer showTime, LocalDate startDate, LocalDate endDate, Integer popularity, Integer movieCd) {
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

    /**
     * 영화 객체 생성
     *
     * @param movieSyncDTO 영화 기본 정보
     * @param movieDetailSyncDTO 영화 상세 정보
     * @param movieDateGeneratorDTO 영화 시작일, 종료일 객체
     *
     * @return Movie
     */
    public static Movie from(MovieSyncDTO movieSyncDTO, MovieDetailSyncDTO movieDetailSyncDTO, MovieDateGeneratorDTO movieDateGeneratorDTO) {
        return Movie.builder()
                .title(movieSyncDTO.getTitle())
                .titleEn(movieSyncDTO.getOriginalTitle())
                .plot(movieSyncDTO.getOverview())
                .posterImage(Paths.get(MovieSyncConstants.POSTER_IMG_FOLDER , movieSyncDTO.getPosterPath()).toString())
                .backdropImage(Paths.get(MovieSyncConstants.BACKDROP_IMG_FOLDER, movieSyncDTO.getBackdropPath()).toString())
                .showTime(movieDetailSyncDTO.getRuntime())
                .productYear(Integer.parseInt(StringUtils.substring(movieSyncDTO.getReleaseDate(), 0, 4)))
                .productDate(movieDetailSyncDTO.getReleaseDate())
                .popularity((int) movieDetailSyncDTO.getPopularity())
                .startDate(movieDateGeneratorDTO.getStartDate())
                .endDate(movieDateGeneratorDTO.getEndDate())
                .movieCd(movieSyncDTO.getId())
                .build();
    }

    @Transactional
    public Movie update(MovieSyncDTO movieSyncDTO, MovieDetailSyncDTO movieDetailSyncDTO, MovieDateGeneratorDTO movieDateGeneratorDTO) {
        this.title = movieSyncDTO.getTitle();
        this.titleEn = movieSyncDTO.getOriginalTitle();
        this.plot = movieSyncDTO.getOverview();
        this.posterImage = Paths.get(MovieSyncConstants.POSTER_IMG_FOLDER, movieSyncDTO.getPosterPath()).toString();
        this.backdropImage = Paths.get(MovieSyncConstants.BACKDROP_IMG_FOLDER, movieSyncDTO.getBackdropPath()).toString();
        this.showTime = movieDetailSyncDTO.getRuntime();
        this.productYear = Integer.parseInt(StringUtils.substring(movieSyncDTO.getReleaseDate(), 0, 4));
        this.productDate = movieDetailSyncDTO.getReleaseDate();
        this.popularity = (int) movieDetailSyncDTO.getPopularity();
        this.endDate = movieDateGeneratorDTO.getEndDate();

        return this;
    }
}
