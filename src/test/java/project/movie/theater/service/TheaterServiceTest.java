package project.movie.theater.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import project.movie.theater.dto.TheaterResDto;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class TheaterServiceTest {

    @Autowired
    TheaterService theaterService;

    // END 는 자동 완성 후 커서 위치
    @Test
    @DisplayName("DB에 저장된 영화관 목록이 존재한다.")
    public void theater_print_test() throws Exception {
        // given
        // when
        List<TheaterResDto> list = theaterService.list();

        // then
        assertThat(list).hasSizeGreaterThan(0);
    }

}