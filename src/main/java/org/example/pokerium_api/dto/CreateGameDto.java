package org.example.pokerium_api.dto;

import lombok.Data;

@Data
public class CreateGameDto {
    private Integer smallBlind;
    private Integer bigBlind;
}
