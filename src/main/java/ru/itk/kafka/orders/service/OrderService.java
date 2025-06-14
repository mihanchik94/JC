package ru.itk.kafka.orders.service;

import ru.itk.kafka.orders.dto.OrderDto;

public interface OrderService {
    OrderDto save(OrderDto orderDto);
}
