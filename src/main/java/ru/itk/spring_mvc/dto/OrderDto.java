package ru.itk.spring_mvc.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import ru.itk.spring_mvc.model.OrderStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class OrderDto {
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Long orderId;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Long customerId;
    private List<ProductDto> products;
    private LocalDateTime orderDate;
    private String shippingAddress;
    private BigDecimal totalPrice;
    private OrderStatus orderStatus;
}
