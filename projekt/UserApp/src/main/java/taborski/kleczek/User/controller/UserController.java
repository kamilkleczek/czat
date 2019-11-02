package taborski.kleczek.User.controller;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import taborski.kleczek.User.exceptions.BadCredentialsException;
import taborski.kleczek.User.exceptions.ResourceAlreadyExistsException;
import taborski.kleczek.User.exceptions.ResourceNotFoundException;
import taborski.kleczek.User.model.User;
import taborski.kleczek.User.service.IUserService;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/user")
public class UserController {
    private final IUserService userService;

    @Autowired
    public UserController(IUserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity loginUser(@Valid @RequestBody User user ) {
        try {
        User loggedUser = userService.loginUser(user);
        return ResponseEntity.ok(loggedUser);
        } catch (BadCredentialsException e) {
            return ResponseEntity.badRequest().body("Bad Credentials");
        }
    }

    @PostMapping("/register")
    public ResponseEntity registerUser(@Valid @RequestBody User user ) {
        try {
            User newUser = userService.createUser(user);
            return ResponseEntity.ok(newUser);
        } catch (ResourceAlreadyExistsException e) {
            return ResponseEntity.badRequest().body("User with name " + user.getName() + " already exists!");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity getUser(@PathVariable(name = "id") @Valid Long userId) {
        try {
            User user= userService.getUserById(userId);
            return ResponseEntity.ok(user);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.badRequest().body("User not found!");
        }
    }


}
