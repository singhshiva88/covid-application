package com.wander.covid.parser.impl;

import com.wander.covid.model.*;
import com.wander.covid.parser.CovidDataParser;
import com.wander.covid.util.Constants;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.*;

@Component
public class Covid19OrgDataParserImpl implements CovidDataParser
{
  private static final String DISTRICT_DAILY_KEY = "districtsDaily";
  private static final String DISTRICT_DAILY_KEY_ACTIVE = "active";
  private static final String DISTRICT_DAILY_KEY_CONFIRMED = "confirmed";
  private static final String DISTRICT_DAILY_KEY_DECEASED = "deceased";
  private static final String DISTRICT_DAILY_KEY_RECOVERED = "recovered";
  private static final String DISTRICT_DAILY_KEY_DATE = "date";

  @Override
  public Map<String, List<CovidData>> parse(Object reponse)
  {
    // Initialize Data
    Map<String, List<CovidData>> covidDataMap = new HashMap<>();
    initializeDataMaps(covidDataMap);
    parseData((Map<String, Map>)reponse, covidDataMap);
    return covidDataMap;
  }

  private void parseNationalLevelData(Map<String, List<CovidData>> covidDataMap,
          List<CovidData> covidAggregatedDataList)
  {
    List<CovidData> stateLevelDataList = covidDataMap.get(Constants.STATE_LEVEL_KEY);
    Map<String, List<CovidData>> nationalLevelMap = new HashMap<>();
    for (CovidData stateData : stateLevelDataList)
    {
      StateLevelCovidData stateLevelCovidData = (StateLevelCovidData)stateData;
      if (nationalLevelMap.get(stateLevelCovidData.getCountryName()) == null)
      {
        List<CovidData> covidData = new ArrayList<>();
        covidData.add(stateData);
        nationalLevelMap.put(stateLevelCovidData.getCountryName(), covidData);
      }
      else
      {
        nationalLevelMap.get(stateLevelCovidData.getCountryName()).add(stateData);
      }
    }

    for (String countryName : nationalLevelMap.keySet())
    {
      Map<Date, List<CovidData>> dateWiseCountryMap = new HashMap<>();
      List<CovidData> dateWiseList = nationalLevelMap.get(countryName);
      for (CovidData covidData : dateWiseList)
      {
        if (dateWiseCountryMap.get(covidData.getDate()) == null)
        {
          List<CovidData> covidList = new ArrayList<>();
          covidList.add(covidData);
          dateWiseCountryMap.put(covidData.getDate(), covidList);
        }
        else
        {
          dateWiseCountryMap.get(covidData.getDate()).add(covidData);
        }
      }
      CovidData highestCovidData = null;

      for (Date date : dateWiseCountryMap.keySet())
      {
        int active = 0;
        int confirmed = 0;
        int deceased = 0;
        int recovered = 0;
        List<CovidData> covidList = dateWiseCountryMap.get(date);
        for (CovidData data : covidList)
        {
          active = active + data.getActive();
          confirmed = confirmed + data.getConfirmed();
          deceased = deceased + data.getDeceased();
          recovered = recovered + data.getRecovered();
        }
        NationalLevelCovidData covidData = new NationalLevelCovidData();
        covidData.setDate(date);
        covidData.setActive(active);
        covidData.setConfirmed(confirmed);
        covidData.setDeceased(deceased);
        covidData.setRecovered(recovered);
        covidData.setCountryName(Constants.COUNTRY_NAME);
        covidDataMap.get(Constants.NATIONAL_LEVEL_KEY).add(covidData);

        if (highestCovidData == null || covidData.getConfirmed() > highestCovidData.getConfirmed())
        {
          highestCovidData = covidData;
        }
      }
      if (highestCovidData != null)
      {
        covidAggregatedDataList.add(getAggregatedData(highestCovidData, RegionType.COUNTRY, countryName, null));
      }
    }
  }

  private void parseData(Map<String, Map> reponse, Map<String, List<CovidData>> covidDataMap)
  {
    List<CovidData> covidAggregatedDataList = covidDataMap.get(Constants.AGGREGATED_LEVEL_KEY);
    Map<String, Map> stateLevelData = reponse.get(DISTRICT_DAILY_KEY);
    for (String stateName : stateLevelData.keySet())
    {
      Map<String, ArrayList<Map>> cityList = stateLevelData.get(stateName);
      List<CovidData> stateAccumulatedList = new ArrayList<>();
      for (String cityName : cityList.keySet())
      {
        ArrayList<Map> cityValues = cityList.get(cityName);
        List<CovidData> list = createDistrictData(cityValues, cityName, stateName, covidDataMap, covidAggregatedDataList, stateName);
        stateAccumulatedList.addAll(list);
      }
      // All the districts of a state covered, now prepare data for that state
      prepareStateData(stateName, covidDataMap, stateAccumulatedList, covidAggregatedDataList);
    }
    // Parsing data for all state finished, now start national level
    parseNationalLevelData(covidDataMap, covidAggregatedDataList);

  }

