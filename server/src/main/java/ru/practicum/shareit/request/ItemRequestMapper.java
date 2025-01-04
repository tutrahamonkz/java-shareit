package ru.practicum.shareit.request;

import ru.practicum.shareit.item.dto.ItemDtoForRequest;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.request.dto.ItemRequestDtoWithItems;
import ru.practicum.shareit.request.model.ItemRequest;

import java.util.ArrayList;
import java.util.List;

public class ItemRequestMapper {
    public static ItemRequest toItemRequest(ItemRequestDto itemRequestDto) {
        ItemRequest itemRequest = new ItemRequest();
        itemRequest.setDescription(itemRequestDto.getDescription());
        itemRequest.setRequestor(itemRequestDto.getRequestor());
        itemRequest.setCreated(itemRequestDto.getCreated());
        return itemRequest;
    }

    public static ItemRequestDto toItemRequestDto(ItemRequest itemRequest) {
        ItemRequestDto itemRequestDto = new ItemRequestDto();
        itemRequestDto.setId(itemRequest.getId());
        itemRequestDto.setDescription(itemRequest.getDescription());
        itemRequestDto.setRequestor(itemRequest.getRequestor());
        itemRequestDto.setCreated(itemRequest.getCreated());
        return itemRequestDto;
    }

    public static List<ItemRequestDto> mapToItemRequestDto(List<ItemRequest> itemRequestList) {
        List<ItemRequestDto> itemRequestDtoList = new ArrayList<>();
        for (ItemRequest itemRequest : itemRequestList) {
            itemRequestDtoList.add(toItemRequestDto(itemRequest));
        }
        return itemRequestDtoList;
    }

    public static ItemRequestDtoWithItems toItemRequestDtoWithItems(ItemRequest itemRequest,
                                                                    List<ItemDtoForRequest> itemDtoList) {
        ItemRequestDtoWithItems itemRequestDtoWithItems = new ItemRequestDtoWithItems();
        itemRequestDtoWithItems.setId(itemRequest.getId());
        itemRequestDtoWithItems.setDescription(itemRequest.getDescription());
        itemRequestDtoWithItems.setRequestor(itemRequest.getRequestor());
        itemRequestDtoWithItems.setCreated(itemRequest.getCreated());
        itemRequestDtoWithItems.setItems(itemDtoList);
        return itemRequestDtoWithItems;
    }
}
