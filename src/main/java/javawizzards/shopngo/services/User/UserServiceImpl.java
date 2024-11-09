package javawizzards.shopngo.services.User;

import javawizzards.shopngo.Util.JwtUtil;
import javawizzards.shopngo.dtos.Auth.LoginDto;
import javawizzards.shopngo.dtos.Auth.RegisterDto;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import javawizzards.shopngo.entities.User;
import javawizzards.shopngo.repositories.UserRepository;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    public UserServiceImpl(UserRepository userRepository, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public User findByUsername(String username) {
        try{
            if (username.isEmpty()){
                return null;
            }

            var user = this.userRepository.findByUsername(username);

            if (user == null) {
                return null;
            }

            return user;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public User findByEmail(String email) {
        try{
            if (email.isEmpty()){
                return null;
            }

            var user = this.userRepository.findByEmail(email);

            if (user == null) {
                return null;
            }

            return user;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public List<User> findAll() {
        try{
            return this.userRepository.findAll();
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public User CreateUser(RegisterDto user) {
        try{
            if (!this.IsUserValid(user)) {
                return null;
            }

            User newUser = new User();

            newUser.setGoogleId(user.getGoogleId());
            newUser.setEmail(user.getEmail());
            newUser.setUsername(user.getUsername());
            newUser.setPictureUrl(user.getPictureUrl());
            newUser.setPhoneNumber(user.getPhoneNumber());

            this.userRepository.save(newUser);

            return newUser;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public User UpdateUser(User user) {
        try{
            if (!this.IsUserValid(user)) {
                return null;
            }

            var userForUpdate = this.userRepository.findByEmail(user.getEmail());

            userForUpdate.setEmail(user.getEmail());
            userForUpdate.setUsername(user.getUsername());
            userForUpdate.setId(user.getId());
            userForUpdate.setGoogleId(user.getGoogleId());
            userForUpdate.setPhoneNumber(user.getPhoneNumber());
            userForUpdate.setPictureUrl(user.getPictureUrl());

            this.userRepository.save(userForUpdate);

            return userForUpdate;
        }catch (Exception e) {
            throw e;
        }
    }

    @Override
    public User DeleteUser(String googleId) {
        try{
            if (googleId.isEmpty()){
                return null;
            }

            var user = this.userRepository.findByGoogleId(googleId);

            this.userRepository.delete(user);

            return user;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public String loginUser(LoginDto loginDetails) {
        try{
            if (!this.IsUserValid(loginDetails)) {
                return null;
            }

            var user = this.userRepository.findByGoogleId(loginDetails.getGoogleId());

            if (user == null){
                return null;
            }

            return this.jwtUtil.generateToken(user);
        } catch (Exception e) {
            throw e;
        }
    }

    private boolean IsUserValid(Object user){
        if (user == null) {
            return false;
        }

        Field[] fields = user.getClass().getDeclaredFields();

        for (Field field : fields) {
            field.setAccessible(true);
            try {
                Object value = field.get(user);
                if (value == null || (value instanceof String && ((String) value).isEmpty())) {
                    return false;
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        return true;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = findByEmail(email);

        if (user == null) {
            throw new UsernameNotFoundException("User not found with username or email: " + email);
        }
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                new ArrayList<>()
        );
    }
}
