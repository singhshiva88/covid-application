package com.wander.covid.service.impl;

import com.wander.covid.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CovidDataCleanServiceImpl implements CovidDataCleanService
{
  @Autowired
  CovidDataService covidDataService;

  @Override
  public void cleanDistrictLevelData()
  {
    covidDataService.truncateDistrictData();
  }

  @Override
  public void cleanStateLevelData()
  {
    covidDataService.truncateStateData();
  }

  @Override
  public void cleanNationalLevelData()
  {
    covidDataService.truncateCountryData();
  }

  @Override
  public void cleanAggregatedData() {
    covidDataService.truncateAggregatedData();
  }

  @Override
  public void cleanAllData()
  {
    cleanDistrictLevelData();
    cleanStateLevelData();
    cleanNationalLevelData();
    cleanAggregatedData();
  }
}
