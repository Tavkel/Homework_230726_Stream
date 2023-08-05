package com.example.homework_230726_stream;

import com.example.homework_230726_stream.helpers.Credentials;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Scanner;

@SpringBootApplication
public class Homework230726StreamApplication {

    public static void main(String[] args) {

        //TODO:
        // - implement validation (check if given credentials grant access to db)
        // - implement encryption
        // - write credentials to a file and read from there if it's present?
        // - ?? ask for url and db driver ??
        boolean valid = true;
        Scanner input = new Scanner(System.in);
        do {
            System.out.println("Enter login...");
            Credentials.setUser(input.nextLine());
            System.out.println("Enter password...");
            Credentials.setPassword(input.nextLine());
        } while (!valid);

        SpringApplication.run(Homework230726StreamApplication.class, args);
    }

}
