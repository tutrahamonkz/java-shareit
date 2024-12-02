package ru.practicum.shareit.user;

import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.model.User;

import java.util.Optional;

public class UserMapper {

    // Преобразует объект User в объект UserDto.
    public static UserDto toUserDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .build();
    }

    /* Обновляет поля объекта User на основе данных из UserDto.
       Если поля в UserDto отсутствуют, используются существующие значения из User. */
    public static User toUserOnUpdate(UserDto userDto, User user) {
        return User.builder()
                .id(userDto.getId())
                .name(Optional.ofNullable(userDto.getName()).orElse(user.getName()))
                .email(Optional.ofNullable(userDto.getEmail()).orElse(user.getEmail()))
                .build();
    }
}
