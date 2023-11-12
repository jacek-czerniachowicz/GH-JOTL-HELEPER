package com.gloomhaven.helper.boot;

import com.gloomhaven.helper.model.dto.UserDTO;
import com.gloomhaven.helper.model.entities.*;
import com.gloomhaven.helper.repository.PerkRepository;
import com.gloomhaven.helper.repository.RoleRepository;
import com.gloomhaven.helper.service.CardService;
import com.gloomhaven.helper.service.ItemService;
import com.gloomhaven.helper.service.RoomService;
import com.gloomhaven.helper.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

import static java.util.Arrays.asList;

@Component
public class DataLoader implements CommandLineRunner {
    private final PerkRepository perkRepository;
    private ItemService itemService;
    private final RoleRepository roleRepository;
    private final UserService userService;
    private final RoomService roomService;
    private final CardService cardService;


    public DataLoader(PerkRepository perkRepository, ItemService itemService, RoleRepository roleRepository, UserService userService, RoomService roomService, CardService cardService) {
        this.perkRepository = perkRepository;
        this.itemService = itemService;
        this.roleRepository = roleRepository;
        this.userService = userService;
        this.roomService = roomService;
        this.cardService = cardService;
    }

    @Override
    public void run(String... args) throws Exception {
        addPerksToDb();
//        addItemsToDb();
        addRolesToDb();

        addAdminToDb();
        addCardsToDb();
    }
    private void addRolesToDb() {
        if(roleRepository.findAll().isEmpty()) {
            RoleEntity roleUser = new RoleEntity("123", "ROLE_USER");
            RoleEntity roleAdmin = new RoleEntity("420", "ROLE_ADMIN");
            roleRepository.saveAll(asList(roleUser, roleAdmin));
        }
    }
    private void addAdminToDb() {
        if(userService.getUsers().isEmpty()){
            UserDTO user = new UserDTO("habaz@ibadlo.com", "root", "toor");
            userService.createUser(user);
            userService.setAdminRole(user.getUsername());

            UserDTO user2 = new UserDTO("user@ibadlo.com", "user", "user");
            userService.createUser(user2);
        }
    }

