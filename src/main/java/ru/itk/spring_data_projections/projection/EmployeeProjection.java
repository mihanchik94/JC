package ru.itk.spring_data_projections.projection;

import org.springframework.beans.factory.annotation.Value;

public interface EmployeeProjection {
    @Value("#{target.firstName + ' ' + target.lastName}")
    String getFullName();
    String getPosition();
    @Value("#{target.department.name}")
    String getDepartmentName();
}
