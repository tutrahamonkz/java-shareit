package ru.practicum.shareit.request.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
import ru.practicum.shareit.user.service.UserService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class ItemRequestServiceImpl implements ItemRequestService {

    private final ItemRequestRepository itemRequestRepository;
    private final ItemRepository itemRepository;
    private final UserService userService;

    @Override
    public ItemRequestDto createItemRequest(ItemRequestDto itemRequestDto, Long requestorId) {

        userService.getUserById(requestorId);

        log.info("Создание нового запроса на предмет: {}, от пользователя: {}", itemRequestDto, requestorId);
        itemRequestDto.setRequestor(requestorId);
        itemRequestDto.setCreated(LocalDateTime.now());
        return ItemRequestMapper.toItemRequestDto(itemRequestRepository
                .save(ItemRequestMapper.toItemRequest(itemRequestDto)));
    }

    @Override
    public List<ItemRequestDtoWithItems> getMyItemRequests(Long userId) {

        userService.getUserById(userId);

        log.info("Получение списка запросов пользователя: {}", userId);
        List<ItemRequest> itemRequests = itemRequestRepository.findByRequestor(userId);
        List<Long> itemIds = itemRequests.stream().map(ItemRequest::getId).toList();
        Map<Long, List<ItemDtoForRequest>> items = itemRepository.findAllByRequestIdIn(itemIds).stream()
                .collect(Collectors.groupingBy(Item::getRequestId,
                        Collectors.mapping(ItemMapper::toItemDtoForRequest, Collectors.toList())));

        return itemRequests.stream()
                .map(itemRequest -> ItemRequestMapper
                        .toItemRequestDtoWithItems(itemRequest, items.get(itemRequest.getId())))
                .toList();
    }

    @Override
    public List<ItemRequestDto> getAllOtherItemRequests(Long userId) {

        log.info("Получение списка всех запросов других пользователей, от пользователя: {}", userId);
        return ItemRequestMapper.mapToItemRequestDto(itemRequestRepository.findByIdIsNotOrderByCreatedDesc(userId));
    }

    @Override
    public ItemRequestDtoWithItems getItemRequestById(Long requestId) {
        ItemRequest itemRequest = itemRequestRepository.findById(requestId)
                .orElseThrow(() -> new NotFoundException("Не найден запрос с id: " + requestId));

        log.info("Получение данных о запросе: {}", requestId);
        List<ItemDtoForRequest> itemDtoList = ItemMapper.toItemDtos(itemRepository.findAllByRequestId(requestId));
        return ItemRequestMapper.toItemRequestDtoWithItems(itemRequest, itemDtoList);
    }
}