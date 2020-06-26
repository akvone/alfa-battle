package com.akvone.core;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class KafkaListeners {

  @KafkaListener(
      topics = "users",
      groupId = "akvone"
//      properties = "auto.offset.reset=earliest"
  )
  public void handle(Object message) {
    log.info("Receive new message {}", message);
  }

}
