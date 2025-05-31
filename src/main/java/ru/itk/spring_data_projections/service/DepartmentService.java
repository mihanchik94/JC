package ru.itk.spring_data_projections.service;

import ru.itk.spring_data_projections.model.Department;

import java.util.List;

public interface DepartmentService {
    Department save(Department department);
    List<Department> findAll();
    Department findById(Long id);
    Department update(Department department);
    void deleteById(Long id);

}
