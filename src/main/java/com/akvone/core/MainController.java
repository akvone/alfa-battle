package com.akvone.core;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/main")
@RequiredArgsConstructor
@Slf4j
public class MainController {

  @GetMapping
  public MainDTO get() {
    return new MainDTO("/v1/main GET Result");
  }
}
