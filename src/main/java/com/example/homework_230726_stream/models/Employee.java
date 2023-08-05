package com.example.homework_230726_stream.models;

import com.fasterxml.jackson.annotation.*;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "EMPLOYEES")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "last_name")
    private String lastName;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "middle_name")
    private String middleName;
    @Column(name = "salary")
    private float salary;

    //При попытке создания/эдита сотрудника
    //2023-08-01 00:18:09.847  WARN 10028 --- [nio-8080-exec-5] .c.j.MappingJackson2HttpMessageConverter : Failed to evaluate Jackson deserialization for type [[simple type, class com.example.homework_230726_stream.models.Employee]]: com.fasterxml.jackson.databind.exc.InvalidDefinitionException: Cannot handle managed/back reference 'defaultReference': back reference type (`java.util.Set<com.example.homework_230726_stream.models.Employee>`) not compatible with managed type (com.example.homework_230726_stream.models.Employee)
    //Я сдаюсь пока - двухсторонние референсы и жсон это отдельная песня <______<'''''
    @ManyToOne
    @JsonManagedReference
    //@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JoinColumn(name = "department_id", nullable = false)
    private Department department;

    public Employee() {
    }

    public Employee(int id, String lastName, String firstName, String middleName, float salary, int departmentId) {

        this.id = id;
        this.lastName = lastName;
        this.firstName = firstName;
        this.middleName = middleName;
        this.salary = salary;
        this.department = new Department(departmentId); //Посмотреть как это делается нормально
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

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    @JsonIgnore
    public int getDepartmentId() {
        return this.getDepartment().getId();
    }

    @Override
    public String toString() {
        return String.format("Id: %d\n" +
                        "ФИО: %s %s %s\n" +
                        "Заработная плата: %.2f\n" +
                        "Отдел: %s",
                id, lastName, firstName, middleName, salary, department.getDepartmentName());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Employee)) return false;
        Employee employee = (Employee) o;
        return Float.compare(employee.salary, salary) == 0
                && Objects.equals(lastName, employee.lastName)
                && Objects.equals(firstName, employee.firstName)
                && Objects.equals(middleName, employee.middleName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(lastName, firstName, middleName, salary);
    }
}
