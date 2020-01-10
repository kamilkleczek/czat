package taborski.kleczek.chat.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import taborski.kleczek.chat.entity.User;

import java.util.ArrayList;
import java.util.List;


@FeignClient(name = "userapp", fallback = IUserAppClient.UserAppClientFallback.class)
public interface IUserAppClient {
    @PostMapping("/api/user/login")
    User loginUser(@RequestBody User user);

    @PostMapping("/api/user/register")
    User registerUser(@RequestBody User user);

    @GetMapping("/api/user/{id}")
    User getUser(@PathVariable(name = "id") Long userId);

    @Slf4j
    @Component
    public static class UserAppClientFallback implements IUserAppClient {
        private long ID = 0;
        private List<User> users = new ArrayList<>();

        @Override
        public User loginUser(User newUser) {
            log.info("UserAppClientFallback loginUser");

            return users.stream()
                    .filter(user ->
                            user.getPassword().equals(newUser.getPassword()) &&
                                    user.getName().equals(newUser.getName()))
                    .findAny()
                    .orElse(null);
        }

        @Override
        public User registerUser(User user) {
            log.info("UserAppClientFallback registerUser");

            user.setId(ID);
            users.add(user);
            ID++;

            return user;
        }

        @Override
        public User getUser(Long userId) {
            log.info("UserAppClientFallback getUser");

            return users.stream()
                    .filter(user -> userId == user.getId())
                    .findAny()
                    .orElse(null);
        }
    }
}

