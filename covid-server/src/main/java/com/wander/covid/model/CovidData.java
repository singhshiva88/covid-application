package com.wander.covid.model;

import java.util.Date;

public class CovidData
{
  private Date date;
  private String location;
  private String parentLocation;
  private RegionType regionType;
  private int confirmed;
  private int deceased;
  private int recovered;

  public Date getDate()
  {
    return date;
  }

  public void setDate(Date date)
  {
    this.date = date;
  }

  public String getLocation()
  {
    return location;
  }

  public void setLocation(String location)
  {
    this.location = location;
  }

  public String getParentLocation()
  {
    return parentLocation;
  }

  public void setParentLocation(String parentLocation)
  {
    this.parentLocation = parentLocation;
  }

  public int getConfirmed()
  {
    return confirmed;
  }

  public void setConfirmed(int confirmed)
  {
    this.confirmed = confirmed;
  }

  public int getDeceased()
  {
    return deceased;
  }

  public void setDeceased(int deceased)
  {
    this.deceased = deceased;
  }

  public int getRecovered()
  {
    return recovered;
  }

  public void setRecovered(int recovered)
  {
    this.recovered = recovered;
  }
}
