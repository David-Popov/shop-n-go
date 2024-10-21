package javawizzards.shopngo.repositories;

import javawizzards.shopngo.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    User findByGoogleId(String googleId);
    User findByEmail(String email);
    User findByUsername(String username);
}
