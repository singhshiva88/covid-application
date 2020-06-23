package com.wander.covid.controller;

import com.wander.covid.model.*;
import com.wander.covid.scheduler.CovidDataRefreshJob;
import com.wander.covid.service.CovidDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = { "http://localhost:4200"})
public class CovidHomeController
{
  @Autowired
  CovidDataService covidDataService;

  @Autowired
  CovidDataRefreshJob covidDataRefreshJob;

  @GetMapping(value = "/region_name/{name}")
  public List<CovidAggregatedData> getAggregatedRegionRecords(@PathVariable("name") String name) {
    return covidDataService.getAggregatedRegionRecords(name);
  }

  @GetMapping(value = "/country/cumulative")
  public List<NationalLevelCovidData> getAllRecordsCountry () {
    return covidDataService.getAllRecordsCountry();
  }

  @GetMapping(value = "/state/cumulative/{name}")
  public List<StateLevelCovidData> getAllRecordsForState(@PathVariable("name") String name) {
    return covidDataService.getAllRecordsForState(name);
  }

  @GetMapping(value = "/district/cumulative/{name}")
  public List<DistrictLevelCovidData> getAllRecordsForDistrict(@PathVariable("name") String name) {
    return covidDataService.getAllRecordsForDistrict(name);
  }

  @GetMapping(value = "/region_type/{type}")
  public List<CovidAggregatedData> getAggregatedRegionTypeRecords(@PathVariable("type") String type) {
    return covidDataService.getAggregatedRegionTypeRecords(type);
  }

  @GetMapping(value = "/region_type/{state}/allcities")
  public List<CovidAggregatedData> getAllCitiesByStateAggregated(@PathVariable("state") String state) {
    return covidDataService.getAllCitiesByStateAggregated(state);
  }

  @GetMapping(value = "/checkAuthentication")
  public ResponseEntity<String> home()
  {
    return new ResponseEntity<>("Welcome to home page with Covid data", HttpStatus.OK);
  }

  @GetMapping(value = "/refresh_data")
  public ResponseEntity<String> refreshData()
  {
    covidDataRefreshJob.manualCovidDataRefresher();
    return ResponseEntity.ok().build();
  }
}