package com.example.homework_230726_stream.dto;

import com.example.homework_230726_stream.models.Employee;
import com.fasterxml.jackson.annotation.JsonBackReference;

import java.util.Objects;
import java.util.Set;

public class DepartmentDto {
        private int id;
        private String departmentName;
        @JsonBackReference
        private Set<Employee> employees;

        public DepartmentDto() {
        }

        public String getDepartmentName() {
            return departmentName;
        }

        public void setDepartmentName(String departmentName) {
            this.departmentName = departmentName;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public Set<Employee> getEmployees() {
            return employees;
        }

        @Override
        public String toString() {
            return "Department{" +
                    "id=" + id +
                    ", departmentName='" + departmentName + '\'' +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof com.example.homework_230726_stream.dto.DepartmentDto)) return false;
            com.example.homework_230726_stream.dto.DepartmentDto that = (com.example.homework_230726_stream.dto.DepartmentDto) o;
            return Objects.equals(departmentName, that.departmentName);
        }

        @Override
        public int hashCode() {
            return Objects.hash(id, departmentName);
        }

}
