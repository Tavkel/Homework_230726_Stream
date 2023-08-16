package com.example.homework_230726_stream.helpers;

public class AppVariables {
    private Integer MAX_EMPLOYEE_COUNT = 6;
    private Integer DEPARTMENT_AMOUNT = 5;

    private Float MIN_SALARY = 10_000f;

    public Integer getMaxEmployeeCount() {
        return MAX_EMPLOYEE_COUNT;
    }

    public Integer getDepartmentAmount() {
        return DEPARTMENT_AMOUNT;
    }

    public Float getMinSalary() {
        return MIN_SALARY;
    }

    public void setMaxEmployeeCount(Integer MAX_EMPLOYEE_COUNT) {
        this.MAX_EMPLOYEE_COUNT = MAX_EMPLOYEE_COUNT;
    }

    public void setDepartmentAmount(Integer DEPARTMENT_AMOUNT) {
        this.DEPARTMENT_AMOUNT = DEPARTMENT_AMOUNT;
    }

    public void setMinSalary(Float MIN_SALARY) {
        this.MIN_SALARY = MIN_SALARY;
    }
}
