package ru.itk.spring_mvc.mapper;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.itk.spring_mvc.dto.OrderDto;
import ru.itk.spring_mvc.dto.ProductDto;
import ru.itk.spring_mvc.model.Customer;
import ru.itk.spring_mvc.model.Order;
import ru.itk.spring_mvc.model.OrderStatus;
import ru.itk.spring_mvc.model.Product;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderMapperTest {
    @Mock
    private ProductMapperImpl productMapper;

    @InjectMocks
    private OrderMapper orderMapper = Mappers.getMapper(OrderMapper.class);

    @Test
    void testFromOrderToOrderDto_withMockedProductMapper() {
        Customer customer = new Customer();
        customer.setCustomerId(1L);

        Product product1 = Product.builder()
                .productId(101L)
                .name("Product 1")
                .description("Description 1")
                .price(BigDecimal.valueOf(10.99))
                .quantityInStock(50)
                .build();

        Product product2 = Product.builder()
                .productId(102L)
                .name("Product 2")
                .description("Description 2")
                .price(BigDecimal.valueOf(20.99))
                .quantityInStock(30)
                .build();

        Order order = Order.builder()
                .orderId(1L)
                .customer(customer)
                .products(Arrays.asList(product1, product2))
                .orderDate(LocalDateTime.now())
                .shippingAddress("123 Main St")
                .totalPrice(BigDecimal.valueOf(31.98))
                .orderStatus(OrderStatus.COMPLETED)
                .build();

        ProductDto productDto1 = ProductDto.builder()
                .productId(101L)
                .name("Product 1")
                .description("Description 1")
                .price(BigDecimal.valueOf(10.99))
                .quantityInStock(50)
                .build();

        ProductDto productDto2 = ProductDto.builder()
                .productId(102L)
                .name("Product 2")
                .description("Description 2")
                .price(BigDecimal.valueOf(20.99))
                .quantityInStock(30)
                .build();

        List<Product> products = List.of(product1, product2);
        List<ProductDto> productDtos = List.of(productDto1, productDto2);

        when(productMapper.fromProductListToProductDtoList(products)).thenReturn(productDtos);

        OrderDto orderDto = orderMapper.fromOrderToOrderDto(order);

        assertNotNull(orderDto);
        assertEquals(order.getOrderId(), orderDto.getOrderId());
        assertEquals(order.getCustomer().getCustomerId(), orderDto.getCustomerId());
        assertNotNull(orderDto.getProducts());
        assertEquals(2, orderDto.getProducts().size());

        ProductDto resultProductDto1 = orderDto.getProducts().get(0);
        assertEquals(productDto1.getProductId(), resultProductDto1.getProductId());
        assertEquals(productDto1.getName(), resultProductDto1.getName());

        ProductDto resultProductDto2 = orderDto.getProducts().get(1);
        assertEquals(productDto2.getProductId(), resultProductDto2.getProductId());
        assertEquals(productDto2.getName(), resultProductDto2.getName());

        verify(productMapper, times(1)).fromProductListToProductDtoList(products);
    }

    @Test
    void testFromOrderDtoToOrder_withMockedProductMapper() {
        ProductDto productDto1 = ProductDto.builder()
                .productId(201L)
                .name("Product A")
                .description("Description A")
                .price(BigDecimal.valueOf(15.99))
                .quantityInStock(40)
                .build();

        ProductDto productDto2 = ProductDto.builder()
                .productId(202L)
                .name("Product B")
                .description("Description B")
                .price(BigDecimal.valueOf(25.99))
                .quantityInStock(20)
                .build();

        OrderDto orderDto = OrderDto.builder()
                .orderId(2L)
                .customerId(2L)
                .products(Arrays.asList(productDto1, productDto2))
                .orderDate(LocalDateTime.now())
                .shippingAddress("456 Market St")
                .totalPrice(BigDecimal.valueOf(41.98))
                .orderStatus(OrderStatus.IN_PROGRESS)
                .build();

        Product product1 = Product.builder()
                .productId(201L)
                .name("Product A")
                .description("Description A")
                .price(BigDecimal.valueOf(15.99))
                .quantityInStock(40)
                .build();

        Product product2 = Product.builder()
                .productId(202L)
                .name("Product B")
                .description("Description B")
                .price(BigDecimal.valueOf(25.99))
                .quantityInStock(20)
                .build();

        when(productMapper.fromProductDtoToProduct(productDto1)).thenReturn(product1);
        when(productMapper.fromProductDtoToProduct(productDto2)).thenReturn(product2);

        Order order = orderMapper.fromOrderDtoToOrder(orderDto);

        assertNotNull(order);
        assertEquals(orderDto.getOrderId(), order.getOrderId());
        assertNotNull(order.getCustomer());
        assertEquals(orderDto.getCustomerId(), order.getCustomer().getCustomerId());
        assertNotNull(order.getProducts());
        assertEquals(2, order.getProducts().size());

        Product resultProduct1 = order.getProducts().get(0);
        assertEquals(product1.getProductId(), resultProduct1.getProductId());
        assertEquals(product1.getName(), resultProduct1.getName());

        Product resultProduct2 = order.getProducts().get(1);
        assertEquals(product2.getProductId(), resultProduct2.getProductId());
        assertEquals(product2.getName(), resultProduct2.getName());

        verify(productMapper, times(1)).fromProductDtoToProduct(productDto1);
        verify(productMapper, times(1)).fromProductDtoToProduct(productDto2);
    }

}