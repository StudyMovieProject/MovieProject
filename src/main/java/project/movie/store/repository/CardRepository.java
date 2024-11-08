package project.movie.store.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.movie.store.domain.Card;

public interface CardRepository extends JpaRepository<Card, Integer> {
}
