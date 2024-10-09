package javawizzards.shopngo.repositories;

import javawizzards.shopngo.entities.CartItem;
import javawizzards.shopngo.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    List<CartItem> findByUser(User user);
}
