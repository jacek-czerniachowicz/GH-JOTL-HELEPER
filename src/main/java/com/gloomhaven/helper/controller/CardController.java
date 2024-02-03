package com.gloomhaven.helper.controller;

import com.gloomhaven.helper.controller.docs.CardDoc;
import com.gloomhaven.helper.model.dto.rest.CardResponse;
import com.gloomhaven.helper.model.dto.rest.ConfirmationResponse;
import com.gloomhaven.helper.model.entities.CardEntity;
import com.gloomhaven.helper.service.CardService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/rooms/{roomId}/heroes/{heroId}/cards")

@RequiredArgsConstructor
public class CardController implements CardDoc {
    private final CardService service;
    private final Logger LOGGER = LoggerFactory.getLogger(CardController.class);

    @GetMapping
    public ResponseEntity<List<CardResponse>> cardDeck(
            @PathVariable("heroId") Long heroId
    ){
        LOGGER.info("Fetching card deck for heroId: {}", heroId);
        return ResponseEntity.ok(
                service.getSelectedCards(heroId).stream().map(CardResponse::new).toList());
    }
    @GetMapping("/available")
    public ResponseEntity<List<CardResponse>> validCards(
            @PathVariable(name = "heroId") Long heroId
    ){
        LOGGER.info("Fetching available cards for heroId: {}", heroId);
        return ResponseEntity.ok(service.getAvailableCards(heroId).stream().map(CardResponse::new).toList());
    }

    @PutMapping("/{cardId}")
    public ResponseEntity<ConfirmationResponse> pickCard(
            @PathVariable(name = "heroId") Long heroId,
            @PathVariable(name = "cardId") Long cardId
    ) {
        LOGGER.info("Picking card with cardId: {} for heroId: {}", cardId, heroId);
        try {
            CardEntity card = service.pickCard(cardId, heroId);
            return ResponseEntity.ok(new ConfirmationResponse("card " + card.getName() + " chosen"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ConfirmationResponse(e.getMessage()));
        }
    }

    @PutMapping("/{cardId}/undo")
    public ResponseEntity<ConfirmationResponse> deselectCard(
            @PathVariable("heroId") Long heroId,
            @PathVariable("cardId") Long cardId
    ){
        LOGGER.info("Deselecting card with cardId: {} for heroId: {}", cardId, heroId);
        try {
            CardEntity card = service.deselectCard(cardId, heroId);
            return ResponseEntity.ok(new ConfirmationResponse("card '" + card.getName() + "' deselected"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ConfirmationResponse(e.getMessage()));
        }
    }
}
