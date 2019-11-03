package taborski.kleczek.chat.controller;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import taborski.kleczek.chat.entity.User;

import javax.validation.Valid;

@FeignClient("userapp")
public interface IUserAppClient {
    @PostMapping("/api/user/login")
    User loginUser(@RequestBody User user );

    @PostMapping("/api/user/register")
    User registerUser( @RequestBody User user );

    @GetMapping("/api/user/{id}")
    User getUser(@PathVariable(name = "id") Long userId);
}

