package taborski.kleczek.chat.controller;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import com.netflix.discovery.shared.Application;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import taborski.kleczek.chat.model.ChatHistoryDao;
import taborski.kleczek.chat.model.Message;

import java.util.List;


@Slf4j
@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class ChatController {

    @Autowired
    private ChatHistoryDao chatHistoryDao;

    @Autowired
    private IUserAppClient userAppClient;

    @MessageMapping("/all")
    @MessageExceptionHandler
    @SendTo("/topic/all")
    public Message post(@Payload Message message, SimpMessageHeaderAccessor headerAccessor) {
        headerAccessor.getSessionAttributes().put("username", message.getSender());
        log.info("message: from {}, text {}", message.getSender(), message.getContent());
        chatHistoryDao.save(message);
        return message;
    }


    @RequestMapping("/history")
    public List<Message> getChatHistory() {
        return chatHistoryDao.get();
    }

    @RequestMapping("/api/greetings")
    public String getGreetings() {
        return userAppClient.greeting();
    }

}