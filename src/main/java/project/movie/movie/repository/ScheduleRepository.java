package project.movie.movie.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import project.movie.movie.domain.Schedule;
import project.movie.movie.dto.ScheduleReqDto;

import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    @Query("select s from Schedule s where s.theater.id = :#{#paramScheduler.theaterId} and s.movie.id = :#{#paramScheduler.movieId} and s.scheduleDate = date(:#{#paramScheduler.bookingDate})")
    List<Schedule> listByDateAndTheaterAndMovie(@Param("paramScheduler") ScheduleReqDto scheduleReqDto);
}
