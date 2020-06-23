package com.wander.covid.parser;

import com.wander.covid.model.CovidData;

import java.util.*;

public interface CovidDataParser
{
  Map<String, List<CovidData>> parse(Object reponse);
}
