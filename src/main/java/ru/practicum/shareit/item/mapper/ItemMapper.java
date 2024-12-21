package ru.practicum.shareit.item.mapper;

import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemDtoBooking;
import ru.practicum.shareit.item.model.Item;

import java.util.List;
import java.util.Optional;

public class ItemMapper {
    // Преобразует объект Item в объект ItemDto.
    public static ItemDto toItemDto(Item item) {
        return ru.practicum.shareit.item.dto.ItemDto.builder()
                .id(item.getId())
                .name(item.getName())
                .description(item.getDescription())
                .available(item.getAvailable())
                .ownerId(item.getOwnerId())
                .requestId(item.getRequestId())
                .build();
    }

    public static ItemDto toItemDto(Item item, List<CommentDto> comments) {
        return ru.practicum.shareit.item.dto.ItemDto.builder()
                .id(item.getId())
                .name(item.getName())
                .description(item.getDescription())
                .available(item.getAvailable())
                .ownerId(item.getOwnerId())
                .requestId(item.getRequestId())
                .comments(comments)
                .build();
    }

    // Обновляет объект Item на основе данных из ItemDto.
    // Если поля в ItemDto отсутствуют, используются существующие значения из Item.
    public static Item toItemOnUpdate(ItemDto itemDto, Item item) {
        Item newItem = new Item();
        newItem.setId(itemDto.getId());
        newItem.setName(Optional.ofNullable(itemDto.getName()).orElse(item.getName()));
        newItem.setDescription(Optional.ofNullable(itemDto.getDescription()).orElse(item.getDescription()));
        newItem.setAvailable(Optional.ofNullable(itemDto.getAvailable()).orElse(item.getAvailable()));
        newItem.setOwnerId(item.getOwnerId());
        newItem.setRequestId(item.getRequestId());
        return newItem;
       /* return Item.builder()
                .id(itemDto.getId())
                .name(Optional.ofNullable(itemDto.getName()).orElse(item.getName()))
                .description(Optional.ofNullable(itemDto.getDescription()).orElse(item.getDescription()))
                .available(Optional.ofNullable(itemDto.getAvailable()).orElse(item.getAvailable()))
                .ownerId(item.getOwnerId())
                .requestId(item.getRequestId())
                .build();*/
    }

    // Преобразует объект ItemDto в объект Item.
    public static Item toItem(ItemDto itemDto) {
        Item newItem = new Item();
        newItem.setId(itemDto.getId());
        newItem.setName(itemDto.getName());
        newItem.setDescription(itemDto.getDescription());
        newItem.setAvailable(itemDto.getAvailable());
        newItem.setOwnerId(itemDto.getOwnerId());
        newItem.setRequestId(itemDto.getRequestId());
        return newItem;
        /*return Item.builder()
                .name(itemDto.getName())
                .description(itemDto.getDescription())
                .available(itemDto.getAvailable())
                .ownerId(itemDto.getOwnerId())
                .requestId(itemDto.getRequestId())
                .build();*/
    }

    public static ItemDtoBooking toItemDtoBooking(Item item, Booking lastBooking, Booking nextBooking,
                                                  List<CommentDto> comments) {
        ItemDtoBooking dto = new ItemDtoBooking();
        dto.setId(item.getId());
        dto.setName(item.getName());
        dto.setDescription(item.getDescription());
        dto.setAvailable(item.getAvailable());
        dto.setOwnerId(item.getOwnerId());
        dto.setRequestId(item.getRequestId());
        dto.setLastBooking(lastBooking);
        dto.setNextBooking(nextBooking);
        dto.setComments(comments);
        return dto;
    }
}