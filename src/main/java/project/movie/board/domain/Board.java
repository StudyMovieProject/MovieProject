package project.movie.board.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import project.movie.board.dto.BoardReqDto;
import project.movie.common.domain.Base;
import project.movie.member.domain.Member;

@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Entity
@Getter
@Slf4j
@ToString
@Table(name = "board")
public class Board extends Base {
    @Id
    @GeneratedValue
    private int seq;

    @Column(name="title")
    private String title;

    @Column(name="content")
    private String content;

    @Column(name="userid")
    private String userid;

    @Column(name="theater")
    private String theater;

    @Column(name="cate")
    private int cate;

    public Board(BoardReqDto requestsDto) {
        this.seq = requestsDto.getSeq();
        this.title = requestsDto.getTitle();
        this.content = requestsDto.getContent();
        this.userid = requestsDto.getUserid();
        this.theater = requestsDto.getTheater();
        this.cate = requestsDto.getCate();
    }

    //업데이트
    public void update(BoardReqDto requestsDto) {
        this.seq = requestsDto.getSeq();
        this.title = requestsDto.getTitle();
        this.content = requestsDto.getContent();
        this.userid = requestsDto.getUserid();
        this.theater = requestsDto.getTheater();
        this.cate = requestsDto.getCate();
    }
}