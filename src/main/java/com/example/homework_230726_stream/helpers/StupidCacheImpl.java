package com.example.homework_230726_stream.helpers;

import com.example.homework_230726_stream.services.interfaces.StupidCache;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Component
public class StupidCacheImpl<T> implements StupidCache {
    private Set<String> keys = new HashSet<>();
    private Map<String, Object> values = new HashMap<>();

    public void dropCacheKey(String key) {
        keys.remove(key);
        values.remove(key);
    }

    public boolean hasKey(String key) {
        return keys.contains(key);
    }

    public T get(String key) {
        return (T) values.get(key);
    }

    public void set(String key, Object value) {
        values.put(key, value);
        keys.add(key);
    }

    @Override
    public boolean checkCache(String key) {
        return this.hasKey(key);
    }

    @Override
    public void loadCache(String key, Object value) {
        this.set(key, (T)value);
    }
}
