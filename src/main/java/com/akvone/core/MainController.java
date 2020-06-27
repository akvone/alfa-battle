package com.akvone.core.controller;

import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class MainController {


  @GetMapping("atms/{id}")
  public ResponseEntity<Map<String, String>> get(@PathVariable int id) {

    return new ResponseEntity<>(Map.of("status", "atm not found"), HttpStatus.NOT_FOUND);
  }


}
