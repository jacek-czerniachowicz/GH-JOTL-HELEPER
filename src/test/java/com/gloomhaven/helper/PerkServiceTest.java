package com.gloomhaven.helper;

import com.gloomhaven.helper.model.dto.CreateHeroDTO;
import com.gloomhaven.helper.model.entities.*;
import com.gloomhaven.helper.service.PerkService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Transactional
public class PerkServiceTest {
    @Autowired
    PerkService perkService;

    @Test
    public void testIsReturningValidPerks(){
        //before
        HeroEntity hero1 = new CreateHeroDTO("test1", RacesEnum.VOIDWARDEN, new UserEntity(), new RoomEntity()).toHeroEntity();
        HeroEntity hero2 = new CreateHeroDTO("test2", RacesEnum.DEMOLITIONIST, new UserEntity(), new RoomEntity()).toHeroEntity();
        //when
        List<PerkEntity> validHeroPerks1 = perkService.getValidPerks(hero1);
        List<PerkEntity> validHeroPerks2 = perkService.getValidPerks(hero2);
        //then
        assertThat(validHeroPerks1.size()).isEqualTo(15);
        assertThat(validHeroPerks1.stream().map(perk -> perk.getName())).contains("Zastąp jedną kartę -1 jedną kartą 0 (leczenie 1 sojusznik)");

        assertThat(validHeroPerks2.size()).isEqualTo(14);
        assertThat(validHeroPerks2.stream().map(perk -> perk.getName())).contains("Dodaj jedną kartę 0 Wszyscy sąsiadujący przeciwnicy otrzymują 1 obrażenie)");

    }
}
