package com.akvone.core.controller;

import com.akvone.core.UserNotFoundException;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class MainControllerAdvice {

  @ExceptionHandler(UserNotFoundException.class)
  public ResponseEntity<ErrorDTO> handle(UserNotFoundException e){
    var error = new ErrorDTO("user not found");

    return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
  }

  @Data
  public static class ErrorDTO{
    private final String status;
  }
}
