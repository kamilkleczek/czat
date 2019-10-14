package taborski.kleczek.chat.model;

import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Component
public class ChatHistoryDao {
    private List<Message> history = new LinkedList<>();
    // A simple cache for temporarily storing chat data


    public void save(Message message) {
        this.history.add(message);
    }

    public List<Message> get() {
        return this.history;
    }

}