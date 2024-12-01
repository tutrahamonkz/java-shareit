package ru.practicum.shareit.user;

import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.model.User;

public class UserMapper {
    public static UserDto toUserDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .build();
    }

    public static User toUser(UserDto userDto) {
        return User.builder()
                .name(userDto.getName())
                .email(userDto.getEmail())
                .build();
    }

    public static User toUserOnUpdate(UserDto userDto, User user) {
        String name = userDto.getName();
        String email = userDto.getEmail();
        User updateUser = User.builder().id(userDto.getId()).build();
        if (name != null) {
            updateUser.setName(name);
        } else {
            updateUser.setName(user.getName());
        }
        if (email != null) {
            updateUser.setEmail(email);
        } else {
            updateUser.setEmail(user.getEmail());
        }
        return updateUser;
    }
}
