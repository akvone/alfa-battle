package com.akvone.core;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class BranchWithPredictDTO {
  private Integer id;
  private String title;
  private Double lon;
  private Double lat;
  private String address;
  private Integer dayOfWeek;
  private Integer hourOfDay;
  private Integer predicting;
}
