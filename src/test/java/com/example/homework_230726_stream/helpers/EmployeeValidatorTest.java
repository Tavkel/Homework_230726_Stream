package com.example.homework_230726_stream.helpers;

import com.example.homework_230726_stream.models.Employee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.stream.Stream;

import static com.example.homework_230726_stream.helpers.EmployeeValidatorTest.ValidatorTestData.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmployeeValidatorTest {
    @Mock
    private AppVariables appVariables;
    private EmployeeValidator sut;

    @BeforeEach
    private void InitializeSut() {
        when(appVariables.getDepartmentAmount()).thenReturn(5);
        when(appVariables.getMinSalary()).thenReturn(10_000f);
        sut = new EmployeeValidator(appVariables);
    }

    private static Stream<Arguments> provideParametersForCheckEmployeeTest() {
        return Stream.of(
                Arguments.of(new Employee(
                        0,
                        VALID_NAME,
                        VALID_NAME,
                        VALID_NAME,
                        VALID_SALARY,
                        VALID_DEPARTMENT_ID
                ), true),
                Arguments.of(new Employee(
                        0,
                        INVALID_NAME1,
                        VALID_NAME,
                        VALID_NAME,
                        VALID_SALARY,
                        VALID_DEPARTMENT_ID
                ), false),
                Arguments.of(new Employee(
                        0,
                        VALID_NAME,
                        INVALID_NAME2,
                        VALID_NAME,
                        VALID_SALARY,
                        VALID_DEPARTMENT_ID
                ), false),
                Arguments.of(new Employee(
                        0,
                        VALID_NAME,
                        VALID_NAME,
                        INVALID_NAME3,
                        VALID_SALARY,
                        VALID_DEPARTMENT_ID
                ), false),
                Arguments.of(new Employee(
                        0,
                        VALID_NAME,
                        VALID_NAME,
                        VALID_NAME,
                        INVALID_SALARY,
                        VALID_DEPARTMENT_ID
                ), false),
                Arguments.of(new Employee(
                        0,
                        VALID_NAME,
                        VALID_NAME,
                        VALID_NAME,
                        VALID_SALARY,
                        INVALID_DEPARTMENT_ID1
                ), false),
                Arguments.of(new Employee(
                        0,
                        VALID_NAME,
                        VALID_NAME,
                        VALID_NAME,
                        VALID_SALARY,
                        INVALID_DEPARTMENT_ID2
                ), false)
        );
    }

    @ParameterizedTest
    @MethodSource("provideParametersForCheckEmployeeTest")
    void checkEmployee(Employee employee, boolean expected) {
        var actual = sut.checkEmployee(employee);
        assertEquals(expected, actual);
    }

    static class ValidatorTestData {
        public static String VALID_NAME = "maria";
        public static String INVALID_NAME1 = "marina123";
        public static String INVALID_NAME2 = "marina#";
        public static String INVALID_NAME3 = "mar ina";
        public static Float VALID_SALARY = 10_000f;
        public static Float INVALID_SALARY = 9999f;
        public static int VALID_DEPARTMENT_ID = 2;
        public static int INVALID_DEPARTMENT_ID1 = 0;
        public static int INVALID_DEPARTMENT_ID2 = 6;

    }
}