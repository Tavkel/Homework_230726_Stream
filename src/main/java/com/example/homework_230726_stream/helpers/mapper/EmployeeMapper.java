package com.example.homework_230726_stream.helpers.mapper;

import com.example.homework_230726_stream.dto.EmployeeDto;
import com.example.homework_230726_stream.models.Employee;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(uses = { DepartmentMapper.class})
public interface EmployeeMapper {
    EmployeeMapper MAPPER = Mappers.getMapper(EmployeeMapper.class);

    @Mapping(source = "departmentId", target ="department.id")
    Employee toEmployee(EmployeeDto employeeDto);
    @InheritInverseConfiguration
    EmployeeDto fromEmployee(Employee employee);
}
