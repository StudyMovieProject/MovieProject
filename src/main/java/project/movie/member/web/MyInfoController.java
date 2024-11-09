package project.movie.member.web;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import project.movie.common.controller.response.ResponseDto;
import project.movie.member.domain.Member;
import project.movie.member.dto.MemberRespDto;
import project.movie.member.dto.MemberUpdateReqDto;
import project.movie.member.service.MemberService;

@RestController
@RequestMapping("/api/me")
@RequiredArgsConstructor
@Slf4j
public class MyInfoController {

    private final MemberService memberService;

    @GetMapping
    public ResponseEntity<?> get(@AuthenticationPrincipal UserDetails userDetails) {
        log.info("MyInfoController getByMemberId 메서드 실행");
        Member member = memberService.getByMemberId(userDetails.getUsername());
        return new ResponseEntity<>(new ResponseDto<>(1, "로그인 유저 정보 조회 성공", MemberRespDto.from(member)), HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<?> update(@RequestBody @Valid MemberUpdateReqDto MemberUpdateReqDto, @AuthenticationPrincipal UserDetails userDetails) {
        log.info("MyInfoController join 메서드 실행 : {}", MemberUpdateReqDto.toString());
        MemberRespDto userRespDto = memberService.update(userDetails.getUsername(), MemberUpdateReqDto);
        return new ResponseEntity<>(new ResponseDto<>(1, "회원정보 수정 성공", userRespDto), HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> delete(@AuthenticationPrincipal UserDetails userDetails, @RequestParam("password") String password) {
        log.info("MyInfoController join 메서드 실행");
        memberService.delete(userDetails.getUsername(), password);
        return new ResponseEntity<>(new ResponseDto<>(1, "회원 삭제 성공", null), HttpStatus.OK);
    }

    public String getLoginMemberId(UserDetails userDetails) {
        String username = userDetails.getUsername();  // 로그인한 유저의 아이디
        log.info("principal : {}", username);
        log.info("principal : {}", userDetails.getAuthorities());
        log.info("principal : {}", userDetails.getPassword());

        return username;
    }

}
