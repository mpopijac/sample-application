package io.sample.application.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import io.sample.application.converter.WorldPopulationResponseConverter;
import io.sample.application.data.CountryPopulationData;
import io.sample.application.data.PaginationData;
import io.sample.application.data.WorldPopulationResponseData;
import io.sample.application.provider.WorldPopulationProvider;
import io.sample.application.service.WorldPopulationService;

@Service
public class WorldPopulationServiceImpl implements WorldPopulationService
{
    private final WorldPopulationProvider worldPopulationProvider;

    private final WorldPopulationResponseConverter worldPopulationResponseConverter;

    @Autowired
    public WorldPopulationServiceImpl(final WorldPopulationProvider worldPopulationProvider,
                                      final WorldPopulationResponseConverter worldPopulationResponseConverter)
    {
        this.worldPopulationProvider = worldPopulationProvider;
        this.worldPopulationResponseConverter = worldPopulationResponseConverter;
    }

    @Override
    public List<CountryPopulationData> getCountriesPopulation(final String year)
    {
        final List<CountryPopulationData> countriesPopulationData = new ArrayList<>();
        WorldPopulationResponseData worldPopulationResponseData;
        PaginationData paginationData;
        int fetchPageData = 1;
        do
        {
            final JsonNode response = worldPopulationProvider.getCountryPopulationData(year, fetchPageData);
            worldPopulationResponseData = worldPopulationResponseConverter.convert(response);
            if (CollectionUtils.isNotEmpty(worldPopulationResponseData.getCountriesPopulationData()))
            {
                countriesPopulationData.addAll(worldPopulationResponseData.getCountriesPopulationData());
            }
            fetchPageData++;
            paginationData = worldPopulationResponseData.getPaginationData();
        }
        while (Objects.nonNull(paginationData) && fetchPageData <= paginationData.getTotalPages());

        return countriesPopulationData;
    }
}
