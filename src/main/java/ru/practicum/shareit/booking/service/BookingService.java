package ru.practicum.shareit.booking.service;

import ru.practicum.shareit.booking.dto.BookingCreate;
import ru.practicum.shareit.booking.dto.BookingDto;

public interface BookingService {

    BookingDto createBooking(BookingCreate booking, Long userId);

    BookingDto acceptBooking(Long ownerId, Long bookingId, Boolean approved);

    BookingDto getBookingById(Long userId, Long bookingId);
}
