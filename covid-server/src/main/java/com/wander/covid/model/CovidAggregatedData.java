package com.wander.covid.model;

import javax.persistence.*;

@Entity
public class CovidAggregatedData extends CovidData
{
  private String regionName;
  private String parentRegion;
  @Enumerated(EnumType.STRING)
  private RegionType regionType;
  public RegionType getRegionType()
  {
    return regionType;
  }

  public void setRegionType(RegionType regionType)
  {
    this.regionType = regionType;
  }

  public String getRegionName()
  {
    return regionName;
  }

  public void setRegionName(String regionName)
  {
    this.regionName = regionName;
  }

  public String getParentRegion()
  {
    return parentRegion;
  }

  public void setParentRegion(String parentRegion)
  {
    this.parentRegion = parentRegion;
  }
}