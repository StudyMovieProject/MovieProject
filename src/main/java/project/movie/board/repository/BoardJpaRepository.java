package project.movie.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.movie.board.domain.Board;

public interface BoardJpaRepository extends JpaRepository<Board, Integer> {
}
