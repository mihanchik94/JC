package ru.itk.spring_mvc.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class ProductDto {
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Long productId;
    private String name;
    private String description;
    private BigDecimal price;
    private Integer quantityInStock;

}
