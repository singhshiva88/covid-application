package com.wander.covid.service;

import com.wander.covid.model.*;

import java.util.List;
import java.util.Map;

public interface CovidDataService
{
  void truncateDistrictData();

  void truncateStateData();

  void truncateCountryData();

  void truncateAggregatedData();

  void saveAllData(Map<String, List<CovidData>> covidDataMap);

  List<CovidAggregatedData> getAggregatedRegionRecords(String name);

  List<CovidAggregatedData> getAggregatedRegionTypeRecords(String type);

  List<NationalLevelCovidData> getAllRecordsCountry();

  List<StateLevelCovidData> getAllRecordsForState(String name);

  List<DistrictLevelCovidData> getAllRecordsForDistrict(String name);

  List<CovidAggregatedData> getAllCitiesByStateAggregated(String state);
}
