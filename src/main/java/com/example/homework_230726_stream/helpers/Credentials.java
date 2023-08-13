package com.example.homework_230726_stream.helpers;

public class Credentials {
    private static String user;
    private static String password;

    public static String getUser() {
        return user;
    }

    public static void setUser(String user) {
        Credentials.user = user;
    }

    public static String getPassword() {
        return password;
    }

    public static void setPassword(String password) {
        Credentials.password = password;
    }
}
