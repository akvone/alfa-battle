package com.akvone.core;

import java.util.Comparator;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class MainController {

  private final MainService mainService;
  private final BranchRepository branchRepository;

  @GetMapping("branches/{id}")
  public ResponseEntity<BranchDTO> get(@PathVariable Integer id) {
    var branchDTO = mainService.getBranchDTO(id);

    return new ResponseEntity<>(branchDTO, HttpStatus.OK);
  }


  @GetMapping("branches")
  public ResponseEntity<BranchWithDistanceDTO> get(@RequestParam Double lat, @RequestParam Double lon) {
    BranchWithDistanceDTO branchWithDistanceDTO = mainService.getBranchWithDistanceDTO(lat, lon);

    return new ResponseEntity<>(branchWithDistanceDTO, HttpStatus.OK);
  }



  @GetMapping("branches/{id}/predict")
  public ResponseEntity<Double> predict(@PathVariable int id,
      @RequestParam int dayOfWeek, @RequestParam int hourOfDay) {
    double predict = mainService.predict(id, dayOfWeek, hourOfDay);

    return new ResponseEntity<>(predict, HttpStatus.OK);
  }


}
