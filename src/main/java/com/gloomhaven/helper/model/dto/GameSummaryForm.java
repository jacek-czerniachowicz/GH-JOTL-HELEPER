package com.gloomhaven.helper.model.dto;

import com.gloomhaven.helper.model.entities.HeroEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GameSummaryForm {
    private int xp;
    private int gold;
    private int progressPoint;

    private Long heroId;
}
