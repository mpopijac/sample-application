package io.sample.application;

import io.sample.application.data.CountryData;
import io.sample.application.data.CountryPopulationData;
import io.sample.application.data.PaginationData;

public final class TestDataHelper
{
    private TestDataHelper()
    {
    }

    public static CountryPopulationData createCountryPopulationData(final String country, final Long population)
    {
        final CountryPopulationData countryPopulationData = new CountryPopulationData();
        countryPopulationData.setPopulation(population);
        final CountryData countryData = new CountryData();
        countryData.setId(country);
        countryData.setName(country);
        countryPopulationData.setCountry(countryData);
        return countryPopulationData;
    }

    public static PaginationData createPaginationData(final Integer currentPage, final Integer totalPages)
    {
        final PaginationData paginationData = new PaginationData();
        paginationData.setCurrentPage(currentPage);
        paginationData.setTotalPages(totalPages);
        return paginationData;
    }

}
