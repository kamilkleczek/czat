package taborski.dawid.history.service;

import taborski.dawid.history.model.History;

import java.util.List;

public interface IHistoryService {
    List<History> getHistory();

    History add(History history);
}
