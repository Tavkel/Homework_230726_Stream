package com.example.homework_230726_stream.helpers;

import com.example.homework_230726_stream.exceptions.InvalidEmployeeDataException;
import com.example.homework_230726_stream.models.Employee;
import org.apache.commons.lang3.StringUtils;

public class EmployeeValidator {
    public static boolean checkEmployee(Employee employee) {
        return StringUtils.isAlpha(employee.getFirstName())
                && StringUtils.isAlpha(employee.getMiddleName())
                && StringUtils.isAlpha(employee.getLastName())
                && employee.getSalary() >= 10_000
                && employee.getDepartmentId() <= 5
                && employee.getDepartmentId() > 0;
    }
}
