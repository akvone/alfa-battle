package com.akvone.core;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.HashMap;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class Analytic {

  private final String userId;
  private double totalSum = 0;
  private Map<Integer, AnalyticInfo> analyticInfo = new HashMap<>();

  @AllArgsConstructor
  @NoArgsConstructor
  @Data
  public static class AnalyticInfo {

    @JsonIgnore
    int counter;
    double min;
    double max;
    double sum;
  }
}
