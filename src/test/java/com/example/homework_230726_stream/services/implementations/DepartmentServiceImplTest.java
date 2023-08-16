package com.example.homework_230726_stream.services.implementations;

import com.example.homework_230726_stream.models.Department;
import com.example.homework_230726_stream.models.Employee;
import com.example.homework_230726_stream.repositories.DepartmentRepository;
import com.example.homework_230726_stream.services.interfaces.DepartmentService;
import com.example.homework_230726_stream.services.interfaces.StupidCache;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static com.example.homework_230726_stream.services.implementations.DepartmentServiceImplTest.DepartmentServiceTestData.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DepartmentServiceImplTest {
    @Mock
    private DepartmentRepository departmentRepository;
    @Mock
    private StupidCache<List<Department>> cache;
    private DepartmentService sut;

    @BeforeEach
    private void initializeSut() {
        sut = new DepartmentServiceImpl(departmentRepository, cache);
        attachEmployees();
    }

    @Test
    void getAllDepartments_shouldReturnListOfAllDepartments() {
        when(cache.get(DepartmentRepository.class.getSimpleName())).thenReturn(DEPARTMENTS);
        var actual = sut.getAllDepartments();
        assertEquals(DEPARTMENTS, actual);
        verify(cache, only()).get(any());
    }

    @Test
    void getMinSalaryEmployee_shouldReturnEmployeeWithMinimalSalary() {
        when(cache.get(DepartmentRepository.class.getSimpleName())).thenReturn(DEPARTMENTS);
        var actual = sut.getMinSalaryEmployee(2);
        assertEquals(EMPLOYEE2, actual);
        verify(cache, only()).get(any());
    }

    @Test
    void getMinSalaryEmployee_shouldThrowNoSuchElementExceptionIfNonexistentDepartmentIdProvided() {
        when(cache.get(DepartmentRepository.class.getSimpleName())).thenReturn(DEPARTMENTS);
        assertThrows(NoSuchElementException.class, () -> sut.getMinSalaryEmployee(6));
        verify(cache, only()).get(any());
    }

    @Test
    void getMaxSalaryEmployee_shouldReturnEmployeeWithMaximumSalary() {
        when(cache.get(DepartmentRepository.class.getSimpleName())).thenReturn(DEPARTMENTS);
        var actual = sut.getMaxSalaryEmployee(5);
        verify(cache, only()).get(any());
        assertEquals(EMPLOYEE55, actual);
    }

    @Test
    void getMaxSalaryEmployee_shouldThrowNoSuchElementExceptionIfNonexistentDepartmentIdProvided() {
        when(cache.get(DepartmentRepository.class.getSimpleName())).thenReturn(DEPARTMENTS);
        assertThrows(NoSuchElementException.class, () -> sut.getMaxSalaryEmployee(6));
        verify(cache, only()).get(any());
    }

    @Test
    void getSumOfSalaries_shouldReturnSumOfSalariesOfAllEmployeesOfDepartment() {
        when(cache.get(DepartmentRepository.class.getSimpleName())).thenReturn(DEPARTMENTS);
        var actual = sut.getSumOfSalaries(5);
        verify(cache, only()).get(any());
        assertEquals(EMPLOYEE55.getSalary() + EMPLOYEE5.getSalary(), actual);
    }

    @Test
    void getSumOfSalaries_shouldThrowNoSuchElementExceptionIfNonexistentDepartmentIdProvided() {
        when(cache.get(DepartmentRepository.class.getSimpleName())).thenReturn(DEPARTMENTS);
        assertThrows(NoSuchElementException.class, () -> sut.getSumOfSalaries(6));
        verify(cache, only()).get(any());
    }

    @Test
    void getEmployeesFromDepartment_shouldReturnSetOfEmployeesOfDepartment() {
        when(cache.get(DepartmentRepository.class.getSimpleName())).thenReturn(DEPARTMENTS);
        var actual = sut.getEmployeesFromDepartment(4);
        verify(cache, only()).get(any());
        assertEquals(new HashSet<>(Arrays.asList(EMPLOYEE4, EMPLOYEE44)), actual);
    }

    @Test
    void getEmployeesFromDepartment_shouldThrowNoSuchElementExceptionIfNonexistentDepartmentIdProvided() {
        when(cache.get(DepartmentRepository.class.getSimpleName())).thenReturn(DEPARTMENTS);
        assertThrows(NoSuchElementException.class, () -> sut.getEmployeesFromDepartment(6));
        verify(cache, only()).get(any());
    }


    static class DepartmentServiceTestData {
        static Department DEP1 = new Department(1, "Management");
        static Department DEP2 = new Department(2, "IT");
        static Department DEP3 = new Department(3, "Logistics");
        static Department DEP4 = new Department(4, "Supply");
        static Department DEP5 = new Department(5, "Accounting");
        public static List<Department> DEPARTMENTS = new ArrayList<>(Arrays.asList(DEP1, DEP2, DEP3, DEP4, DEP5));

        static Employee EMPLOYEE1 = new Employee(1, "Kuznetsova", "Maria", "Ivanovna", 10_000f, DEP1);
        static Employee EMPLOYEE11 = new Employee(2, "Kuznetsovaaa", "Maria", "Ivanovna", 20_000f, DEP1);
        static Employee EMPLOYEE2 = new Employee(3, "Pushkin", "Vasiliy", "Petrovich", 20_000f, DEP2);
        static Employee EMPLOYEE22 = new Employee(4, "Pushkinnn", "Vasiliy", "Petrovich", 30_000f, DEP2);
        static Employee EMPLOYEE3 = new Employee(5, "Rukin", "Petr", "Vasilyevich", 30_000f, DEP3);
        static Employee EMPLOYEE33 = new Employee(6, "Rukinnn", "Petr", "Vasilyevich", 40_000f, DEP3);
        static Employee EMPLOYEE4 = new Employee(7, "Letov", "Egor", "Fedorovich", 40_000f, DEP4);
        static Employee EMPLOYEE44 = new Employee(8, "Letovvv", "Egor", "Fedorovich", 50_000f, DEP4);
        static Employee EMPLOYEE5 = new Employee(9, "Esenin", "Andrei", "Olegovich", 50_000f, DEP5);
        static Employee EMPLOYEE55 = new Employee(10, "Eseninnn", "Andrei", "Olegovich", 60_000f, DEP5);

        public static void attachEmployees() {
            DEP1.setEmployees(new HashSet<>(Arrays.asList(EMPLOYEE1, EMPLOYEE11)));
            DEP2.setEmployees(new HashSet<>(Arrays.asList(EMPLOYEE2, EMPLOYEE22)));
            DEP3.setEmployees(new HashSet<>(Arrays.asList(EMPLOYEE3, EMPLOYEE33)));
            DEP4.setEmployees(new HashSet<>(Arrays.asList(EMPLOYEE4, EMPLOYEE44)));
            DEP5.setEmployees(new HashSet<>(Arrays.asList(EMPLOYEE5, EMPLOYEE55)));
        }
    }
}