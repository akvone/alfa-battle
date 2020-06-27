package com.akvone.core;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class KafkaListeners {

  private final MainService mainService;
  private final ObjectMapper objectMapper = new ObjectMapper();

  @KafkaListener(
      topics = "RAW_PAYMENTS",
      groupId = "akvone22",
      properties = "auto.offset.reset=earliest"
  )
  public void handle(ConsumerRecord<String, String> record) {
    var paymentAsJson = record.value();
    try {
      Payment payment = objectMapper.readValue(paymentAsJson, Payment.class);
      mainService.handleNewPayment(payment);
    } catch (JsonProcessingException e) {
      log.error("Can't process payment {}", paymentAsJson);
    }

  }

}
