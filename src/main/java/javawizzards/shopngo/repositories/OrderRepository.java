package javawizzards.shopngo.repositories;

import javawizzards.shopngo.entities.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<Order, UUID> {
    Page<Order> findByUserIdOrderByCreatedAtDesc(UUID userId, Pageable pageable);
    List<Order> findByUserIdAndCreatedAtBetween(UUID userId, LocalDateTime startDate, LocalDateTime endDate);
    List<Order> findByStatus(Order.Status status);
    List<Order> findByUserId(UUID userId);
}