package com.example.homework_230726_stream.services.implementations;

import com.example.homework_230726_stream.controllers.GlobalControllerExceptionHandler;
import com.example.homework_230726_stream.helpers.StupidCache;
import com.example.homework_230726_stream.models.Department;
import com.example.homework_230726_stream.models.Employee;
import com.example.homework_230726_stream.services.interfaces.DepartmentService;
import com.example.homework_230726_stream.services.interfaces.EmployeeService;
import com.example.homework_230726_stream.services.repositories.DepartmentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

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

    @Override
    public Department getDepartmentById(int id) {
        checkCache();
        return cache.get(cacheKey).stream().filter(d -> d.getId() ==id).findFirst().get();
    }

    @Override
    public Employee getMinSalaryEmployee(int id) {
        checkCache();
        return  cache.get(cacheKey).stream()
                .filter(d -> d.getId() == id).findFirst().get()
                .getEmployees().stream().min(Comparator.comparingDouble(Employee::getSalary)).get();
    }

    @Override
    public Employee getMaxSalaryEmployee(int id) {
        checkCache();
        return  cache.get(cacheKey).stream()
                .filter(d -> d.getId() == id).findFirst().get()
                .getEmployees().stream().max(Comparator.comparingDouble(Employee::getSalary)).get();
    }

    @Override
    public Collection<Employee> getEmployeesFromDepartment(int id) {
        checkCache();
        return cache.get(cacheKey).stream().filter(d -> d.getId() == id).findFirst().get().getEmployees();
    }

    //Прям чувствую что перемудрил, но времени нет, и так из-за форс мажора опаздываю
    @Override
    public Collection<Employee> getEmployeesFromAllDepartments() {
        checkCache();
        var result = new ArrayList<Employee>();
        var departments = cache.get(cacheKey).stream().sorted(Comparator.comparingInt(d -> d.getId())).collect(Collectors.toList());
        for (var department : departments){
            try {
                result.addAll(department.getEmployees());
            } catch (Exception e){
                logger.info("No employees in department " + department.getDepartmentName() + " found");
            }
        }
        return result;
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
