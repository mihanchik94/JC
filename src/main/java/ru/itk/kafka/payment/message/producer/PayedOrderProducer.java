package ru.itk.kafka.payment.message.producer;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Component;
import ru.itk.kafka.payment.dto.OrderDto;

@Component
public class PayedOrderProducer extends AbstractProducer<OrderDto> {
    public PayedOrderProducer(KafkaTemplate<String, Object> kafkaTemplate, RetryTemplate retryTemplate,
                              @Value("${spring.kafka.topics.payed_orders.name}") String topicName,
                              @Value("${spring.kafka.topics.payed_orders-dlq.name}") String dlqTopicName) {
        super(kafkaTemplate, retryTemplate, topicName, dlqTopicName);
    }
}
