package com.example.homework_230726_stream.services.interfaces;

public interface StupidCache<T> {
    void dropCacheKey(String key);
    boolean hasKey(String key);
    T get(String key);
    void set(String key, T value);
    boolean checkCache(String key);
    void loadCache(String key, T value);
}
