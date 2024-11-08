package project.movie.store.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.movie.store.domain.Cart;

public interface CartRepository extends JpaRepository<Cart, Integer> {
}
