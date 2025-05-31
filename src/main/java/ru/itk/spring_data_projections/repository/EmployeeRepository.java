package ru.itk.spring_data_projections.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itk.spring_data_projections.model.Employee;
import ru.itk.spring_data_projections.projection.EmployeeProjection;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    List<EmployeeProjection> findAllProjectedBy();
    Optional<EmployeeProjection> findProjectedById(Long id);

}
