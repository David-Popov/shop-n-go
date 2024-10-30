package javawizzards.shopngo.repositories;

import javawizzards.shopngo.entities.Cart;
import javawizzards.shopngo.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CartRepository extends JpaRepository<Cart, UUID> {
    Optional<Cart> findByUser(User user);
    Optional<Cart> findByUserId(UUID userId);
}
