package io.sample.application.data;

import java.util.List;

import lombok.Data;

@Data
public class WorldPopulationResponseData
{
    private PaginationData paginationData;

    private List<CountryPopulationData> countriesPopulationData;
}
