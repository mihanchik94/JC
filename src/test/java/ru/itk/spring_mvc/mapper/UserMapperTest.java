package ru.itk.spring_mvc.mapper;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.itk.spring_mvc.dto.OrderDto;
import ru.itk.spring_mvc.dto.UserDto;
import ru.itk.spring_mvc.model.Order;
import ru.itk.spring_mvc.model.OrderStatus;
import ru.itk.spring_mvc.model.User;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserMapperTest {

    @Mock
    private OrderMapperImpl orderMapper;

    @InjectMocks
    private final UserMapper userMapper = Mappers.getMapper(UserMapper.class);

    @Test
    public void testFromUserToUserDto() {
        Order order = Order.builder()
                .withId(100L)
                .withProducts("Product1, Product2, Product3")
                .withTotalPrice(new BigDecimal("99.99"))
                .withStatus(OrderStatus.ACCEPTED)
                .build();

        OrderDto orderDto = OrderDto.builder()
                .withOrderId(100L)
                .withProducts("Product1, Product2, Product3")
                .withTotalPrice(new BigDecimal("99.99"))
                .withStatus(OrderStatus.ACCEPTED)
                .build();

        User user = User.builder()
                .withId(1L)
                .withName("John")
                .withSurname("Doe")
                .withEmail("JohnDoe@example.com")
                .withOrders(List.of(order))
                .build();

        when(orderMapper.fromOrderListToOrderDtoList(List.of(order))).thenReturn(List.of(orderDto));
        UserDto userDto = userMapper.fromUserToUserDto(user);

        assertNotNull(userDto);
        assertEquals(userDto.getName(), user.getName());
        assertEquals(userDto.getSurname(), user.getSurname());
        assertEquals(userDto.getEmail(), user.getEmail());
        assertEquals(userDto.getOrders().size(), 1);
        assertEquals(userDto.getOrders().get(0).getProducts(), user.getOrders().get(0).getProducts());
    }

    @Test
    public void testFromUserListToUserDtoList() {
        Order order1 = Order.builder()
                .withId(100L)
                .withProducts("Product1, Product2, Product3")
                .withTotalPrice(new BigDecimal("99.99"))
                .withStatus(OrderStatus.ACCEPTED)
                .build();

        OrderDto orderDto1 = OrderDto.builder()
                .withOrderId(100L)
                .withProducts("Product1, Product2, Product3")
                .withTotalPrice(new BigDecimal("99.99"))
                .withStatus(OrderStatus.ACCEPTED)
                .build();

        Order order2 = Order.builder()
                .withId(101L)
                .withProducts("Product3, Product4")
                .withTotalPrice(new BigDecimal("59.99"))
                .withStatus(OrderStatus.IN_PROGRESS)
                .build();

        OrderDto orderDto2 = OrderDto.builder()
                .withOrderId(101L)
                .withProducts("Product3, Product4")
                .withTotalPrice(new BigDecimal("59.99"))
                .withStatus(OrderStatus.IN_PROGRESS)
                .build();

        User user1 = User.builder()
                .withId(1L)
                .withName("John")
                .withSurname("Doe")
                .withEmail("JohnDoe@example.com")
                .withOrders(List.of(order1))
                .build();

        User user2 = User.builder()
                .withId(2L)
                .withName("Jane")
                .withSurname("Smith")
                .withEmail("JaneSmith@example.com")
                .withOrders(List.of(order2))
                .build();

        List<User> users = List.of(user1, user2);

        when(orderMapper.fromOrderListToOrderDtoList(List.of(order1))).thenReturn(List.of(orderDto1));
        when(orderMapper.fromOrderListToOrderDtoList(List.of(order2))).thenReturn(List.of(orderDto2));
        List<UserDto> userDtos = userMapper.fromUserListToUserDtoList(users);

        assertEquals(userDtos.size(), 2);

        for (int index = 0; index < users.size(); index++) {
            UserDto userDto = userDtos.get(index);
            User user = users.get(index);
            assertEquals(userDto.getName(), user.getName());
            assertEquals(userDto.getSurname(), user.getSurname());
            assertEquals(userDto.getEmail(), user.getEmail());
            assertEquals(userDto.getOrders().size(), 1);
            assertEquals(userDto.getOrders().get(0).getProducts(), user.getOrders().get(0).getProducts());
        }
    }

    @Test
    public void testFromUserDtoToUser() {

        UserDto userDto = UserDto.builder()
                .withName("John")
                .withSurname("Doe")
                .withEmail("JohnDoe@example.com")
                .build();

        User user = userMapper.fromUserDtoToUser(userDto);

        assertNotNull(user);
        assertEquals(user.getName(), userDto.getName());
        assertEquals(user.getSurname(), userDto.getSurname());
        assertEquals(user.getEmail(), userDto.getEmail());
    }

}