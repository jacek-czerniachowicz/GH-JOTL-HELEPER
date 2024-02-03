package com.gloomhaven.helper.controller;

import com.gloomhaven.helper.controller.docs.PerkDoc;
import com.gloomhaven.helper.model.dto.rest.ConfirmationResponse;
import com.gloomhaven.helper.model.dto.rest.PerkResponse;
import com.gloomhaven.helper.model.entities.PerkEntity;
import com.gloomhaven.helper.service.PerkService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/rooms/{roomId}/heroes/{heroId}/perks")
public class PerkController implements PerkDoc {
    private final PerkService service;

    @GetMapping
    public ResponseEntity<List<PerkResponse>> heroPerks(
            @PathVariable(name = "heroId") Long heroId
    ){
        return ResponseEntity.ok(service.getValidPerks(heroId).stream().map(PerkResponse::new).toList());
    }

    @PutMapping("/{perkId}")
    public ResponseEntity<ConfirmationResponse> choosePerk(
            @PathVariable(name = "heroId") Long heroId,
            @PathVariable(name = "perkId") Long perkId
    ){
        try {
            PerkEntity perk = service.choosePerkById(perkId, heroId);
            return ResponseEntity.ok(new ConfirmationResponse("perk " + perk.getName() + " chosen" ));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ConfirmationResponse(e.getMessage()));
        }
    }

    @GetMapping("/chosen")
    public ResponseEntity<List<PerkResponse>> chosenPerks(
            @PathVariable(name = "heroId") Long heroId
    ){
        return ResponseEntity.ok(service.getChosenPerks(heroId).stream().map(PerkResponse::new).toList());
    }
}
