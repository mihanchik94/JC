package ru.itk.kafka.notification.message.consumer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import ru.itk.kafka.notification.dto.OrderDto;
import ru.itk.kafka.notification.message.producer.NotificationUserProducer;


@Component
@Slf4j
@RequiredArgsConstructor
public class OrderConsumer {
    private final NotificationUserProducer notificationUserProducer;


    @KafkaListener(topics = "${spring.kafka.topics.sent_orders.name}", groupId = "${spring.kafka.consumer.group-id}")
    public void listen(OrderDto orderDto)  {
        log.info("Received order for notification: {}", orderDto.getOrderId());
        try {
            OrderDto payedDto = OrderDto.builder()
                    .withOrderId(orderDto.getOrderId())
                    .withProducts(orderDto.getProducts())
                    .withStatus("completed")
                    .build();
            log.info("Order {} is completed", orderDto.getOrderId());
            notificationUserProducer.sendMessage(String.valueOf(payedDto.getOrderId()), payedDto);
        } catch (Exception e) {
            log.error("Error during finishing process of order {}: {}", orderDto.getOrderId(), e.getMessage());
        }
    }


}
