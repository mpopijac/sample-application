package io.sample.application.facade.impl;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.sample.application.data.CountryPopulationData;
import io.sample.application.facade.WorldPopulationFacade;
import io.sample.application.service.WorldPopulationService;

@Component
public class WorldPopulationFacadeImpl implements WorldPopulationFacade
{
    private final WorldPopulationService worldPopulationService;

    @Autowired
    public WorldPopulationFacadeImpl(final WorldPopulationService worldPopulationService)
    {
        this.worldPopulationService = worldPopulationService;
    }

    @Override
    public List<CountryPopulationData> getCountriesPopulation(final String year, final String sort, final Long limit)
    {
        List<CountryPopulationData> countriesPopulationData = worldPopulationService.getCountriesPopulation(year);
        if ("desc".equalsIgnoreCase(sort))
        {
            countriesPopulationData = countriesPopulationData.stream()
                .sorted(Comparator.comparingLong(CountryPopulationData::getPopulation).reversed())
                .collect(Collectors.toList());
        }
        else
        {
            countriesPopulationData = countriesPopulationData.stream()
                .sorted(Comparator.comparingLong(CountryPopulationData::getPopulation))
                .collect(Collectors.toList());
        }

        if (Objects.nonNull(limit))
        {
            countriesPopulationData = countriesPopulationData.stream().limit(limit).collect(Collectors.toList());
        }
        return countriesPopulationData;
    }
}
