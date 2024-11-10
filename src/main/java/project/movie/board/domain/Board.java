package project.movie.board.domain;

import jakarta.persistence.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import project.movie.board.dto.BoardReqDto;
import project.movie.common.domain.Base;
import project.movie.member.domain.Member;
import project.movie.member.domain.MemberRole;
import project.movie.member.domain.MemberStatus;

@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Entity
@Getter
@Slf4j
@Table(name = "board")
public class Board extends Base {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int seq;

    @Column(name="title")
    private String title;

    @Column(name="content")
    private String content;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name="userid")
    private Member userid;

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
        this.title = requestsDto.getTitle();
        this.content = requestsDto.getContent();
        this.userid = requestsDto.getUserid();
        this.theater = requestsDto.getTheater();
        this.cate = requestsDto.getCate();
    }


}