  private void prepareStateData(String stateName, Map<String, List<CovidData>> covidDataMap,
          List<CovidData> stateAccumulatedList, List<CovidData> covidAggregatedDataList)
  {
    Map<Date, List<CovidData>> dateWiseStateMap = new HashMap<>();
    for (CovidData covidData : stateAccumulatedList)
    {
      if (dateWiseStateMap.get(covidData.getDate()) == null)
      {
        List<CovidData> covidList = new ArrayList<>();
        covidList.add(covidData);
        dateWiseStateMap.put(covidData.getDate(), covidList);
      }
      else
      {
        dateWiseStateMap.get(covidData.getDate()).add(covidData);
      }
    }

    CovidData highestCovidData = null;
    for (Date date : dateWiseStateMap.keySet())
    {
      int active = 0;
      int confirmed = 0;
      int deceased = 0;
      int recovered = 0;
      List<CovidData> covidList = dateWiseStateMap.get(date);
      for (CovidData data : covidList)
      {
        active = active + data.getActive();
        confirmed = confirmed + data.getConfirmed();
        deceased = deceased + data.getDeceased();
        recovered = recovered + data.getRecovered();
      }
      StateLevelCovidData covidData = new StateLevelCovidData();
      covidData.setDate(date);
      covidData.setActive(active);
      covidData.setConfirmed(confirmed);
      covidData.setDeceased(deceased);
      covidData.setRecovered(recovered);
      covidData.setStateName(stateName);
      covidData.setCountryName(Constants.COUNTRY_NAME);
      covidDataMap.get(Constants.STATE_LEVEL_KEY).add(covidData);

      if (highestCovidData == null || covidData.getConfirmed() > highestCovidData.getConfirmed())
      {
        highestCovidData = covidData;
      }
    }
    if (highestCovidData != null)
    {
      // Aggregate it to state level total data
      covidAggregatedDataList.add(getAggregatedData(highestCovidData, RegionType.STATE, stateName, "India"));
    }
  }

  private CovidData getAggregatedData(CovidData highestCovidData, RegionType regionType, String regionName, String parentRegion)
  {
    CovidAggregatedData covidAggregatedData = new CovidAggregatedData();
    covidAggregatedData.setRegionType(regionType);
    covidAggregatedData.setRegionName(regionName);
    covidAggregatedData.setParentRegion(parentRegion);
    covidAggregatedData.setActive(highestCovidData.getActive());
    covidAggregatedData.setConfirmed(highestCovidData.getConfirmed());
    covidAggregatedData.setDeceased(highestCovidData.getDeceased());
    covidAggregatedData.setRecovered(highestCovidData.getRecovered());
    covidAggregatedData.setTested(highestCovidData.getTested());
    return covidAggregatedData;
  }

  private List<CovidData> createDistrictData(ArrayList<Map> districtValues, String cityName, String stateName,
          Map<String, List<CovidData>> covidDataMap, List<CovidData> covidAggregatedDataList, String parentRegion)
  {
    List<CovidData> cityCovidList = new ArrayList<>();
    CovidData highestCovidData = null;

    for (Map<String, Object> data : districtValues)
    {
      DistrictLevelCovidData covidData = new DistrictLevelCovidData();
      covidData.setCityName(cityName);
      covidData.setStateName(stateName);
      covidData.setActive(data.get(DISTRICT_DAILY_KEY_ACTIVE) != null ? (int)data.get(DISTRICT_DAILY_KEY_ACTIVE) : 0);
      covidData.setConfirmed(data.get(DISTRICT_DAILY_KEY_CONFIRMED) != null ? (int)data.get(DISTRICT_DAILY_KEY_CONFIRMED) : 0);
      covidData.setDeceased(data.get(DISTRICT_DAILY_KEY_DECEASED) != null ? (int)data.get(DISTRICT_DAILY_KEY_DECEASED) : 0);
      covidData.setRecovered(data.get(DISTRICT_DAILY_KEY_RECOVERED) != null ? (int)data.get(DISTRICT_DAILY_KEY_RECOVERED) : 0);
      try
      {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        Date date = formatter.parse(data.get(DISTRICT_DAILY_KEY_DATE).toString());
        covidData.setDate(date);
        cityCovidList.add(covidData);
        covidDataMap.get(Constants.DISTRICT_LEVEL_KEY).addAll(cityCovidList);
      }
      catch (Exception e)
      {
        // just ignore this data.... Let others accumulate
      }
      if (highestCovidData == null || covidData.getConfirmed() > highestCovidData.getConfirmed())
      {
        highestCovidData = covidData;
      }
    }

    if (highestCovidData != null)
    {
      covidAggregatedDataList.add(getAggregatedData(highestCovidData, RegionType.DISTRICT, cityName, parentRegion));
    }
    return cityCovidList;
  }

  private void initializeDataMaps(Map<String, List<CovidData>> covidDataMap)
  {
    List<CovidData> nationalLevelList = new ArrayList<>();
    covidDataMap.put(Constants.NATIONAL_LEVEL_KEY, nationalLevelList);

    List<CovidData> stateLevelList = new ArrayList<>();
    covidDataMap.put(Constants.STATE_LEVEL_KEY, stateLevelList);

    List<CovidData> districtLevelList = new ArrayList<>();
    covidDataMap.put(Constants.DISTRICT_LEVEL_KEY, districtLevelList);

    List<CovidData> aggregatedDataList = new ArrayList<>();
    covidDataMap.put(Constants.AGGREGATED_LEVEL_KEY, aggregatedDataList);
  }
}