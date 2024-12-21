package ru.practicum.shareit.booking.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter @Setter @ToString
public class BookingCreate {
    private Long itemId;
    @Future @NotNull
    private LocalDateTime start;
    @Future @NotNull
    private LocalDateTime end;
}