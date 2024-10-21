package javawizzards.shopngo.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import javawizzards.shopngo.dtos.Auth.LoginDto;
import javawizzards.shopngo.dtos.Auth.RegisterDto;
import javawizzards.shopngo.dtos.Request;
import javawizzards.shopngo.services.User.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/create")
    public ResponseEntity<String> createUser(@RequestBody Request<RegisterDto> request, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String errorMessage = Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage();
            return ResponseEntity.badRequest().body(errorMessage);
        }

        try {
            var data = this.userService.CreateUser(request.getData());

            if (data == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Registration failed");
            }

            return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody Request<LoginDto> request, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String errorMessage = Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage();
            return ResponseEntity.badRequest().body(errorMessage);
        }

        try {
            String token = userService.loginUser(request.getData());

            if (token != null) {
                return ResponseEntity.ok(token);
            }

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not found");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
