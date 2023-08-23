package com.gloomhaven.helper.boot;

import com.gloomhaven.helper.model.entities.PerkEntity;
import com.gloomhaven.helper.model.entities.Races;
import com.gloomhaven.helper.repository.PerkRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataLoader implements CommandLineRunner {
    private final PerkRepository perkRepository;

    public DataLoader(PerkRepository perkRepository) {
        this.perkRepository = perkRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        addPerksToDb();
    }
    private void addPerksToDb(){
        if (perkRepository.findAll().size() == 0) {
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
