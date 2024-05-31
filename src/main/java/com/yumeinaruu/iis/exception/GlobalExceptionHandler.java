package com.yumeinaruu.iis.exception;

import com.yumeinaruu.iis.exception.custom_exception.CustomValidationException;
import com.yumeinaruu.iis.exception.custom_exception.SameUserInDatabase;
import jakarta.validation.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;

import java.nio.file.FileAlreadyExistsException;
import java.sql.SQLException;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler({CustomValidationException.class, ValidationException.class})
    public ResponseEntity<HttpStatus> handleCustomValidationException(CustomValidationException ex) {
        log.error("Error occurred: " + ex);
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(SameUserInDatabase.class)
    public ResponseEntity<HttpStatus> handleSameUserInDatabaseException(SameUserInDatabase ex) {
        log.error("Error occurred: " + ex);
        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    @ExceptionHandler(SQLException.class)
    public ResponseEntity<HttpStatus> handleSQLException(SQLException ex) {
        log.error("Error occurred: " + ex);
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<HttpStatus> handleIllegalArgumentException(IllegalArgumentException ex) {
        log.error("Error occurred: " + ex);
        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    @ExceptionHandler(FileAlreadyExistsException.class)
    public ResponseEntity<HttpStatus> handleFileAlreadyExistsException(FileAlreadyExistsException ex) {
        log.error("Error occurred: " + ex);
        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    @ExceptionHandler(HttpClientErrorException.Forbidden.class)
    public ResponseEntity<HttpStatus> handleHttpClientErrorException(HttpClientErrorException ex) {
        log.warn("Forbidden: " + ex);
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(value = {UsernameNotFoundException.class})
    public ResponseEntity<HttpStatus> usernameNotFound(Exception exception) {
        log.error(exception + "");
        return new ResponseEntity<>(HttpStatus.valueOf(401));
    }
}
