package com.gloomhaven.helper.boot;

import com.gloomhaven.helper.model.dto.UserDTO;
import com.gloomhaven.helper.model.entities.*;
import com.gloomhaven.helper.repository.ItemRepository;
import com.gloomhaven.helper.repository.PerkRepository;
import com.gloomhaven.helper.repository.RoleRepository;
import com.gloomhaven.helper.repository.RoomRepository;
import com.gloomhaven.helper.service.RoomService;
import com.gloomhaven.helper.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

import static java.util.Arrays.asList;

@Component
public class DataLoader implements CommandLineRunner {
    @Autowired
    private final PerkRepository perkRepository;
    @Autowired
    private final ItemRepository itemRepository;
    @Autowired
    private final RoleRepository roleRepository;
    @Autowired
    private final UserService userService;
    private final RoomService roomService;
    private final RoomRepository roomRepository;

    public DataLoader(PerkRepository perkRepository, ItemRepository itemRepository, RoleRepository roleRepository, UserService userService, RoomService roomService, RoomRepository roomRepository) {
        this.perkRepository = perkRepository;
        this.itemRepository = itemRepository;
        this.roleRepository = roleRepository;
        this.userService = userService;
        this.roomService = roomService;
        this.roomRepository = roomRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        addPerksToDb();
        addItemsToDb();
        addRolesToDb();
        addAdminToDb();
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
        }
    }

    private void addTestRoomsToDb() {
        UserEntity root = userService.findByUsername("root");
        if(roomService.getRooms(root).isEmpty()) {
//            roomService.createRoom();


        }
    }

    private void addItemsToDb() {
        if(itemRepository.findAll().isEmpty()){
            itemRepository.saveAll(List.of(
                    new ItemEntity("Habadzibadlo"),
                    new ItemEntity("Papiesz"),
                    new ItemEntity("Gumowe Jebadlo"),
                    new ItemEntity("Groszek")
            ));
        }
    }

    private void addPerksToDb(){
        if (perkRepository.findAll().isEmpty()) {
            perkRepository.saveAll(List.of(
                    new PerkEntity("Usuń cztery karty 0", Races.REDGUARD),
                    new PerkEntity("Usuń dwie kart -1", Races.REDGUARD),
                    new PerkEntity("Usuń jedną kartę -2 oraz jedną kartę +1", Races.REDGUARD),
                    new PerkEntity("Usuń jedną kartę -2 oraz jedną kartę +1", Races.REDGUARD),
                    new PerkEntity("Zastąp jedną kartę +1 jedną kartą +2 (ogień)", Races.REDGUARD),
                    new PerkEntity("Zastąp jedną kartę +1 jedną kartą +2 (ogień)", Races.REDGUARD),
                    new PerkEntity("Zastąp jedną kartę +1 jedną kartą +2 (światło)", Races.REDGUARD),
                    new PerkEntity("Zastąp jedną kartę +1 jedną kartą +2 (światło)", Races.REDGUARD),
                    new PerkEntity("Dodaj jedną kartę +1 (ogień)(światło)", Races.REDGUARD),
                    new PerkEntity("Dodaj jedną kartę +1 (ogień)(światło)", Races.REDGUARD),
                    new PerkEntity("Dodaj jedną kartę +1 (tarcza 1)", Races.REDGUARD),
                    new PerkEntity("Dodaj jedną kartę +1 (tarcza 1)", Races.REDGUARD),
                    new PerkEntity("Zastąp jedną kartę 0 jedną kartą +1 (unieruchomienie)", Races.REDGUARD),
                    new PerkEntity("Zastąp jedną kartę 0 jedną kartą +1 (zranienie)", Races.REDGUARD),

                    new PerkEntity("Usuń dwie kart -1", Races.VOIDWARDEN),
                    new PerkEntity("Usuń jedną kartę -2", Races.VOIDWARDEN),
                    new PerkEntity("Zastąp jedną kartę 0 jedną kartą +1 (ciemność)", Races.VOIDWARDEN),
                    new PerkEntity("Zastąp jedną kartę 0 jedną kartą +1 (ciemność)", Races.VOIDWARDEN),
                    new PerkEntity("Zastąp jedną kartę 0 jedną kartą +1 (mróz)", Races.VOIDWARDEN),
                    new PerkEntity("Zastąp jedną kartę 0 jedną kartą +1 (mróz)", Races.VOIDWARDEN),
                    new PerkEntity("Zastąp jedną kartę -1 jedną kartą 0 (leczenie 1 sojusznik)", Races.VOIDWARDEN),
                    new PerkEntity("Zastąp jedną kartę -1 jedną kartą 0 (leczenie 1 sojusznik)", Races.VOIDWARDEN),
                    new PerkEntity("Dodaj jedną kartą +1 (leczenie 1 sojusznik)", Races.VOIDWARDEN),
                    new PerkEntity("Dodaj jedną kartą +1 (leczenie 1 sojusznik)", Races.VOIDWARDEN),
                    new PerkEntity("Dodaj jedną kartą +1 (leczenie 1 sojusznik)", Races.VOIDWARDEN),
                    new PerkEntity("Dodaj jedną kartą +1 (zatrucie)", Races.VOIDWARDEN),
                    new PerkEntity("Dodaj jedną kartą +3", Races.VOIDWARDEN),
                    new PerkEntity("Dodaj jedną kartą +1 (klątwa)", Races.VOIDWARDEN),
                    new PerkEntity("Dodaj jedną kartą +1 (klątwa)", Races.VOIDWARDEN),

                    new PerkEntity("Usuń dwie karty -1", Races.HATCHET),
                    new PerkEntity("Usuń dwie karty -1", Races.HATCHET),
                    new PerkEntity("Zastąp jedną kartę 0 jedną kartą +2 (zamroczenie)", Races.HATCHET),
                    new PerkEntity("Zastąp jedną kartę 0 jedną kartą +1 (zatrucie)", Races.HATCHET),
                    new PerkEntity("Zastąp jedną kartę 0 jedną kartą +1 (zranienie)", Races.HATCHET),
                    new PerkEntity("Zastąp jedną kartę 0 jedną kartą +1 (unieruchomienie)", Races.HATCHET),
                    new PerkEntity("Zastąp jedną kartę 0 jedną kartą +1 (odepchnięcie 2)", Races.HATCHET),
                    new PerkEntity("Zastąp jedną kartę 0 jedną kartą 0 (ogłuszenie)", Races.HATCHET),
                    new PerkEntity("Zastąp jedną kartę +1 jedną kartą +1 (ogłuszenie)", Races.HATCHET),
                    new PerkEntity("Dodaj jedną kartę +2 (wiatr)", Races.HATCHET),
                    new PerkEntity("Dodaj jedną kartę +2 (wiatr)", Races.HATCHET),
                    new PerkEntity("Dodaj jedną kartę +2 (wiatr)", Races.HATCHET),
                    new PerkEntity("Zastąp jedną kartę +1 jedną kartą +3", Races.HATCHET),
                    new PerkEntity("Zastąp jedną kartę +1 jedną kartą +3", Races.HATCHET),
                    new PerkEntity("Zastąp jedną kartę +1 jedną kartą +3", Races.HATCHET),

                    new PerkEntity("Usuń cztery karty 0", Races.DEMOLITIONIST),
                    new PerkEntity("Usuń dwie karty -1", Races.DEMOLITIONIST),
                    new PerkEntity("Usuń dwie karty -1", Races.DEMOLITIONIST),
                    new PerkEntity("Usuń jedną kartę -2 oraz jedną kartę +1", Races.DEMOLITIONIST),
                    new PerkEntity("Zastąp jedną kartę 0 jedną kartą +2 (zmroczenie)", Races.DEMOLITIONIST),
                    new PerkEntity("Zastąp jedną kartę 0 jedną kartą +2 (zmroczenie)", Races.DEMOLITIONIST),
                    new PerkEntity("Zastąp jedną kartę -1 jedną kartą 0 (zatrucie)", Races.DEMOLITIONIST),
                    new PerkEntity("Dodaj jedną kartę +2", Races.DEMOLITIONIST),
                    new PerkEntity("Dodaj jedną kartę +2", Races.DEMOLITIONIST),
                    new PerkEntity("Zastąp jedną kartę +1 jedną kartą +2 (natura)", Races.DEMOLITIONIST),
                    new PerkEntity("Zastąp jedną kartę +1 jedną kartą +2 (natura)", Races.DEMOLITIONIST),
                    new PerkEntity("Zastąp jedną kartę +1 jedną kartą +2 (ogień)", Races.DEMOLITIONIST),
                    new PerkEntity("Zastąp jedną kartę +1 jedną kartą +2 (ogień)", Races.DEMOLITIONIST),
                    new PerkEntity("Dodaj jedną kartę 0 Wszyscy sąsiadujący przeciwnicy otrzymują 1 obrażenie)", Races.DEMOLITIONIST)
            ));
        }
    }
}
