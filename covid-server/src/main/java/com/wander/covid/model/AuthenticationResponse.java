package com.wander.covid.model;

public class AuthenticationResponse
{
  private final String accessToken;

  public AuthenticationResponse(String access_token)
  {
    this.accessToken = access_token;
  }

  public String getAccessToken()
  {
    return accessToken;
  }
}
