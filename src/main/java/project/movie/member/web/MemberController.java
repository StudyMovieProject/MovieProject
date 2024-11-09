package project.movie.member.web;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import project.movie.common.controller.response.ResponseDto;
import project.movie.member.domain.Member;
import project.movie.member.dto.MemberRespDto;
import project.movie.member.dto.MemberSaveReqDto;
import project.movie.member.dto.MemberUpdateReqDto;
import project.movie.member.service.MemberService;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
@Slf4j
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/join")
    public ResponseEntity<?> join(@RequestBody @Valid MemberSaveReqDto memberSaveReqDto, BindingResult bindingResult) {
        log.info("MemberController join 메서드 실행");
        MemberRespDto userRespDto = memberService.create(memberSaveReqDto);
        return new ResponseEntity<>(new ResponseDto<>(1, "회원가입 성공", userRespDto), HttpStatus.CREATED);
    }

    @GetMapping("/{memberId}")
    public ResponseEntity<?> get(@PathVariable String memberId) {
        log.info("MemberController getByMemberId 메서드 실행");
        Member member = memberService.getByMemberId(memberId);
        return new ResponseEntity<>(new ResponseDto<>(1, "유저 정보 조회 성공", MemberRespDto.from(member)), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> list() {
        log.info("getAllMembers : 모든 사용자 조회 메서드 실행");
        List<Member> allMembers = memberService.list();
        List<MemberRespDto> memberRespDtos = allMembers.stream().map(MemberRespDto::from).toList();
        return new ResponseEntity<>(new ResponseDto<>(1, "사용자 목록 조회 성공", memberRespDtos), HttpStatus.OK);
    }

    @Deprecated
    @PutMapping("/update")
    public ResponseEntity<?> update(@RequestBody @Valid MemberUpdateReqDto MemberUpdateReqDto) {
        log.info("MemberController update 메서드 실행 : {}", MemberUpdateReqDto.toString());
        MemberRespDto userRespDto = memberService.update(MemberUpdateReqDto.getMemberId(), MemberUpdateReqDto);
        return new ResponseEntity<>(new ResponseDto<>(1, "회원정보 수정 성공", userRespDto), HttpStatus.OK);
    }

    @Deprecated
    @DeleteMapping("/delete/{memberId}")
    public ResponseEntity<?> delete(@PathVariable String memberId, @RequestParam("password") String password) {
        log.info("MemberController delete( 메서드 실행 : {}", memberId);
        memberService.delete(memberId, password);
        return new ResponseEntity<>(new ResponseDto<>(1, "회원 삭제 성공", null), HttpStatus.OK);
    }

//    @GetMapping("/{memberId}/verify")
//    public ResponseEntity<Void> verifyEmail(
//            @PathVariable String memberId,
//            @RequestParam String certificationCode) {
//        memberService.verifyEmail(memberId, certificationCode);
//        return ResponseEntity.status(HttpStatus.OK)
//                .location(URI.create("http://localhost:3000"))
//                .build();
//    }
}
