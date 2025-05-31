package ru.itk.spring_data_projections.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.itk.spring_data_projections.dto.EmployeeDto;
import ru.itk.spring_data_projections.projection.EmployeeProjection;
import ru.itk.spring_data_projections.service.EmployeeService;

import java.util.List;

@RestController
@RequestMapping("/employee")
@RequiredArgsConstructor
public class EmployeeController {
    private final EmployeeService employeeService;

    @PostMapping("/")
    public ResponseEntity<EmployeeProjection> save(@RequestBody EmployeeDto employeeDto) {
        return new ResponseEntity<>(employeeService.save(employeeDto), HttpStatus.CREATED);
    }

    @GetMapping("/")
    public ResponseEntity<List<EmployeeProjection>> getAll() {
        List<EmployeeProjection> employeeProjections = employeeService.findAll();
        return employeeProjections.isEmpty()
                ? new ResponseEntity<>(employeeProjections, HttpStatus.NO_CONTENT)
                : new ResponseEntity<>(employeeProjections, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeProjection> getById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(employeeService.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmployeeProjection> update(@PathVariable("id") Long id, @RequestBody EmployeeDto employeeDto) {
        return ResponseEntity.ok(employeeService.update(id, employeeDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        employeeService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
