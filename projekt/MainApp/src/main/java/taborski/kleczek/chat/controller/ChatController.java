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
import taborski.kleczek.chat.entity.History;
import taborski.kleczek.chat.entity.User;
import taborski.kleczek.chat.model.Message;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@CrossOrigin(origins = "http://192.168.99.100:3000")
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
        headerAccessor.getSessionAttributes().put("id", message.getSenderId());
        log.info("message: from {}, text {}, senderId {}", message.getSender(), message.getContent(), message.getSenderId());
        log.info("Adding message to history");
        History history = new History();
        history.setMessage(message.getContent());
        history.setSenderId(message.getSenderId());
        history.setType(message.getType().name());
        historyAppClient.addHistory(history);
        return message;
    }

    @GetMapping("api/history")
    public ResponseEntity getChatHistory() {
        log.info("Fetching full history");
        List<History> history = historyAppClient.getHistory();
        return ResponseEntity.ok(history.stream().map(el -> {
            Message message = new Message();
            message.setContent(el.getMessage());
            User sender = userAppClient.getUser(el.getSenderId());

            message.setSender(sender.getName());
            message.setSenderId(el.getSenderId());
            message.setType(Message.Type.valueOf(el.getType()));

            return message;
        }).collect(Collectors.toList()));
    }

    @PostMapping("api/login")
    public ResponseEntity loginUser(@RequestBody User user) {
        log.info("login user {}", user.getName());
        try {
            User logged = userAppClient.loginUser(user);
            log.info("login success {}", logged.getName());

            return ResponseEntity.ok(logged);
        } catch (Exception e) {
            log.error("login error = {}", e.getMessage());
            return ResponseEntity.badRequest().body("Bad credentials!");
        }
    }

    public ResponseEntity defaultLogin() {
        return ResponseEntity.badRequest().body("Login service is down, try again later.");
    }

    @PostMapping("api/register")
    ResponseEntity registerUser(@RequestBody User user) {
        log.info("register user = {}", user.getName());
        try {
            User registerUser = userAppClient.registerUser(user);
            log.info("register success {}", registerUser.getName());

            return ResponseEntity.ok(registerUser);
        } catch (Exception e) {
            log.error("register error = {}", e.getMessage());
            return ResponseEntity.badRequest().body("User already exists!");
        }
    }
}