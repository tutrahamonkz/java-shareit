package ru.practicum.shareit.booking;

import ru.practicum.shareit.booking.dto.BookingCreate;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.BookingStatus;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;

import java.util.ArrayList;
import java.util.List;

public class BookingMapper {

    // Преобразует объект BookingDto в объект Booking.
    public static Booking toBooking(BookingDto bookingDto, Item item, User booker) {
        Booking booking = new Booking();
        booking.setStart(bookingDto.getStart());
        booking.setEnd(bookingDto.getEnd());
        booking.setItem(item);
        booking.setBooker(booker);
        booking.setStatus(bookingDto.getStatus());
        return booking;
    }

    // Преобразует объект Booking в объект BookingDto.
    public static BookingDto toBookingDto(Booking booking) {
        BookingDto bookingDto = new BookingDto();
        bookingDto.setId(booking.getId());
        bookingDto.setStart(booking.getStart());
        bookingDto.setEnd(booking.getEnd());
        bookingDto.setItem(booking.getItem());
        bookingDto.setBooker(booking.getBooker());
        bookingDto.setStatus(booking.getStatus());
        return bookingDto;
    }

    // Преобразует объект BookingCreate в объект Booking.
    public static Booking mapToBooking(BookingCreate bookingCreate, Item item, User booker) {
        Booking booking = new Booking();
        booking.setStart(bookingCreate.getStart());
        booking.setEnd(bookingCreate.getEnd());
        booking.setItem(item);
        booking.setBooker(booker);
        booking.setStatus(BookingStatus.WAITING);
        return booking;
    }

    // Преобразует список объектов UserDto в список объектов User.
    public static List<BookingDto> mapToBookingDto(Iterable<Booking> bookings) {
        List<BookingDto> dtos = new ArrayList<>();
        for (Booking booking : bookings) {
            dtos.add(toBookingDto(booking));
        }
        return dtos;
    }
}