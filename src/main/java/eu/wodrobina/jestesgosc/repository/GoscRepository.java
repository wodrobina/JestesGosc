package eu.wodrobina.jestesgosc.repository;

import eu.wodrobina.jestesgosc.model.Gosc;
import org.springframework.data.repository.Repository;

import java.util.Collection;

public interface GoscRepository extends Repository<Gosc, Long> {

    Gosc findByToPeriodIsNull();

    Collection<Gosc> findAll();

    void save(Gosc gosc);
}
