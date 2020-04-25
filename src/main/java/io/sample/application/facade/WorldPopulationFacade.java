package io.sample.application.facade;

import java.util.List;

import io.sample.application.data.CountryPopulationData;

public interface WorldPopulationFacade
{
    List<CountryPopulationData> getCountriesPopulation(String year, String sort, Long limit);
}
