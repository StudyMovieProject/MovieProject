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
    private String userid;
    private String title;
    private String content;
    private String theater;
    private int cate;

    public BoardRespDto(Board entity) {
        this.seq = entity.getSeq();
        this.userid = entity.getUserid();
        this.title = entity.getTitle();
        this.content = entity.getContent();
        this.theater = entity.getTheater();
        this.cate = entity.getCate();
        //this.createDate = entity.createDate();

    }

}