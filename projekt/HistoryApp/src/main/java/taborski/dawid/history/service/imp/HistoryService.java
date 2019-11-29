package taborski.dawid.history.service.imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import taborski.dawid.history.model.History;
import taborski.dawid.history.repository.IHistoryRepository;
import taborski.dawid.history.service.IHistoryService;

import java.util.List;

@Service
public class HistoryService implements IHistoryService {
    private IHistoryRepository historyRepository;

    @Autowired
    public HistoryService(IHistoryRepository historyRepository) {
        this.historyRepository = historyRepository;
    }

    @Override
    public List<History> getHistory() {
        return this.historyRepository.findAll();
    }

    @Override
    public History add(History history) {
        return this.historyRepository.save(history);
    }
}
