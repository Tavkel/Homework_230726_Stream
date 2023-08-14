package com.example.homework_230726_stream.services.implementations;

import com.example.homework_230726_stream.exceptions.EmployeeAlreadyExistsException;
import com.example.homework_230726_stream.exceptions.InvalidEmployeeDataException;
import com.example.homework_230726_stream.exceptions.MaxEmployeeCountReachedException;
import com.example.homework_230726_stream.helpers.EmployeeValidator;
import com.example.homework_230726_stream.models.Department;
import com.example.homework_230726_stream.models.Employee;
import com.example.homework_230726_stream.repositories.EmployeeRepository;
import com.example.homework_230726_stream.services.interfaces.EmployeeService;
import com.example.homework_230726_stream.services.interfaces.StupidCache;
import org.hibernate.mapping.Any;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;
import java.util.stream.Collectors;

import static com.example.homework_230726_stream.services.implementations.EmployeeServiceTestData.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceImplTest {
    @Mock
    private EmployeeRepository employeeRepository;
    @Mock
    private StupidCache<List<Employee>> cache;
    private EmployeeService sut;

    @BeforeEach
    private void initializeSut() {
        sut = new EmployeeServiceImpl(employeeRepository, cache);
    }

    @Test
    void getAllEmployees_shouldReturnListOfAllEmployees() {
        when(cache.get(EmployeeRepository.class.getSimpleName())).thenReturn(EMPLOYEES);
        var actual = sut.getAllEmployees();
        verify(cache, only()).get(any());
        assertEquals(EMPLOYEES, actual);
    }

    @Test
    void getAllEmployeesGroupedByDepartment_shouldReturnMapOfAllEmployeesGroupedByDepartmentName() {
        when(cache.get(EmployeeRepository.class.getSimpleName())).thenReturn(EMPLOYEES);

        var actual = sut.getAllEmployeesGroupedByDepartment();

        verify(cache, only()).get(any());

        var expected = new HashMap<String, List<Employee>>();
        expected.put(DEP1.getDepartmentName(), EMPLOYEES.stream().filter(e -> e.getDepartmentId() == 1).collect(Collectors.toList()));
        expected.put(DEP2.getDepartmentName(), EMPLOYEES.stream().filter(e -> e.getDepartmentId() == 2).collect(Collectors.toList()));
        expected.put(DEP3.getDepartmentName(), EMPLOYEES.stream().filter(e -> e.getDepartmentId() == 3).collect(Collectors.toList()));
        expected.put(DEP4.getDepartmentName(), EMPLOYEES.stream().filter(e -> e.getDepartmentId() == 4).collect(Collectors.toList()));
        expected.put(DEP5.getDepartmentName(), EMPLOYEES.stream().filter(e -> e.getDepartmentId() == 5).collect(Collectors.toList()));

        assertEquals(expected, actual);
    }

    @Test
    void getEmployeeById_shouldReturnEmployeeWithGivenId() {
        when(cache.get(EmployeeRepository.class.getSimpleName())).thenReturn(EMPLOYEES);
        var actual = sut.getEmployeeById(1);
        verify(cache, only()).get(any());
        var expected = EMPLOYEE1;
        assertEquals(expected, actual);
    }

    @Test
    void getEmployeeById_shouldThrowNoSuchElementExceptionIfGivenIdIsNotFound() {
        when(cache.get(EmployeeRepository.class.getSimpleName())).thenReturn(EMPLOYEES);
        assertThrows(NoSuchElementException.class, () -> sut.getEmployeeById(6));
        verify(cache, only()).get(any());
    }

    @Test
    void updateEmployee_shouldDropCache_thenPassEmployeeToRepositoryIfValid_thenReturnEmployeeBack() {
        when(cache.get(EmployeeRepository.class.getSimpleName())).thenReturn(EMPLOYEES);
        var actual = sut.updateEmployee(VALID_EMPLOYEE);

        InOrder inOrder = inOrder(cache, employeeRepository);
        inOrder.verify(cache, times(1)).get(any());
        inOrder.verify(cache, times(1)).dropCache();

        // нужно ли мокать утилитарный класс EmployeeValidator? Или стоит прислушаться к этой статье:
        // https://www.baeldung.com/mockito-mock-static-methods и переделать checkEmployee в нестатический,
        // сделать валидатор компонентом и инжектнуть его в сервис?

        inOrder.verify(employeeRepository, times(1)).saveAndFlush(VALID_EMPLOYEE_CAPITALIZED);
        assertEquals(VALID_EMPLOYEE_CAPITALIZED, actual);
    }

    @Test
    void updateEmployee_shouldThrowNoSuchElementExceptionIfProvidedEmployeeIdIsMissingInDb() {
        when(cache.get(EmployeeRepository.class.getSimpleName())).thenReturn(EMPLOYEES);
        assertThrows(NoSuchElementException.class, () -> sut.updateEmployee(INVALID_EMPLOYEE_FOR_UPDATE));
        verify(cache, never()).dropCache();
        verify(employeeRepository, never()).saveAndFlush(any());
    }

    @Test
    void updateEmployee_shouldThrowInvalidEmployeeDataExceptionIfInvalidDataProvided() {
        assertThrows(InvalidEmployeeDataException.class, () -> sut.updateEmployee(INVALID_EMPLOYEE));
        verify(cache, never()).dropCache();
        verify(employeeRepository, never()).saveAndFlush(any());
    }

    @Test
    void createEmployee_shouldCreateNewEmployeeIfMaxCountIsNotReachedEmployeeDataIsValidAndSameEmployeeDoesNotExist() {
        when(cache.get(EmployeeRepository.class.getSimpleName())).thenReturn(EMPLOYEES);
        var actual = sut.createEmployee(VALID_EMPLOYEE_FOR_CREATION);

        InOrder inOrder = inOrder(cache, employeeRepository);
        inOrder.verify(cache, times(1)).get(any());
        inOrder.verify(cache, times(1)).dropCache();
        inOrder.verify(employeeRepository, times(1)).saveAndFlush(VALID_EMPLOYEE_FOR_CREATION);

        assertEquals(VALID_EMPLOYEE_FOR_CREATION, actual);
    }

    @Test
    void createEmployee_shouldThrowMaxEmployeeCountReachedExceptionIfMaxAmountReached() {
        when(cache.get(EmployeeRepository.class.getSimpleName())).thenReturn(EMPLOYEES);

        // Почему это не сработало? Дебаггер видит что в поле 3, но сравнение employees.size() (там 5) со значением поля
        // (3) выдает false, 3 >= 5 == false. Моя не понимать.
        // смена значения поля в самом классе дает ожидаемый результат и тест проходит успешно.
        //TODO:
        // - move max_employee_count property to configuration class. sometime. may be. hopefully.
        try{
            var field = sut.getClass().getDeclaredField("MAX_EMPLOYEE_COUNT");
            field.setAccessible(true);
            field.set(sut, 3);
        } catch (Exception ignore){}
        assertThrows(MaxEmployeeCountReachedException.class, () -> sut.createEmployee(VALID_EMPLOYEE_FOR_CREATION));

        verify(cache, only()).get(any());
        verify(cache, never()).dropCache();
        verify(employeeRepository, never()).saveAndFlush(any());
    }

    @Test
    void createEmployee_shouldThrowEmployeeAlreadyExistsExceptionIfEmployeeWithSameDataIsFound() {
        when(cache.get(EmployeeRepository.class.getSimpleName())).thenReturn(EMPLOYEES);


        assertThrows(EmployeeAlreadyExistsException.class, () -> sut.createEmployee(EMPLOYEE1));

        verify(cache, only()).get(any());
        verify(cache, never()).dropCache();
        verify(employeeRepository, never()).saveAndFlush(any());
    }
}

