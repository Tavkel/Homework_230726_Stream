package com.example.homework_230726_stream.controllers;

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
    public ResponseEntity<Collection<Department>> getDepartments() {
        return new ResponseEntity<>(departmentService.getAllDepartments(), HttpStatus.OK);
    }

    @GetMapping(path = "/all", params = {"id"})
    public ResponseEntity<Collection<Employee>> getEmployeesFromDepartment(@RequestParam Integer id) {
        return new ResponseEntity<>(departmentService.getEmployeesFromDepartment(id), HttpStatus.OK);
    }

    @GetMapping(path = "/all", params = {})
    public ResponseEntity<Map<String, List<Employee>>> getAllEmployeesGroupedByDepartment() {
        return new ResponseEntity<>(employeeService.getAllEmployeesGroupedByDepartment(), HttpStatus.OK);
    }

    @GetMapping("/min-salary")
    public ResponseEntity<Employee> getMinSalaryEmployeeFromDepartment(@RequestParam int id) {
        return new ResponseEntity<>(departmentService.getMinSalaryEmployee(id), HttpStatus.OK);
    }

    @GetMapping("/max-salary")
    public ResponseEntity<Employee> getMaxSalaryEmployeeFromDepartment(@RequestParam int id) {
        return new ResponseEntity<>(departmentService.getMaxSalaryEmployee(id), HttpStatus.OK);
    }

}
