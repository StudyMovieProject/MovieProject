package project.movie.board.dto;

import lombok.*;
import project.movie.board.domain.Board;
import project.movie.member.domain.Member;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString

public class BoardRespDto {
    private int seq;
    private String title;
    private String content;
    private String theater;
    private int cate;
    private String memberId;

    public BoardRespDto(Board board) {

        this.seq = board.getSeq();
        this.title = board.getTitle();
        this.content = board.getContent();
        this.theater = board.getTheater();
        this.cate = board.getCate();
        this.memberId = board.getMember().getMemberId();
    }

    public BoardRespDto(BoardRespDto boardRespDto) {
    }


}