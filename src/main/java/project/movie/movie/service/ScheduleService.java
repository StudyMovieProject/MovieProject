package project.movie.movie.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.movie.movie.dto.ScheduleSaveDto;
import project.movie.movie.repository.ScheduleRepository;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    @Transactional
    public void create(ScheduleSaveDto scheduleSaveDto) {
        // 1. 영화 시간표 저장
        scheduleRepository.save(scheduleSaveDto.to());
    }
}
