package ua.procamp.footballmanager.model.dto;

import lombok.Data;

@Data
public class SetCaptainDto {

    private Long teamId;

    private Long captainId;
}
