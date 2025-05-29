package ru.itk.spring_mvc.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.itk.spring_mvc.dto.OrderDto;
import ru.itk.spring_mvc.service.JsonService;
import ru.itk.spring_mvc.service.OrderService;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
@Validated
public class OrderController {

    private final OrderService orderService;
    private final JsonService jsonService;

    @PostMapping("/")
    public ResponseEntity<String> createOrder(@RequestBody String jsonOrderDto) {
        OrderDto dtoFromUser = jsonService.deserializeFromJson(jsonOrderDto, OrderDto.class);
        return new ResponseEntity<>(
                jsonService.serializeToJson(orderService.createOrder(dtoFromUser)), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<String> getOrderById(@PathVariable(name = "id") Long id) {
        OrderDto orderDto = orderService.findById(id);
        return ResponseEntity.ok(jsonService.serializeToJson(orderDto));
    }
}
