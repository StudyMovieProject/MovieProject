package project.movie.member.single;

import org.junit.jupiter.api.Test;
import project.movie.member.domain.MemberRole;

public class SIngleTest {

    // END 는 자동 완성 후 커서 위치
    @Test
    public void grade_value_test() throws Exception {
        // given
        String gradeValue = "CUSTOMER";

        // when
        MemberRole memberRole = MemberRole.valueOf(gradeValue);
        System.out.println(memberRole);
        System.out.println(memberRole.getValue());

        // then
    }

}
