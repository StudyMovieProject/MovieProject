package project.movie.board.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.movie.board.domain.Board;
import project.movie.board.dto.BoardDto;
import project.movie.board.dto.BoardReqDto;
import project.movie.board.dto.BoardRespDto;
import project.movie.board.repository.BoardJpaRepository;
import project.movie.member.domain.Member;
import project.movie.member.repository.MemberRepository;
import project.movie.member.service.MemberService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class BoardService {
    private final BoardJpaRepository boardRepository;
    private final MemberRepository memberRepository;
    // 전체 게시물 조회
    @Transactional(readOnly = true)
    public  List<BoardRespDto> getLists() {
        return boardRepository.findAll().stream()
                .map(BoardRespDto::new)
                .collect(Collectors.toList());
    }
    //선택한 게시물 조회
    @Transactional
    public BoardRespDto getList(int id) {
        return boardRepository.findById(id).map(BoardRespDto::new).orElseThrow(
                () -> new IllegalArgumentException("선택한 게시물이 존재하지 않습니다.")
        );
    }
    //내가 작성한 게시물 조회
    @Transactional
    public List<BoardRespDto> getMyList(String userid) {
        return boardRepository.findByMember_MemberId(userid).stream()
                .map(BoardRespDto::new)
                .collect(Collectors.toList());
    }

    // 게시물 작성
    @Transactional
    public BoardRespDto writeList(BoardReqDto requestsDto,String userId) {
        Member member = memberRepository.findByMemberId(userId)
                .orElseThrow(() -> new RuntimeException("Member not found"));
        requestsDto.setMember(userId); // Set the userId in the request DTO
        Board board = new Board(requestsDto, memberRepository);
        boardRepository.save(board);
        return new BoardRespDto(board);
    }

    // 게시물 수정
    @Transactional
    public BoardRespDto updateList(int id, BoardReqDto requestsDto) throws Exception {
        Board board = boardRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("선택한 게시물이 존재하지 않습니다.")
        );

        board.update(requestsDto);
        return new BoardRespDto(board);
    }


    // 게시글 삭제
    @Transactional
    public BoardRespDto deleteList(int id) {
        Board board = boardRepository.findById(id).orElseThrow(() -> {
            return new IllegalArgumentException("선택한 게시물이 존재하지 않습니다.");
        });

        // 게시글이 있는 경우 삭제처리
        boardRepository.deleteById(id);

        return new BoardRespDto(board);
    }


}
