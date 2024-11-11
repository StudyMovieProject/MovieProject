package project.movie.board.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BoardDto {
    private int seq;
    private String memberId;
    private String title;
    private String content;
    private String theater;
    private int cate;

    public static BoardDto toDto(BoardRespDto board) {
        return new BoardDto(
                board.getSeq(),
                board.getMemberId(),
                board.getTitle(),
                board.getContent(),
                board.getTheater(),
                board.getCate());
    }


}
