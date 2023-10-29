package com.gloomhaven.helper.service;

import com.gloomhaven.helper.model.entities.ItemEntity;
import com.gloomhaven.helper.repository.ItemRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemService {
    private final ItemRepository itemRepository;

    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @Transactional
    public List<ItemEntity> findAll(){
        return itemRepository.findAll();
    }
    @Transactional
    public ItemEntity find(long id){
        return itemRepository.findAllById(id);
    }
    @Transactional
    public void save(ItemEntity item){
        itemRepository.save(item);
    }
    @Transactional
    public void saveAll(Iterable<ItemEntity> collection){
        itemRepository.saveAll(collection);
    }

}