package com.wander.covid.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CovidHomeController
{
  @RequestMapping(value = "/home")
  public ResponseEntity<String> home()
  {
    return new ResponseEntity<>("Welcome to home page with Covid data", HttpStatus.OK);
  }
}
