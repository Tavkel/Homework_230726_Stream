package com.example.homework_230726_stream.controllers;

import com.example.homework_230726_stream.models.Department;
import com.example.homework_230726_stream.models.Employee;
import com.example.homework_230726_stream.services.interfaces.DepartmentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/departments")
public class DepartmentController {
    private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @GetMapping
    public ResponseEntity<Collection<Department>> getDepartments(){
        return new ResponseEntity<>(departmentService.getAllDepartments(), HttpStatus.OK);
    }
    //Кривые наименования ввиду несостыковки требований задачи и построения логики программы вцелом (да, знаю это плохо):
    //в задаче не предусмотрено существование класса Department
    @GetMapping("/all")
    public ResponseEntity<Collection<Employee>> getAllEmployeesFromDepartment(@RequestParam(required = false) Integer id){
        var result = new ArrayList<Employee>();
        if(id == null){
            result.addAll(departmentService.getEmployeesFromAllDepartments());
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
        result.addAll(departmentService.getEmployeesFromDepartment(id));
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/min-salary")
    public ResponseEntity<Employee> getMinSalaryEmployeeFromDepartment(@RequestParam int id){
        return new ResponseEntity<>(departmentService.getMinSalaryEmployee(id), HttpStatus.OK);
    }

    @GetMapping("/max-salary")
    public ResponseEntity<Employee> getMaxSalaryEmployeeFromDepartment(@RequestParam int id){
        return new ResponseEntity<>(departmentService.getMaxSalaryEmployee(id), HttpStatus.OK);
    }

}
