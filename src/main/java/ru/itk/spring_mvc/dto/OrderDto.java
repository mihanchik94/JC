package ru.itk.spring_mvc.dto;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.*;
import ru.itk.spring_mvc.model.OrderStatus;
import ru.itk.spring_mvc.view.Views;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder(setterPrefix = "with")
public class OrderDto {

    private Long orderId;

    private String customerName;

    private String customerSurname;

    @JsonView(Views.UserDetails.class)
    private String products;

    @JsonView(Views.UserDetails.class)
    private BigDecimal totalPrice;

    @JsonView(Views.UserDetails.class)
    private OrderStatus status;
}
