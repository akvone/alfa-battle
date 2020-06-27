package com.akvone.core;

import java.util.Collection;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class MainController {

  private final MainService mainService;

  @GetMapping("analytic")
  public ResponseEntity<Collection<Analytic>> getAnalytic() {
    var analytics = mainService.getFullAnalytic();

    return new ResponseEntity<>(analytics, HttpStatus.OK);
  }

  @GetMapping("analytic/{userId}")
  public ResponseEntity<Analytic> getAnalytic(@PathVariable String userId) {
    var analytic = mainService.getAnalytic(userId);

    return new ResponseEntity<>(analytic, HttpStatus.OK);
  }
}
