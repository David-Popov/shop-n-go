package javawizzards.shopngo.services.User;

import org.springframework.stereotype.Service;
import javawizzards.shopngo.entities.User;
import javawizzards.shopngo.repositories.UserRepository;
import org.springframework.security.oauth2.core.user.OAuth2User;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void processOAuthPostLogin(OAuth2User oAuth2User) {
        String googleId = oAuth2User.getAttribute("sub");

        User existingUser = userRepository.findByGoogleId(googleId);
        if (existingUser == null) {
            User newUser = new User();
            newUser.setGoogleId(googleId);
            newUser.setName(oAuth2User.getAttribute("name"));
            newUser.setEmail(oAuth2User.getAttribute("email"));

            userRepository.save(newUser);
        }
    }
}
