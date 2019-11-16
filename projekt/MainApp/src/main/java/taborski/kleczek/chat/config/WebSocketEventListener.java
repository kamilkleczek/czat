package taborski.kleczek.chat.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import taborski.kleczek.chat.controller.IHistoryAppClient;
import taborski.kleczek.chat.entity.History;
import taborski.kleczek.chat.model.Message;

@Slf4j
@Component
public class WebSocketEventListener {

    @Autowired
    private SimpMessageSendingOperations messagingTemplate;

    @Autowired
    private IHistoryAppClient historyAppClient;

    @EventListener
    public void handleWebSocketConnectListener(SessionConnectedEvent event) {
        log.info("Received a new web socket connection");
    }

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        log.info("{}", headerAccessor);
        String username = (String) headerAccessor.getSessionAttributes().get("username");
        if (username != null) {
            Long senderId = (Long) headerAccessor.getSessionAttributes().get("id");
            log.info("User Disconnected : " + username);

            Message chatMessage = new Message();
            chatMessage.setType(Message.Type.LEAVE);
            chatMessage.setContent(username + " left!");
            chatMessage.setSender(username);
            chatMessage.setSenderId(senderId);
            History history = new History();
            history.setType(chatMessage.getType().name());
            history.setSenderId(senderId);
            history.setMessage(chatMessage.getContent());
            historyAppClient.addHistory(history);

            messagingTemplate.convertAndSend("/topic/all", chatMessage);
        }
    }
}