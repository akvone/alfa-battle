package com.akvone.core;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class AppListeners {

  @EventListener(ApplicationReadyEvent.class)
  public void handle() {
  }

  private void produceMessage() {
  }

}
