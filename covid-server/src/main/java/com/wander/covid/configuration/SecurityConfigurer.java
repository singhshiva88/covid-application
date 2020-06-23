package com.wander.covid.configuration;

import com.wander.covid.filters.JwtRequestFilter;
import com.wander.covid.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
public class SecurityConfigurer extends WebSecurityConfigurerAdapter
{
  @Autowired
  private UserServiceImpl userDetailsService;

  @Autowired
  private JwtRequestFilter jwtRequestFilter;

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception
  {
    auth.userDetailsService(userDetailsService);
  }

  @Override
  protected void configure(HttpSecurity httpSecurity) throws Exception
  {
    httpSecurity.cors().disable();
    httpSecurity.csrf().disable().authorizeRequests().antMatchers("/authenticate", "/signup", "/load", "/h2-console/**")
            .permitAll().antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
            .anyRequest().authenticated().and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    httpSecurity.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
    httpSecurity.headers().frameOptions().sameOrigin();
  }

  @Bean
  public PasswordEncoder passwordEncoder()
  {
    return NoOpPasswordEncoder.getInstance();
  }

  @Override
  @Bean
  public AuthenticationManager authenticationManagerBean() throws Exception
  {
    return super.authenticationManagerBean();
  }
}