package ru.itk.spring_data_projections.service;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.itk.spring_data_projections.model.Department;
import ru.itk.spring_data_projections.repository.DepartmentRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DepartmentServiceImplTest {
    @Mock
    private DepartmentRepository departmentRepository;

    @InjectMocks
    private DepartmentServiceImpl departmentService;

    private Department department;

    @BeforeEach
    public void setUp() {
        department = new Department();
        department.setId(1L);
        department.setName("HR");
    }

    @Test
    public void testSaveDepartment() {
        when(departmentRepository.save(any(Department.class))).thenReturn(department);

        Department savedDepartment = departmentService.save(department);

        assertNotNull(savedDepartment);
        assertEquals(department.getId(), savedDepartment.getId());
        assertEquals(department.getName(), savedDepartment.getName());

        verify(departmentRepository, times(1)).save(department);
    }

    @Test
    public void testFindAllDepartments() {
        when(departmentRepository.findAll()).thenReturn(java.util.List.of(department));

        List<Department> departments = departmentService.findAll();

        assertNotNull(departments);
        assertEquals(1, departments.size());
        assertEquals(department.getId(), departments.get(0).getId());
        assertEquals(department.getName(), departments.get(0).getName());

        verify(departmentRepository, times(1)).findAll();
    }

    @Test
    public void testFindById() {
        when(departmentRepository.findById(1L)).thenReturn(Optional.of(department));

        Department foundDepartment = departmentService.findById(1L);

        assertNotNull(foundDepartment);
        assertEquals(department.getId(), foundDepartment.getId());
        assertEquals(department.getName(), foundDepartment.getName());

        verify(departmentRepository, times(1)).findById(1L);
    }

    @Test
    public void testFindByIdNotFound() {
        when(departmentRepository.findById(2L)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> departmentService.findById(2L));

        assertEquals("Department with id = 2 not found", exception.getMessage());

        verify(departmentRepository, times(1)).findById(2L);
    }

    @Test
    public void testUpdateDepartment() {
        Department updatedDepartment = new Department();
        updatedDepartment.setId(1L);
        updatedDepartment.setName("Finance");

        when(departmentRepository.existsById(1L)).thenReturn(true);
        when(departmentRepository.save(any(Department.class))).thenReturn(updatedDepartment);

        Department result = departmentService.update(updatedDepartment);

        assertNotNull(result);
        assertEquals(updatedDepartment.getId(), result.getId());
        assertEquals(updatedDepartment.getName(), result.getName());

        verify(departmentRepository, times(1)).existsById(1L);
        verify(departmentRepository, times(1)).save(updatedDepartment);
    }

    @Test
    public void testUpdateDepartmentNotFound() {
        Department updatedDepartment = new Department();
        updatedDepartment.setId(1L);
        updatedDepartment.setName("Finance");

        when(departmentRepository.existsById(updatedDepartment.getId())).thenReturn(false);

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> departmentService.update(updatedDepartment));

        assertEquals("Department with id = 1 not found", exception.getMessage());

        verify(departmentRepository, times(1)).existsById(1L);
        verify(departmentRepository, never()).save(any(Department.class));
    }

    @Test
    public void testDeleteById() {
        departmentService.deleteById(1L);
        verify(departmentRepository, times(1)).deleteById(1L);
    }

}