package com.example.homework_230726_stream.controllers;

import com.example.homework_230726_stream.dto.EmployeeDto;
import com.example.homework_230726_stream.helpers.EmployeeValidator;
import com.example.homework_230726_stream.helpers.mapper.EmployeeMapper;
import com.example.homework_230726_stream.models.Employee;
import com.example.homework_230726_stream.services.interfaces.EmployeeService;
import org.mapstruct.Mapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/employees")
public class EmployeeController {
    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping
    public ResponseEntity<List<EmployeeDto>> getAllEmployees() {
        var result = employeeService.getAllEmployees()
                .stream()
                .map(EmployeeMapper.MAPPER::fromEmployee)
                .collect(Collectors.toList());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/employee")
    public ResponseEntity<EmployeeDto> getEmployeeById(@RequestParam int id) {
        return new ResponseEntity<>(EmployeeMapper.MAPPER.fromEmployee(employeeService.getEmployeeById(id)), HttpStatus.OK);
    }

    @PostMapping("/update")
    public ResponseEntity<EmployeeDto> updateEmployee(@RequestBody EmployeeDto employee) {
        Employee result;
        if (employee.getId() == 0) {
            result = employeeService.createEmployee(EmployeeMapper.MAPPER.toEmployee(employee));
        } else {
            result = employeeService.updateEmployee(EmployeeMapper.MAPPER.toEmployee(employee));
        }
        return new ResponseEntity<>(EmployeeMapper.MAPPER.fromEmployee(result), HttpStatus.OK);
    }
}
