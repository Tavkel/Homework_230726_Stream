package com.example.homework_230726_stream.helpers.mapper;

import com.example.homework_230726_stream.dto.DepartmentDto;
import com.example.homework_230726_stream.models.Department;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface DepartmentMapper {
    DepartmentMapper MAPPER = Mappers.getMapper(DepartmentMapper.class);

    @Mapping(target ="employees", ignore = true)
    Department toDepartment(DepartmentDto departmentDto);

    @InheritInverseConfiguration
    DepartmentDto fromDepartment(Department department);
}
