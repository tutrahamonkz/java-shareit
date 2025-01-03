package ru.practicum.shareit.request;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.request.dto.ItemRequestDtoWithItems;
import ru.practicum.shareit.request.service.ItemRequestService;

import java.util.List;

@RestController
@RequestMapping(path = "/requests")
@AllArgsConstructor
public class ItemRequestController {

    private final ItemRequestService itemRequestService;

    @PostMapping
    public ItemRequestDto createItemRequest(@RequestHeader(name = "X-Sharer-User-Id") Long userId,
                                            @Valid @RequestBody ItemRequestDto itemRequestDto) {
        return itemRequestService.createItemRequest(itemRequestDto, userId);
    }

    @GetMapping
    public List<ItemRequestDtoWithItems> getMyItemRequests(@RequestHeader(name = "X-Sharer-User-Id") Long userId) {
        return itemRequestService.getMyItemRequests(userId);
    }

    @GetMapping("/all")
    public List<ItemRequestDto> getAllOtherItemRequests(@RequestHeader(name = "X-Sharer-User-Id") Long userId) {
        return itemRequestService.getAllOtherItemRequests(userId);
    }

    @GetMapping("/{requestId}")
    public ItemRequestDtoWithItems getItemRequestById(@PathVariable Long requestId) {
        return itemRequestService.getItemRequestById(requestId);
    }
}