package io.sample.application.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.sample.application.data.CountryPopulationData;
import io.sample.application.facade.WorldPopulationFacade;

@RestController
@RequestMapping(ApiConstants.API)
public class WorldPopulationController
{
    private final WorldPopulationFacade worldPopulationFacade;

    @Autowired
    public WorldPopulationController(final WorldPopulationFacade worldPopulationFacade)
    {
        this.worldPopulationFacade = worldPopulationFacade;
    }

    @GetMapping(value = ApiConstants.GET_COUNTRIES_POPULATION)
    public List<CountryPopulationData> getCountriesPopulation(
        @RequestParam(required = false) final String sort,
        @RequestParam(required = false) final Long limit)
    {
        return worldPopulationFacade.getCountriesPopulation("2018", sort, limit);
    }
}
