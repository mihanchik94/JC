package ru.itk.spring_mvc.mapper;

import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import ru.itk.spring_mvc.dto.OrderDto;
import ru.itk.spring_mvc.model.Order;
import ru.itk.spring_mvc.model.OrderStatus;
import ru.itk.spring_mvc.model.User;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OrderMapperTest {
    private final OrderMapper orderMapper = Mappers.getMapper(OrderMapper.class);

    @Test
    public void testFromOrderToOrderDto() {
        User user = User.builder()
                .withId(1L)
                .withName("John")
                .withSurname("Doe")
                .build();

        Order order = Order.builder()
                .withId(100L)
                .withUser(user)
                .withProducts("Product1, Product2, Product3")
                .withTotalPrice(new BigDecimal("99.99"))
                .withStatus(OrderStatus.ACCEPTED)
                .build();

        OrderDto dto = orderMapper.fromOrderToOrderDto(order);

        assertEquals(dto.getOrderId(), order.getId());
        assertEquals(dto.getCustomerName(), order.getUser().getName());
        assertEquals(dto.getCustomerSurname(), order.getUser().getSurname());
        assertEquals(dto.getProducts(), order.getProducts());
        assertEquals(dto.getTotalPrice(), order.getTotalPrice());
        assertEquals(dto.getStatus(), order.getStatus());
    }

    @Test
    public void testFromOrderListToOrderDtoList() {
        User user = User.builder()
                .withId(1L)
                .withName("John")
                .withSurname("Doe")
                .build();

        User user2 = User.builder()
                .withId(2L)
                .withName("Jane")
                .withSurname("Smith")
                .build();

        List<Order> orders = List.of(
                Order.builder()
                        .withId(100L)
                        .withUser(user)
                        .withProducts("Product1, Product2")
                        .withTotalPrice(new BigDecimal("99.99"))
                        .withStatus(OrderStatus.ACCEPTED)
                        .build(),
                Order.builder()
                        .withId(101L)
                        .withUser(user2)
                        .withProducts("Product3, Product4")
                        .withTotalPrice(new BigDecimal("59.99"))
                        .withStatus(OrderStatus.IN_PROGRESS)
                        .build()
        );

        List<OrderDto> dtoList = orderMapper.fromOrderListToOrderDtoList(orders);

        assertEquals(dtoList.size(), 2);
        for (int index = 0; index < orders.size(); index++) {
            OrderDto dto = dtoList.get(index);
            Order order = orders.get(index);
            assertEquals(dto.getOrderId(), order.getId());
            assertEquals(dto.getCustomerName(), order.getUser().getName());
            assertEquals(dto.getCustomerSurname(), order.getUser().getSurname());
            assertEquals(dto.getProducts(), order.getProducts());
            assertEquals(dto.getTotalPrice(), order.getTotalPrice());
            assertEquals(dto.getStatus(), order.getStatus());
        }
    }

}