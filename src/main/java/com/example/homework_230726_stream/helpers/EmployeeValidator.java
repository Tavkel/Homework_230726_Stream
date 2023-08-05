package com.example.homework_230726_stream.helpers;

import com.example.homework_230726_stream.dto.EmployeeDto;
import com.example.homework_230726_stream.exceptions.InvalidEmployeeDataException;
import org.apache.commons.lang3.StringUtils;

public class EmployeeValidator {
    public static void checkEmployee(EmployeeDto employee) {
        if (!StringUtils.isAlpha(employee.getFirstName())
                && StringUtils.isAlpha(employee.getMiddleName())
                && StringUtils.isAlpha(employee.getLastName())
                && employee.getSalary() >= 10_000
                && employee.getDepartmentId() <= 5
                && employee.getDepartmentId() > 0) {
            throw new InvalidEmployeeDataException();
        }
    }
}
