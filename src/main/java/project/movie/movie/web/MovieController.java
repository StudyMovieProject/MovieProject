package project.movie.movie.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import project.movie.common.web.response.ResponseDto;
import project.movie.movie.domain.Movie;
import project.movie.movie.domain.MovieStatus;
import project.movie.movie.dto.MovieResDto;
import project.movie.movie.service.MovieService;

import java.util.List;

@RestController
@RequestMapping("/api/movies")
@RequiredArgsConstructor
@Slf4j
public class MovieController {

    private final MovieService movieService;


    /**
     * 영화 상태에 따라 목록 조회 (인기작 | 최근 개봉작 | 상영 예정작)
     *
     * @param status [POPULAR | LATEST | UPCOMING]
     *
     * @return List
     */
    @GetMapping("/status/{status}")
    public ResponseEntity<ResponseDto<List<MovieResDto>>> getMoviesByStatus(@PathVariable String status) {
        log.info("영화 상태 조회 메서드 실행: {}", status);

        try {
            MovieStatus movieStatus = MovieStatus.valueOf(status.toUpperCase());
            List<Movie> movies = movieService.getMoviesByStatus(movieStatus);
            List<MovieResDto> movieRespDtos = movies.stream().map(MovieResDto::from).toList();
            return ResponseEntity.ok(new ResponseDto<>(1, "영화 목록 조회 성공", movieRespDtos));
        } catch (IllegalArgumentException e) {
            log.warn("유효하지 않은 영화 상태: {}", status);
            return ResponseEntity.badRequest()
                    .body(new ResponseDto<>(-1, "유효하지 않은 영화 상태입니다.", null));
        }
    }

    // 인기 영화 목록 조회
    @GetMapping("/popular")
    public ResponseEntity<?> getPopularMovies() {
        List<Movie> popularMovies = movieService.getPopularMovies();
        List<MovieResDto> movieRespDtos = popularMovies.stream().map(MovieResDto::from).toList();
        return new ResponseEntity<>(new ResponseDto<>(1, "박스 오피스 영화 목록 조회 성공", movieRespDtos), HttpStatus.OK);
    }

    // 최신 영화 목록 조회
    @GetMapping("/latest")
    public ResponseEntity<?> getLatestMovies() {
        List<Movie> popularMovies = movieService.getLatestMovies();
        List<MovieResDto> movieRespDtos = popularMovies.stream().map(MovieResDto::from).toList();
        return new ResponseEntity<>(new ResponseDto<>(1, "박스 오피스 영화 목록 조회 성공", movieRespDtos), HttpStatus.OK);
    }

    // 예정 영화 목록 조회
    @GetMapping("/upcoming")
    public ResponseEntity<?> getUpcomingMovies() {
        List<Movie> popularMovies = movieService.getUpcomingMovies();
        List<MovieResDto> movieRespDtos = popularMovies.stream().map(MovieResDto::from).toList();
        return new ResponseEntity<>(new ResponseDto<>(1, "박스 오피스 영화 목록 조회 성공", movieRespDtos), HttpStatus.OK);
    }

    // 영화 상세 정보 조회
    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable Long id) {
        log.info("getByMovieId 메서드 실행: {}", id);
        Movie movie = movieService.getById(id);
        return new ResponseEntity<>(new ResponseDto<>(1, "영화 정보 조회 성공", MovieResDto.from(movie)), HttpStatus.OK);
    }
}
