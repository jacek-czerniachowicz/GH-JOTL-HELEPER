package com.gloomhaven.helper.controller.rest;

import com.gloomhaven.helper.controller.rest.docs.CardDoc;
import com.gloomhaven.helper.model.dto.rest.CardResponse;
import com.gloomhaven.helper.model.dto.rest.ConfirmationResponse;
import com.gloomhaven.helper.model.entities.CardEntity;
import com.gloomhaven.helper.service.CardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/rooms/{roomId}/heroes/{heroId}/cards")

@RequiredArgsConstructor
public class CardRestController implements CardDoc {
    private final CardService service;

    @GetMapping
    public ResponseEntity<List<CardResponse>> validCards(
            @PathVariable(name = "heroId") Long heroId
    ){
        return ResponseEntity.ok(service.getAvailableCards(heroId).stream().map(CardResponse::new).toList());
    }

    @PutMapping("/{cardId}")
    public ResponseEntity<ConfirmationResponse> chooseCard(
            @PathVariable(name = "heroId") Long heroId,
            @PathVariable(name = "cardId") Long cardId
    ) {

        try {
            CardEntity card = service.chooseCard(cardId, heroId);
            return ResponseEntity.ok(new ConfirmationResponse("card " + card.getName() + " chosen"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ConfirmationResponse(e.getMessage()));
        }
    }

    @GetMapping("/chosen")
    public ResponseEntity<List<CardResponse>> chosenCards(
            @PathVariable("heroId") Long heroId
    ){
        return ResponseEntity.ok(
                service.chosenCards(heroId).stream().map(CardResponse::new).toList());
    }

    @PutMapping("/{cardId}/undo")
    public ResponseEntity<ConfirmationResponse> unchooseCard(
            @PathVariable("heroId") Long heroId,
            @PathVariable("cardId") Long cardId
    ){
        try {
            CardEntity card = service.unchooseCard(cardId, heroId);
            return ResponseEntity.ok(new ConfirmationResponse("card '" + card.getName() + "' unchoose"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ConfirmationResponse(e.getMessage()));
        }
    }
}
