package com.wander.covid.repository;

import com.wander.covid.model.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CovidDataRepository extends JpaRepository<CovidData, Long>
{

  List<CovidAggregatedData> findByRegionName(String name);

  List<CovidAggregatedData> findByRegionType(RegionType type);

  List<NationalLevelCovidData> findByCountryName(String name);

  List<StateLevelCovidData> findByStateName(String name);

  List<DistrictLevelCovidData> findByCityName(String name);

  @Query("SELECT c FROM CovidAggregatedData c where c.parentRegion = :state and c.regionType = 'DISTRICT'")
  List<CovidAggregatedData> getAggregatedCityByState(@Param("state") String state);

  @Modifying
  @Query(
          value = "truncate table district_level_covid_data",
          nativeQuery = true
  )
  void truncateDistrictData();

  @Modifying
  @Query(
          value = "truncate table state_level_covid_data",
          nativeQuery = true
  )
  void truncateStateData();

  @Modifying
  @Query(
          value = "truncate table national_level_covid_data",
          nativeQuery = true
  )
  void truncateCountryData();

  @Modifying
  @Query(
          value = "truncate table covid_aggregated_data",
          nativeQuery = true
  )
  void truncateAggregatedData();

}
