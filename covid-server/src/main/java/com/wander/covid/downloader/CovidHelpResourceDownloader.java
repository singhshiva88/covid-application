package com.wander.covid.downloader;

import com.wander.covid.model.CovidHelpCenter;

import java.util.List;

public interface CovidHelpResourceDownloader
{
  List<CovidHelpCenter> loadCovidHelpCernters();
}
