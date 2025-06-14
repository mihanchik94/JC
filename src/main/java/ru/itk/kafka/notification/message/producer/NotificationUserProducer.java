package ru.itk.kafka.notification.message.producer;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Component;
import ru.itk.kafka.notification.dto.OrderDto;

@Component
public class NotificationUserProducer extends AbstractProducer<OrderDto> {
    public NotificationUserProducer(KafkaTemplate<String, Object> kafkaTemplate, RetryTemplate retryTemplate,
                                    @Value("${spring.kafka.topics.user_notification.name}") String topicName,
                                    @Value("${spring.kafka.topics.user_notification-dlq.name}") String dlqTopicName) {
        super(kafkaTemplate, retryTemplate, topicName, dlqTopicName);
    }
}
