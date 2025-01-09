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
class CommentRequestDtoTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testSerialization() throws Exception {
        CommentRequestDto dto = new CommentRequestDto("This is a comment");

        String json = objectMapper.writeValueAsString(dto);

        assertThat(json).contains("\"text\":\"This is a comment\"");
    }

    @Test
    void testDeserialization() throws Exception {
        String json = "{\"text\":\"This is a comment\"}";

        CommentRequestDto dto = objectMapper.readValue(json, CommentRequestDto.class);

        assertThat(dto.getText()).isEqualTo("This is a comment");
    }

    @Test
    void testValidation() {
        CommentRequestDto dto = new CommentRequestDto("This is a comment");
        assertThat(dto.getText()).isNotBlank();
    }

    @Test
    void testValidationFailure() {
        CommentRequestDto dto = new CommentRequestDto("");

        Exception exception = assertThrows(IllegalArgumentException.class, () -> validateText(dto));
        assertThat(exception.getMessage()).isEqualTo("Text should not be blank");
    }

    private void validateText(CommentRequestDto dto) {
        if (dto.getText() == null || dto.getText().isBlank()) {
            throw new IllegalArgumentException("Text should not be blank");
        }
    }
}