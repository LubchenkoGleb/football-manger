package ua.procamp.footballmanager.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.procamp.footballmanager.model.entity.Player;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlayerDto {

    private Long id;

    private String firstName;

    private String LastName;

    private Player.Position position;

    private LocalDate birthday;

    public static PlayerDto of(Player player) {
        return new PlayerDto(
                player.getId(),
                player.getFirstName(),
                player.getLastName(),
                player.getPosition(),
                player.getBirthday());
    }
}
