package com.example.homework_230726_stream.helpers;

import com.example.homework_230726_stream.exceptions.InvalidEmployeeDataException;
import com.example.homework_230726_stream.models.Employee;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

@Component
public class EmployeeValidator {
    private final Integer departmentAmount;
    private final Float minSalary;
    public EmployeeValidator(AppVariables appVariables){
        this.departmentAmount = appVariables.getDepartmentAmount();
        this.minSalary = appVariables.getMinSalary();
    }
    public boolean checkEmployee(Employee employee) {
        return StringUtils.isAlpha(employee.getFirstName())
                && StringUtils.isAlpha(employee.getMiddleName())
                && StringUtils.isAlpha(employee.getLastName())
                && employee.getSalary() >= minSalary
                && employee.getDepartmentId() <= departmentAmount
                && employee.getDepartmentId() > 0;
    }
}
