package ru.practicum.shareit.request.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import ru.practicum.shareit.validation.CreateValidationGroup;

import java.time.LocalDateTime;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ItemRequestRequestDto {

    @NotBlank(groups = CreateValidationGroup.class)
    private String description;
    private Long requestor;
    private LocalDateTime created;
}
