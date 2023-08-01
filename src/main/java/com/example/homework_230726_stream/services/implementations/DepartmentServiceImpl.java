package com.example.homework_230726_stream.services.implementations;

import com.example.homework_230726_stream.helpers.StupidCache;
import com.example.homework_230726_stream.models.Department;
import com.example.homework_230726_stream.models.Employee;
import com.example.homework_230726_stream.services.interfaces.DepartmentService;
import com.example.homework_230726_stream.repositories.DepartmentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class DepartmentServiceImpl implements DepartmentService {
    private final Logger logger = LoggerFactory.getLogger(DepartmentServiceImpl.class);
    private final DepartmentRepository departmentRepository;
    private final StupidCache<List<Department>> cache;
    private final String cacheKey = "departments";

    public DepartmentServiceImpl(DepartmentRepository departmentRepository, StupidCache cache) {

        this.departmentRepository = departmentRepository;
        this.cache = cache;
    }

    @Override
    public List<Department> getAllDepartments() {
        checkCache();
        return cache.get(cacheKey);
    }

    private Department getDepartmentById(int id) {
        checkCache();
        var result = cache.get(cacheKey).stream()
                .filter(d -> d.getId() == id)
                .findFirst();
        if (result.isPresent()) {
            return result.get();
        } else {
            throw new NoSuchElementException();
        }
    }

    @Override
    public Employee getMinSalaryEmployee(int id) {
        checkCache();
        var department = getDepartmentById(id);
        var result = department.getEmployees().stream()
                .min(Comparator.comparingDouble(Employee::getSalary));
        if (result.isPresent()) {
            return result.get();
        } else {
            throw new NoSuchElementException();
        }
    }

    @Override
    public Employee getMaxSalaryEmployee(int id) {
        checkCache();
        var department = getDepartmentById(id);
        var result = department.getEmployees().stream()
                .max(Comparator.comparingDouble(Employee::getSalary));
        if (result.isPresent()) {
            return result.get();
        } else {
            throw new NoSuchElementException();
        }
    }

    @Override
    public Collection<Employee> getEmployeesFromDepartment(int id) {
        checkCache();
        var department = getDepartmentById(id);
        return department.getEmployees();
    }

    private void checkCache() {
        if (!cache.hasKey(cacheKey)) {
            loadCache();
        }
    }

    private void loadCache() {
        cache.set(cacheKey, departmentRepository.findAll());
    }
}
