package com.wander.covid.controller;

import com.wander.covid.model.User;
import com.wander.covid.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController
{
  @Autowired
  private UserService userService;

  @PostMapping(value = "/signup")
  public ResponseEntity<User> create(@RequestBody User user)
  {
    if (userService.findByUsername(user.getUsername()) != null) {
      return ResponseEntity.status(HttpStatus.CONFLICT).body(user);
    }
    userService.saveUser(user);
    return ResponseEntity.status(HttpStatus.OK).body(user);
  }
}
