package com.wander.covid.parser.impl;

import com.wander.covid.downloader.CovidDataDownloader;
import com.wander.covid.model.CovidData;
import com.wander.covid.parser.CovidDataParser;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
class Covid19OrgDataParserImplTest
{
  @Autowired
  private CovidDataDownloader covidDataDownloader;

  @Autowired
  private CovidDataParser covidDataParser;

  @Test
  void parse()
  {
    Object object = covidDataDownloader.downloadFromOfflineJson();
    Map<String, List<CovidData>> covidDataMap = covidDataParser.parse(object);
    Assert.assertEquals(covidDataMap.size(), 4);
    Assert.assertNotNull(covidDataMap.get("DISTRICT"));
    Assert.assertNotNull(covidDataMap.get("STATE"));
    Assert.assertNotNull(covidDataMap.get("NATIONAL"));
    Assert.assertNotNull(covidDataMap.get("AGGREGATED"));
  }
}