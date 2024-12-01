package ru.practicum.shareit.item.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.item.ItemMapper;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.repository.ItemRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class ItemServiceImpl implements ItemService {
    private final ItemRepository itemRepository;

    @Override
    public ItemDto createItem(Item item) {
        return ItemMapper.toItemDto(itemRepository.createItem(item));
    }

    @Override
    public List<ItemDto> getAllItemByOwnerId(Long ownerId) {
        return itemRepository.getAllItemByOwnerId(ownerId).stream()
                .map(ItemMapper::toItemDto)
                .toList();
    }

    @Override
    public ItemDto getItemById(Long itemId) {
        return ItemMapper.toItemDto(findById(itemId));
    }

    @Override
    public ItemDto updateItem(ItemDto item) {
        return ItemMapper.toItemDto(itemRepository.updateItem(ItemMapper.toItemOnUpdate(item, findById(item.getId()))));
    }

    @Override
    public void deleteItemByItemId(Long itemId) {
        itemRepository.deleteItemByItemId(itemId);
    }

    private Item findById(Long itemId) {
        return itemRepository.getItemById(itemId)
                .orElseThrow(() -> new NotFoundException("Не найден предмет с id: " + itemId));
    }

    @Override
    public List<ItemDto> searchItemsByText(String text) {
        return itemRepository.searchItemsByText(text).stream()
                .map(ItemMapper::toItemDto)
                .toList();
    }
}
