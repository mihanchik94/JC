package ru.itk.spring_mvc.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.itk.spring_mvc.dto.OrderDto;
import ru.itk.spring_mvc.mapper.OrderMapper;
import ru.itk.spring_mvc.model.Customer;
import ru.itk.spring_mvc.model.Order;
import ru.itk.spring_mvc.repository.OrderRepository;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final CustomerService customerService;
    private final OrderMapper orderMapper;

    @Transactional
    @Override
    public OrderDto createOrder(OrderDto orderDto) {
        Customer customer = customerService.findCustomerById(orderDto.getCustomerId());
        Order order = orderMapper.fromOrderDtoToOrder(orderDto);
        order.setCustomer(customer);
        return orderMapper.fromOrderToOrderDto(orderRepository.save(order));
    }

    @Override
    public OrderDto findById(Long id) {
        return orderMapper.fromOrderToOrderDto(findOrderById(id));
    }

    private Order findOrderById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Product with id = %d not found", id)));
    }
}
