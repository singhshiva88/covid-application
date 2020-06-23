package com.wander.covid.service.impl;

import com.wander.covid.model.*;
import com.wander.covid.repository.CovidDataRepository;
import com.wander.covid.service.CovidDataService;
import com.wander.covid.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;

@Service
public class CovidDataServiceImpl implements CovidDataService
{
  @Autowired
  CovidDataRepository covidDataRepository;

  @Override
  @Transactional
  public void truncateDistrictData()
  {
    covidDataRepository.truncateDistrictData();
  }

  @Override
  @Transactional
  public void truncateStateData()
  {
    covidDataRepository.truncateStateData();
  }

  @Override
  @Transactional
  public void truncateCountryData()
  {
    covidDataRepository.truncateCountryData();
  }

  @Override
  @Transactional
  public void truncateAggregatedData()
  {
    covidDataRepository.truncateAggregatedData();
  }

  @Override
  public void saveAllData(Map<String, List<CovidData>> covidDataMap)
  {
    List<CovidData> districtDataDateWise = covidDataMap.get(Constants.DISTRICT_LEVEL_KEY);
    covidDataRepository.saveAll(districtDataDateWise);
    List<CovidData> stateDataDateWise = covidDataMap.get(Constants.STATE_LEVEL_KEY);
    covidDataRepository.saveAll(stateDataDateWise);
    List<CovidData> nationalDataDateWise = covidDataMap.get(Constants.NATIONAL_LEVEL_KEY);
    covidDataRepository.saveAll(nationalDataDateWise);
    List<CovidData> aggregatedCovidData = covidDataMap.get(Constants.AGGREGATED_LEVEL_KEY);
    covidDataRepository.saveAll(aggregatedCovidData);
  }

  @Override
  public List<CovidAggregatedData> getAggregatedRegionRecords(String name)
  {
    return covidDataRepository.findByRegionName(name);
  }

  @Override
  public List<CovidAggregatedData> getAggregatedRegionTypeRecords(String type)
  {
    RegionType regionType = null;
    if (type.equals("country"))
    {
      regionType = RegionType.COUNTRY;
    }
    else if (type.equals("state"))
    {
      regionType = RegionType.STATE;
    }
    else if (type.equals("district"))
    {
      regionType = RegionType.DISTRICT;
    }
    return covidDataRepository.findByRegionType(regionType);
  }

  @Override
  public List<NationalLevelCovidData> getAllRecordsCountry()
  {
    return covidDataRepository.findByCountryName("India");
  }

  @Override
  public List<StateLevelCovidData> getAllRecordsForState(String name)
  {
    return covidDataRepository.findByStateName(name);
  }

  @Override
  public List<DistrictLevelCovidData> getAllRecordsForDistrict(String name)
  {
    return covidDataRepository.findByCityName(name);
  }

  @Override
  public List<CovidAggregatedData> getAllCitiesByStateAggregated(String state)
  {
    return covidDataRepository.getAggregatedCityByState(state);
  }
}
