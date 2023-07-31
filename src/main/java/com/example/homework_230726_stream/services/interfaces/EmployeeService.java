package com.example.homework_230726_stream.services.interfaces;

import com.example.homework_230726_stream.exceptions.MaxEmployeeCountReachedException;
import com.example.homework_230726_stream.models.Employee;

import java.util.List;

public interface EmployeeService {
    List<Employee> getAllEmployees();

    Employee getEmployeeById(int id);

    Employee updateEmployee(Employee employee);

    Employee createEmployee(Employee employee) throws MaxEmployeeCountReachedException;
}
