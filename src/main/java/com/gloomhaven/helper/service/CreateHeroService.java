package com.gloomhaven.helper.service;

import com.gloomhaven.helper.model.dto.CreateHeroDTO;
import com.gloomhaven.helper.model.dto.rest.CreateHeroRequest;
import com.gloomhaven.helper.model.entities.HeroEntity;
import com.gloomhaven.helper.model.entities.RoomEntity;
import com.gloomhaven.helper.model.entities.UserEntity;
import com.gloomhaven.helper.repository.HeroRepository;
import com.gloomhaven.helper.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateHeroService {
    private final HeroRepository heroRepository;
    private final RoomService roomService;
    private final UserRepository userRepository;

    public HeroEntity createHero(CreateHeroRequest request, String userEmail, Long roomId) throws Exception {
        UserEntity user = userRepository.findByEmail(userEmail).orElseThrow();
        RoomEntity room = roomService.getRoom(roomId);
        HeroEntity hero = createHeroEntity(request, user, room);
        roomService.updateRoom(hero.getRoom());
        return heroRepository.save(hero);
    }

    @NotNull
    private HeroEntity createHeroEntity(CreateHeroRequest request, UserEntity user, RoomEntity room) throws Exception{
        validateIsUserAbleToCreateNewHero(request, user, room);
        CreateHeroDTO heroDTO = new CreateHeroDTO(request);
        heroDTO.setUser(user);
        heroDTO.setRoom(room);
        return heroDTO.toHeroEntity();
    }

    private void validateIsUserAbleToCreateNewHero(CreateHeroRequest request, UserEntity user, RoomEntity room) throws Exception {
        if (!isUserHaveNotHeroInRoom(user, room)){
            throw new Exception("The user already has his hero in the room");
        }
        if (!isHeroNameUniqueInRoom(request.getName(), room)){
            throw new Exception("hero name is not unique");
        }
    }
    public boolean isHeroNameUniqueInRoom(String heroName, RoomEntity room) {
        return heroRepository.countByRoomAndName(room, heroName) == 0;
    }
    public boolean isUserHaveNotHeroInRoom(UserEntity user, RoomEntity room){
        return heroRepository.countByRoomAndUser(room, user) == 0;
    }
}
