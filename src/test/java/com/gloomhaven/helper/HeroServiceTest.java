package com.gloomhaven.helper;

import com.gloomhaven.helper.model.dto.CreateHeroDTO;
import com.gloomhaven.helper.model.entities.HeroEntity;
import com.gloomhaven.helper.model.entities.RacesEnum;
import com.gloomhaven.helper.model.entities.RoomEntity;
import com.gloomhaven.helper.model.entities.UserEntity;
import com.gloomhaven.helper.repository.HeroRepository;
import com.gloomhaven.helper.service.HeroService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Transactional
public class HeroServiceTest {
    @Autowired
    private HeroService heroService;
    @Autowired
    private HeroRepository heroRepository;

    @Test
    public void testGetAllHeroes() {
        //before
        List<HeroEntity> before = heroService.getAllHeroes();
        List<HeroEntity> heroes = createListOfTwoSampleHero();
        heroRepository.saveAll(heroes);
        //when
        List<HeroEntity> result = heroService.getAllHeroes();
        //then
        assertEquals(before.size() + 2, result.size());
    }

    @Test
    public void testGetHeroById() {
        //before
        HeroEntity hero = createSampleHero();
        heroRepository.save(hero);
        //when
        HeroEntity result = heroService.getHeroById(hero.getId());
        //then
        assertEquals(hero.getId(), result.getId());
    }

    @Test
    public void testCreateHero() {
        //before
        HeroEntity hero = createSampleHero();
        //when
        HeroEntity createdHero = heroService.createHero(hero);
        //then
        assertEquals(hero, createdHero);
    }

    @Test
    public void testUpdateHero() {
        //before
        HeroEntity existingHero = createSampleHero();
        heroRepository.save(existingHero);
        HeroEntity updatedHero = new HeroEntity();
        updatedHero.setGold(50);
        //when
        HeroEntity result = heroService.updateHero(existingHero.getId(), updatedHero);
        //then
        assertEquals(50, result.getGold());
    }

    @Test
    public void testDeleteHero() {
        //before
        HeroEntity hero = createSampleHero();
        heroRepository.save(hero);
        //when
        heroService.deleteHero(hero.getId());
        Optional<HeroEntity> deletedHero = heroRepository.findById(hero.getId());
        //then
        assertTrue(deletedHero.isEmpty());
    }

    @Test
    public void testGetHeroesByRoomId(){
        //before
        UserEntity userEntity1 = new UserEntity("addres@email.com", "username", "password");
        UserEntity userEntity2 = new UserEntity("aaaa@email.com", "username2", "password");
        RoomEntity room = new RoomEntity("room", userEntity1);

        HeroEntity sampleHero1 = createSampleHero();
        sampleHero1.setRoom(room);
        sampleHero1.setUser(userEntity1);
        heroService.createHero(sampleHero1);

        HeroEntity sampleHero2 = createSampleHero();
        sampleHero2.setName("wojtyła");
        sampleHero2.setRoom(room);
        sampleHero2.setUser(userEntity2);
        heroService.createHero(sampleHero2);
        //when
        List<HeroEntity> heroesByRoomId = heroService.getHeroesByRoomId(room.getId());
        //then
        assertThat(heroesByRoomId).containsOnly(sampleHero1, sampleHero2);
        assertEquals(heroesByRoomId.get(0).getRoom(), room);
        assertEquals(heroesByRoomId.get(1).getUser(), userEntity2);


    }

    public HeroEntity createSampleHero(){
        return new CreateHeroDTO("Zbychu", RacesEnum.HATCHET, new UserEntity(), new RoomEntity()).toHeroEntity();
    }
    public List<HeroEntity> createListOfTwoSampleHero(){
        return List.of(
                new CreateHeroDTO("Zbychu", RacesEnum.HATCHET, new UserEntity(), new RoomEntity()).toHeroEntity(),
                new CreateHeroDTO("Zdzichu", RacesEnum.REDGUARD, new UserEntity(), new RoomEntity()).toHeroEntity()
        );
    }
}
