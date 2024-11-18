package project.movie.theater.web;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.TestExecutionEvent;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@SqlGroup({
    @Sql(value = "/sql/member-service-test-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
    @Sql(value = "/sql/delete-member-data.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
})
class TheaterControllerTest {

    @Autowired
    private MockMvc mockMvc;

    // END 는 자동 완성 후 커서 위치
    @Test
    @WithUserDetails(value = "net1506", setupBefore = TestExecutionEvent.TEST_EXECUTION)
    @DisplayName("영화관 목록 조회 테스트")
    public void listTheater_test() throws Exception {
        // given
        // when
        ResultActions resultActions = mockMvc.perform(get("/api/theaters"));
        resultActions.andExpect(status().isOk());

        // then
    }

}