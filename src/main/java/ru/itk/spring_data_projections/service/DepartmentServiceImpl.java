package ru.itk.spring_data_projections.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.itk.spring_data_projections.model.Department;
import ru.itk.spring_data_projections.repository.DepartmentRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {
    private final DepartmentRepository departmentRepository;

    @Override
    public Department save(Department department) {
        return departmentRepository.save(department);
    }

    @Override
    public List<Department> findAll() {
        return departmentRepository.findAll();
    }

    @Override
    public Department findById(Long id) {
        return departmentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Department with id = %d not found", id)));
    }

    @Override
    public Department update(Department department) {
        if (!departmentRepository.existsById(department.getId())) {
            throw new EntityNotFoundException(String.format("Department with id = %d not found", department.getId()));
        }
        return departmentRepository.save(department);
    }

    @Override
    public void deleteById(Long id) {
        departmentRepository.deleteById(id);
    }
}
