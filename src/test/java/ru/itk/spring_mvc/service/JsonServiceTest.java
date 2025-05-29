package ru.itk.spring_mvc.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.serializer.support.SerializationFailedException;
import ru.itk.spring_mvc.dto.ProductDto;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class JsonServiceTest {
    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    public JsonService jsonService;

    @Test
    @SneakyThrows
    void testSerializeToJsonSuccess() {
        ProductDto product = ProductDto.builder()
                .name("Test Product")
                .description("A test product")
                .price(new BigDecimal("10.99"))
                .quantityInStock(100)
                .build();
        String expectedJson = "{\"name\":\"Test Product\",\"description\":\"A test product\",\"price\":10.99,\"quantityInStock\":100}";

        when(objectMapper.writeValueAsString(product)).thenReturn(expectedJson);

        String result = jsonService.serializeToJson(product);

        assertEquals(expectedJson, result);
        verify(objectMapper, times(1)).writeValueAsString(product);
    }

    @Test
    void testSerializeToJsonFailure() throws JsonProcessingException {
        ProductDto product = ProductDto.builder()
                .name("Test Product")
                .description("A test product")
                .price(new BigDecimal("10.99"))
                .quantityInStock(100)
                .build();

        when(objectMapper.writeValueAsString(product)).thenThrow(new JsonProcessingException("Error") {});

        SerializationFailedException exception = assertThrows(SerializationFailedException.class, () -> {
            jsonService.serializeToJson(product);
        });

        assertEquals("Serialization was failed", exception.getMessage());
        verify(objectMapper, times(1)).writeValueAsString(product);
    }

    @Test
    void testDeserializeFromJsonSuccess() throws JsonProcessingException {
        String json = "{\"name\":\"Test Product\",\"description\":\"A test product\",\"price\":10.99,\"quantityInStock\":100}";
        ProductDto expectedProduct = ProductDto.builder()
                .name("Test Product")
                .description("A test product")
                .price(new BigDecimal("10.99"))
                .quantityInStock(100)
                .build();

        when(objectMapper.readValue(json, ProductDto.class)).thenReturn(expectedProduct);

        ProductDto result = jsonService.deserializeFromJson(json, ProductDto.class);

        assertEquals(expectedProduct, result);
        verify(objectMapper, times(1)).readValue(json, ProductDto.class);
    }

    @Test
    void testDeserializeFromJson_Failure() throws JsonProcessingException {
        String json = "Invalid JSON";

        when(objectMapper.readValue(json, ProductDto.class)).thenThrow(new JsonProcessingException("Error") {});

        SerializationFailedException exception = assertThrows(SerializationFailedException.class,
                () -> jsonService.deserializeFromJson(json, ProductDto.class));

        assertEquals("Deserialization was failed", exception.getMessage());
        verify(objectMapper, times(1)).readValue(json, ProductDto.class);
    }


}