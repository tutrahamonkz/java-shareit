package ru.practicum.shareit.item.dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@JsonTest
class ItemRequestDtoTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testSerialization() throws Exception {
        ItemRequestDto dto = new ItemRequestDto("Item Name", "Item Description", true, 1L);

        String json = objectMapper.writeValueAsString(dto);

        assertThat(json).contains("\"name\":\"Item Name\"");
        assertThat(json).contains("\"description\":\"Item Description\"");
        assertThat(json).contains("\"available\":true");
        assertThat(json).contains("\"requestId\":1");
    }

    @Test
    void testDeserialization() throws Exception {
        String json = "{\"name\":\"Item Name\",\"description\":\"Item Description\",\"available\":true,\"requestId\":1}";

        ItemRequestDto dto = objectMapper.readValue(json, ItemRequestDto.class);

        assertThat(dto.getName()).isEqualTo("Item Name");
        assertThat(dto.getDescription()).isEqualTo("Item Description");
        assertThat(dto.getAvailable()).isTrue();
        assertThat(dto.getRequestId()).isEqualTo(1L);
    }

    @Test
    void testValidation() {
        ItemRequestDto dto = new ItemRequestDto("Item Name", "Item Description", true, 1L);
        validateFields(dto);
    }

    @Test
    void testValidationFailureName() {
        ItemRequestDto dto = new ItemRequestDto("", "Item Description", true, 1L);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> validateFields(dto));
        assertThat(exception.getMessage()).isEqualTo("Name should not be blank");
    }

    @Test
    void testValidationFailureDescription() {
        ItemRequestDto dto = new ItemRequestDto("Item Name", "", true, 1L);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> validateFields(dto));
        assertThat(exception.getMessage()).isEqualTo("Description should not be blank");
    }

    @Test
    void testValidationFailureAvailable() {
        ItemRequestDto dto = new ItemRequestDto("Item Name", "Item Description", null, 1L);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> validateFields(dto));
        assertThat(exception.getMessage()).isEqualTo("Available should not be null");
    }

    private void validateFields(ItemRequestDto dto) {
        if (dto.getName() == null || dto.getName().isBlank()) {
            throw new IllegalArgumentException("Name should not be blank");
        }
        if (dto.getDescription() == null || dto.getDescription().isBlank()) {
            throw new IllegalArgumentException("Description should not be blank");
        }
        if (dto.getAvailable() == null) {
            throw new IllegalArgumentException("Available should not be null");
        }
    }
}