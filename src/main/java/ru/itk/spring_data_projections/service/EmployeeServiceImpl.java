package ru.itk.spring_data_projections.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.itk.spring_data_projections.dto.EmployeeDto;
import ru.itk.spring_data_projections.mapper.EmployeeMapper;
import ru.itk.spring_data_projections.model.Department;
import ru.itk.spring_data_projections.model.Employee;
import ru.itk.spring_data_projections.projection.EmployeeProjection;
import ru.itk.spring_data_projections.repository.EmployeeRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final DepartmentService departmentService;
    private final EmployeeMapper employeeMapper;

    @Override
    public EmployeeProjection save(EmployeeDto employee) {
        Employee savedEmployee = employeeMapper.dtoToEntity(employee);
        Department department = departmentService.findById(employee.getDepartmentId());
        savedEmployee.setDepartment(department);
        return employeeMapper.entityToProjection(employeeRepository.save(savedEmployee));
    }

    @Override
    public List<EmployeeProjection> findAll() {
        return employeeRepository.findAllProjectedBy();
    }

    @Override
    public EmployeeProjection findById(Long id) {
        return employeeRepository.findProjectedById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Employee with id = %d not found", id)));
    }

    @Override
    @Transactional
    public EmployeeProjection update(Long id, EmployeeDto employee) {
        Employee updatedEmployee = findEmployeeById(id);
        Department department = departmentService.findById(employee.getDepartmentId());
        updatedEmployee.setFirstName(employee.getFirstName());
        updatedEmployee.setLastName(employee.getLastName());
        updatedEmployee.setSalary(employee.getSalary());
        updatedEmployee.setPosition(employee.getPosition());
        updatedEmployee.setDepartment(department);
        return employeeMapper.entityToProjection(employeeRepository.save(updatedEmployee));
    }

    @Override
    public void deleteById(Long id) {
        employeeRepository.deleteById(id);
    }

    private Employee findEmployeeById(Long id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Employee with id = %d not found", id)));
    }
}
