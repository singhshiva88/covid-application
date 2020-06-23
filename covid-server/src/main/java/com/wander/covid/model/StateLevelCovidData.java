package com.wander.covid.model;

import javax.persistence.Entity;

@Entity
public class StateLevelCovidData extends CovidData
{
  private String stateName;
  private String countryName;

  public String getStateName()
  {
    return stateName;
  }

  public void setStateName(String stateName)
  {
    this.stateName = stateName;
  }

  public String getCountryName()
  {
    return countryName;
  }

  public void setCountryName(String countryName)
  {
    this.countryName = countryName;
  }
}
