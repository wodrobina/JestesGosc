package eu.wodrobina.jestesgosc.repository;

import eu.wodrobina.jestesgosc.model.User;
import org.springframework.data.repository.Repository;

import java.util.Optional;

public interface UserRepository extends Repository<User, String> {

    void save(User user);

    Iterable<User> findAll();

    Optional<User> findByEmail(String email);

    void deleteByEmail(String email);
}
