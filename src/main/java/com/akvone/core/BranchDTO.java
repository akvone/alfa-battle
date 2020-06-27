package com.akvone.core;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BranchDTO {
  private Integer id;
  private String title;
  private String lon;
  private String lat;
  private String address;
}
