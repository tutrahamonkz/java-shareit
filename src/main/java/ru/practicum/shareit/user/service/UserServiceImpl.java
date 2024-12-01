package ru.practicum.shareit.user.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.user.UserMapper;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.repository.UserRepository;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public UserDto createUser(User user) {
        return UserMapper.toUserDto(userRepository.createUser(user));
    }

    @Override
    public UserDto getUserById(Long userId) {
        return UserMapper.toUserDto(findById(userId));
    }

    @Override
    public UserDto updateUserById(UserDto user) {
        return UserMapper.toUserDto(userRepository
                .updateUserById(UserMapper.toUserOnUpdate(user, findById(user.getId()))));
    }

    @Override
    public void deleteUserById(Long userId) {
        userRepository.deleteUserById(userId);
    }

    private User findById(Long userId) {
        return userRepository.getUserById(userId)
                .orElseThrow(() -> new NotFoundException("Не найден пользователь с id: " + userId));
    }
}