package com.wander.covid.downloader.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.wander.covid.downloader.CovidDataDownloader;
import com.wander.covid.model.CovidData;
import com.wander.covid.parser.CovidDataParser;
import com.wander.covid.service.CovidDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.util.*;

@Component
public class Covid19indiaDownloader implements CovidDataDownloader
{
  Logger LOG = LoggerFactory.getLogger(Covid19indiaDownloader.class);

  @Autowired
  RestTemplate restTemplate;

  @Autowired
  CovidDataService covidDataService;

  @Autowired
  CovidDataDownloader covidDataDownloader;

  @Autowired
  private CovidDataParser covidDataParser;
  @Value("${covid.data.download.url}")
  private String downloadURL;

  @Override
  public Map<String, List<CovidData>> loadData()
  {
    LOG.info("********Data load started, downloading data from: " + downloadURL);
    long downloadingStart = System.currentTimeMillis();
    Object jsonObject = getDataFromCovid19Org();
    long downloadingEnd = System.currentTimeMillis();
    LOG.info(
            "********Data Downloaded, Parsing Started, "
                    + ", Time taken in data downloading: " + ((downloadingEnd - downloadingStart) / 1000) + " Seconds");

    long parsingStart = System.currentTimeMillis();
    Map<String, List<CovidData>> covidDataMap = covidDataParser.parse(jsonObject);
    long parsingEnd = System.currentTimeMillis();
    LOG.info("********Parsing finished, saving data into database"
            + "Time taken in parsing: " + ((parsingEnd - parsingStart) / 1000) + " Seconds");

    covidDataService.saveAllData(covidDataMap);
    long savingDataEnd = System.currentTimeMillis();
    LOG.info("********Data load complete"
            + ", Total Time taken in data load: " + ((savingDataEnd - downloadingStart) / 1000) + " Seconds");
    return covidDataMap;
  }

  @HystrixCommand(fallbackMethod = "downloadFromOfflineJson")
  public Object getDataFromCovid19Org()
  {
    Object result;
    try
    {
      result = restTemplate.getForObject(downloadURL, LinkedHashMap.class);
    }
    catch (Throwable e)
    {
      result = downloadFromOfflineJson();
    }
    return result;
  }

  public Object downloadFromOfflineJson()
  {
    LOG.info("*****Download failed, trying offline file to load data");
    Object result = null;
    ObjectMapper objectMapper = new ObjectMapper();
    File file;
    try
    {
      file = ResourceUtils.getFile("classpath:CovidData.json");
      result = objectMapper.readValue(file, LinkedHashMap.class);
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    return result;
  }
}