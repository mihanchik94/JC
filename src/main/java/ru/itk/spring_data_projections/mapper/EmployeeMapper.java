package ru.itk.spring_data_projections.mapper;

import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import ru.itk.spring_data_projections.dto.EmployeeDto;
import ru.itk.spring_data_projections.model.Employee;
import ru.itk.spring_data_projections.projection.EmployeeProjection;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE,
        builder = @Builder(disableBuilder = true))
public interface EmployeeMapper {

    @Mapping(source = "departmentId", target = "department.id")
    Employee dtoToEntity(EmployeeDto employeeDto);

    default EmployeeProjection entityToProjection(Employee employee) {
        return new EmployeeProjection() {
            @Override
            public String getFullName() {
                return employee.getFirstName() + " " + employee.getLastName();
            }

            @Override
            public String getPosition() {
                return employee.getPosition();
            }

            @Override
            public String getDepartmentName() {
                return employee.getDepartment().getName();
            }
        };
    }


}
