package ru.itk.spring_data_projections.service;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.itk.spring_data_projections.dto.EmployeeDto;
import ru.itk.spring_data_projections.mapper.EmployeeMapper;
import ru.itk.spring_data_projections.model.Department;
import ru.itk.spring_data_projections.model.Employee;
import ru.itk.spring_data_projections.projection.EmployeeProjection;
import ru.itk.spring_data_projections.repository.EmployeeRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceImplTest {

    @Mock
    private EmployeeRepository employeeRepository;
    @Mock
    private DepartmentService departmentService;
    @Mock
    private EmployeeMapper employeeMapper;
    @InjectMocks
    private EmployeeServiceImpl employeeService;


    @Test
    public void testSave() {
        EmployeeDto employeeDto = EmployeeDto.builder()
                .firstName("John")
                .lastName("Doe")
                .position("Developer")
                .salary(new BigDecimal("70000"))
                .departmentId(1L)
                .build();

        Employee savedEmployee = Employee.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .position("Developer")
                .salary(new BigDecimal("70000"))
                .department(Department.builder().id(1L).name("IT").build())
                .build();

        EmployeeProjection projection = new EmployeeProjection() {
            @Override
            public String getFullName() {
                return "John Doe";
            }

            @Override
            public String getPosition() {
                return "Developer";
            }

            @Override
            public String getDepartmentName() {
                return "IT";
            }
        };

        when(employeeMapper.dtoToEntity(employeeDto)).thenReturn(savedEmployee);
        when(departmentService.findById(1L)).thenReturn(Department.builder().id(1L).name("IT").build());
        when(employeeRepository.save(savedEmployee)).thenReturn(savedEmployee);
        when(employeeMapper.entityToProjection(savedEmployee)).thenReturn(projection);

        EmployeeProjection result = employeeService.save(employeeDto);

        assertNotNull(result);
        assertEquals("John Doe", result.getFullName());
        assertEquals("Developer", result.getPosition());
        assertEquals("IT", result.getDepartmentName());

        verify(employeeMapper, times(1)).dtoToEntity(employeeDto);
        verify(departmentService, times(1)).findById(1L);
        verify(employeeRepository, times(1)).save(savedEmployee);
        verify(employeeMapper, times(1)).entityToProjection(savedEmployee);
    }

    @Test
    public void testFindAll() {

        EmployeeProjection projection1 = new EmployeeProjection() {
            @Override
            public String getFullName() {
                return "John Doe";
            }

            @Override
            public String getPosition() {
                return "Developer";
            }

            @Override
            public String getDepartmentName() {
                return "IT";
            }
        };

        EmployeeProjection projection2 = new EmployeeProjection() {
            @Override
            public String getFullName() {
                return "Jane Smith";
            }

            @Override
            public String getPosition() {
                return "Manager";
            }

            @Override
            public String getDepartmentName() {
                return "HR";
            }
        };

        when(employeeRepository.findAllProjectedBy()).thenReturn(java.util.List.of(projection1, projection2));

        List<EmployeeProjection> result = employeeService.findAll();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("John Doe", result.get(0).getFullName());
        assertEquals("Manager", result.get(1).getPosition());

        verify(employeeRepository, times(1)).findAllProjectedBy();
    }

    @Test
    public void testFindById() {
        Long id = 1L;
        EmployeeProjection projection = new EmployeeProjection() {
            @Override
            public String getFullName() {
                return "John Doe";
            }

            @Override
            public String getPosition() {
                return "Developer";
            }

            @Override
            public String getDepartmentName() {
                return "IT";
            }
        };

        when(employeeRepository.findProjectedById(id)).thenReturn(Optional.of(projection));

        EmployeeProjection result = employeeService.findById(id);

        assertNotNull(result);
        assertEquals("John Doe", result.getFullName());
        assertEquals("Developer", result.getPosition());
        assertEquals("IT", result.getDepartmentName());

        verify(employeeRepository, times(1)).findProjectedById(id);
    }

    @Test
    public void testFindByIdNotFound() {
        Long id = 1L;
        when(employeeRepository.findProjectedById(id)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> employeeService.findById(id));

        verify(employeeRepository, times(1)).findProjectedById(id);
    }

    @Test
    public void testUpdateProjection() {
        Long id = 1L;
        EmployeeDto employeeDto = EmployeeDto.builder()
                .firstName("Jane")
                .lastName("Smith")
                .position("Manager")
                .salary(new BigDecimal("90000"))
                .departmentId(2L)
                .build();

        Employee employee = Employee.builder()
                .id(id)
                .firstName("John")
                .lastName("Doe")
                .position("Developer")
                .salary(new BigDecimal("70000"))
                .department(Department.builder().id(1L).name("IT").build())
                .build();

        Department department = Department.builder().id(2L).name("HR").build();

        Employee updatedEmployee = Employee.builder()
                .id(id)
                .firstName("Jane")
                .lastName("Smith")
                .position("Manager")
                .salary(new BigDecimal("90000"))
                .department(department)
                .build();

        EmployeeProjection projection = new EmployeeProjection() {
            @Override
            public String getFullName() {
                return "Jane Smith";
            }

            @Override
            public String getPosition() {
                return "Manager";
            }

            @Override
            public String getDepartmentName() {
                return "HR";
            }
        };

        when(employeeRepository.findById(id)).thenReturn(Optional.of(employee));
        when(departmentService.findById(department.getId())).thenReturn(department);
        when(employeeRepository.save(updatedEmployee)).thenReturn(updatedEmployee);
        when(employeeMapper.entityToProjection(updatedEmployee)).thenReturn(projection);

        EmployeeProjection result = employeeService.update(id, employeeDto);

        assertNotNull(result);
        assertEquals("Jane Smith", result.getFullName());
        assertEquals("Manager", result.getPosition());
        assertEquals("HR", result.getDepartmentName());

        verify(employeeRepository, times(1)).findById(id);
        verify(departmentService, times(1)).findById(2L);
        verify(employeeRepository, times(1)).save(updatedEmployee);
        verify(employeeMapper, times(1)).entityToProjection(updatedEmployee);
    }

    @Test
    public void testDeleteById() {
        Long id = 1L;

        employeeService.deleteById(id);

        verify(employeeRepository, times(1)).deleteById(id);
    }

}