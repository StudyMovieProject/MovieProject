package project.movie.movie.sync;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import project.movie.movie.domain.Movie;
import project.movie.movie.domain.MovieStatus;
import project.movie.movie.dto.ScheduleSaveDto;
import project.movie.movie.repository.MovieRepository;
import project.movie.movie.service.ScheduleService;
import project.movie.theater.domain.Screen;
import project.movie.theater.domain.Theater;
import project.movie.theater.repository.ScreenRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static project.movie.common.util.date.DateFormatUtil.*;

@Slf4j
@Component
@RequiredArgsConstructor
public class MovieTimetableDataMakeScheduler {

    private final MovieRepository movieRepository;

    private final ScreenRepository screenRepository;

    private final ScheduleService scheduleService;

    public static final LocalTime THEATER_FIRST_MOVIE_PLAY_TIME = LocalTime.of(7, 30);
    public static final int MOVIE_BUFFER_INTERVAL = 20; // (분단위)
    public static final LocalDate TODAY = LocalDate.now();

    @Scheduled(cron = "20 * 4 * * ?")
    @Scheduled(fixedRate = 5000L)
    public void makeMovieTimetableData() {
        System.out.println("makeMovieTimetableData 실행하였습니다.");
        // ##################나중에 고려해야 할 것들##################
        // 일단 ScheduleSaveDto 부터 만들어야 될듯...
        // 영화 시작 시간은 tempTime 시간을 하나 두고 그걸로 계산 해도 좋을듯 함??? 이 시간 사용
        //  - 영화관 오픈 시간에서 계속 시간 더해가면서 사용하는 거지
        // 시간표 만드는 도중 영화 순회가 모두 끝나게 되면 어떻게 하지 ?  그럼 해당 스크린의 시간표 생성은 종료 될텐데...????
        // 영화 순회 도중 ... 잘못하면 한번도 상영되지 않는 영화가 생길수도 있을 것 같아보인다...
        // 영화 목록을 가지고 있는 Map 을 만들고 시간표를 만들면 해당 Map 에서 영화 정보를 제거한다.
        // 만약 Map 이 다 비워지거나 다음 screen 을 순회하게 되면 똑같은 Map 을 만들어서 다시 순회하데 하면 될것 같다.
        /**
         * 1. 스크린 목록을 가져온다.
         * 2. 영화 목록을 가져온다.
         * 3. 스크린 목록을 가져온다.
         * 4. 영화관 오픈 시간을 상수로 선언한다. 07:30 (LocalDateTime)
         * 5. 영화관 마감 시간을 상수로 정의한다. 01:00 (LocalDateTime)
         * 6. 스크린 순회한다.
         * 7. 이 내부 안에서 다시 영화목록을 순회한다.
         * 8. ScheduleSaveDto 객체를 빌더 형식으로 생성한다.
         *    - 스크린 엔티티를 넣어준다.
         *    - 영화 엔티티를 넣어준다. (이건 엔티티니까)
         *    - 영화 시작 시간을 넣어준다. 스크린 처음 순회하는건 영화관 오픈 시간[THEATER_FIRST_MOVIE_PLAY_TIME](07:30)
         *    - 영화 종료 시간은 시작 시간에 영화 상영 시간(moviesByStatusList.get(i).getShowTime())을 더한 시간으로 계산
         *    - 영화관의 code 값이 존재하니 해당 값을 통해 locations 객체를 가져올수 있다.
         *    - code 에는 영화관의 locations 객체 code 값을 넣어주자
         *  9. ScheduleSaveDto 가 생성 되었으니 해당 객체를 통해 Schedule 를 생성한다.
         *  10. 저장한다.
         *    - 저장 전에 영화 종료 시간이 영화관 마감 시간인지 체크한다. 영화관 마감 시간을 넘어가면 아무것도 하지않고 함수 종료
         *  11. 영화목록 순회 중 다음 영화로 넘어감
         *  12. 영화 목록을 모두 순회한다.
         *  13. 다음 스크린으로 넘어가서 해당 행동을 반복한다.
         */
        List<Screen> allScreenList = screenRepository.findAll();
        List<Movie> moviesByStatusList = movieRepository.findMoviesByStatus(MovieStatus.POPULAR.toString());

        // Empty 체크
        if (validateTimeTableCreation(allScreenList, moviesByStatusList)) {
            return;
        }

        for (Screen screen : allScreenList) {
            Theater theater = screen.getTheater();
            LocalDateTime dateTimeNow = LocalDateTime.of(TODAY, THEATER_FIRST_MOVIE_PLAY_TIME); // currentStartTime 시간이 +1 days 되었는지 추적용
            LocalTime currentStartTime = THEATER_FIRST_MOVIE_PLAY_TIME;
            LocalTime currentEndTime = THEATER_FIRST_MOVIE_PLAY_TIME;

            for (Movie movie : moviesByStatusList) {
                int showTimeMinutes = movie.getShowTime(); // 영화 상영 시간
                System.out.println("영화 제목: " + movie.getTitle());
                System.out.println("영화 상영시간: " + showTimeMinutes);
                currentEndTime = currentEndTime.plusMinutes(showTimeMinutes); // 영화 종료 시간은 영화 상영시간을 더해준다.

                // 영화관 마감 시간 체크
                if (!TODAY.getDayOfWeek().equals(dateTimeNow.getDayOfWeek())) {
                    log.debug("영화관 마감 시간 체크");
                    break;
                }

                // ScheduleSaveDto 생성
                ScheduleSaveDto scheduleSaveDto = ScheduleSaveDto.builder()
                        .movie(movie)
                        .theater(theater)
                        .screen(screen)
                        .scheduleDate(convertToLocalDateToDate(TODAY))
                        .startAt(convertToLocalTimeToTime(currentStartTime))
                        .endAt(convertToLocalTimeToTime(currentEndTime))
                        .build();

                // 영화 시간표 저장
                scheduleService.create(scheduleSaveDto);

                System.out.println("영화 시작시간: " + currentStartTime);
                System.out.println("영화 종료시간: " + currentEndTime);
                // 다음 영화 시작 시간을 계산
                currentEndTime = currentEndTime.plusMinutes(MOVIE_BUFFER_INTERVAL); // 상영 시간 추가
                currentStartTime = currentStartTime
                                        .plusMinutes(showTimeMinutes) // 상영 시간 추가
                                        .plusMinutes(MOVIE_BUFFER_INTERVAL); // 영화 상영 간 대기 시간 추가
                dateTimeNow = dateTimeNow
                                        .plusMinutes(showTimeMinutes) // 상영 시간 추가
                                        .plusMinutes(MOVIE_BUFFER_INTERVAL); // 영화 상영 간 대기 시간 추가
            }
        }
    }

    // 시간표 저장을 위해 필요한 영화 목록, 스크린 데이터 유효성 체크
    public boolean validateTimeTableCreation(List<?>... lists) {
        for (List list : lists) {
            if (CollectionUtils.isEmpty(list))
                return true;
        }
        return false;
    }
}
