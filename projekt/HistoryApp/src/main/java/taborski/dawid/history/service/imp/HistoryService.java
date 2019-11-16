package taborski.dawid.history.service.imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import taborski.dawid.history.model.History;
import taborski.dawid.history.repository.IHistoryRepository;
import taborski.dawid.history.service.IHistoryService;

import java.util.List;

@Service
public class HistoryService implements IHistoryService {
    private IHistoryRepository hisotryRepository;

    @Autowired
    public HistoryService(IHistoryRepository hisotryRepository) {
        this.hisotryRepository = hisotryRepository;
    }

    @Override
    public List<History> getHistory() {
        return this.hisotryRepository.findAll();
    }

    @Override
    public History add(History history) {
        return this.hisotryRepository.save(history);
    }
}
