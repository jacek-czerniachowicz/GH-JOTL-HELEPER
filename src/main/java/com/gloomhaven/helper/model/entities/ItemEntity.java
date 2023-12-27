package com.gloomhaven.helper.model.entities;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor

@Table(name = "items")
public class ItemEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "item_data")
    private ItemEnum itemData;

    @ManyToOne
    @JoinColumn(name = "hero_id", referencedColumnName = "id")
    private HeroEntity hero;

    @ManyToOne
    @JoinColumn(name = "room_id", referencedColumnName = "id")
    private RoomEntity room;

    private boolean isEquipped;

    public ItemEntity(ItemEnum itemData) {
        this.itemData = itemData;
    }

    public ItemEntity(ItemEnum itemData, RoomEntity room) {
        this.itemData = itemData;
        this.room = room;
        isEquipped = false;
    }

    public void changeEquip() {
        this.isEquipped = !isEquipped;
    }
}
