package com.example.homework_230726_stream.helpers;

import com.example.homework_230726_stream.models.Department;
import com.example.homework_230726_stream.models.Employee;
import com.example.homework_230726_stream.repositories.DepartmentRepository;
import com.example.homework_230726_stream.repositories.EmployeeRepository;
import com.example.homework_230726_stream.services.interfaces.StupidCache;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class StupidCacheImplTest {
    @Mock
    private EmployeeRepository employeeRepository;
    @Mock
    private DepartmentRepository departmentRepository;
    private StupidCache sut = new StupidCacheImpl(employeeRepository, departmentRepository);
    private CacheTestData testData;

    //Я забыл почему это не аннотировано BeforeEach
    //У меня течет мозг от этого класса. Надо передохнуть, может завтра после разбора что-то более дельное придет в голову.
    void fillCache() {
        sut = new StupidCacheImpl(employeeRepository, departmentRepository);
        testData = new CacheTestData();
        try {
            var valuesField = sut.getClass().getDeclaredField("values");
            var keysField = sut.getClass().getDeclaredField("keys");
            valuesField.setAccessible(true);
            keysField.setAccessible(true);
            valuesField.set(sut, testData.values);
            keysField.set(sut, testData.keys);
        } catch (Exception ignored) {
        }
    }

    @Test
    void dropCache_shouldClearKeysAndValuesCollections() {
        fillCache();
        sut.dropCache();
        //Не могу протестить опустошение поля values, как быть? Танцевать с рефлексией?
        //Да и тест через hasKey тестит не совсем то что надо =\
        assertFalse(sut.hasKey("EmployeeRepository") && sut.hasKey("DepartmentRepository"));
    }

    //hasKey должен быть приватным - нет смысла его оставлять публичным, однако как тогда тестить drop становится совсем
    //непонятно, особенно учитывая тот ад что творится в get()
    @Test
    void hasKey_shouldReturnTrueIfKeyIsPresent() {
        fillCache();
        assertTrue(sut.hasKey("EmployeeRepository") && sut.hasKey("DepartmentRepository"));
    }

    @Test
    void hasKey_shouldReturnFalseIfKeyIsNotPresentOrNull() {
        assertFalse(sut.hasKey(null) || sut.hasKey("unknownRepository"));
    }

    //Тут еще один вопрос: ключи генерятся от имени класса репозитроия,
    //насколько корректно в тесте этого не учитывать и передавать их строкой?
    //насколько корректно ставить в expected данные из testData, которыми и наполняется кэш?
    @Test
    void get_shouldReturnEmployeeListIfEmployeeKeyProvided() {
        fillCache();
        var actual = sut.get("EmployeeRepository");
        assertEquals(testData.values.get("EmployeeRepository"), actual);
    }
}

class CacheTestData {
    public CacheTestData() {
        keys = new HashSet<>();
        keys.add("EmployeeRepository");
        keys.add("DepartmentRepository");
        values = new HashMap<>();
        var employee = new Employee(1, "Kuznetsova", "Maria", "Ivanovna", 10_000f, 1);
        var department = new Department("QA");
        department.setId(1);
        values.put("EmployeeRepository", employee);
        values.put("DepartmentRepository", department);
    }

    public HashSet<String> keys;

    public HashMap<String, Object> values;
}