package ru.itk.spring_mvc.service;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.itk.spring_mvc.dto.OrderDto;
import ru.itk.spring_mvc.mapper.OrderMapper;
import ru.itk.spring_mvc.model.Customer;
import ru.itk.spring_mvc.model.Order;
import ru.itk.spring_mvc.model.OrderStatus;
import ru.itk.spring_mvc.repository.OrderRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {
    @Mock
    private OrderRepository orderRepository;

    @Mock
    private CustomerService customerService;

    @Mock
    private OrderMapper orderMapper;

    @InjectMocks
    private OrderServiceImpl orderServiceImpl;

    @Test
    void createOrderSuccessfully() {
        OrderDto orderDto = new OrderDto();
        orderDto.setCustomerId(1L);
        orderDto.setProducts(new ArrayList<>());
        orderDto.setOrderDate(LocalDateTime.now());
        orderDto.setShippingAddress("123 Main St");
        orderDto.setTotalPrice(BigDecimal.valueOf(100.00));
        orderDto.setOrderStatus(OrderStatus.ACCEPTED);

        Customer customer = new Customer();
        customer.setCustomerId(1L);
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customer.setEmail("john.doe@example.com");
        customer.setContactNumber("1234567890");

        Order order = new Order();
        order.setOrderId(1L);
        order.setCustomer(customer);
        order.setProducts(new ArrayList<>());
        order.setOrderDate(LocalDateTime.now());
        order.setShippingAddress("123 Main St");
        order.setTotalPrice(BigDecimal.valueOf(100.00));
        order.setOrderStatus(OrderStatus.ACCEPTED);

        OrderDto expectedOrderDto = new OrderDto();
        expectedOrderDto.setOrderId(1L);
        expectedOrderDto.setCustomerId(1L);
        expectedOrderDto.setProducts(new ArrayList<>());
        expectedOrderDto.setOrderDate(LocalDateTime.now());
        expectedOrderDto.setShippingAddress("123 Main St");
        expectedOrderDto.setTotalPrice(BigDecimal.valueOf(100.00));
        expectedOrderDto.setOrderStatus(OrderStatus.ACCEPTED);

        when(customerService.findCustomerById(1L)).thenReturn(customer);
        when(orderMapper.fromOrderDtoToOrder(orderDto)).thenReturn(order);
        when(orderRepository.save(any(Order.class))).thenReturn(order);
        when(orderMapper.fromOrderToOrderDto(order)).thenReturn(expectedOrderDto);

        OrderDto result = orderServiceImpl.createOrder(orderDto);

        assertNotNull(result);
        assertEquals(expectedOrderDto.getOrderId(), result.getOrderId());
        assertEquals(expectedOrderDto.getCustomerId(), result.getCustomerId());
        assertEquals(expectedOrderDto.getProducts(), result.getProducts());
        assertEquals(expectedOrderDto.getOrderDate(), result.getOrderDate());
        assertEquals(expectedOrderDto.getShippingAddress(), result.getShippingAddress());
        assertEquals(expectedOrderDto.getTotalPrice(), result.getTotalPrice());
        assertEquals(expectedOrderDto.getOrderStatus(), result.getOrderStatus());

        verify(customerService, times(1)).findCustomerById(1L);
        verify(orderMapper, times(1)).fromOrderDtoToOrder(orderDto);
        verify(orderMapper, times(1)).fromOrderToOrderDto(order);
    }

    @Test
    void findByIdSuccessfully() {
        Long orderId = 1L;
        Order order = new Order();
        order.setOrderId(orderId);
        order.setCustomer(new Customer());
        order.setProducts(new ArrayList<>());
        order.setOrderDate(LocalDateTime.now());
        order.setShippingAddress("123 Main St");
        order.setTotalPrice(BigDecimal.valueOf(100.00));
        order.setOrderStatus(OrderStatus.ACCEPTED);

        OrderDto expectedOrderDto = new OrderDto();
        expectedOrderDto.setOrderId(orderId);
        expectedOrderDto.setCustomerId(1L);
        expectedOrderDto.setProducts(new ArrayList<>());
        expectedOrderDto.setOrderDate(LocalDateTime.now());
        expectedOrderDto.setShippingAddress("123 Main St");
        expectedOrderDto.setTotalPrice(BigDecimal.valueOf(100.00));
        expectedOrderDto.setOrderStatus(OrderStatus.ACCEPTED);

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));
        when(orderMapper.fromOrderToOrderDto(order)).thenReturn(expectedOrderDto);

        OrderDto result = orderServiceImpl.findById(orderId);

        assertNotNull(result);
        assertEquals(expectedOrderDto.getOrderId(), result.getOrderId());
        assertEquals(expectedOrderDto.getCustomerId(), result.getCustomerId());
        assertEquals(expectedOrderDto.getProducts(), result.getProducts());
        assertEquals(expectedOrderDto.getOrderDate(), result.getOrderDate());
        assertEquals(expectedOrderDto.getShippingAddress(), result.getShippingAddress());
        assertEquals(expectedOrderDto.getTotalPrice(), result.getTotalPrice());
        assertEquals(expectedOrderDto.getOrderStatus(), result.getOrderStatus());

        verify(orderRepository, times(1)).findById(orderId);
        verify(orderMapper, times(1)).fromOrderToOrderDto(order);
    }

    @Test
    void findByIdWhenOrderNotFound() {
        Long orderId = 1L;
        when(orderRepository.findById(orderId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> orderServiceImpl.findById(orderId));
        verify(orderRepository, times(1)).findById(orderId);
    }

}