package eu.wodrobina.jestesgosc.repository;

import eu.wodrobina.jestesgosc.model.Gosc;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;
import java.util.Optional;

public interface GoscRepository extends CrudRepository<Gosc, Long> {

    Optional<Gosc> findByFromPeriodIsNotNullAndToPeriodIsNull();

    Optional<Gosc> findByUser_Email(String email);

    Collection<Gosc> findAll();
}
