package project.movie.board.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.movie.board.domain.Board;
import project.movie.board.dto.BoardDto;
import project.movie.board.dto.BoardReqDto;
import project.movie.board.dto.BoardRespDto;
import project.movie.board.repository.BoardJpaRepository;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class BoardService {
    private final BoardJpaRepository boardRepository;

    // 전체 게시물 조회
    @Transactional(readOnly = true)
    public  List<BoardDto> getLists() {
        List<Board> boards = boardRepository.findAll();
        List<BoardDto> boardDtos = new ArrayList<>();
        boards.forEach(s -> boardDtos.add(BoardDto.toDto(s)));
        return boardDtos;
    }
    //선택한 게시물 조회
    @Transactional
    public BoardRespDto getList(int id) {
        return boardRepository.findById(id).map(BoardRespDto::new).orElseThrow(
                () -> new IllegalArgumentException("선택한 게시물이 존재하지 않습니다.")
        );
    }
    // 게시물 작성
    @Transactional
    public BoardRespDto writeList(BoardReqDto requestsDto) {
        Board board = new Board(requestsDto);
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
