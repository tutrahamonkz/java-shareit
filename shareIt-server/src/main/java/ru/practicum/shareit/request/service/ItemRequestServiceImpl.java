package ru.practicum.shareit.request.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.item.dto.ItemDtoForRequest;
import ru.practicum.shareit.item.mapper.ItemMapper;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.request.ItemRequestMapper;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.request.dto.ItemRequestDtoWithItems;
import ru.practicum.shareit.request.model.ItemRequest;
import ru.practicum.shareit.request.repository.ItemRequestRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ItemRequestServiceImpl implements ItemRequestService {

    private final ItemRequestRepository itemRequestRepository;
    private final ItemRepository itemRepository;

    @Override
    public ItemRequestDto createItemRequest(ItemRequestDto itemRequestDto, Long requestorId) {
        itemRequestDto.setRequestor(requestorId);
        itemRequestDto.setCreated(LocalDateTime.now());
        return ItemRequestMapper.toItemRequestDto(itemRequestRepository
                .save(ItemRequestMapper.toItemRequest(itemRequestDto)));
    }

    @Override
    public List<ItemRequestDtoWithItems> getMyItemRequests(Long userId) {
        List<ItemRequest> itemRequests = itemRequestRepository.findByRequestor(userId);
        List<Long> itemIds = itemRequests.stream().map(ItemRequest::getId).toList();
        Map<Long, List<ItemDtoForRequest>> items = itemRepository.findAllByRequestIdIn(itemIds).stream()
                .collect(Collectors.groupingBy(Item::getRequestId,
                        Collectors.mapping(ItemMapper::toItemDtoForRequest, Collectors.toList())));

        return itemRequests.stream()
                .map(itemRequest -> ItemRequestMapper.
                        toItemRequestDtoWithItems(itemRequest, items.get(itemRequest.getId())))
                .toList();
    }

    @Override
    public List<ItemRequestDto> getAllOtherItemRequests(Long userId) {
        return ItemRequestMapper.mapToItemRequestDto(itemRequestRepository.findByIdIsNotOrderByCreatedDesc(userId));
    }

    @Override
    public ItemRequestDtoWithItems getItemRequestById(Long requestId) {
        List<ItemDtoForRequest> itemDtoList = ItemMapper.toItemDtos(itemRepository.findAllByRequestId(requestId));
        return ItemRequestMapper.toItemRequestDtoWithItems(itemRequestRepository.findById(requestId)
                .orElseThrow(() -> new NotFoundException("Не найден запрос с id: " + requestId)), itemDtoList);
    }
}