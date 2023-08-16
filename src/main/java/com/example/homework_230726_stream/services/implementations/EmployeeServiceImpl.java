package com.example.homework_230726_stream.services.implementations;

import com.example.homework_230726_stream.exceptions.EmployeeAlreadyExistsException;
import com.example.homework_230726_stream.exceptions.InvalidEmployeeDataException;
import com.example.homework_230726_stream.exceptions.MaxEmployeeCountReachedException;
import com.example.homework_230726_stream.helpers.AppVariables;
import com.example.homework_230726_stream.helpers.EmployeeValidator;
import com.example.homework_230726_stream.models.Employee;
import com.example.homework_230726_stream.services.interfaces.EmployeeService;
import com.example.homework_230726_stream.repositories.EmployeeRepository;
import com.example.homework_230726_stream.services.interfaces.StupidCache;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    private final int MAX_EMPLOYEE_COUNT;
    private final EmployeeRepository employeeRepository;
    private final EmployeeValidator employeeValidator;
    private final StupidCache<List<Employee>> cache;
    private final String cacheKey = EmployeeRepository.class.getSimpleName(); //"employees";

    public EmployeeServiceImpl(EmployeeRepository employeeRepository,
                               StupidCache cache,
                               AppVariables appVariables,
                               EmployeeValidator employeeValidator) {
        this.employeeRepository = employeeRepository;
        this.cache = cache;
        this.MAX_EMPLOYEE_COUNT = appVariables.getMaxEmployeeCount();
        this.employeeValidator = employeeValidator;
    }

    @Override
    public List<Employee> getAllEmployees() {
        return cache.get(cacheKey);
    }


    //TODO
    // - check mapping Employee to EmployeeDto
    // - problem: how to group by department name if no department name exists if mapping to dto
    @Override
    public Map<String, List<Employee>> getAllEmployeesGroupedByDepartment() {
        return cache.get(cacheKey)
                .stream()
                .collect(Collectors.groupingBy(e -> e.getDepartment().getDepartmentName()));
    }

    @Override
    public Employee getEmployeeById(int id) {
        return cache.get(cacheKey).stream()
                .filter(e -> e.getId() == id)
                .findFirst()
                .orElseThrow(NoSuchElementException::new);
    }

    @Override
    public Employee updateEmployee(Employee employee) {
        if (!employeeValidator.checkEmployee(employee)) {
            throw new InvalidEmployeeDataException();
        }

        if (cache.get(cacheKey).stream().noneMatch(e -> e.getId() == employee.getId())) {
            throw new NoSuchElementException();
        }

        capitalizeNames(employee);

        cache.dropCache();
        employeeRepository.saveAndFlush(employee);
        return employee;
    }

    @Override
    public Employee createEmployee(Employee employee) {
        var employees = cache.get(cacheKey);
        if (employees.size() >= MAX_EMPLOYEE_COUNT) {
            throw new MaxEmployeeCountReachedException();
        }

        if (!employeeValidator.checkEmployee(employee)) throw new InvalidEmployeeDataException();
        capitalizeNames(employee);

        if (employees.contains(employee)) {
            throw new EmployeeAlreadyExistsException();
        }
        cache.dropCache();
        employeeRepository.saveAndFlush(employee);
        return employee;
    }

    private static void capitalizeNames(Employee employee) {
        employee.setFirstName(StringUtils.capitalize(employee.getFirstName()));
        employee.setMiddleName(StringUtils.capitalize(employee.getMiddleName()));
        employee.setLastName(StringUtils.capitalize(employee.getLastName()));
    }
}
