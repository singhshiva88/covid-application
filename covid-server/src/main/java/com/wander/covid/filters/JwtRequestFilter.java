package com.wander.covid.filters;

import com.wander.covid.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter
{
  @Resource(name = "userService")
  private UserDetailsService userDetailsService;

  @Autowired
  private JwtUtil jwtUtil;

  @Override
  protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain)
          throws ServletException, IOException
  {
    final String authorizationHeader = httpServletRequest.getHeader("Authorization");
    String accessToken = null;
    String username = null;
    if (authorizationHeader != null && authorizationHeader.startsWith("Bearer "))
    {
      accessToken = authorizationHeader.substring(7);
      username = jwtUtil.extractUsername(accessToken);
    }
    if (username != null && SecurityContextHolder.getContext().getAuthentication() == null)
    {
      UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
      if (jwtUtil.validateToken(accessToken, userDetails))
      {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null,
                userDetails.getAuthorities());
        usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
      }
    }
    filterChain.doFilter(httpServletRequest, httpServletResponse);
  }
}
