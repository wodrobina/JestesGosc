package eu.wodrobina.jestesgosc.repository;

import eu.wodrobina.jestesgosc.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, String> {

    Iterable<User> findAll();

    Optional<User> findByEmail(String email);

    void deleteByEmail(String email);

}
