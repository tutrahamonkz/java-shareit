package ru.practicum.shareit.request.service;

import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.request.dto.ItemRequestDtoWithItems;

import java.util.List;

public interface ItemRequestService {

    ItemRequestDto createItemRequest(ItemRequestDto itemRequestDto, Long requestorId);

    List<ItemRequestDtoWithItems> getMyItemRequests(Long userId);

    List<ItemRequestDto> getAllOtherItemRequests(Long userId);

    ItemRequestDtoWithItems getItemRequestById(Long requestId);
}
