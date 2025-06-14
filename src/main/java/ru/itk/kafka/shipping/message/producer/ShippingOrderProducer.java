package ru.itk.kafka.shipping.message.producer;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Component;
import ru.itk.kafka.shipping.dto.OrderDto;

@Component
public class ShippingOrderProducer extends AbstractProducer<OrderDto> {
    public ShippingOrderProducer(KafkaTemplate<String, Object> kafkaTemplate, RetryTemplate retryTemplate,
                                 @Value("${spring.kafka.topics.sent_orders.name}") String topicName,
                                 @Value("${spring.kafka.topics.sent_orders-dlq.name}") String dlqTopicName) {
        super(kafkaTemplate, retryTemplate, topicName, dlqTopicName);
    }
}
