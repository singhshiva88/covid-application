package com.wander.covid.downloader;

import com.wander.covid.model.CovidData;

import java.util.List;
import java.util.Map;

public interface CovidDataDownloader
{
  Map<String, List<CovidData>> loadData();

  Object downloadFromOfflineJson();
}
