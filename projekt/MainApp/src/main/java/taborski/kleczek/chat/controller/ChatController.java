package taborski.kleczek.chat.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.web.bind.annotation.*;
import taborski.kleczek.chat.entity.User;
import taborski.kleczek.chat.model.Message;

import javax.validation.Valid;
import java.util.List;


@Slf4j
@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class ChatController {

    @Autowired
    private IUserAppClient userAppClient;

    @Autowired
    private IHistoryAppClient historyAppClient;


    @MessageMapping("/all")
    @MessageExceptionHandler
    @SendTo("/topic/all")
    public Message post(@Payload Message message, SimpMessageHeaderAccessor headerAccessor) {
        headerAccessor.getSessionAttributes().put("username", message.getSender());
        log.info("message: from {}, text {}", message.getSender(), message.getContent());
        // TODO save data from historyAppClient
//        chatHistoryDao.save(message);
        return message;
    }


    @GetMapping("/history")
    public List<Message> getChatHistory() {
//        return chatHistoryDao.get();
        // TODO get data from historyAppClient
        return null;
    }


    @PostMapping("api/login")
    public ResponseEntity loginUser(@RequestBody User user) {
        log.info("login user {}", user.getName());
        try {
            User logged = userAppClient.loginUser(user);
            return ResponseEntity.ok(logged);
        } catch (Exception e) {
            log.error("login error = {}", e.getMessage());
            return ResponseEntity.badRequest().body("Bad credentials!");
        }
    }

    @GetMapping("api/{id}")
    public ResponseEntity getUser(@PathVariable(name = "id") @Valid Long userId) {
        log.info("geting user id = {}", userId);
        try {
            User user = userAppClient.getUser(userId);
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            log.error("get user error = {}", e.getMessage());
            return ResponseEntity.badRequest().body("User not found!");
        }
    }

    @PostMapping("api/register")
    ResponseEntity registerUser(@RequestBody User user) {
        log.info("register user = {}", user.getName());
        try {
            User registerUser = userAppClient.registerUser(user);
            return ResponseEntity.ok(registerUser);
        } catch (Exception e) {
            log.error("register error = {}", e.getMessage());
            return ResponseEntity.badRequest().body("User already exists!");
        }
    }


}