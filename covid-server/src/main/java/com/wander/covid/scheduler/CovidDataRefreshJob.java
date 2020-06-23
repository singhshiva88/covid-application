package com.wander.covid.scheduler;

import com.wander.covid.downloader.CovidDataDownloader;
import com.wander.covid.service.CovidDataCleanService;
import com.wander.covid.service.CovidDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class CovidDataRefreshJob
{
  @Autowired
  CovidDataCleanService covidDataCleanService;

  @Autowired
  CovidDataDownloader covidDataDownloader;

  @Autowired
  CovidDataService covidDataService;

  @Scheduled(fixedDelay = 1000 * 60 * 60, initialDelay = 2000)
  // This api would refresh data every one from start of the application
  public void fixedDelayCovidDataRefresher()
  {
    covidDataCleanService.cleanAllData();
    covidDataDownloader.loadData();
  }

  public void manualCovidDataRefresher()
  {
    covidDataCleanService.cleanAllData();
    covidDataDownloader.loadData();
  }
}