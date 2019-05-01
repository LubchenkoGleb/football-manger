package ua.procamp.footballmanager.model.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, name = "first_name")
    private String firstName;

    @Column(nullable = false, name = "last_name")
    private String LastName;

    @Enumerated(EnumType.STRING)
    private Position position;

    @Column(nullable = false)
    private LocalDate birthday;

    @ManyToOne
    @JoinColumn(name = "team_id")
    private Team team;

    public enum Position {
        GOALKEEPER,
        DEFENDER,
        MIDFIELDER,
        FORWARD
    }
}
