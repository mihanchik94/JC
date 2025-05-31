package ru.itk.spring_data_projections.service;

import ru.itk.spring_data_projections.dto.EmployeeDto;
import ru.itk.spring_data_projections.projection.EmployeeProjection;

import java.util.List;

public interface EmployeeService {
    EmployeeProjection save(EmployeeDto employee);
    List<EmployeeProjection> findAll();
    EmployeeProjection findById(Long id);
    EmployeeProjection update(Long id, EmployeeDto employee);
    void deleteById(Long id);

}
