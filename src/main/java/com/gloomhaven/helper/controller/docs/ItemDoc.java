package com.gloomhaven.helper.controller.docs;


import com.gloomhaven.helper.model.dto.rest.ConfirmationResponse;
import com.gloomhaven.helper.model.dto.rest.ItemResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Tag(name = "Items")
public interface ItemDoc {

    @Operation(
            description = "If its possible to buy item its return 200 ok",
            summary = "Endpoint for buying items"
    )
    ResponseEntity<ConfirmationResponse> butItem(@PathVariable(name = "roomId") long roomId,
                                                        @PathVariable(name = "itemId") long itemId,
                                                        @AuthenticationPrincipal UserDetails userDetails);

    @Operation(
            description = "Its checking is user have equipped item of that type" +
                    "and if its equipped it will replace them",
            summary = "Endpoint for equipping items"
    )
    ResponseEntity<ConfirmationResponse> equipItem(@PathVariable(name = "roomId") long roomId,
                                                   @PathVariable(name = "itemId") long itemId,
                                                   @AuthenticationPrincipal UserDetails userDetails);

    @Operation(
            description = "Its returning list of all available items is this room",
            summary = "Endpoint for fetching items"
    )
    ResponseEntity<List<ItemResponse>> items(@PathVariable(name = "roomId") long roomId);


    @Operation(
            description = "Its returning list of all hero items",
            summary = "Endpoint for fetching hero items"
    )
    ResponseEntity<List<ItemResponse>> heroItems(@PathVariable(name = "roomId") long roomId,
                                                        @AuthenticationPrincipal UserDetails userDetails);
}
