package ru.itk.kafka.payment.message.consumer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import ru.itk.kafka.payment.dto.OrderDto;
import ru.itk.kafka.payment.message.producer.PayedOrderProducer;


@Component
@Slf4j
@RequiredArgsConstructor
public class OrderConsumer {
    private final PayedOrderProducer payedOrderProducer;


    @KafkaListener(topics = "${spring.kafka.topics.orders.name}", groupId = "${spring.kafka.consumer.group-id}")
    public void listen(OrderDto orderDto)  {
        log.info("Received odder for payment: {}", orderDto.getOrderId());
        try {
            OrderDto payedDto = OrderDto.builder()
                    .withOrderId(orderDto.getOrderId())
                    .withProducts(orderDto.getProducts())
                    .withStatus("paid")
                    .build();
            log.info("Order {} is paid", orderDto.getOrderId());
            payedOrderProducer.sendMessage(String.valueOf(payedDto.getOrderId()), payedDto);
        } catch (Exception e) {
            log.error("Error during payment process of order {}: {}", orderDto.getOrderId(), e.getMessage());
        }
    }


}
