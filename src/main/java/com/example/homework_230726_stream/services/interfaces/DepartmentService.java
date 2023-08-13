package com.example.homework_230726_stream.services.interfaces;

import com.example.homework_230726_stream.models.Department;
import com.example.homework_230726_stream.models.Employee;

import java.util.Collection;
import java.util.List;

public interface DepartmentService {
    List<Department> getAllDepartments();

    Employee getMinSalaryEmployee(int id);

    Employee getMaxSalaryEmployee(int id);

    Float getSumOfSalaries(int id);

    Collection<Employee> getEmployeesFromDepartment(int id);
}
