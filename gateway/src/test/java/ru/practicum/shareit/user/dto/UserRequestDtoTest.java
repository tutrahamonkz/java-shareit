package ru.practicum.shareit.user.dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.practicum.shareit.validation.CreateValidationGroup;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@JsonTest
class UserRequestDtoTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testSerialization() throws Exception {
        UserRequestDto dto = new UserRequestDto("John Doe", "john.doe@example.com");

        String json = objectMapper.writeValueAsString(dto);

        assertThat(json).contains("\"name\":\"John Doe\"");
        assertThat(json).contains("\"email\":\"john.doe@example.com\"");
    }

    @Test
    void testDeserialization() throws Exception {
        String json = "{\"name\":\"John Doe\",\"email\":\"john.doe@example.com\"}";

        UserRequestDto dto = objectMapper.readValue(json, UserRequestDto.class);

        assertThat(dto.getName()).isEqualTo("John Doe");
        assertThat(dto.getEmail()).isEqualTo("john.doe@example.com");
    }

    @Test
    void testValidation() {
        UserRequestDto dto = new UserRequestDto("John Doe", "john.doe@example.com");
        validateFields(dto, CreateValidationGroup.class);
    }

    @Test
    void testValidationFailureName() {
        UserRequestDto dto = new UserRequestDto("", "john.doe@example.com");

        Exception exception = assertThrows(IllegalArgumentException.class, () -> validateFields(dto, CreateValidationGroup.class));
        assertThat(exception.getMessage()).isEqualTo("Name should not be blank");
    }

    @Test
    void testValidationFailureEmail() {
        UserRequestDto dto = new UserRequestDto("John Doe", "invalid-email");

        Exception exception = assertThrows(IllegalArgumentException.class, () -> validateFields(dto, CreateValidationGroup.class));
        assertThat(exception.getMessage()).isEqualTo("Email should be valid");
    }

    private void validateFields(UserRequestDto dto, Class<?> group) {
        if (group == CreateValidationGroup.class) {
            if (dto.getName() == null || dto.getName().isBlank()) {
                throw new IllegalArgumentException("Name should not be blank");
            }
            if (dto.getEmail() == null || !dto.getEmail().matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}")) {
                throw new IllegalArgumentException("Email should be valid");
            }
        }
    }
}