package eu.wodrobina.jestesgosc.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.util.Objects;

@Entity
public class Gosc {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gosc_generator")
    @SequenceGenerator(name = "gosc_generator", sequenceName = "gosc_seq", allocationSize = 5)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    private Instant fromPeriod;
    private Instant toPeriod;

    @NotNull
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @MapsId
    @JoinColumn(name = "id")
    private User user;

    protected Gosc() {
    }

    public Gosc(User user) {
        this(user, null, null);
    }

    public Gosc(User user, Instant fromPeriod, Instant toPeriod) {
        this.user = user;
        this.fromPeriod = fromPeriod;
        this.toPeriod = toPeriod;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return user.getName();
    }

    public Instant getFromPeriod() {
        return fromPeriod;
    }

    public Instant getToPeriod() {
        return toPeriod;
    }

    public String getEmail() {
        return user.getEmail();
    }

    public void setSuccessor(Gosc successor) {
        Instant successionTimestamp = Instant.now();
        toPeriod = successionTimestamp;
        successor.succession(successionTimestamp);
    }

    public void succession(Instant successionTimestamp) {
        fromPeriod = successionTimestamp;
        toPeriod = null;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Gosc gosc = (Gosc) o;
        return Objects.equals(user, gosc.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user);
    }
}
