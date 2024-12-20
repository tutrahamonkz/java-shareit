package ru.practicum.shareit.booking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.BookingStatus;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    List<Booking> findAllByBookerId(Long bookerId);

    List<Booking> findAllByBookerIdAndStatus(Long bookerId, BookingStatus status);

    @Query("select b from Booking b " +
            "join b.item it " +
            "where it.ownerId = ?1")
    List<Booking> findAllByItemOwnerId(Long ownerId);

    @Query("select b from Booking b " +
            "join b.item it " +
            "where it.ownerId = ?1 and b.status = ?2")
    List<Booking> findAllByItemOwnerIdAndStatus(Long ownerId, BookingStatus status);
}