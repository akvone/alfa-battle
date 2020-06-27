package com.akvone.core;

import javax.persistence.EntityNotFoundException;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class MainAdvice {

  @ExceptionHandler(EntityNotFoundException.class)
  public ResponseEntity<ErrorDTO> handle(EntityNotFoundException e){
    var error = new ErrorDTO("branch not found");

    return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
  }

  @Data
  public static class ErrorDTO{
    private final String status;
  }
}
