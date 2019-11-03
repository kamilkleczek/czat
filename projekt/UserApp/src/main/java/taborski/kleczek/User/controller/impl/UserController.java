package taborski.kleczek.User.controller.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import taborski.kleczek.User.exceptions.BadCredentialsException;
import taborski.kleczek.User.exceptions.ResourceAlreadyExistsException;
import taborski.kleczek.User.exceptions.ResourceNotFoundException;
import taborski.kleczek.User.model.User;
import taborski.kleczek.User.service.IUserService;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/api/user")
public class UserController {
    private final IUserService userService;

    @Autowired
    public UserController(IUserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public User loginUser(@Valid @RequestBody User user) {
        log.info("login user {}", user.getName());
        try {
            User logged = userService.loginUser(user);
            log.info("User found id = {}", logged.getId());

            return logged;
        } catch (BadCredentialsException exc) {
            log.error("User name = {} bad credentials", user.getName());
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Bad credentials!", exc);
        }
    }

    @PostMapping("/register")
    public User registerUser(@Valid @RequestBody User user) {
        log.info("register user {}", user.getName());
        try {
            User newUser = userService.createUser(user);
            log.info("User registered in id = {}", newUser.getId());

            return newUser;
        } catch (ResourceAlreadyExistsException exc) {
            log.error("User name = {} already exists ", user.getName());
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "User already exists!", exc);
        }
    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable(name = "id") @Valid Long userId) {
        log.info("get user id = {}", userId);
        try {
            User user = userService.getUserById(userId);
            log.info("User found id = {}", userId);

            return user;
        } catch (ResourceNotFoundException exc) {
            log.error("User id = {} not found", userId);
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "User not found!", exc);
        }
    }
}