    private void addPerksToDb(){
        if (perkRepository.findAll().isEmpty()) {
            perkRepository.saveAll(List.of(
                    new PerkEntity("Usuń cztery karty 0", RacesEnum.REDGUARD),
                    new PerkEntity("Usuń dwie kart -1", RacesEnum.REDGUARD),
                    new PerkEntity("Usuń jedną kartę -2 oraz jedną kartę +1", RacesEnum.REDGUARD),
                    new PerkEntity("Usuń jedną kartę -2 oraz jedną kartę +1", RacesEnum.REDGUARD),
                    new PerkEntity("Zastąp jedną kartę +1 jedną kartą +2 (ogień)", RacesEnum.REDGUARD),
                    new PerkEntity("Zastąp jedną kartę +1 jedną kartą +2 (ogień)", RacesEnum.REDGUARD),
                    new PerkEntity("Zastąp jedną kartę +1 jedną kartą +2 (światło)", RacesEnum.REDGUARD),
                    new PerkEntity("Zastąp jedną kartę +1 jedną kartą +2 (światło)", RacesEnum.REDGUARD),
                    new PerkEntity("Dodaj jedną kartę +1 (ogień)(światło)", RacesEnum.REDGUARD),
                    new PerkEntity("Dodaj jedną kartę +1 (ogień)(światło)", RacesEnum.REDGUARD),
                    new PerkEntity("Dodaj jedną kartę +1 (tarcza 1)", RacesEnum.REDGUARD),
                    new PerkEntity("Dodaj jedną kartę +1 (tarcza 1)", RacesEnum.REDGUARD),
                    new PerkEntity("Zastąp jedną kartę 0 jedną kartą +1 (unieruchomienie)", RacesEnum.REDGUARD),
                    new PerkEntity("Zastąp jedną kartę 0 jedną kartą +1 (zranienie)", RacesEnum.REDGUARD),

                    new PerkEntity("Usuń dwie kart -1", RacesEnum.VOIDWARDEN),
                    new PerkEntity("Usuń jedną kartę -2", RacesEnum.VOIDWARDEN),
                    new PerkEntity("Zastąp jedną kartę 0 jedną kartą +1 (ciemność)", RacesEnum.VOIDWARDEN),
                    new PerkEntity("Zastąp jedną kartę 0 jedną kartą +1 (ciemność)", RacesEnum.VOIDWARDEN),
                    new PerkEntity("Zastąp jedną kartę 0 jedną kartą +1 (mróz)", RacesEnum.VOIDWARDEN),
                    new PerkEntity("Zastąp jedną kartę 0 jedną kartą +1 (mróz)", RacesEnum.VOIDWARDEN),
                    new PerkEntity("Zastąp jedną kartę -1 jedną kartą 0 (leczenie 1 sojusznik)", RacesEnum.VOIDWARDEN),
                    new PerkEntity("Zastąp jedną kartę -1 jedną kartą 0 (leczenie 1 sojusznik)", RacesEnum.VOIDWARDEN),
                    new PerkEntity("Dodaj jedną kartą +1 (leczenie 1 sojusznik)", RacesEnum.VOIDWARDEN),
                    new PerkEntity("Dodaj jedną kartą +1 (leczenie 1 sojusznik)", RacesEnum.VOIDWARDEN),
                    new PerkEntity("Dodaj jedną kartą +1 (leczenie 1 sojusznik)", RacesEnum.VOIDWARDEN),
                    new PerkEntity("Dodaj jedną kartą +1 (zatrucie)", RacesEnum.VOIDWARDEN),
                    new PerkEntity("Dodaj jedną kartą +3", RacesEnum.VOIDWARDEN),
                    new PerkEntity("Dodaj jedną kartą +1 (klątwa)", RacesEnum.VOIDWARDEN),
                    new PerkEntity("Dodaj jedną kartą +1 (klątwa)", RacesEnum.VOIDWARDEN),

                    new PerkEntity("Usuń dwie karty -1", RacesEnum.HATCHET),
                    new PerkEntity("Usuń dwie karty -1", RacesEnum.HATCHET),
                    new PerkEntity("Zastąp jedną kartę 0 jedną kartą +2 (zamroczenie)", RacesEnum.HATCHET),
                    new PerkEntity("Zastąp jedną kartę 0 jedną kartą +1 (zatrucie)", RacesEnum.HATCHET),
                    new PerkEntity("Zastąp jedną kartę 0 jedną kartą +1 (zranienie)", RacesEnum.HATCHET),
                    new PerkEntity("Zastąp jedną kartę 0 jedną kartą +1 (unieruchomienie)", RacesEnum.HATCHET),
                    new PerkEntity("Zastąp jedną kartę 0 jedną kartą +1 (odepchnięcie 2)", RacesEnum.HATCHET),
                    new PerkEntity("Zastąp jedną kartę 0 jedną kartą 0 (ogłuszenie)", RacesEnum.HATCHET),
                    new PerkEntity("Zastąp jedną kartę +1 jedną kartą +1 (ogłuszenie)", RacesEnum.HATCHET),
                    new PerkEntity("Dodaj jedną kartę +2 (wiatr)", RacesEnum.HATCHET),
                    new PerkEntity("Dodaj jedną kartę +2 (wiatr)", RacesEnum.HATCHET),
                    new PerkEntity("Dodaj jedną kartę +2 (wiatr)", RacesEnum.HATCHET),
                    new PerkEntity("Zastąp jedną kartę +1 jedną kartą +3", RacesEnum.HATCHET),
                    new PerkEntity("Zastąp jedną kartę +1 jedną kartą +3", RacesEnum.HATCHET),
                    new PerkEntity("Zastąp jedną kartę +1 jedną kartą +3", RacesEnum.HATCHET),

                    new PerkEntity("Usuń cztery karty 0", RacesEnum.DEMOLITIONIST),
                    new PerkEntity("Usuń dwie karty -1", RacesEnum.DEMOLITIONIST),
                    new PerkEntity("Usuń dwie karty -1", RacesEnum.DEMOLITIONIST),
                    new PerkEntity("Usuń jedną kartę -2 oraz jedną kartę +1", RacesEnum.DEMOLITIONIST),
                    new PerkEntity("Zastąp jedną kartę 0 jedną kartą +2 (zmroczenie)", RacesEnum.DEMOLITIONIST),
                    new PerkEntity("Zastąp jedną kartę 0 jedną kartą +2 (zmroczenie)", RacesEnum.DEMOLITIONIST),
                    new PerkEntity("Zastąp jedną kartę -1 jedną kartą 0 (zatrucie)", RacesEnum.DEMOLITIONIST),
                    new PerkEntity("Dodaj jedną kartę +2", RacesEnum.DEMOLITIONIST),
                    new PerkEntity("Dodaj jedną kartę +2", RacesEnum.DEMOLITIONIST),
                    new PerkEntity("Zastąp jedną kartę +1 jedną kartą +2 (natura)", RacesEnum.DEMOLITIONIST),
                    new PerkEntity("Zastąp jedną kartę +1 jedną kartą +2 (natura)", RacesEnum.DEMOLITIONIST),
                    new PerkEntity("Zastąp jedną kartę +1 jedną kartą +2 (ogień)", RacesEnum.DEMOLITIONIST),
                    new PerkEntity("Zastąp jedną kartę +1 jedną kartą +2 (ogień)", RacesEnum.DEMOLITIONIST),
                    new PerkEntity("Dodaj jedną kartę 0 Wszyscy sąsiadujący przeciwnicy otrzymują 1 obrażenie)", RacesEnum.DEMOLITIONIST)
            ));
        }
    }
    private void addCardsToDb(){
        if (!cardService.getAllCards().isEmpty()){
            return;
        }
        cardService.addCards(List.of(
                new CardEntity("Dorzut", 0, RacesEnum.HATCHET),
                new CardEntity("Pakiet opiekuńczy", 0, RacesEnum.HATCHET),
                new CardEntity("Gustowny kapelusz", 0, RacesEnum.HATCHET),
                new CardEntity("Bliskie cięcia", 1, RacesEnum.HATCHET),
                new CardEntity("Środek ciężkości", 1, RacesEnum.HATCHET),
                new CardEntity("Zaostrzone klingi", 3, RacesEnum.HATCHET)
        ));
    }
}
