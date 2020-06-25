package com.wander.covid.controller;

import com.wander.covid.model.User;
import com.wander.covid.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "${host.name}")
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

  @GetMapping(value = "/get/name")
  public ResponseEntity<String> getUserName()
  {
    String username;
    Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    if (principal instanceof UserDetails) {
      username = ((UserDetails)principal).getUsername();
    } else {
      username = principal.toString();
    }
    return ResponseEntity.ok(username);
  }
}
