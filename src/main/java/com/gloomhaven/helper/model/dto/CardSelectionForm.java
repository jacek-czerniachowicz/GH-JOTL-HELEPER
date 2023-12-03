package com.gloomhaven.helper.model.dto;

import java.util.ArrayList;
import java.util.List;

public class CardSelectionForm {
    private Long heroId;
    private List<Long> selectedCardsId;

    public CardSelectionForm() {
        this.selectedCardsId = new ArrayList<>();
    }

    public Long getHeroId() {
        return heroId;
    }

    public void setHeroId(Long heroId) {
        this.heroId = heroId;
    }

    public List<Long> getSelectedCardsId() {
        return selectedCardsId;
    }

    public void setSelectedCardsId(List<Long> selectedCardsId) {
        this.selectedCardsId = selectedCardsId;
    }
}
