package taborski.kleczek.chat.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.validator.internal.util.logging.LoggerFactory;
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

@CrossOrigin(origins = "http://192.168.99.100:3000")
@RestController
public class ChatController {
    private final Logger logger = LogManager.getLogger(this.getClass());
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
        History history = new History();
        history.setMessage(message.getContent());
        history.setSenderId(message.getSenderId());
        history.setType(message.getType().name());
        logger.info("Message with the contanet =[" + message.getContent() + "]");
        historyAppClient.addHistory(history);
        return message;
    }

    @GetMapping("api/history")
    public ResponseEntity getChatHistory() {
        List<History> history = historyAppClient.getHistory();
            logger.info("Get History");
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
        try {
            User logged = userAppClient.loginUser(user);
            logger.info("Logged with the name =[" + logged.getName() + "]");


            return ResponseEntity.ok(logged);
        } catch (Exception e) {
            logger.error("Logged failed with the name =[" + user.getName() + "]");

            return ResponseEntity.badRequest().body("Bad credentials!");
        }
    }

    @PostMapping("api/register")
    ResponseEntity registerUser(@RequestBody User user) {
        try {
            User registerUser = userAppClient.registerUser(user);
            logger.info("Register with the name =[" + registerUser.getName() + "]");

            return ResponseEntity.ok(registerUser);
        } catch (Exception e) {
            logger.error("Register failed with the name =[" + user.getName() + "]");

            return ResponseEntity.badRequest().body("User already exists!");
        }
    }
}