package javawizzards.shopngo.repositories;

import javawizzards.shopngo.entities.Cart;
import javawizzards.shopngo.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CartRepository extends JpaRepository<Cart, UUID> {
    Optional<Cart> findByUser(User user);
    Optional<Cart> findByUserId(UUID userId);

    @Query("SELECT c FROM Cart c WHERE c.expiryDate < :expiryDate")
    List<Cart> findByExpiryDateBefore(@Param("expiryDate") LocalDateTime expiryDate);

    @Query("SELECT c FROM Cart c WHERE c.lastUpdated < :lastUpdated")
    List<Cart> findInactiveCarts(@Param("lastUpdated") LocalDateTime lastUpdated);
}