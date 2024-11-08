package project.movie.board.web;

import io.swagger.annotations.ApiOperation;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import project.movie.auth.jwt.dto.CustomUserDetails;
import project.movie.auth.jwt.service.CustomUserDetailsService;
import project.movie.board.domain.Board;
import project.movie.board.dto.BoardDto;
import project.movie.board.dto.BoardReqDto;
import project.movie.board.dto.BoardRespDto;
import project.movie.board.service.BoardService;
import project.movie.common.controller.response.ResponseDto;
import project.movie.member.domain.Member;
import project.movie.member.dto.MemberRespDto;
import project.movie.member.service.MemberService;
@RequestMapping("/api/board")
@RequiredArgsConstructor
@RestController
@Slf4j
public class BoardController {
    private final MemberService memberService;
    private final BoardService boardService;
    // 전체 게시물 조회
    @RequestMapping(value = "/lists", method = RequestMethod.GET)
    public ResponseEntity<?> getLists() {
        log.info("전체 게시물 가져오기 실행");

        return new ResponseEntity<>(new ResponseDto<>(1, "전체 게시물 조회 성공", boardService.getLists()), HttpStatus.OK);
    }

    //선택한 게시물 조회
    @RequestMapping(value = "/lists/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getList(@PathVariable int id) {

        return new ResponseEntity<>(new ResponseDto<>(1, "선택한 게시물 조회 성공",  boardService.getList(id)), HttpStatus.OK);
    }

    // 게시물 작성
    @RequestMapping(value = "/write", method = RequestMethod.POST)
    public ResponseEntity<?> writeList(@RequestBody BoardReqDto requestsDto, BindingResult bindingResult) {
        BoardRespDto boardRespDto = boardService.writeList(requestsDto);
        return new ResponseEntity<>(new ResponseDto<>(1, "게시물 작성 성공", boardRespDto), HttpStatus.OK);

    }

    //선택 게시물 수정
    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    public ResponseEntity<?> updateList(@PathVariable int id, @RequestBody BoardReqDto requestsDto )throws Exception{
        return new ResponseEntity<>(new ResponseDto<>(1, "게시물 수정 성공", boardService.updateList(id, requestsDto)), HttpStatus.OK);
    }

    //선택한 게시물 삭제
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public ResponseEntity<?> deleteList(@PathVariable int id ){
        return new ResponseEntity<>(new ResponseDto<>(1, "게시물 삭제 성공", boardService.deleteList(id)), HttpStatus.OK);
    }
}
