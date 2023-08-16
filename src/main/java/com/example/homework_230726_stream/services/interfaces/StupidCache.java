package com.example.homework_230726_stream.services.interfaces;

public interface StupidCache<T> {
    T get(String key);
    void dropCache();
}
