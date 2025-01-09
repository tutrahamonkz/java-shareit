package ru.practicum.shareit.booking.dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@JsonTest
class BookItemRequestDtoTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testSerialization() throws Exception {
        BookItemRequestDto dto = new BookItemRequestDto(1L, LocalDateTime.now().plusDays(1), LocalDateTime.now().plusDays(2));

        String json = objectMapper.writeValueAsString(dto);

        assertThat(json).contains("\"itemId\":1");
        assertThat(json).contains("\"start\":");
        assertThat(json).contains("\"end\":");
    }

    @Test
    void testDeserialization() throws Exception {
        String json = "{\"itemId\":1,\"start\":\"" + LocalDateTime.now().plusDays(1).toString() + "\",\"end\":\"" + LocalDateTime.now().plusDays(2).toString() + "\"}";

        BookItemRequestDto dto = objectMapper.readValue(json, BookItemRequestDto.class);

        assertThat(dto.getItemId()).isEqualTo(1L);
        assertThat(dto.getStart()).isNotNull();
        assertThat(dto.getEnd()).isNotNull();
    }

    @Test
    void testValidDates() {
        BookItemRequestDto dto = new BookItemRequestDto(1L, LocalDateTime.now().plusDays(1), LocalDateTime.now().plusDays(2));
        assertThat(dto.getStart()).isBefore(dto.getEnd());
    }

    @Test
    void testInvalidStartDate() {
        BookItemRequestDto dto = new BookItemRequestDto(1L, LocalDateTime.now().minusDays(1), LocalDateTime.now().plusDays(2));
        assertThrows(IllegalArgumentException.class, () -> validateDates(dto));
    }

    @Test
    void testInvalidEndDate() {
        BookItemRequestDto dto = new BookItemRequestDto(1L, LocalDateTime.now().plusDays(1), LocalDateTime.now().minusDays(2));
        assertThrows(IllegalArgumentException.class, () -> validateDates(dto));
    }

    @Test
    void testSameStartAndEndDate() {
        BookItemRequestDto dto = new BookItemRequestDto(1L, LocalDateTime.now().plusDays(1), LocalDateTime.now().plusDays(1));
        assertThrows(IllegalArgumentException.class, () -> validateDates(dto));
    }

    private void validateDates(BookItemRequestDto dto) {
        if (dto.getStart().isEqual(dto.getEnd())) {
            throw new IllegalArgumentException("Start date should not be equal to end date");
        }
        if (dto.getStart().isAfter(dto.getEnd())) {
            throw new IllegalArgumentException("Start date should not be after end date");
        }
        if (dto.getStart().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Start date should not be in the past");
        }
        if (dto.getEnd().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("End date should not be in the past");
        }
    }
}