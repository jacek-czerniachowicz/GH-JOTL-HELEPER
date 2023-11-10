package com.gloomhaven.helper.model.dto;

import java.util.ArrayList;
import java.util.List;

public class PerkSelectionForm {
    private Long heroId;
    private List<Long> selectedPerksId;

    public PerkSelectionForm() {
        this.selectedPerksId = new ArrayList<>();
    }

    public Long getHeroId() {
        return heroId;
    }

    public void setHeroId(Long heroId) {
        this.heroId = heroId;
    }

    public List<Long> getSelectedPerksId() {
        return selectedPerksId;
    }

    public void setSelectedPerksId(List<Long> selectedPerksId) {
        this.selectedPerksId = selectedPerksId;
    }
}
