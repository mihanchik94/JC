package ru.itk.kafka.shipping.dto;

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
