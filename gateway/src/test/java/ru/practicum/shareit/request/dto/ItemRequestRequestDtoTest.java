package ru.practicum.shareit.request.dto;

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
class ItemRequestRequestDtoTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testSerialization() throws Exception {
        ItemRequestRequestDto dto = new ItemRequestRequestDto("This is a description");

        String json = objectMapper.writeValueAsString(dto);

        assertThat(json).contains("\"description\":\"This is a description\"");
    }

    @Test
    void testDeserialization() throws Exception {
        String json = "{\"description\":\"This is a description\"}";

        ItemRequestRequestDto dto = objectMapper.readValue(json, ItemRequestRequestDto.class);

        assertThat(dto.getDescription()).isEqualTo("This is a description");
    }

    @Test
    void testValidation() {
        ItemRequestRequestDto dto = new ItemRequestRequestDto("This is a description");
        assertThat(dto.getDescription()).isNotBlank();
    }

    @Test
    void testValidationFailure() {
        ItemRequestRequestDto dto = new ItemRequestRequestDto("");

        Exception exception = assertThrows(IllegalArgumentException.class, () -> validateDescription(dto));
        assertThat(exception.getMessage()).isEqualTo("Description should not be blank");
    }

    private void validateDescription(ItemRequestRequestDto dto) {
        if (dto.getDescription() == null || dto.getDescription().isBlank()) {
            throw new IllegalArgumentException("Description should not be blank");
        }
    }
}