package taborski.kleczek.chat.model;

import lombok.Data;

@Data
public class Message {
    private Type type;
    private String content;
    private String sender;

    public enum Type {
        CHAT,
        JOIN,
        LEAVE
    }
}
