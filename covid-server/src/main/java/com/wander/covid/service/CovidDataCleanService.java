package com.wander.covid.service;

public interface CovidDataCleanService
{
  void cleanDistrictLevelData();

  void cleanStateLevelData();

  void cleanNationalLevelData();

  void cleanAggregatedData();

  void cleanAllData();
}
