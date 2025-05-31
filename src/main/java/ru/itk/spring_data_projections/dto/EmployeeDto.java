package ru.itk.spring_data_projections.dto;

import lombok.*;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class EmployeeDto {
    private String firstName;

    private String lastName;

    private String position;

    private BigDecimal salary;

    private Long departmentId;
}
