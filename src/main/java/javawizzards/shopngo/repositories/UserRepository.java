package javawizzards.shopngo.repositories;

import javawizzards.shopngo.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
//    User findByGoogleId(String googleId);
}
