package ru.itk.spring_data_projections.mapper;

import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import ru.itk.spring_data_projections.dto.EmployeeDto;
import ru.itk.spring_data_projections.model.Department;
import ru.itk.spring_data_projections.model.Employee;
import ru.itk.spring_data_projections.projection.EmployeeProjection;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class EmployeeMapperTest {

    private final EmployeeMapper employeeMapper = Mappers.getMapper(EmployeeMapper.class);


    @Test
    public void testDtoToEntity() {
        EmployeeDto dto = EmployeeDto.builder()
                .firstName("John")
                .lastName("Doe")
                .position("Developer")
                .salary(new BigDecimal("70000.00"))
                .departmentId(1L)
                .build();

        Employee entity = employeeMapper.dtoToEntity(dto);

        assertNotNull(entity);
        assertEquals(dto.getFirstName(), entity.getFirstName());
        assertEquals(dto.getLastName(), entity.getLastName());
        assertEquals(dto.getPosition(), entity.getPosition());
        assertEquals(dto.getSalary(), entity.getSalary());
        assertNotNull(entity.getDepartment());
        assertEquals(dto.getDepartmentId(), entity.getDepartment().getId());
    }

    @Test
    public void testEntityToProjection() {
        Department department = new Department();
        department.setId(1L);
        department.setName("Engineering");

        Employee employee = new Employee();
        employee.setId(1L);
        employee.setFirstName("Jane");
        employee.setLastName("Smith");
        employee.setPosition("Manager");
        employee.setSalary(new BigDecimal("90000.00"));
        employee.setDepartment(department);

        EmployeeProjection projection = employeeMapper.entityToProjection(employee);

        assertNotNull(projection);
        assertEquals("Jane Smith", projection.getFullName());
        assertEquals("Manager", projection.getPosition());
        assertEquals("Engineering", projection.getDepartmentName());
    }

}