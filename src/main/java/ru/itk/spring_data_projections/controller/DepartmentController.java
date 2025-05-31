package ru.itk.spring_data_projections.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.itk.spring_data_projections.model.Department;
import ru.itk.spring_data_projections.service.DepartmentService;

import java.util.List;

@RestController
@RequestMapping("/department")
@RequiredArgsConstructor
public class DepartmentController {
    private final DepartmentService departmentService;

    @PostMapping("/")
    public ResponseEntity<Department> save(@RequestBody Department department) {
        return new ResponseEntity<>(departmentService.save(department), HttpStatus.CREATED);
    }

    @GetMapping("/")
    public ResponseEntity<List<Department>> getAll() {
        List<Department> departments = departmentService.findAll();
        return departments.isEmpty()
                ? new ResponseEntity<>(departments, HttpStatus.NO_CONTENT)
                : new ResponseEntity<>(departments, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Department> getById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(departmentService.findById(id));
    }

    @PutMapping("/")
    public ResponseEntity<Department> update(@RequestBody Department department) {
        return ResponseEntity.ok(departmentService.update(department));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        departmentService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
