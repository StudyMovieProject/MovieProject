package project.movie.board.dto;

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

    private int seq;
    private Member userid;
    private String title;
    private String content;
    private String theater;
    private int cate;


}
