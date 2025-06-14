package ru.itk.kafka.orders.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.itk.kafka.orders.dto.OrderDto;
import ru.itk.kafka.orders.service.OrderService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/new")
    public ResponseEntity<String> create(OrderDto orderDto) {
        orderService.save(orderDto);
        return new ResponseEntity<>("Order created", HttpStatus.CREATED);
    }
}
