package com.gloomhaven.helper.controller.docs;

import com.gloomhaven.helper.model.dto.rest.CreateRoomRequest;
import com.gloomhaven.helper.model.dto.rest.InviteCodeDTO;
import com.gloomhaven.helper.model.dto.rest.RoomResponse;
import com.gloomhaven.helper.model.entities.InviteCodeEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Tag(name = "Rooms")
public interface RoomDoc {

    @Operation(
            description = "",
            summary = "Endpoint for creating new room"
    )
    ResponseEntity<RoomResponse> createRoom(
            @RequestBody CreateRoomRequest request,
            @AuthenticationPrincipal UserDetails userDetails
    );

    @Operation(
            description = "",
            summary = "Endpoint for fetching all existing rooms"
    )
    ResponseEntity<List<RoomResponse>> rooms();

    @Operation(
            description = "",
            summary = "Endpoint for fetching authenticated user rooms"
    )
    ResponseEntity<List<RoomResponse>> userRooms(
            @AuthenticationPrincipal UserDetails userDetails
    );

    @Operation(
            description = "",
            summary = "Endpoint for fetching single room"
    )
    ResponseEntity<RoomResponse> room(@PathVariable Long id);


    @Operation(
            description = "",
            summary = "Endpoint for adding authenticated user to room with id"
    )
    ResponseEntity<RoomResponse> join(@PathVariable Long id,
                                      @AuthenticationPrincipal UserDetails userDetails);

    @Operation(
            description = "",
            summary = "Endpoint for removing room"
    )
    ResponseEntity<Void> deleteRoom(@PathVariable Long id);

    @Operation(
            description = "",
            summary = "Endpoint for creating new invite code"
    )
    ResponseEntity<InviteCodeEntity> addInviteCode(@RequestBody InviteCodeDTO request);

    @Operation(
            description = "",
            summary = "Endpoint for fetching all existing invite codes"
    )
    ResponseEntity<List<InviteCodeDTO>> loadInviteCodes();


    @Operation(
            description = "",
            summary = "Endpoint for checking invite code that is saved for the room id"
    )
    String getInviteCode(@PathVariable Long roomId);

    @Operation(
            description = "",
            summary = "Endpoint for checking room id that is saved for the invite code"
    )
    Long getRoomId(@PathVariable String code);
}
