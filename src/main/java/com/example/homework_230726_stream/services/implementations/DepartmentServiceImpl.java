package com.example.homework_230726_stream.services.implementations;

import com.example.homework_230726_stream.models.Department;
import com.example.homework_230726_stream.models.Employee;
import com.example.homework_230726_stream.services.interfaces.DepartmentService;
import com.example.homework_230726_stream.repositories.DepartmentRepository;
import com.example.homework_230726_stream.services.interfaces.StupidCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class DepartmentServiceImpl implements DepartmentService {
    private final Logger logger = LoggerFactory.getLogger(DepartmentServiceImpl.class);
    private final DepartmentRepository departmentRepository;
    private final StupidCache<List<Department>> cache;
    private final String cacheKey = DepartmentRepository.class.getSimpleName(); //"departments";

    public DepartmentServiceImpl(DepartmentRepository departmentRepository, StupidCache cache) {

        this.departmentRepository = departmentRepository;
        this.cache = cache;
    }

    @Override
    public List<Department> getAllDepartments() {
        return cache.get(cacheKey);
    }

    private Department getDepartmentById(int id) {
        return cache.get(cacheKey).stream()
                .filter(d -> d.getId() == id)
                .findFirst()
                .orElseThrow(NoSuchElementException::new);
    }

    @Override
    public Employee getMinSalaryEmployee(int id) {
        var department = getDepartmentById(id);
        return department.getEmployees().stream()
                .min(Comparator.comparingDouble(Employee::getSalary))
                .orElseThrow(NoSuchElementException::new);
    }

    @Override
    public Employee getMaxSalaryEmployee(int id) {
        var department = getDepartmentById(id);
        return department.getEmployees().stream()
                .max(Comparator.comparingDouble(Employee::getSalary))
                .orElseThrow(NoSuchElementException::new);
    }

    @Override
    public Float getSumOfSalaries(int id) {
        var department = getDepartmentById(id);
        return department.getEmployees().stream()
                .map(Employee::getSalary)
                .reduce(0.0f, Float::sum);
    }

    @Override
    public Collection<Employee> getEmployeesFromDepartment(int id) {
        var department = getDepartmentById(id);
        return department.getEmployees();
    }
}
