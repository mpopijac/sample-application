package io.sample.application.facade;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import io.sample.application.data.CountryPopulationData;
import io.sample.application.facade.impl.WorldPopulationFacadeImpl;
import io.sample.application.service.WorldPopulationService;

import static io.sample.application.TestDataHelper.createCountryPopulationData;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class WorldPopulationFacadeImplTest
{
    @InjectMocks
    private WorldPopulationFacadeImpl worldPopulationFacade;

    @Mock
    private WorldPopulationService worldPopulationService;

    private CountryPopulationData countryPopulationData1;

    private CountryPopulationData countryPopulationData2;

    private CountryPopulationData countryPopulationData3;

    private CountryPopulationData countryPopulationData4;

    @Before
    public void setUp()
    {
        countryPopulationData1 = createCountryPopulationData("Country1", 80L);
        countryPopulationData2 = createCountryPopulationData("Country2", 60L);
        countryPopulationData3 = createCountryPopulationData("Country3", 100L);
        countryPopulationData4 = createCountryPopulationData("Country4", 90L);

        when(worldPopulationService.getCountriesPopulation(any())).thenReturn(Arrays.asList(countryPopulationData1, countryPopulationData2, countryPopulationData3, countryPopulationData4));
    }

    @Test
    public void test_getCountriesPopulationWithoutSortAndLimitParameter()
    {
        final List<CountryPopulationData> countriesPopulationData = worldPopulationFacade.getCountriesPopulation("2018", null, null);
        assertThat(countriesPopulationData, hasSize(3));
        assertThat(countriesPopulationData, contains(countryPopulationData2, countryPopulationData1, countryPopulationData4));
    }

    @Test
    public void test_getCountriesPopulationSortAscWithoutLimitParameter()
    {
        final List<CountryPopulationData> countriesPopulationData = worldPopulationFacade.getCountriesPopulation("2018", "asc", null);
        assertThat(countriesPopulationData, hasSize(3));
        assertThat(countriesPopulationData, contains(countryPopulationData2, countryPopulationData1, countryPopulationData4));
    }

    @Test
    public void test_getCountriesPopulationSortDescWithoutLimitParameter()
    {
        final List<CountryPopulationData> countriesPopulationData = worldPopulationFacade.getCountriesPopulation("2018", "desc", null);
        assertThat(countriesPopulationData, hasSize(3));
        assertThat(countriesPopulationData, contains(countryPopulationData3, countryPopulationData4, countryPopulationData1));
    }

    @Test
    public void test_getCountriesPopulationSortDescLimitTwoParameter()
    {
        final List<CountryPopulationData> countriesPopulationData = worldPopulationFacade.getCountriesPopulation("2018", "desc", 4L);
        assertThat(countriesPopulationData, hasSize(4));
        assertThat(countriesPopulationData, contains(countryPopulationData3, countryPopulationData4, countryPopulationData1, countryPopulationData2));
    }

    @Test
    public void test_getCountriesPopulationSortAscLimitTwoParameter()
    {
        final List<CountryPopulationData> countriesPopulationData = worldPopulationFacade.getCountriesPopulation("2018", "asc", 2L);
        assertThat(countriesPopulationData, hasSize(2));
        assertThat(countriesPopulationData, contains(countryPopulationData2, countryPopulationData1));
    }

    @Test
    public void test_getCountriesPopulationEmptyList()
    {
        when(worldPopulationService.getCountriesPopulation(any())).thenReturn(Collections.emptyList());
        final List<CountryPopulationData> countriesPopulationData = worldPopulationFacade.getCountriesPopulation("2018", "asc", 2L);
        assertThat(countriesPopulationData, empty());
    }
}
