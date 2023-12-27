package com.gloomhaven.helper.model.dto.rest;

import com.gloomhaven.helper.model.entities.PerkEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PerkResponse {
    private Long id;
    private String description;

    public PerkResponse(PerkEntity perk) {
        this.id = perk.getId();
        this.description = perk.getName();
    }
}



