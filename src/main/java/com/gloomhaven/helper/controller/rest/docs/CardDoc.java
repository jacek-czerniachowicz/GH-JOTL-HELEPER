package com.gloomhaven.helper.controller.rest.docs;

import com.gloomhaven.helper.model.dto.rest.CardResponse;
import com.gloomhaven.helper.model.dto.rest.ConfirmationResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Tag(name = "Cards")
public interface CardDoc {
    @Operation(
            description = "GET endpoint that's return valid cards (based on hero level " +
                    "and previously chosen cards",
            summary = "Returns valid hero cards"
    )
    ResponseEntity<List<CardResponse>> validCards(@PathVariable(name = "heroId") Long heroId);

    @Operation(
            description = "Chooses a skill card with ID = cardId for hero with ID = heroId, " +
                    "checking is card ID is correct and is hero has required level",
            summary = "Chooses a skill card"
    )
    ResponseEntity<ConfirmationResponse> chooseCard(
            @PathVariable(name = "heroId") Long heroId,
            @PathVariable(name = "cardId") Long cardId
    );
}
