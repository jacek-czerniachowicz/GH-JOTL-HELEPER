package com.gloomhaven.helper.controller;

import com.gloomhaven.helper.controller.docs.RoomDoc;
import com.gloomhaven.helper.model.dto.rest.CreateRoomRequest;
import com.gloomhaven.helper.model.dto.rest.InviteCodeDTO;
import com.gloomhaven.helper.model.dto.rest.RoomResponse;
import com.gloomhaven.helper.model.entities.InviteCodeEntity;
import com.gloomhaven.helper.model.entities.RoomEntity;
import com.gloomhaven.helper.service.InviteCodeService;
import com.gloomhaven.helper.service.RoomService;
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
public class RoomController implements RoomDoc {
    private final RoomService service;
    private final InviteCodeService invService;

    @PostMapping
    public ResponseEntity<RoomResponse> createRoom(
            @RequestBody CreateRoomRequest request,
            @AuthenticationPrincipal UserDetails userDetails
    ){
        RoomEntity room = service.createRoom(request.getName(), userDetails.getUsername());
        RoomResponse roomResponse = new RoomResponse(room);

        return ResponseEntity
                .created(URI.create("/api/v1/rooms/" + room.getId()))
                .body(roomResponse);
    }
    @GetMapping
    public ResponseEntity<List<RoomResponse>> rooms(){
        List<RoomEntity> rooms = service.getRooms();
        List<RoomResponse> response = rooms.stream().map(RoomResponse::new).toList();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/user-rooms")
    public ResponseEntity<List<RoomResponse>> userRooms(
            @AuthenticationPrincipal UserDetails userDetails
    ){

        List<RoomEntity> rooms = service.getRooms(userDetails.getUsername());
        List<RoomResponse> response = rooms.stream().map(RoomResponse::new).toList();
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

    @PostMapping("/invite")
    public ResponseEntity<InviteCodeEntity> addInviteCode(@RequestBody InviteCodeDTO request){
        return ResponseEntity.ok(invService.addInviteCode(request.getRoomId(), request.getCode()));
    }

    @GetMapping("/invite")
    public ResponseEntity<List<InviteCodeDTO>> loadInviteCodes(){
        return ResponseEntity.ok(invService.findAll().stream().map(entity ->
                new InviteCodeDTO(entity.getRoomId(), entity.getCode())).toList());
    }

    @GetMapping("invite/getCode/{roomId}")
    public String getInviteCode(@PathVariable Long roomId){
        return invService.findCode(roomId);
    }

    @GetMapping("invite/getId/{code}")
    public Long getRoomId(@PathVariable String code){
        return invService.findRoomId(code);
    }
}
