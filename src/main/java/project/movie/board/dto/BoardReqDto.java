package project.movie.board.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import project.movie.member.domain.Member;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class BoardReqDto {
    @Schema(description = "게시물 번호", required = true, example = "1")
    private int seq;

    @Schema(description = "유저 아이디", required = true, example = "jungin2")
    private String member;

    @Schema(description = "제목", required = true, example = "문의드립니다")
    private String title;

    @Schema(description = "내용", required = true, example = "00개봉일 언제인가요")
    private String content;

    @Schema(description = "극장", required = true, example = "신도림")
    private String theater;

    @Schema(description = "문의유형", required = true, example = "1:문의|2:건의|3:칭찬|4:불만|5:기타")
    private int cate;


}
