package ru.itk.spring_data_projections.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itk.spring_data_projections.model.Department;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
}
