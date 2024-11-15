package project.movie.theater.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.movie.theater.domain.Schedule;
import project.movie.theater.dto.ScheduleResDto;
import project.movie.theater.dto.ScheduleSaveDto;
import project.movie.theater.dto.ScheduleReqDto;
import project.movie.theater.repository.ScheduleRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    @Transactional
    public void create(ScheduleSaveDto scheduleSaveDto) {
        // 1. 영화 시간표 저장
        scheduleRepository.save(scheduleSaveDto.to());
    }

    @Transactional
    public List<ScheduleResDto> listByDateAndTheaterAndMovie(ScheduleReqDto scheduleReqDto) {
        List<Schedule> schedules = scheduleRepository.listByDateAndTheaterAndMovie(scheduleReqDto);
        return schedules.stream().map(ScheduleResDto::from).toList();
    }
}
