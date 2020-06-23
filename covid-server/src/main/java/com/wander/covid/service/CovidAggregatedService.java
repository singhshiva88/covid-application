package com.wander.covid.service;

import com.wander.covid.model.CovidData;

import java.util.List;

public interface CovidAggregatedService
{
  void truncateAggregatedData();

  void saveAll(List<CovidData> aggregatedCovidData);
}
