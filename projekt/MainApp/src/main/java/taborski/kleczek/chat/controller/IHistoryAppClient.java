package taborski.kleczek.chat.controller;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import taborski.kleczek.chat.entity.History;
import taborski.kleczek.chat.entity.User;

import java.util.List;

@FeignClient("historyapp")
public interface IHistoryAppClient {
    @PostMapping("/api/history")
    History addHistory(@RequestBody History history);

    @GetMapping("/api/history")
    List<History> getHistory();
}

