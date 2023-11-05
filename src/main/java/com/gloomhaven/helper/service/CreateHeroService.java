package com.gloomhaven.helper.service;

import com.gloomhaven.helper.model.dto.CreateHeroDTO;
import com.gloomhaven.helper.model.entities.RoomEntity;
import com.gloomhaven.helper.model.entities.UserEntity;
import com.gloomhaven.helper.repository.HeroRepository;
import com.gloomhaven.helper.repository.RoomRepository;
import org.springframework.stereotype.Service;

@Service
public class CreateHeroService {
    private HeroRepository heroRepository;
    private RoomService roomService;

    public CreateHeroService(HeroRepository heroRepository, RoomService roomService) {
        this.heroRepository = heroRepository;
        this.roomService = roomService;
    }

    public boolean isHeroNameUniqueInRoom(String heroName, RoomEntity room) {
        return heroRepository.countByRoomAndName(room, heroName) == 0;
    }
    public boolean isUserHaveNotHeroInRoom(UserEntity user, RoomEntity room){
        return heroRepository.countByRoomAndUser(room, user) == 0;
    }
    public boolean createHero(CreateHeroDTO heroDTO, UserEntity user){
        RoomEntity room = roomService.getRoom(heroDTO.getRoomId());
        if (!isUserHaveNotHeroInRoom(user, room)){
            return false;
        }
        if (!isHeroNameUniqueInRoom(heroDTO.getName(), room)){
            return false;
        }
        heroDTO.setRoom(room);
        heroDTO.setUser(user);
        roomService.updateRoom(heroDTO.getRoom());
        heroRepository.save(heroDTO.toHeroEntity());
        return true;
    }
}
