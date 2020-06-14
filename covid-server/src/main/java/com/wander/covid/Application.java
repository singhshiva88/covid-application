package com.wander.covid;

import com.wander.covid.model.User;
import com.wander.covid.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class Application
{
  public static void main(String[] args)
  {
    System.out.println("******************************** COVID19 DASHBOARD APPLICATION STARTED ********************************");
    SpringApplication.run(Application.class, args);
  }

  @Bean
  public WebMvcConfigurer corsConfigurer() {
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
}
