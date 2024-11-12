package project.movie.movie.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.movie.movie.domain.Schedule;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

}
