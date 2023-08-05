package com.example.homework_230726_stream.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class EmployeeDto {
    private int id;
    private String lastName;
    private String firstName;
    private String middleName;
    private float salary;
    private int department;

    public EmployeeDto(int id, String lastName, String firstName, String middleName, float salary, int departmentId) {
        this.id = id;
        this.lastName = lastName;
        this.firstName = firstName;
        this.middleName = middleName;
        this.salary = salary;
        this.department = departmentId;
    }

    public EmployeeDto() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public float getSalary() {
        return salary;
    }

    public void setSalary(float salary) {
        this.salary = salary;
    }

    @JsonProperty("department")
    public int getDepartmentId() {
        return department;
    }

    public void setDepartmentId(int departmentId) {
        this.department = departmentId;
    }
}
