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
    private ItemEnum itemEnum;

    @ManyToOne
    @JoinColumn(name = "hero_id", referencedColumnName = "id")
    private HeroEntity hero;

    @ManyToOne
    @JoinColumn(name = "room_id", referencedColumnName = "id")
    private RoomEntity room;

    public ItemEntity(ItemEnum itemEnum) {
        this.itemEnum = itemEnum;
    }
}
