package com.gloomhaven.helper.controller.rest;

import com.gloomhaven.helper.model.dto.rest.ItemResponse;
import com.gloomhaven.helper.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.hibernate.cache.spi.support.AbstractReadWriteAccess;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/rooms/{roomId}/items")
@RequiredArgsConstructor
public class ItemRestController {
    private final ItemService itemService;


/**
 * CO potrzebujemy?
 * CRUD
 * POST and GET :
 * zakupy \
 *
 * wczytanie
 *
 *
 */
    @GetMapping
    public ResponseEntity<List<ItemResponse>> items(@PathVariable(name = "roomId") long roomId) {
        return ResponseEntity.ok(itemService.getAvailableItems(roomId).stream().map(ItemResponse::new).toList());
    }

    @PostMapping("/{itemId}")
    public ResponseEntity<ItemResponse> butItem(@PathVariable(name = "roomId") long roomId,
                                                @PathVariable(name = "itemId") long itemId,
                                                @AuthenticationPrincipal UserDetails userDetails) {


        try {
            return ResponseEntity.ok(new ItemResponse(
                    itemService.buyItem(roomId, itemId, userDetails.getUsername()))
            );
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ItemResponse.builder()
                            .description(e.getMessage())
                    .build());
        }
    }

    @PutMapping("/{itemId}")
    public ResponseEntity<ItemResponse> equipItem(@PathVariable(name = "roomId") long roomId,
                                                  @PathVariable(name = "itemId") long itemId,
                                                  @AuthenticationPrincipal UserDetails userDetails) {

        return ResponseEntity.ok(new ItemResponse(
                itemService.equipItem(roomId,itemId, userDetails.getUsername()))
        );
    }

    @GetMapping("/hero-items")
    public ResponseEntity<List<ItemResponse>> heroItems(@PathVariable(name = "roomId") long roomId,
                                                        @AuthenticationPrincipal UserDetails userDetails){

        return ResponseEntity.ok(itemService.getHeroItems(roomId, userDetails.getUsername())
                        .stream().map(ItemResponse::new).toList());
    }

}
