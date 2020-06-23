package com.wander.covid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.time.Duration;

@SpringBootApplication
@EnableScheduling
@EnableCircuitBreaker
public class Application
{
  static Logger LOG = LoggerFactory.getLogger(Application.class);


  public static void main(String[] args)
  {
    LOG.info("******************************** COVID19 DASHBOARD APPLICATION STARTED ********************************");
    SpringApplication.run(Application.class, args);
  }

  @Bean
  public WebMvcConfigurer corsConfigurer()
  {
    return new WebMvcConfigurer()
    {
      @Override
      public void addCorsMappings(CorsRegistry registry)
      {
        registry.addMapping("/*")
                .allowedHeaders("*")
                .allowedOrigins("*")
                .allowedMethods("*")
                .allowCredentials(true);
      }
    };
  }

  @Bean
  public RestTemplate restTemplate(RestTemplateBuilder builder,
          @Value("${custom.rest.connection.connect-timeout}") Integer connectionTimeout,
          @Value("${custom.rest.connection.read-timeout}") Integer readTimeout)
  {
    return builder.setConnectTimeout(Duration.ofSeconds(connectionTimeout)).setReadTimeout(Duration.ofSeconds(readTimeout)).build();
  }
}