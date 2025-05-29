package ru.itk.spring_mvc.service;

import ru.itk.spring_mvc.dto.OrderDto;

public interface OrderService {
    OrderDto createOrder(OrderDto orderDto);
    OrderDto findById(Long id);

}
