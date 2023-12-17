package com.gloomhaven.helper.boot;

import com.gloomhaven.helper.model.dto.UserDTO;
import com.gloomhaven.helper.model.entities.CardEntity;
import com.gloomhaven.helper.model.entities.PerkEntity;
import com.gloomhaven.helper.model.entities.RacesEnum;
import com.gloomhaven.helper.repository.PerkRepository;
import com.gloomhaven.helper.service.CardService;
import com.gloomhaven.helper.service.user.IUserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataLoader implements CommandLineRunner {
    private final PerkRepository perkRepository;
//    private final RoleRepository roleRepository;
    private final IUserService IUserService;
    private final CardService cardService;


    public DataLoader(PerkRepository perkRepository,
//                      RoleRepository roleRepository,
                      IUserService IUserService, CardService cardService) {
        this.perkRepository = perkRepository;
//        this.roleRepository = roleRepository;
        this.IUserService = IUserService;
        this.cardService = cardService;
    }

    @Override
    public void run(String... args) {
        addPerksToDb();
//        addRolesToDb();
//        addAdminAndUserToDb();
        addCardsToDb();
    }

//    private void addRolesToDb() {
//        if(roleRepository.findAll().isEmpty()) {
//            RoleEntity roleUser = new RoleEntity("123", "ROLE_USER");
//            RoleEntity roleAdmin = new RoleEntity("420", "ROLE_ADMIN");
//            roleRepository.saveAll(asList(roleUser, roleAdmin));
//        }
//    }

    private void addAdminAndUserToDb() {
        if(IUserService.getUsers().isEmpty()){
            UserDTO root = new UserDTO("habaz@ibadlo.com", "root", "toor");
            IUserService.createUser(root);
            IUserService.setAdminRole(root.getUsername());

            UserDTO user = new UserDTO("user@email.com", "user", "user");
            IUserService.createUser(user);
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
