package taborski.kleczek.chat.controller;

import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import taborski.kleczek.chat.model.ChatHistoryDao;
import taborski.kleczek.chat.model.Message;

import java.util.List;
import java.util.Map;


@Slf4j
@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class ChatController {
    @Autowired
    private ChatHistoryDao chatHistoryDao;

    @MessageMapping("/all")
    @SendTo("/topic/all")
    public Map<String, String> post(@Payload Map<String, String> message) {

        log.info("message: {}", message);
        message.put("timestamp", Long.toString(System.currentTimeMillis()));
        chatHistoryDao.save(message);
        return message;
    }


    @RequestMapping("/history")
    public List<Map<String, String>> getChatHistory() {
        return chatHistoryDao.get();
    }
}