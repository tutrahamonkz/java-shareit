package ru.practicum.shareit.item;

import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;

public class ItemMapper {
    public static ItemDto toItemDto(Item item) {
        return ItemDto.builder()
                .id(item.getId())
                .name(item.getName())
                .description(item.getDescription())
                .available(item.getAvailable())
                .ownerId(item.getOwnerId())
                .requestId(item.getRequestId())
                .build();
    }

    public static Item toItemOnUpdate(ItemDto itemDto, Item item) {
        String name = itemDto.getName();
        String description = itemDto.getDescription();
        Boolean available = itemDto.getAvailable();
        Item updateItem = Item.builder()
                .id(itemDto.getId())
                .ownerId(item.getOwnerId())
                .requestId(item.getRequestId())
                .build();
        if (name != null) {
            updateItem.setName(name);
        } else {
            updateItem.setName(item.getName());
        }
        if (description != null) {
            updateItem.setDescription(description);
        } else {
            updateItem.setDescription(item.getDescription());
        }
        if (available != null) {
            updateItem.setAvailable(available);
        } else {
            updateItem.setAvailable(item.getAvailable());
        }
        return updateItem;
    }
}