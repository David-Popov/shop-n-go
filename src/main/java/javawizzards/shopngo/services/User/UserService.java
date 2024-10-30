package javawizzards.shopngo.services.User;

import javawizzards.shopngo.dtos.Auth.LoginDto;
import javawizzards.shopngo.dtos.Auth.RegisterDto;
import javawizzards.shopngo.entities.User;
import org.hibernate.type.descriptor.converter.spi.JpaAttributeConverter;

import java.util.List;

public interface UserService {
    User findByUsername(String username);
    User findByEmail(String email);
    List<User> findAll();
    User CreateUser(RegisterDto user);
    User UpdateUser(User user);
    User DeleteUser(String googleId);
    String loginUser(LoginDto loginDetails);
}
