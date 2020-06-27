package com.akvone.core;

import static java.lang.Math.*;

public class MathUtils {

  public static final Double R = 6_371_000d;

  public static Double countDistance(Double dlat1, Double dlat2, Double dlon1, Double dlon2) {
    var lat1 = toRad(dlat1);
    var lat2 = toRad(dlat2);
    var lon1 = toRad(dlon1);
    var lon2 = toRad(dlon2);

    var leftSin = sin((lat2 - lat1) / 2);
    var rightSin = sin((lon2 - lon1) / 2);
    double forSqrt = pow(leftSin, 2) + cos(lat1) * cos(lat2) * pow(rightSin, 2);

    return 2 * R * asin(sqrt(forSqrt));
  }

  public static Double toRad(Double degree){
    return degree * PI / 180;
  }
}
