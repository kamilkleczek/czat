package taborski.kleczek.User.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import taborski.kleczek.User.model.User;

import java.util.Optional;

@Repository
public interface IUserRepository extends JpaRepository<User, Long> {
    Boolean existsByName(String name);
    Optional<User> findByName(String name);
}
