package com.akvone.core;

import com.akvone.core.Analytic.AnalyticInfo;
import java.util.Collection;
import java.util.HashMap;
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
        return new AnalyticInfo(payment.amount, payment.amount, payment.amount);
      } else {
        analyticInfo.sum = roundCorrectly(analyticInfo.sum + payment.amount);
        analyticInfo.min = Math.min(analyticInfo.min, payment.amount);
        analyticInfo.max = Math.max(analyticInfo.max, payment.amount);

        return analyticInfo;
      }
    });

    log.info("Done");
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
}
