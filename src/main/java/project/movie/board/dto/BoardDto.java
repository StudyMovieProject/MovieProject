package project.movie.board.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.movie.board.domain.Board;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BoardDto {
    private int seq;
    private String userid;
    private String title;
    private String content;
    private String theater;
    private int cate;


    public static BoardDto toDto(Board board) {
        return new BoardDto(
                board.getSeq(),
                board.getUserid(),
                board.getTitle(),
                board.getContent(),
                board.getTheater(),
                board.getCate());
    }


}
