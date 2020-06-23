package com.wander.covid.parser;

import com.wander.covid.model.CovidData;

import java.util.List;

public interface CovidHelpCenterParser
{
  List<CovidData> parse(Object data);
}
