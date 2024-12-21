package ru.practicum.shareit.item.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.repository.BookingRepository;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.item.ItemMapper;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemDtoBooking;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.user.service.UserService;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class ItemServiceImpl implements ItemService {
    private final ItemRepository itemRepository;
    private final UserService userService;
    private final BookingRepository bookingRepository;

    @Override
    public ItemDto createItem(ItemDto item) {
        userService.getUserById(item.getOwnerId());
        log.info("Создание нового предмета: {}", item);
        return ItemMapper.toItemDto(itemRepository.save(ItemMapper.toItem(item)));
    }

    @Override
    public List<ItemDtoBooking> getAllItemByOwnerId(Long ownerId) {
        log.info("Получение всех предметов по ID владельца: {}", ownerId);
        userService.getUserById(ownerId);
        List<Item> items = itemRepository.findByOwnerId(ownerId);
        List<ItemDtoBooking> dtos = new ArrayList<>();

        for (Item item : items) {
            Booking lastBooking = bookingRepository.findLastBooking(item.getId());
            Booking nextBooking = bookingRepository.findNextBooking(item.getId());
            dtos.add(ItemMapper.toItemDtoBooking(item, lastBooking, nextBooking));
        }

        return dtos;
    }

    @Override
    public ItemDto getItemById(Long itemId) {
        log.info("Получение предмета по ID: {}", itemId);
        return ItemMapper.toItemDto(findById(itemId));
    }

    @Override
    public ItemDto updateItem(ItemDto item) {
        log.info("Обновление предмета по ID: {}", item.getId());
        userService.getUserById(item.getOwnerId());
        return ItemMapper.toItemDto(itemRepository.save(ItemMapper.toItemOnUpdate(item, findById(item.getId()))));
    }

    @Override
    public void deleteItemByItemId(Long itemId) {
        log.info("Удаление предмета по ID: {}", itemId);
        itemRepository.deleteById(itemId);
    }

    @Override
    public List<ItemDto> searchItemsByText(String text) {
        if (text.isBlank()) {
            return new ArrayList<>();
        }
        log.info("Поиск предметов по тексту: {}", text);
        return itemRepository.findByText(text).stream()
                .map(ItemMapper::toItemDto)
                .toList();
    }

    private Item findById(Long itemId) {
        return itemRepository.findById(itemId)
                .orElseThrow(() -> new NotFoundException("Не найден предмет с id: " + itemId));
    }
}