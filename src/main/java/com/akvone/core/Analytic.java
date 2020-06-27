package com.akvone.core;

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

    double min;
    double max;
    double sum;
  }
}
