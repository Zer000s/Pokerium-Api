package org.example.pokerium_api.dto;

import lombok.Data;

@Data
public class ActionDto {
    private String action; // FOLD, CALL, RAISE
    private Integer amount;
}