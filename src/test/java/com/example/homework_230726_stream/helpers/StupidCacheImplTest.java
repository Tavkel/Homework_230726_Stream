package com.example.homework_230726_stream.helpers;

import com.example.homework_230726_stream.exceptions.KeyDoesNotExistException;
import com.example.homework_230726_stream.models.Department;
import com.example.homework_230726_stream.models.Employee;
import com.example.homework_230726_stream.repositories.DepartmentRepository;
import com.example.homework_230726_stream.repositories.EmployeeRepository;
import com.example.homework_230726_stream.services.interfaces.StupidCache;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static com.example.homework_230726_stream.helpers.StupidCacheImplTest.CacheTestData.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StupidCacheImplTest {
    @Mock
    private EmployeeRepository employeeRepository;
    @Mock
    private DepartmentRepository departmentRepository;
    private StupidCache sut;
    private CacheTestData testData;

    @BeforeEach
    private void InitializeSut(){
        attachEmployees();
        sut = new StupidCacheImpl(employeeRepository, departmentRepository);
    }

    @Test
    void get_shouldReturnEmployeeListIfEmployeeKeyProvided() {
        when(employeeRepository.findAll()).thenReturn(EMPLOYEES);
        var actual = sut.get("EmployeeRepository");
        assertEquals(EMPLOYEES, actual);
    }

    @Test
    void get_shouldReturnDepartmentListIfDepartmentKeyProvided() {
        when(departmentRepository.findAll()).thenReturn(DEPARTMENTS);
        var actual = sut.get("DepartmentRepository");
        assertEquals(DEPARTMENTS, actual);
    }

    @Test
    void get_shouldThrowKeyDoesNotExistExceptionIfWrongKeyProvided() {
        assertThrows(KeyDoesNotExistException.class, () -> sut.get("UnknownKey"));
    }


    static class CacheTestData {
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
        public static List<Employee> EMPLOYEES = Arrays.asList(EMPLOYEE1, EMPLOYEE11,
                EMPLOYEE2, EMPLOYEE22, EMPLOYEE3, EMPLOYEE33, EMPLOYEE4, EMPLOYEE44,
                EMPLOYEE5, EMPLOYEE55);
        public static void attachEmployees() {
            DEP1.setEmployees(new HashSet<>(Arrays.asList(EMPLOYEE1, EMPLOYEE11)));
            DEP2.setEmployees(new HashSet<>(Arrays.asList(EMPLOYEE2, EMPLOYEE22)));
            DEP3.setEmployees(new HashSet<>(Arrays.asList(EMPLOYEE3, EMPLOYEE33)));
            DEP4.setEmployees(new HashSet<>(Arrays.asList(EMPLOYEE4, EMPLOYEE44)));
            DEP5.setEmployees(new HashSet<>(Arrays.asList(EMPLOYEE5, EMPLOYEE55)));
        }
    }
}