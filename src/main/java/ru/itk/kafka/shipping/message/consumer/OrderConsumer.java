package ru.itk.kafka.shipping.message.consumer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import ru.itk.kafka.shipping.dto.OrderDto;
import ru.itk.kafka.shipping.message.producer.ShippingOrderProducer;


@Component
@Slf4j
@RequiredArgsConstructor
public class OrderConsumer {
    private final ShippingOrderProducer shippingOrderProducer;


    @KafkaListener(topics = "${spring.kafka.topics.payed_orders.name}", groupId = "${spring.kafka.consumer.group-id}")
    public void listen(OrderDto orderDto)  {
        log.info("Received order for shipping: {}", orderDto.getOrderId());
        try {
            OrderDto payedDto = OrderDto.builder()
                    .withOrderId(orderDto.getOrderId())
                    .withProducts(orderDto.getProducts())
                    .withStatus("sent")
                    .build();
            log.info("Order {} is sent", orderDto.getOrderId());
            shippingOrderProducer.sendMessage(String.valueOf(payedDto.getOrderId()), payedDto);
        } catch (Exception e) {
            log.error("Error during shipping process of order {}: {}", orderDto.getOrderId(), e.getMessage());
        }
    }


}
