package com.example.homework_230726_stream.services.implementations;

import com.example.homework_230726_stream.exceptions.EmployeeAlreadyExistsException;
import com.example.homework_230726_stream.exceptions.MaxEmployeeCountReachedException;
import com.example.homework_230726_stream.helpers.StupidCacheImpl;
import com.example.homework_230726_stream.models.Employee;
import com.example.homework_230726_stream.services.interfaces.EmployeeService;
import com.example.homework_230726_stream.repositories.EmployeeRepository;
import com.example.homework_230726_stream.services.interfaces.StupidCache;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    private final int MAX_EMPLOYEE_COUNT = 11;
    private final EmployeeRepository employeeRepository;

    private final StupidCache<List<Employee>> cache;
    private final String cacheKey = "employees";

    public EmployeeServiceImpl(EmployeeRepository employeeRepository, StupidCache cache) {
        this.employeeRepository = employeeRepository;
        this.cache = cache;
    }

    @Override
    public List<Employee> getAllEmployees() {
        if(!cache.checkCache(cacheKey)){
            cache.loadCache(cacheKey, employeeRepository.findAll());
        }
        return cache.get(cacheKey);
    }

    @Override
    public Map<String, List<Employee>> getAllEmployeesGroupedByDepartment() {
        if(!cache.checkCache(cacheKey)){
            cache.loadCache(cacheKey, employeeRepository.findAll());
        }
        return cache.get(cacheKey).stream()
                .collect(Collectors.groupingBy(e -> e.getDepartment().getDepartmentName()));
    }

    @Override
    public Employee getEmployeeById(int id) {
        if(!cache.checkCache(cacheKey)){
            cache.loadCache(cacheKey, employeeRepository.findAll());
        }
        var result = cache.get(cacheKey).stream()
                .filter(e -> e.getId() == id)
                .findFirst();
        if (result.isPresent()) {
            return result.get();
        } else {
            throw new NoSuchElementException();
        }
    }

    @Override
    public Employee updateEmployee(Employee employee) {
        cache.dropCacheKey(cacheKey);
        employeeRepository.saveAndFlush(employee);
        return employee;
    }

    @Override
    public Employee createEmployee(Employee employee) {
        if(!cache.checkCache(cacheKey)){
            cache.loadCache(cacheKey, employeeRepository.findAll());
        }
        if (cache.get(cacheKey).size() >= MAX_EMPLOYEE_COUNT) {
            throw new MaxEmployeeCountReachedException();
        }
        if (cache.get(cacheKey).contains(employee)) {
            throw new EmployeeAlreadyExistsException();
        }
        cache.dropCacheKey(cacheKey);
        employeeRepository.saveAndFlush(employee);
        return employee;
    }
}
