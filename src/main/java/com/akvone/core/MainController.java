package com.akvone.core;

import java.util.Comparator;
import java.util.List;
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
    var entity = branchRepository.getOne(id);
    var branchDTO = new BranchDTO(entity.getId(), entity.getTitle(), entity.getLon(), entity.getLat(),
        entity.getAddress());

    return new ResponseEntity<>(branchDTO, HttpStatus.OK);
  }

  @GetMapping("branches")
  public ResponseEntity<BranchWithDistanceDTO> get(@RequestParam Double lat, @RequestParam Double lon) {

    var list= branchRepository.findAll().stream().map(value -> MathUtils.countDistance(lat, value.getLat(), lon, value.getLon())).collect(
        Collectors.toList());

    var entity = branchRepository.findAll().stream()
        .min(Comparator.comparingDouble(value -> MathUtils.countDistance(lat, value.getLat(), lon, value.getLon())))
        .get();
    Integer minDistance = MathUtils.countDistance(lat, entity.getLat(), lon, entity.getLon()).intValue();

    var branchWithDistanceDTO = new BranchWithDistanceDTO(entity.getId(), entity.getTitle(), entity.getLon(),
        entity.getLat(), entity.getAddress(), minDistance);
    return new ResponseEntity<>(branchWithDistanceDTO,HttpStatus.OK);
  }


}
