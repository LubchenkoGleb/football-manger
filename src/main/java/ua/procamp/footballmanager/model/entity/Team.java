package ua.procamp.footballmanager.model.entity;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@EqualsAndHashCode(of = "name")
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NaturalId
    @Column(nullable = false)
    private String name;

    @OneToOne
    @JoinColumn(name = "captain_id")
    private Player captain;

    @OneToMany(mappedBy = "team")
    @Setter(AccessLevel.PRIVATE)
    private List<Player> players = new ArrayList<>();

    public void addPlayer(Player player) {
        this.players.add(player);
        player.setTeam(this);
    }

    public void deletePlayer(Player player) {
        this.players.remove(player);
        player.setTeam(null);
    }
}
