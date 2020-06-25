package com.wander.covid.controller;

import com.wander.covid.model.AuthenticationRequest;
import com.wander.covid.model.AuthenticationResponse;
import com.wander.covid.service.impl.UserServiceImpl;
import com.wander.covid.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "${host.name}")
public class AuthenticationController
{
  @Autowired
  AuthenticationManager authenticationManager;

  @Autowired
  private UserServiceImpl userService;

  @Autowired
  private JwtUtil jwtTokenUtil;

  @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
  public ResponseEntity<String> authenticate(@RequestBody AuthenticationRequest authenticationRequest)
  {
    try
    {
      authenticationManager
              .authenticate(new UsernamePasswordAuthenticationToken(
                      authenticationRequest.getUsername(), authenticationRequest.getPassword()));
    }
    catch (BadCredentialsException e)
    {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
    final UserDetails userDetails = userService.loadUserByUsername(authenticationRequest.getUsername());
    final String jwtToken = jwtTokenUtil.generateToken(userDetails);
    return ResponseEntity.ok(jwtToken);
  }
}
