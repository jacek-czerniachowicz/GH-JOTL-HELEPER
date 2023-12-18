package com.gloomhaven.helper.controller.rest;

import com.gloomhaven.helper.model.dto.rest.CreateRoomRequest;
import com.gloomhaven.helper.model.dto.rest.RoomResponse;
import com.gloomhaven.helper.model.entities.RoomEntity;
import com.gloomhaven.helper.service.RoomService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/rooms")
public class RoomRestController {
    private final RoomService service;

    @PostMapping
    public ResponseEntity<RoomResponse> createRoom(
            @RequestBody CreateRoomRequest request,
            @AuthenticationPrincipal UserDetails userDetails
    ){
        System.out.println(request.getName());
        RoomEntity room = service.createRoom(request.getName(), userDetails.getUsername());
        RoomResponse roomResponse = RoomResponse.builder()
                .roomName(room.getName())
                .host(room.getHost().getNickname())
                .build();

        return ResponseEntity
                .created(URI.create("/api/v1/rooms/" + room.getId()))
                .body(roomResponse);
    }
    @GetMapping
    public ResponseEntity<List<RoomResponse>> rooms(){
        List<RoomEntity> rooms = service.getRooms();
        List<RoomResponse> response = rooms.stream().map(room ->
                RoomResponse.builder()
                        .id(room.getId())
                        .roomName(room.getName())
                        .host(room.getHost().getNickname())
                        .build()
        )
                .toList();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/user-rooms")
    public ResponseEntity<List<RoomResponse>> userRooms(
            @AuthenticationPrincipal UserDetails userDetails
    ){

        List<RoomEntity> rooms = service.getRooms(userDetails.getUsername());
        List<RoomResponse> response = rooms.stream().map(room ->
                        RoomResponse.builder()
                                .id(room.getId())
                                .roomName(room.getName())
                                .host(room.getHost().getNickname())
                                .build()
                )
                .toList();
        return ResponseEntity.ok(response);
    }



    @GetMapping("/{id}")
    public ResponseEntity<RoomResponse> room(@PathVariable Long id){
        RoomEntity room = service.getRoom(id);
        return ResponseEntity.ok(new RoomResponse(room));
    }

    @PutMapping("/join/{id}")
    public ResponseEntity<RoomResponse> join(@PathVariable Long id,
                                 @AuthenticationPrincipal UserDetails userDetails){
        RoomEntity room = service.joinToRoom(id, userDetails.getUsername());
        return ResponseEntity.ok(new RoomResponse(room));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRoom(@PathVariable Long id){
        boolean confirmation = service.delete(id);
        if (confirmation){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
