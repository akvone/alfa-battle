package com.akvone.configuration;

import java.security.KeyStore;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
@Slf4j
public class RestConfiguration {

  @Bean
  public RestTemplate secureRestTemplate() {
    var restTemplate = new RestTemplate();

    var password = "TODO";
    var filePath = "TODO";

    try {
      var keyStore = KeyStore.getInstance("jks");

      keyStore.load(new ClassPathResource(filePath).getInputStream(), password.toCharArray());

      var sslContext = new SSLContextBuilder()
          .loadTrustMaterial(null, new TrustSelfSignedStrategy())
          .loadKeyMaterial(keyStore, password.toCharArray())
          .build();

      HttpClient httpClient = HttpClients.custom()
          .setSSLContext(sslContext)
          .setMaxConnTotal(20)
          .setMaxConnPerRoute(10)
          .build();
      var requestFactory = new HttpComponentsClientHttpRequestFactory(httpClient);
      restTemplate.setRequestFactory(requestFactory);
    } catch (Exception e) {
      log.error("Can not create RestTemplate", e);

      e.printStackTrace();
    }

    return restTemplate;
  }

}
