package ru.itk.kafka.shipping.message.producer;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.scheduling.annotation.Async;

@Slf4j
@AllArgsConstructor
public abstract class AbstractProducer<T> {

    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final RetryTemplate retryTemplate;
    private String topicName;
    private String dlqTopicName;


    @Async
    public void sendMessage(String key, T event) {
        log.info("Attempting to send message: key={}, event={}", key, event);
        try {
            retryTemplate.execute(context -> {
                kafkaTemplate.send(topicName, key, event)
                        .thenAccept(sendResult -> log.info("Message sent: topic={}, partition={}, offset={}",
                                sendResult.getRecordMetadata().topic(),
                                sendResult.getRecordMetadata().partition(),
                                sendResult.getRecordMetadata().offset()))
                        .exceptionally(ex -> {
                            log.error("Failed to send message: {}", ex.getMessage());
                            return null;
                        });
                return null;
            });
        } catch (Exception e) {
            log.error("All retries failed. Sending to DLQ: {}", e.getMessage());
            kafkaTemplate.send(dlqTopicName, key, event);
        }
    }
}