class EmployeeServiceTestData {
    static Department DEP1 = new Department(1, "Management");
    static Department DEP2 = new Department(2, "IT");
    static Department DEP3 = new Department(3, "Logistics");
    static Department DEP4 = new Department(4, "Supply");
    static Department DEP5 = new Department(5, "Accounting");
    public static List<Department> DEPARTMENTS = new ArrayList<>(Arrays.asList(DEP1, DEP2, DEP3, DEP4, DEP5));

    static Employee EMPLOYEE1 = new Employee(1,"Kuznetsova","Maria","Ivanovna",10_000f,DEP1);
    static Employee EMPLOYEE2 = new Employee(2,"Pushkin","Vasiliy","Petrovich",20_000f,DEP2);
    static Employee EMPLOYEE3 = new Employee(3,"Rukin","Petr","Vasilyevich",30_000f,DEP3);
    static Employee EMPLOYEE4 = new Employee(4,"Letov","Egor","Fedorovich",40_000f,DEP4);
    static Employee EMPLOYEE5 = new Employee(5,"Esenin","Andrei","Olegovich",50_000f,DEP5);
    static Employee INVALID_EMPLOYEE = new Employee(6,"122409", "(*&", "sdfsdf", 4f, DEP1);
    static Employee VALID_EMPLOYEE = new Employee(5,"esenin","andrei","olegovich",50_000f,DEP5);
    static Employee INVALID_EMPLOYEE_FOR_UPDATE = new Employee(6,"esenin","andrei","olegovich",50_000f,DEP5);
    static Employee VALID_EMPLOYEE_CAPITALIZED = new Employee(5,"Esenin","Andrei","Olegovich",50_000f,DEP5);
    static Employee VALID_EMPLOYEE_FOR_CREATION = new Employee(0,"Esenin","Andrei","Andreevich",50_000f,DEP5);
    public static List<Employee> EMPLOYEES = new ArrayList<>(Arrays.asList(
            EMPLOYEE1,
            EMPLOYEE2,
            EMPLOYEE3,
            EMPLOYEE4,
            EMPLOYEE5
    ));
}