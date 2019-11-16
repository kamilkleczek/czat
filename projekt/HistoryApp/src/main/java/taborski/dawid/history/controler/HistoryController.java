package taborski.dawid.history.controler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import taborski.dawid.history.model.History;
import taborski.dawid.history.service.IHistoryService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/history")
public class HistoryController {
    private IHistoryService historyService;

    @Autowired
    public HistoryController(IHistoryService historyService) {
        this.historyService = historyService;
    }

    @GetMapping
    public List<History> getHisotry() {
        return this.historyService.getHistory();
    }


    @PostMapping
    public History addHisotry(@RequestBody History history) {
        return this.historyService.add(history);
    }
}
