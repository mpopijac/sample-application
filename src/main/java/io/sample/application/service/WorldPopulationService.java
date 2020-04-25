package io.sample.application.service;

import java.util.List;

import io.sample.application.data.CountryPopulationData;

public interface WorldPopulationService
{
    List<CountryPopulationData> getCountriesPopulation(String year);
}
