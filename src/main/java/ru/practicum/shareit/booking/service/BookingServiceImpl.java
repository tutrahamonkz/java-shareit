package ru.practicum.shareit.booking.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.booking.BookingMapper;
import ru.practicum.shareit.booking.dto.BookingCreate;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.BookingStatus;
import ru.practicum.shareit.booking.repository.BookingRepository;
import ru.practicum.shareit.exception.BadRequestException;
import ru.practicum.shareit.exception.ForbiddenException;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.exception.UnAvaliableException;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.repository.UserRepository;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public BookingDto createBooking(BookingCreate bookingCreate, Long userId) {
        log.info("Создание нового резервирования: {}, пользователем с id: {}", bookingCreate, userId);
        User booker = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Не найден пользователь с id: " + userId));
        Item item = itemRepository.findById(bookingCreate.getItemId())
                .orElseThrow(() -> new NotFoundException("Не найден предмет с id: " + userId));
        if (bookingCreate.getStart().equals(bookingCreate.getEnd())) {
            throw new BadRequestException("Дата начала бронирования не должна совпадать " +
                    "с дадой окончания бронирования");
        }
        if (!item.getAvailable()) {
            throw new UnAvaliableException("Для бронирования не доступен предмет с id: " + item.getId());
        }
        Booking booking = bookingRepository.save(BookingMapper.mapToBooking(bookingCreate, item, booker));
        return BookingMapper.toBookingDto(booking);
    }

    @Override
    public BookingDto acceptBooking(Long ownerId, Long bookingId, Boolean approved) {
        log.info("Подтверждение резервирования по ID: {}", bookingId);
        Booking booking = findById(bookingId);
        if (!ownerId.equals(booking.getItem().getOwnerId())) {
            throw new ForbiddenException("Изменять статус может только владелец предмета");
        }
        if (approved) {
            booking.setStatus(BookingStatus.APPROVED);
        } else {
            booking.setStatus(BookingStatus.REJECTED);
        }
        return BookingMapper.toBookingDto(bookingRepository.save(booking));
    }

    @Override
    public BookingDto getBookingById(Long userId, Long bookingId) {
        log.info("Получение данных о резервировании по ID: {}", bookingId);
        Booking booking = findById(bookingId);
        if (!(userId.equals(booking.getBooker().getId()) || userId.equals(booking.getItem().getOwnerId()))) {
            throw new ForbiddenException("Просмотреть резервирование может только владелец предмета или " +
                    "пользователь сделавший запрос на резервирование");
        }
        return BookingMapper.toBookingDto(findById(bookingId));
    }

    private Booking findById(Long id) {
        return bookingRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Не найден резервирование с id: " + id));
    }
}
