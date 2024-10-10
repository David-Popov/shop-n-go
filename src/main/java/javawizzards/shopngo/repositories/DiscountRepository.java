package javawizzards.shopngo.repositories;

import javawizzards.shopngo.entities.Discount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiscountRepository extends JpaRepository<Discount, Long> {
}
