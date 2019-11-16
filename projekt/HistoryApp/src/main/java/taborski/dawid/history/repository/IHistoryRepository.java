package taborski.dawid.history.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import taborski.dawid.history.model.History;

import java.util.List;

@Repository
public interface IHistoryRepository extends JpaRepository<History, Long> {
    List<History> findAllBySenderId(Long id);

}
