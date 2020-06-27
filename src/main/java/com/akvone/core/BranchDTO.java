package com.akvone.core;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BranchDTO {
  private Integer id;
  private String title;
  private Double lon;
  private Double lat;
  private String address;
}
