package ru.itk.kafka.orders.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder(setterPrefix = "with")
public class OrderDto {
    private Long orderId;

    private String products;

    private String status;
}
