package ru.itk.kafka.partitions.message.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;

@Slf4j
@AllArgsConstructor
public abstract class AbstractProducer<T> {

    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final ObjectMapper objectMapper;
    private String topicName;

    public void sendMessage(String key, T event) throws JsonProcessingException {
        log.info("Attempting to send message: key={}, event={}", key, event);
        String serializedEvent = objectMapper.writeValueAsString(event);
        kafkaTemplate.send(topicName, key, event)
                .thenAccept(sendResult -> log.info("Message sent: topic={}, partition={}, offset={}",
                        sendResult.getRecordMetadata().topic(),
                        sendResult.getRecordMetadata().partition(),
                        sendResult.getRecordMetadata().offset()))
                .exceptionally(ex -> {
                    log.error("Failed to send message: {}", ex.getMessage());
                    return null;
                });
    }
}
