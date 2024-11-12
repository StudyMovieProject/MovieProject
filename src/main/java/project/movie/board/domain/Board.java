package project.movie.board.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import project.movie.board.dto.BoardReqDto;
import project.movie.common.domain.Base;
import project.movie.member.domain.Member;
import project.movie.member.domain.MemberRole;
import project.movie.member.domain.MemberStatus;
import project.movie.member.repository.MemberRepository;

@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Entity
@Getter
@Slf4j
@Table(name = "board")
public class Board extends Base {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "게시물 번호", required = true, example = "1")
    private int seq;

    @Column(name="title")
    @Schema(description = "제목", required = true, example = "문의드립니다")
    private String title;

    @Column(name="content")
    @Schema(description = "내용", required = true, example = "00개봉일 언제인가요")
    private String content;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name="member_id")
    @Schema(description = "유저 아이디", required = true, example = "jungin2")
    private Member member;

    @Column(name="theater")
    @Schema(description = "극장", required = true, example = "신도림")
    private String theater;

    @Column(name="cate")
    @Schema(description = "문의유형", required = true, example = "1:문의|2:건의|3:칭찬|4:불만|5:기타")
    private int cate;

    public Board(BoardReqDto requestsDto, MemberRepository memberRepository){
        this.seq = requestsDto.getSeq();
        this.title = requestsDto.getTitle();
        this.content = requestsDto.getContent();
        this.member = memberRepository.findByMemberId(requestsDto.getMember())
                .orElseThrow(() -> new RuntimeException("Member not found"));
        this.theater = requestsDto.getTheater();
        this.cate = requestsDto.getCate();
    }

    //업데이트
    public void update(BoardReqDto requestsDto) {
        this.title = requestsDto.getTitle();
        this.content = requestsDto.getContent();
        //this.member = requestsDto.getMember()
        this.theater = requestsDto.getTheater();
        this.cate = requestsDto.getCate();
    }


}