package project.movie.member.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.transaction.annotation.Transactional;
import project.movie.common.dto.DummyObject;
import project.movie.common.handler.exception.CustomApiException;
import project.movie.member.domain.Member;
import project.movie.member.domain.MemberStatus;
import project.movie.member.dto.MemberRespDto;
import project.movie.member.dto.MemberSaveReqDto;
import project.movie.member.dto.MemberUpdateReqDto;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ActiveProfiles("test")
@SpringBootTest
@ExtendWith(MockitoExtension.class)
@SqlGroup({
        @Sql(value = "/sql/member-service-test-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
        @Sql(value = "/sql/delete-member-data.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
})
class MemberServiceTest extends DummyObject {

    @Autowired
    MemberService memberService;

    // END 는 자동 완성 후 커서 위치
    @Test
    @DisplayName("사용자를 생성한다.")
    public void create_test() throws Exception {
        // given
        Member member = newMockMember("net1510", "net1510", "leejongwook");

        // when
        MemberRespDto memberRespDto = memberService.create(new MemberSaveReqDto(member));

        // then
        assertNotNull(memberRespDto);
        assertThat(memberRespDto.getStatus()).isEqualTo(MemberStatus.ACTIVE); // 초기 회원 가입시 false 가 기본 값
        assertThat(memberRespDto.getMemberId()).isEqualTo("net1510");
        assertThat(memberRespDto.getFullname()).isEqualTo("leejongwook");
    }

    @Test
    @DisplayName("이미 저장된 유저로 회원 가입시 에러를 발생시킨다.")
    public void duplicateMember_test() throws Exception {
        // given
        Member member1 = newMockMember("net1509", "lee", "leejongwook");
        Member member2 = newMockMember("net1509", "lee2", "leejongwook2");

        memberService.create(new MemberSaveReqDto(member1));

        // when
        // then
        assertThrows(CustomApiException.class, () -> memberService.create(new MemberSaveReqDto(member2)));
    }

    // END 는 자동 완성 후 커서 위치
    @Test
    @DisplayName("사용자 조회 테스트")
    public void getByMemberId_test() throws Exception {
        // given
        String memberId = "net1506";

        // when
        MemberRespDto findMemberId = MemberRespDto.from(memberService.getByMemberId(memberId));

        // then
        assertThat(findMemberId.getMemberId()).isEqualTo(memberId);
        assertThat(findMemberId.getEmail()).isEqualTo(memberId + "@naver.com");
    }

    @Test
    @DisplayName("존재하지 않는 사용자를 조회시 에러가 발생한다.")
    public void getByMemberId_not_exist_test() throws Exception {
        // given
        String memberId = "net9999";

        // when
        // then
        assertThrows(CustomApiException.class, () -> memberService.getByMemberId(memberId));
    }

    // END 는 자동 완성 후 커서 위치
    @Test
    @DisplayName("유저 정보를 수정합니다.")
    public void update_test() throws Exception {
        // given
        String memberId = "net1506";

        Member udpateMember = newMockMember(memberId, "jongmin", "kimjongmin");

        // when
        MemberUpdateReqDto memberUpdateReqDto = MemberUpdateReqDto.builder()
                .member(udpateMember)
                .build();

        // then
        MemberRespDto updateResponse = memberService.update(memberId, memberUpdateReqDto);

        assertThat(updateResponse.getUsername()).isEqualTo("jongmin");
        assertThat(updateResponse.getFullname()).isEqualTo("kimjongmin");
    }

    // END 는 자동 완성 후 커서 위치
//    @Test
//    @DisplayName("멤버 삭제 테스트")
//    @Transactional
//    public void delete_test() throws Exception {
//        // given
//        String memberId = "net1506";
//
//        // when
//        Member byMemberId = memberService.getByMemberId(memberId);
//        System.out.println("삭제 전 확인차 출력 :" + byMemberId);
//        memberService.delete(memberId, "1234");
//
//        // then
//        Assertions.assertThrows(CustomApiException.class, () -> memberService.getByMemberId(memberId));
//    }

    @Test
    @DisplayName("존재하지 않는 사용자 삭제 시 에러 발생")
    public void delete_not_exist_user_test() throws Exception {
        // given
        String memberId = "net1507";

        // when
        // then
        CustomApiException customApiException = assertThrows(CustomApiException.class, () -> memberService.delete(memberId, "1234"));
        assertThat(customApiException.getMessage()).isEqualTo("net1507 는 존재하지 않는 사용자 입니다");
    }

}