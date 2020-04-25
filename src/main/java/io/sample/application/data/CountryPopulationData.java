package io.sample.application.data;

import lombok.Data;

@Data
public class CountryPopulationData
{
    String country;

    String countryIso3Code;

    Long population;
}
