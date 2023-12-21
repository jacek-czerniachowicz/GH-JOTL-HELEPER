package com.gloomhaven.helper.service;

import com.gloomhaven.helper.model.dto.CreateHeroDTO;
import com.gloomhaven.helper.model.dto.rest.CreateHeroRequest;
import com.gloomhaven.helper.model.entities.HeroEntity;
import com.gloomhaven.helper.model.entities.RacesEnum;
import com.gloomhaven.helper.model.entities.RoomEntity;
import com.gloomhaven.helper.model.entities.UserEntity;
import com.gloomhaven.helper.repository.HeroRepository;
import com.gloomhaven.helper.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateHeroService {
    private final HeroRepository heroRepository;
    private final RoomService roomService;
    private final UserRepository userRepository;

    private final Logger LOGGER = LoggerFactory.getLogger(CreateHeroDTO.class);

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

    public HeroEntity createHero(CreateHeroRequest request, String userEmail, Long roomId) throws Exception {
        UserEntity user = userRepository.findByEmail(userEmail).orElseThrow();
        LOGGER.info("user found");
        RoomEntity room = roomService.getRoom(roomId);
        LOGGER.info("room found");
        if (!isUserHaveNotHeroInRoom(user, room)){
            LOGGER.warn("The user already has his hero in the room".toUpperCase());
            return heroRepository.findByRoomIdAndUserId(room.getId(), user.getId());
        }
        if (!isHeroNameUniqueInRoom(request.getName(), room)){
            LOGGER.warn("hero name is not unique".toUpperCase());
            throw new Exception();
        }

        LOGGER.info("heroDTO creating...");
        LOGGER.info("RaceEnum: " + RacesEnum.valueOf(request.getRaceName()).name());
        CreateHeroDTO heroDTO = CreateHeroDTO.builder()
                .name(request.getName())
                .race(RacesEnum.valueOf(request.getRaceName()))
                .room(room)
                .user(user)
                .build();
        LOGGER.info("heroDTO created");
        roomService.updateRoom(heroDTO.getRoom());
        return heroRepository.save(heroDTO.toHeroEntity());
    }
}
