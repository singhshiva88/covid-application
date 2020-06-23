package com.wander.covid.model;

import javax.persistence.Entity;

@Entity
public class DistrictLevelCovidData extends CovidData
{
  private String cityName;
  private String stateName;

  public String getCityName()
  {
    return cityName;
  }

  public void setCityName(String cityName)
  {
    this.cityName = cityName;
  }

  public String getStateName()
  {
    return stateName;
  }

  public void setStateName(String stateName)
  {
    this.stateName = stateName;
  }
}
