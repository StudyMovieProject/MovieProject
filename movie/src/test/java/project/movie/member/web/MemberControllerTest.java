package project.movie.member.web;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import project.movie.auth.jwt.util.JWTUtil;
import project.movie.common.dto.DummyObject;
import project.movie.member.domain.MemberRole;
import project.movie.member.domain.MemberStatus;
import project.movie.member.dto.MemberSaveReqDto;
import project.movie.member.dto.MemberUpdateReqDto;
import project.movie.member.repository.MemberJpaRepository;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@SqlGroup({
        @Sql(value = "/sql/member-service-test-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
        @Sql(value = "/sql/delete-all-data.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
})
class MemberControllerTest extends DummyObject {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper om;
    @Autowired
    private MemberJpaRepository memberRepository;
    @Autowired
    EntityManager em;
    @Autowired
    JWTUtil jwtUtil;

    @BeforeEach
    public void setUp() {
        memberRepository.save(newMember("net1506", "이종욱"));
        em.clear();
    }

    @Test
    @DisplayName("회원가입 정상 테스트")
    public void join_success_test() throws Exception {
        // given
        MemberSaveReqDto memberSaveReqDto = new MemberSaveReqDto();
        memberSaveReqDto.setMemberId("jongwook2");
        memberSaveReqDto.setPassword("123456");
        memberSaveReqDto.setUsername("jongwooktwo");
        memberSaveReqDto.setAddress("Seoul");
        memberSaveReqDto.setEmail("net1506@naver.com");
        memberSaveReqDto.setFullname("leejongwook");
        memberSaveReqDto.setRole(MemberRole.CUSTOMER.toString());
        memberSaveReqDto.setTel("01023231222");
        memberSaveReqDto.setDetailAddress("89-8");
        memberSaveReqDto.setZipcode("417-840");
        memberSaveReqDto.setStatus(MemberStatus.PENDING);

        String requestBody = om.writeValueAsString(memberSaveReqDto);

        // when
        ResultActions resultActions = mockMvc.perform(post("/api/members/join")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody));
        // then
        resultActions.andExpect(status().isCreated());
    }

    @Test
    @DisplayName("회원가입 중복 테스트")
    public void join_duplicate_test() throws Exception {
        // given
        MemberSaveReqDto memberSaveReqDto = new MemberSaveReqDto();
        memberSaveReqDto.setMemberId("net1506");
        memberSaveReqDto.setPassword("123456");
        memberSaveReqDto.setUsername("jongwook");
        memberSaveReqDto.setAddress("Seoul");
        memberSaveReqDto.setEmail("net1506@naver.com");
        memberSaveReqDto.setFullname("leejongwook");
        memberSaveReqDto.setRole(MemberRole.CUSTOMER.toString());
        memberSaveReqDto.setTel("01023231222");
        memberSaveReqDto.setDetailAddress("89-8");
        memberSaveReqDto.setZipcode("417-840");
        memberSaveReqDto.setStatus(MemberStatus.PENDING);

        String requestBody = om.writeValueAsString(memberSaveReqDto);

        // when
        ResultActions resultActions = mockMvc.perform(post("/api/members/join")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody));
        // then
        resultActions.andExpect(status().isBadRequest());
        resultActions.andExpect(jsonPath("$.msg").value("동일한 아이디가 존재합니다."));
    }

    @Test
    @DisplayName("회원가입 파라미터 유효성 에러 테스트")
    public void join_parameter_valid_error_test() throws Exception {
        // given
        MemberSaveReqDto memberSaveReqDto = new MemberSaveReqDto();
        memberSaveReqDto.setMemberId("jongwook1234");
        memberSaveReqDto.setPassword("");
        memberSaveReqDto.setUsername("jongwook");
        memberSaveReqDto.setAddress("Seoul");
        memberSaveReqDto.setEmail("net1506");
        memberSaveReqDto.setFullname("leejongwook");
        memberSaveReqDto.setRole(MemberRole.CUSTOMER.toString());
        memberSaveReqDto.setTel("01023231222111");
        memberSaveReqDto.setDetailAddress("89-8");
        memberSaveReqDto.setZipcode("417-840");
        memberSaveReqDto.setStatus(MemberStatus.PENDING);

        String requestBody = om.writeValueAsString(memberSaveReqDto);

        // when
        ResultActions resultActions = mockMvc.perform(post("/api/members/join")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody));
        // then
        resultActions.andExpect(status().isBadRequest());
        resultActions.andExpect(jsonPath("$.data.email").value("이메일 형식으로 작성해주세요"));
        resultActions.andExpect(jsonPath("$.data.password").value("size must be between 4 and 20"));
        resultActions.andExpect(jsonPath("$.data.tel").value("연락처 형식으로 작성해주세요"));
    }

    @Test
    @DisplayName("멤버 수정 테스트")
    public void member_update_test() throws Exception {

        // given
        String memberId = "net1506";
        MemberUpdateReqDto memberUpdateReqDto = new MemberUpdateReqDto();
        memberUpdateReqDto.setMemberId(memberId);
        memberUpdateReqDto.setPassword("newPassword123");
        memberUpdateReqDto.setUsername("홍길동");
        memberUpdateReqDto.setEmail("honggildong@example.com");
        memberUpdateReqDto.setTel("01012345678");
        memberUpdateReqDto.setZipcode("12345");
        memberUpdateReqDto.setAddress("서울");
        memberUpdateReqDto.setDetailAddress("78-7");
        memberUpdateReqDto.setFullname("홍길동");
        memberUpdateReqDto.setRole(MemberRole.CUSTOMER); // 또는 MemberGrade.ADMIN
        memberUpdateReqDto.setStatus(MemberStatus.ACTIVE); // 필요에 따라 설정
        memberUpdateReqDto.setCertificationCode("certification-code-123");

        // when
        // then
        mockMvc.perform(put("/api/members/update/me")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(memberUpdateReqDto))
                        .header("Authorization", DummyObject.generateJwtToken(jwtUtil)))

                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("멤버 삭제 테스트")
    public void user_delete_test() throws Exception {
        // given
        // when
        // then
        mockMvc.perform(delete("/api/members/delete/me")
                .header("Authorization", DummyObject.generateJwtToken(jwtUtil)))
                .andExpect(status().isOk());
    }

}