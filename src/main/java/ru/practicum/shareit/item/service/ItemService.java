package ru.practicum.shareit.item.service;

import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;

import java.util.List;

public interface ItemService {
    ItemDto createItem(Item item);
    List<ItemDto> getAllItemByOwnerId(Long ownerId);
    ItemDto getItemById(Long itemId);
    ItemDto updateItem(ItemDto item);
    void deleteItemByItemId(Long itemId);
    List<ItemDto> searchItemsByText(String text);
}
