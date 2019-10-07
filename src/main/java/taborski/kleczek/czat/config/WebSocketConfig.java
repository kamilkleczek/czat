package taborski.kleczek.czat.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

/*
* Configuracja websocketow
* klienci moga sie podlaczac za pomoca /ws endpoint
* */
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
    //STOMP = Simple Text Oriented Messaging Protocol
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // WithSockJs zapewnia obsluge jezeli przegladarka natywnie nie obsluguje WebSocketow
        registry.addEndpoint("/ws").withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // Wiadomosci  wysylane na ten endpoint  sa przekazywane do message handling methods
        registry.setApplicationDestinationPrefixes("/app");
        // Wiadomosci wysylane na ten endpoint sa przekazywane do Brokera
        registry.enableSimpleBroker("/topic");
    }
}
