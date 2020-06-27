package com.akvone.core;

import com.akvone.core.Analytic.AnalyticInfo;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MainService {

  private final HashMap<String, Analytic> storage = new HashMap<>();

  public synchronized void handleNewPayment(Payment payment) {
    log.info("Handle new payment {}", payment);

    var categoryId = payment.getCategoryId();

    var analytic = storage.computeIfAbsent(payment.getUserId(), Analytic::new);
    var newTotalSum = roundCorrectly(analytic.getTotalSum() + payment.amount);
    analytic.setTotalSum(newTotalSum);

    analytic.getAnalyticInfo().compute(categoryId, (id, analyticInfo) -> {
      if (analyticInfo == null) {
        return new AnalyticInfo(1, payment.amount, payment.amount, payment.amount);
      } else {
        analyticInfo.counter++;
        analyticInfo.sum = roundCorrectly(analyticInfo.sum + payment.amount);
        analyticInfo.min = Math.min(analyticInfo.min, payment.amount);
        analyticInfo.max = Math.max(analyticInfo.max, payment.amount);

        return analyticInfo;
      }
    });

    log.debug("Done");
  }

  private double roundCorrectly(double a) {
    return Math.round(a * 100) / 100d;
  }

  public Analytic getAnalytic(String userId) {
    var analytic = storage.get(userId);

    if (analytic == null) {
      throw new UserNotFoundException();
    } else {
      return analytic;
    }
  }

  public Collection<Analytic> getFullAnalytic() {
    return storage.values();
  }

  public Map<String, Integer> getStats(String userId) {
    var analytic = getAnalytic(userId);
    var analyticInfo = analytic.getAnalyticInfo();

    var oftenCategoryId = analyticInfo.entrySet().stream()
        .max(Comparator.comparingInt(o -> o.getValue().counter))
        .get().getKey();
    var rareCategoryId = analyticInfo.entrySet().stream()
        .min(Comparator.comparingInt(o -> o.getValue().counter))
        .get().getKey();
    var maxAmountCategoryId = analyticInfo.entrySet().stream()
        .max(Comparator.comparingDouble(o -> o.getValue().sum))
        .get().getKey();
    var minAmountCategoryId = analyticInfo.entrySet().stream()
        .min(Comparator.comparingDouble(o -> o.getValue().sum))
        .get().getKey();

    return Map.of(
        "oftenCategoryId", oftenCategoryId,
        "rareCategoryId", rareCategoryId,
        "maxAmountCategoryId", maxAmountCategoryId,
        "minAmountCategoryId", minAmountCategoryId
    );
  }
}
