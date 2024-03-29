package com.gloomhaven.helper.controller;

import com.gloomhaven.helper.controller.docs.HeroDoc;
import com.gloomhaven.helper.model.dto.rest.CreateHeroRequest;
import com.gloomhaven.helper.model.dto.rest.UpdateHeroRequest;
import com.gloomhaven.helper.model.dto.rest.auth.HeroesResponse;
import com.gloomhaven.helper.model.entities.HeroEntity;
import com.gloomhaven.helper.service.CreateHeroService;
import com.gloomhaven.helper.service.HeroService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/rooms/{roomId}/heroes")
public class HeroController implements HeroDoc {
    private final HeroService heroService;
    private final CreateHeroService createHeroService;

    @GetMapping
    public ResponseEntity<List<HeroesResponse>> heroes(@PathVariable Long roomId) {
        List<HeroEntity> heroesByRoomId = heroService.getHeroesByRoomId(roomId);
        return ResponseEntity.ok(
                heroesByRoomId.stream()
                        .map(HeroesResponse::new)
                        .toList()
        );
    }

    @PostMapping
    public ResponseEntity<HeroesResponse> createHero(@PathVariable Long roomId,
                                                     @AuthenticationPrincipal UserDetails userDetails,
                                                     @RequestBody CreateHeroRequest request) {
        try {
            return ResponseEntity.ok(new HeroesResponse(
                    createHeroService.createHero(request, userDetails.getUsername(), roomId)));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().header("ERROR_DESC", e.getMessage()).build();
        }
    }

    @GetMapping("/user-hero")
    public ResponseEntity<HeroesResponse> userHero(@PathVariable Long roomId,
                                                   @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(new HeroesResponse(
                heroService.getUserHero(roomId, userDetails.getUsername())
        ));
    }

    @GetMapping("/{heroId}")
    public ResponseEntity<HeroesResponse> hero(@PathVariable Long roomId,
                                               @PathVariable Long heroId) {
        return ResponseEntity.ok(new HeroesResponse(
                heroService.getHeroByUserId(roomId, heroId)
        ));
    }

    @PutMapping("/{heroId}")
    public ResponseEntity<HeroesResponse> updateHero(@PathVariable Long heroId,
                                                     @RequestBody UpdateHeroRequest request) {
        return ResponseEntity.ok(new HeroesResponse(heroService.updateHero(heroId, request)));
    }
}
