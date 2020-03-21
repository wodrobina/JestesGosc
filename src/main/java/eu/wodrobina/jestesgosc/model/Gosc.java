package eu.wodrobina.jestesgosc.model;

import javax.persistence.*;
import java.time.Instant;

@Entity
public class Gosc {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gosc_generator")
    @SequenceGenerator(name = "gosc_generator", sequenceName = "gosc_seq", allocationSize = 5)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    private String name;

    private String email;

    private Instant fromPeriod;

    private Instant toPeriod;

    protected Gosc() {
    }

    public Gosc(String email, String name, Instant fromPeriod) {
        this(email, name, fromPeriod, null);
    }

    public Gosc(String email, String name, Instant fromPeriod, Instant toPeriod) {
        this.name = name;
        this.email = email;
        this.fromPeriod = fromPeriod;
        this.toPeriod = toPeriod;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Instant getFromPeriod() {
        return fromPeriod;
    }

    public Instant getToPeriod() {
        return toPeriod;
    }

    public String getEmail() {
        return email;
    }
}
