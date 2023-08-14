package com.example.homework_230726_stream.helpers;

import com.example.homework_230726_stream.repositories.DepartmentRepository;
import com.example.homework_230726_stream.repositories.EmployeeRepository;
import com.example.homework_230726_stream.services.interfaces.StupidCache;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Component
public class StupidCacheImpl<T> implements StupidCache {
    private Set<String> keys = new HashSet<>();
    private Map<String, Object> values = new HashMap<>();
    //TODO: put repos into collection may be?
    // - How to chance constructor to accept unknown amount of different repos?
    private EmployeeRepository employeeRepository;
    private DepartmentRepository departmentRepository;

    public StupidCacheImpl(EmployeeRepository employeeRepository, DepartmentRepository departmentRepository) {
        this.employeeRepository = employeeRepository;
        this.departmentRepository = departmentRepository;
    }

    public void dropCache() {
        keys.clear();
        values.clear();
    }

    public boolean hasKey(String key) {
        return keys.contains(key);
    }

    public T get(String key) {
        if(!this.checkCache(key)){
            try {
                var repository = this.getClass().getDeclaredField(StringUtils.uncapitalize(key)).get(this);
                var result = repository.getClass().getDeclaredMethod("findAll").invoke(repository);
                this.set(key, result);
            } catch (Exception e) {
                System.out.println("something went wrong while calling repository");
            }
        }
        return (T) values.get(key);
    }

    private void set(String key, Object value) {
        values.put(key, value);
        keys.add(key);
    }

    private boolean checkCache(String key) {
        return this.hasKey(key);
    }
}
