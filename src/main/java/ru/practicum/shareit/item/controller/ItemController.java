package ru.practicum.shareit.item.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.service.ItemService;
import ru.practicum.shareit.user.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/items")
@AllArgsConstructor
public class ItemController {
    private final ItemService itemService;
    private final UserService userService;

    @PostMapping
    public ItemDto createItem(@RequestHeader(name = "X-Sharer-User-Id") Long ownerId, @Valid @RequestBody Item item) {
        userService.getUserById(ownerId);
        item.setOwnerId(ownerId);
        return itemService.createItem(item);
    }

    @PatchMapping("/{itemId}")
    public ItemDto updateItem(@PathVariable Long itemId, @RequestBody ItemDto item,
                              @RequestHeader(name = "X-Sharer-User-Id") Long ownerId) {
        userService.getUserById(ownerId);

        item.setId(itemId);
        item.setOwnerId(ownerId);

        return itemService.updateItem(item);
    }

    @GetMapping
    public List<ItemDto> getAllItemByOwner(@RequestHeader(name = "X-Sharer-User-Id") Long ownerId) {
        userService.getUserById(ownerId);
        return itemService.getAllItemByOwnerId(ownerId);
    }

    @GetMapping("/{itemId}")
    public ItemDto getItemById(@PathVariable Long itemId) {
        return itemService.getItemById(itemId);
    }

    @GetMapping("/search")
    public List<ItemDto> searchItemsByText(@RequestParam String text) {
        return itemService.searchItemsByText(text);
    }
}