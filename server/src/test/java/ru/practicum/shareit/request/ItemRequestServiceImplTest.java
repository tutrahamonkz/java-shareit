package ru.practicum.shareit.request;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.request.dto.ItemRequestDtoWithItems;
import ru.practicum.shareit.request.model.ItemRequest;
import ru.practicum.shareit.request.repository.ItemRequestRepository;
import ru.practicum.shareit.request.service.ItemRequestServiceImpl;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.repository.UserRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
class ItemRequestServiceImplTest {

    @Autowired
    private ItemRequestServiceImpl itemRequestService;

    @Autowired
    private ItemRequestRepository itemRequestRepository;

    @Autowired
    private UserRepository userRepository;

    private ItemRequestDto itemRequestDto;
    private ItemRequest itemRequest;
    private User user;

    @BeforeEach
    void setUp() {
        // Создание и сохранение пользователя
        user = new User(null, "John Doe", "john.doe@example.com");
        user = userRepository.save(user);

        // Используем ID сохраненного пользователя
        itemRequestDto = new ItemRequestDto(null, "Need a book", user.getId(), null);
        itemRequest = new ItemRequest(null, "Need a book", user.getId(), null);
    }

    @Test
    void createItemRequest() {
        ItemRequestDto createdRequest = itemRequestService.createItemRequest(itemRequestDto, user.getId());
        assertNotNull(createdRequest);
        assertNotNull(createdRequest.getId());
        assertEquals(itemRequestDto.getDescription(), createdRequest.getDescription());
        assertEquals(itemRequestDto.getRequestor(), createdRequest.getRequestor());
    }

    @Test
    void getMyItemRequests() {
        itemRequestRepository.save(itemRequest);
        List<ItemRequestDtoWithItems> requests = itemRequestService.getMyItemRequests(user.getId());
        assertNotNull(requests);
        assertFalse(requests.isEmpty());
        assertEquals(itemRequest.getDescription(), requests.get(0).getDescription());
    }

    @Test
    void getAllOtherItemRequests() {
        itemRequestRepository.save(itemRequest);
        List<ItemRequestDto> requests = itemRequestService.getAllOtherItemRequests(2L);
        assertNotNull(requests);
        assertFalse(requests.isEmpty());
        assertEquals(itemRequest.getDescription(), requests.get(0).getDescription());
    }

    @Test
    void getItemRequestById() {
        itemRequestRepository.save(itemRequest);
        ItemRequestDtoWithItems request = itemRequestService.getItemRequestById(itemRequest.getId());
        assertNotNull(request);
        assertEquals(itemRequest.getDescription(), request.getDescription());
    }

    @Test
    void getItemRequestByIdNotFound() {
        assertThrows(NotFoundException.class, () -> itemRequestService.getItemRequestById(999L));
    }
}
