package project.movie.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.movie.board.domain.Board;
import project.movie.member.domain.Member;

import java.util.List;
import java.util.Optional;

public interface BoardJpaRepository extends JpaRepository<Board, Integer> {
    List<Board> findByUserid(String userid);
}
