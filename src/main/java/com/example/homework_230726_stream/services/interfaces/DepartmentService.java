package com.example.homework_230726_stream.services.interfaces;

import com.example.homework_230726_stream.models.Department;
import com.example.homework_230726_stream.models.Employee;

import java.util.Collection;
import java.util.List;

public interface DepartmentService {
    List<Department> getAllDepartments();

    Department getDepartmentById(int id);

    Employee getMinSalaryEmployee(int id);

    Employee getMaxSalaryEmployee(int id);

    Collection<Employee> getEmployeesFromDepartment(int id);

    Collection<Employee> getEmployeesFromAllDepartments();
}
