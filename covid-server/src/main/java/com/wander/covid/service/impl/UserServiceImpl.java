package com.wander.covid.service.impl;

import com.wander.covid.downloader.impl.Covid19indiaDownloader;
import com.wander.covid.model.User;
import com.wander.covid.repository.RoleRepository;
import com.wander.covid.repository.UserRepository;
import com.wander.covid.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service("userService")
public class UserServiceImpl implements UserService, UserDetailsService
{

  Logger LOG = LoggerFactory.getLogger(Covid19indiaDownloader.class);

  @Autowired
  private UserRepository userRepository;

  @Override
  public User findByUsername(String username)
  {
    return userRepository.findByUsername(username);
  }

  @Override
  public void saveUser(User user)
  {
    if (findByUsername(user.getUsername()) == null)
    {
      userRepository.save(user);
    }
    else
    {
      LOG.error("User Already extsts................................");
    }
  }

  @Override
  public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException
  {
    User user = userRepository.findByUsername(userName);
    return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), new ArrayList<>());
  }
}
