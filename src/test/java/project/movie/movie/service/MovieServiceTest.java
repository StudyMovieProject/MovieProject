package project.movie.movie.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.transaction.annotation.Transactional;
import project.movie.common.dto.DummyObject;
import project.movie.movie.dto.MovieAvailableReqDto;
import project.movie.movie.dto.MovieAvailableResDto;
import project.movie.movie.repository.MovieRepository;

import java.util.List;

@ActiveProfiles("test")
@SpringBootTest
@ExtendWith(MockitoExtension.class)
@SqlGroup({
        @Sql(value = "/sql/schedule-service-test-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
        @Sql(value = "/sql/reservation-service-test-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
        @Sql(value = "/sql/delete-all-data.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
})
class MovieServiceTest extends DummyObject {

    @Autowired
    private MovieService movieService;

    @Autowired
    private MovieRepository movieRepository;


//    // END 는 자동 완성 후 커서 위치
//    @Test
//    public void dateTEst() throws Exception {
//        // given
//        LocalTime now = LocalTime.now();
//        LocalTime newNow = now.plusMinutes(120);
//
//        Time time = Time.valueOf(DateFormatUtil.convertToLocalTimeToTime(newNow));
//        newNow = newNow.plusMinutes(120);
//
////        System.out.println(now);
//        System.out.println(time);
//        System.out.println(newNow);
//
//
//        // when
//
//        // then
//    }
//
//    // END 는 자동 완성 후 커서 위치
//    @Test
//    public void listMovieByStatus_test() throws Exception {
//        // given
//        String STATUS = "POPULAR";
////        List<Movie> moviesByStatus = movieService.getMoviesByStatus(MovieStatus.valueOf(STATUS));
//        List<Movie> moviesByStatus = movieService.getLatestMovies();
//
//        // when
//        for (Movie movie : moviesByStatus) {
//            System.out.println("movie = " + movie.getTitle());
//        }
//
//        // then
//    }
//
//    @Test
//    public void getMoviesPage_Default_test() throws Exception {
//        // given
//        String criteria = "title";
//        String sort = "";
//
//        // when
//        List<Movie> movieList = movieRepository.findAll();
//
//        // then
//        for (Movie movie : movieList) {
//            System.out.println(movie.toString());
//        }
//    }
//
//    // END 는 자동 완성 후 커서 위치
//    @Test
//    public void getMoviesPage_DESC_test() throws Exception {
//        // given
//        String criteria = "productDate";
//        String sort = "DESC"; // 최신 개봉작
//
//        // when
//        Page<Movie> moviesPage = movieService.getMoviesPage(criteria, sort);
//        List<Movie> movieList = moviesPage.getContent();
//
//        // then
//        for (Movie movie : movieList) {
//            System.out.println(movie.toString());
//        }
//    }
//
//    // END 는 자동 완성 후 커서 위치
//    @Test
//    public void getMoviesPage_ASC_test() throws Exception {
//        // given
//        String criteria = "productDate";
//        String sort = "ASC"; // 오래된 개봉 순
//
//        // when
//        Page<Movie> moviesPage = movieService.getMoviesPage(criteria, sort);
//        List<Movie> movieList = moviesPage.getContent();
//
//        // then
//        for (Movie movie : movieList) {
//            System.out.println(movie.toString());
//        }
//    }

    // END 는 자동 완성 후 커서 위치
    @Test
    @Transactional
    @DisplayName("영화 스케쥴이 존재/비존재 시 isWatchable(상영 가능) 변수 테스트")
    public void list_available_movie_test() throws Exception {
        // given
        MovieAvailableReqDto movieAvailableReqDto = new MovieAvailableReqDto();
        movieAvailableReqDto.setScheduleDate("2024-11-19");
        movieAvailableReqDto.setTheaterId(2L);
        List<MovieAvailableResDto> movieAvailableResDtos = movieService.findAvailableMovies(movieAvailableReqDto);

        // when
        MovieAvailableResDto movieAvailableResDtoNotWatchable = movieAvailableResDtos.get(19);
        MovieAvailableResDto movieAvailableResDtoWatchable = movieAvailableResDtos.get(20);

        // then
        Assertions.assertThat(movieAvailableResDtoNotWatchable.isWatchable()).isFalse();
        Assertions.assertThat(movieAvailableResDtoWatchable.isWatchable()).isTrue();
    }
}