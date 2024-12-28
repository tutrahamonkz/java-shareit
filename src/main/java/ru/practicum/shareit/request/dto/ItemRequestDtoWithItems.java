package ru.practicum.shareit.request.dto;

import lombok.Getter;
import lombok.Setter;
import ru.practicum.shareit.item.dto.ItemDtoForRequest;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class ItemRequestDtoWithItems {

    private Long id;

    private String description;

    private Long requestor;

    private LocalDateTime created;

    private List<ItemDtoForRequest> items;
}
