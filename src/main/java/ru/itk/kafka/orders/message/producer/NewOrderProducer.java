package ru.itk.kafka.orders.message.producer;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Component;
import ru.itk.kafka.orders.dto.OrderDto;

@Component
public class NewOrderProducer extends AbstractProducer<OrderDto> {

    public NewOrderProducer(KafkaTemplate<String, Object> kafkaTemplate,
                            RetryTemplate retryTemplate,
                            @Value("${spring.kafka.topics.orders.name}") String topicName,
                            @Value("${spring.kafka.topics.orders-dlq.name}") String dlqTopicName) {
        super(kafkaTemplate, retryTemplate, topicName, dlqTopicName);
    }
}
