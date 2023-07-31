package com.example.homework_230726_stream.controllers;

import com.example.homework_230726_stream.models.Employee;
import com.example.homework_230726_stream.services.interfaces.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {
    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping
    public ResponseEntity<List<Employee>> getAllEmployees() {
        return new ResponseEntity<>(employeeService.getAllEmployees(), HttpStatus.OK);
    }

    @GetMapping("/employee")
    public ResponseEntity<Employee> getEmployeeById(@RequestParam int id) {
        return new ResponseEntity<>(employeeService.getEmployeeById(id), HttpStatus.OK);
    }

    @PostMapping("/update")
    public ResponseEntity<Employee> updateEmployee(@RequestBody Employee employee) {
        if (employee.getId() == 0) {
            var result = employeeService.createEmployee(employee);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            var result = employeeService.updateEmployee(employee);
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
    }
}
