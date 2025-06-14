package ru.itk.kafka.orders.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.itk.kafka.orders.dto.OrderDto;
import ru.itk.kafka.orders.mapper.OrderMapper;
import ru.itk.kafka.orders.message.producer.NewOrderProducer;
import ru.itk.kafka.orders.model.Order;
import ru.itk.kafka.orders.repository.OrderRepository;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final OrderMapper mapper;
    private final NewOrderProducer newOrderProducer;

    @Override
    public OrderDto save(OrderDto orderDto) {
        Order order = mapper.toEntity(orderDto);
        order.setStatus("new");
        OrderDto saved = mapper.toDto(orderRepository.save(order));
        newOrderProducer.sendMessage(String.valueOf(saved.getOrderId()), saved);
        return saved;
    }
}
