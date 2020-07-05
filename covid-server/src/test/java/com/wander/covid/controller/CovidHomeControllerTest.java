package com.wander.covid.controller;

import com.wander.covid.downloader.CovidDataDownloader;
import com.wander.covid.model.*;
import com.wander.covid.scheduler.CovidDataRefreshJob;
import org.junit.jupiter.api.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CovidHomeControllerTest
{

  @MockBean
  private CovidDataRefreshJob covidDataRefreshJob;

  @Autowired
  CovidHomeController covidHomeController;

  @Autowired
  CovidDataDownloader covidDataDownloader;

  @BeforeAll
  public void loadCovidDataBeforeALL()
  {
    covidDataDownloader.loadData();
  }

  @Test
  void getAggregatedRegionRecords()
  {
    List<CovidAggregatedData> response = covidHomeController.getAggregatedRegionRecords("Maharashtra");
    Assertions.assertNotNull(response);
    Assertions.assertEquals(1, response.size());
    assertTrue(response.get(0).getActive() > 0);
    assertTrue(response.get(0).getConfirmed() > 0);
    assertTrue(response.get(0).getRecovered() > 0);
    assertTrue(response.get(0).getDeceased() > 0);
    Assertions.assertEquals("India", response.get(0).getParentRegion());
    assertEquals(RegionType.STATE, response.get(0).getRegionType());
  }

  @Test
  void getAllRecordsCountry()
  {
    List<NationalLevelCovidData> response = covidHomeController.getAllRecordsCountry();
    assertTrue(response != null);
    assertTrue(response.size() > 1);
    assertTrue(response.get(0).getCountryName().equals("India"));
  }

  @Test
  void getAllRecordsForState()
  {
    List<StateLevelCovidData> response = covidHomeController.getAllRecordsForState("Delhi");
    assertTrue(response != null);
    assertTrue(response.size() >= 1);
    assertTrue(response.get(0).getCountryName().equals("India"));
    assertTrue(response.get(0).getStateName().equals("Delhi"));
  }

  @Test
  void getAllRecordsForDistrict()
  {
    List<DistrictLevelCovidData> response = covidHomeController.getAllRecordsForDistrict("Agra");
    assertTrue(response != null);
    assertTrue(response.size() >= 1);
    assertTrue(response.get(0).getStateName().equals("Uttar Pradesh"));
  }

  @Test
  void getAggregatedRegionTypeRecords()
  {
    List<CovidAggregatedData> covidAggregatedData1 = covidHomeController.getAggregatedRegionTypeRecords("country");
    assertTrue(covidAggregatedData1.size() > 0);
    List<CovidAggregatedData> covidAggregatedData2 = covidHomeController.getAggregatedRegionTypeRecords("state");
    assertTrue(covidAggregatedData2.size() > 0);
    List<CovidAggregatedData> covidAggregatedData3 = covidHomeController.getAggregatedRegionTypeRecords("district");
    assertTrue(covidAggregatedData3.size() > 0);
  }

  @Test
  void getAllCitiesByStateAggregated()
  {
    List<CovidAggregatedData> covidAggregatedData = covidHomeController.getAllCitiesByStateAggregated("Uttar Pradesh");
    assertTrue(covidAggregatedData.size() > 0);
  }
}