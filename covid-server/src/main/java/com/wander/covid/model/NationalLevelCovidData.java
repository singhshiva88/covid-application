package com.wander.covid.model;

import javax.persistence.Entity;

@Entity
public class NationalLevelCovidData extends CovidData
{
  private String countryName;

  public String getCountryName()
  {
    return countryName;
  }

  public void setCountryName(String countryName)
  {
    this.countryName = countryName;
  }
}
