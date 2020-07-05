package com.wander.covid.service;

import com.wander.covid.model.*;
import com.wander.covid.repository.CovidDataRepository;
import com.wander.covid.service.impl.CovidDataServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class CovidDataServiceTest
{
  @Mock
  private CovidDataRepository covidDataRepository;

  @InjectMocks
  private CovidDataService covidDataService = new CovidDataServiceImpl();

  @Test
  void getAggregatedRegionRecords()
  {
    List<CovidAggregatedData> AggregatedDataList = new ArrayList<>();
    CovidAggregatedData aggregatedData = new CovidAggregatedData();
    aggregatedData.setId(1);
    AggregatedDataList.add(aggregatedData);
    Mockito.when(covidDataRepository.findByRegionName("India")).thenReturn(AggregatedDataList);
    List<CovidAggregatedData> data1 = covidDataService.getAggregatedRegionRecords("India");
    Assert.assertEquals(data1.get(0).getId(), 1);
  }

  @Test
  void getAllRecordsCountry()
  {
    List<NationalLevelCovidData> data = new ArrayList<>();
    NationalLevelCovidData covidData = new NationalLevelCovidData();
    covidData.setId(1);
    data.add(covidData);
    Mockito.when(covidDataRepository.findByCountryName("India")).thenReturn(data);
    List<NationalLevelCovidData> data1 = covidDataService.getAllRecordsCountry();
    Assert.assertEquals(data1.get(0).getId(), 1);
  }
}