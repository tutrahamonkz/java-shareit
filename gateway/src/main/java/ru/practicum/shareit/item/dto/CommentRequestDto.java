package ru.practicum.shareit.item.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CommentRequestDto {

    private String text;
    private Long itemId;
    private String authorName;
    private LocalDateTime created;
}
