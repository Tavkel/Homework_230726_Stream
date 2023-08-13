package com.example.homework_230726_stream.controllers;

import com.example.homework_230726_stream.exceptions.EmployeeAlreadyExistsException;
import com.example.homework_230726_stream.exceptions.InvalidEmployeeDataException;
import com.example.homework_230726_stream.exceptions.MaxEmployeeCountReachedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.NoSuchElementException;

@ControllerAdvice
public class GlobalControllerExceptionHandler {
    private final Logger logger = LoggerFactory.getLogger(GlobalControllerExceptionHandler.class);

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<String> handleMissingParametersException(WebRequest request, MissingServletRequestParameterException e) {
        logger.error("Missing parameters in request " + request.getDescription(true));
        return ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT).body("Error. Parameters expected: id<br>Value type - Integer<br> "
                + e);

    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<String> handleNoSuchElementException(WebRequest request, NoSuchElementException e) {
        logger.error("No element found " + request.getDescription(true));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error. No element found<br> "
                + e);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<String> handleMethodArgumentTypeMismatchException(WebRequest request, MethodArgumentTypeMismatchException e) {
        logger.error("Wrong argument type " + request.getDescription(true));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Wrong argument type!<br> "
                + e);
    }

    @ExceptionHandler(MaxEmployeeCountReachedException.class)
    public ResponseEntity<String> handleMaxEmployeeCountReachedException(WebRequest request, MaxEmployeeCountReachedException e) {
        logger.error("Max employee count reached " + request.getDescription(true));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Sorry, no more employees allowed<br> "
                + e);
    }

    @ExceptionHandler(EmployeeAlreadyExistsException.class)
    public ResponseEntity<String> handleEmployeeAlreadyExistsException(WebRequest request, EmployeeAlreadyExistsException e) {
        logger.error("Employee already exists " + request.getDescription(true));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Employee already exists!<br> "
                + e);
    }

    @ExceptionHandler(InvalidEmployeeDataException.class)
    public ResponseEntity<String> handleInvalidEmployeeDataException(WebRequest request, InvalidEmployeeDataException e) {
        logger.error("Invalid employee data! " + request.getDescription(true));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid employee data!<br> "
                + e);
    }
}
