package taborski.kleczek.chat.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import taborski.kleczek.chat.entity.History;
import taborski.kleczek.chat.entity.User;

import java.util.ArrayList;
import java.util.List;

@FeignClient(name = "historyapp", fallback = IHistoryAppClient.HistoryAppClientFallback.class)
public interface IHistoryAppClient {
    @PostMapping("/api/history")
    History addHistory(@RequestBody History history);

    @GetMapping("/api/history")
    List<History> getHistory();

    @Slf4j
    @Component
    public static class HistoryAppClientFallback implements IHistoryAppClient {
        private long ID = 0;
        private List<History> history = new ArrayList<>();

        @Override
        public History addHistory(History history) {
            log.info("HistoryAppClientFallback addHistory");

            history.setId(ID);
            ID++;

            return history;
        }

        @Override
        public List<History> getHistory() {
            log.info("HistoryAppClientFallback getHistory");

            return history;
        }
    }
}

