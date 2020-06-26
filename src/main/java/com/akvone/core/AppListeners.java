package com.akvone.core;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class AppListeners {

  private final KafkaTemplate<String, String> kafkaTemplate;

  @EventListener(ApplicationReadyEvent.class)
  public void handle() {
    produceMessage();
  }

  private void produceMessage() {
    var message = "AAA";
    log.info(String.format("#### -> Producing message -> %s", message));
    this.kafkaTemplate.send("users", message);
  }


}
