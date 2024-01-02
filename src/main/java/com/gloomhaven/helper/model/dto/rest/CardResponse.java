package com.gloomhaven.helper.model.dto.rest;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.gloomhaven.helper.model.entities.CardEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CardResponse {
    private Long id;
    private String name;
    @JsonProperty("required_level")
    private int requiredLevel;

    public CardResponse(CardEntity card) {
        this.id = card.getId();
        this.name = card.getName();
        this.requiredLevel = card.getRequiredLevel();
    }
}
