package project.movie.theater.web;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.movie.common.web.response.ResponseDto;
import project.movie.theater.dto.ScheduleResDto;
import project.movie.theater.dto.ScheduleReqDto;
import project.movie.theater.service.ScheduleService;

import java.util.List;

@RestController
@RequestMapping("/api/schedules")
@RequiredArgsConstructor
@Slf4j
public class ScheduleController {

    private final ScheduleService scheduleService;

    @Operation(summary = "영화 상영 시간표 정보")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "영화 상영 시간표 정보 조회 성공",
                    content = {@Content(schema = @Schema(implementation = ResponseEntity.class))}),
            @ApiResponse(responseCode = "403", description = "액세스할 수 있는 권한이 없습니다."),
    })
    @GetMapping
    public ResponseEntity<ResponseDto<List<ScheduleResDto>>> listShowTimes(
            @Parameter(description = "영화 상영 시간표 정보 요청 DTO") @RequestBody ScheduleReqDto scheduleReqDto) {
        List<ScheduleResDto> schduleResDtoList = null;
//        try {
        log.info("ScheduleService > listByDateAndTheaterAndMovie START: {}", scheduleReqDto);

        schduleResDtoList = scheduleService.findShowTimes(scheduleReqDto);

        log.info("ScheduleService > listByDateAndTheaterAndMovie END: {}", scheduleReqDto);
//        } catch (Exception e) {
//            log.error("[ERROR] ScheduleService > listByDateAndTheaterAndMovie: {}", e.getMessage());
//            return ResponseEntity.ok(new ResponseDto<>(-1, "시간표 목록 조회 실패", null));
//        }
        return ResponseEntity.ok(new ResponseDto<>(1, "영화 상영 시간표 정보 조회 성공", schduleResDtoList));
    }


}