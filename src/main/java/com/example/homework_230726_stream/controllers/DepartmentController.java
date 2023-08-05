package com.example.homework_230726_stream.controllers;

import com.example.homework_230726_stream.dto.DepartmentDto;
import com.example.homework_230726_stream.dto.EmployeeDto;
import com.example.homework_230726_stream.helpers.mapper.DepartmentMapper;
import com.example.homework_230726_stream.helpers.mapper.EmployeeMapper;
import com.example.homework_230726_stream.models.Department;
import com.example.homework_230726_stream.models.Employee;
import com.example.homework_230726_stream.services.interfaces.DepartmentService;
import com.example.homework_230726_stream.services.interfaces.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/departments")
public class DepartmentController {
    private final DepartmentService departmentService;
    private final EmployeeService employeeService;

    public DepartmentController(DepartmentService departmentService, EmployeeService employeeService) {
        this.departmentService = departmentService;
        this.employeeService = employeeService;
    }

    @GetMapping
    public ResponseEntity<List<DepartmentDto>> getDepartments() {
        var result = departmentService.getAllDepartments()
                .stream()
                .map(DepartmentMapper.MAPPER::fromDepartment)
                .collect(Collectors.toList());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping(path = "/all", params = {"id"})
    public ResponseEntity<List<EmployeeDto>> getEmployeesFromDepartment(@RequestParam Integer id) {
        var result = departmentService.getEmployeesFromDepartment(id)
                .stream().map(EmployeeMapper.MAPPER::fromEmployee)
                .collect(Collectors.toList());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    //Need help here.
    @GetMapping(path = "/all", params = {})
    public ResponseEntity<Map<String, List<Employee>>> getAllEmployeesGroupedByDepartment() {
        var result = employeeService.getAllEmployeesGroupedByDepartment();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/min-salary")
    public ResponseEntity<EmployeeDto> getMinSalaryEmployeeFromDepartment(@RequestParam int id) {
        var result = EmployeeMapper.MAPPER.fromEmployee(departmentService.getMinSalaryEmployee(id));
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/max-salary")
    public ResponseEntity<EmployeeDto> getMaxSalaryEmployeeFromDepartment(@RequestParam int id) {
        var result = EmployeeMapper.MAPPER.fromEmployee(departmentService.getMaxSalaryEmployee(id));
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

}
