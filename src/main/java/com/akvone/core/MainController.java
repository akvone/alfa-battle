package com.akvone.core;

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

  private final MainService mainService;
  private final BranchRepository branchRepository;

  @GetMapping("branches/{id}")
  public ResponseEntity<BranchDTO> get(@PathVariable Integer id) {
    var branch = branchRepository.getOne(id);
    var branchDTO = new BranchDTO(branch.getId(), branch.getTitle(), branch.getLon(), branch.getLat(), branch.getAddress());

    return new ResponseEntity<>(branchDTO, HttpStatus.OK);
  }

}
