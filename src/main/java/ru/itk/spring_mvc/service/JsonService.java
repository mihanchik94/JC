package ru.itk.spring_mvc.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.core.serializer.support.SerializationFailedException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JsonService {
    private final ObjectMapper objectMapper;

    public String serializeToJson(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new SerializationFailedException("Serialization was failed");
        }
    }

    public <T> T deserializeFromJson(String jsonObject, Class<T> clazz) {
        try {
            return objectMapper.readValue(jsonObject, clazz);
        } catch (JsonProcessingException e) {
            throw new SerializationFailedException("Deserialization was failed");
        }
    }

}
