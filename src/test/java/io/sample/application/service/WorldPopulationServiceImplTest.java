package io.sample.application.service;

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

import com.fasterxml.jackson.databind.JsonNode;
import io.sample.application.converter.WorldPopulationResponseConverter;
import io.sample.application.data.CountryPopulationData;
import io.sample.application.data.PaginationData;
import io.sample.application.data.WorldPopulationResponseData;
import io.sample.application.provider.WorldPopulationProvider;
import io.sample.application.service.impl.WorldPopulationServiceImpl;

import static io.sample.application.TestDataHelper.createCountryPopulationData;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class WorldPopulationServiceImplTest
{
    @InjectMocks
    private WorldPopulationServiceImpl worldPopulationService;

    @Mock
    private WorldPopulationProvider worldPopulationProvider;

    @Mock
    private WorldPopulationResponseConverter worldPopulationResponseConverter;

    private List<CountryPopulationData> countriesPopulationData;

    @Before
    public void setUp()
    {
        final JsonNode responsePageOne = mock(JsonNode.class);
        when(worldPopulationProvider.getCountryPopulationData("2018", 1)).thenReturn(responsePageOne);

        final JsonNode responsePageTwo = mock(JsonNode.class);
        when(worldPopulationProvider.getCountryPopulationData("2018", 2)).thenReturn(responsePageTwo);

        final JsonNode responsePageThree = mock(JsonNode.class);
        when(worldPopulationProvider.getCountryPopulationData("2018", 3)).thenReturn(responsePageThree);

        final PaginationData paginationData = mock(PaginationData.class);
        when(paginationData.getTotalPages()).thenReturn(3);

        countriesPopulationData = createCountryPopulationDataList();

        final WorldPopulationResponseData worldPopulationResponseDataPageOne = mock(WorldPopulationResponseData.class);
        when(worldPopulationResponseDataPageOne.getPaginationData()).thenReturn(paginationData);
        when(worldPopulationResponseDataPageOne.getCountriesPopulationData())
            .thenReturn(Arrays.asList(countriesPopulationData.get(0), countriesPopulationData.get(1), countriesPopulationData.get(2)));
        when(worldPopulationResponseConverter.convert(responsePageOne)).thenReturn(worldPopulationResponseDataPageOne);

        final WorldPopulationResponseData worldPopulationResponseDataPageTwo = mock(WorldPopulationResponseData.class);
        when(worldPopulationResponseDataPageTwo.getPaginationData()).thenReturn(paginationData);
        when(worldPopulationResponseDataPageTwo.getCountriesPopulationData())
            .thenReturn(Arrays.asList(countriesPopulationData.get(3), countriesPopulationData.get(4)));
        when(worldPopulationResponseConverter.convert(responsePageTwo)).thenReturn(worldPopulationResponseDataPageTwo);

        final WorldPopulationResponseData worldPopulationResponseDataPageThree = mock(WorldPopulationResponseData.class);
        when(worldPopulationResponseDataPageThree.getPaginationData()).thenReturn(paginationData);
        when(worldPopulationResponseDataPageThree.getCountriesPopulationData())
            .thenReturn(Collections.singletonList(countriesPopulationData.get(5)));
        when(worldPopulationResponseConverter.convert(responsePageThree)).thenReturn(worldPopulationResponseDataPageThree);

    }

    @Test
    public void test_getCountriesPopulation()
    {
        final List<CountryPopulationData> populationData = worldPopulationService.getCountriesPopulation("2018");
        assertThat(populationData, hasSize(6));
        assertThat(populationData, equalTo(countriesPopulationData));
    }

    private List<CountryPopulationData> createCountryPopulationDataList()
    {
        return Arrays.asList(createCountryPopulationData("Country1", 1000L)
            , createCountryPopulationData("Country2", 800L)
            , createCountryPopulationData("Country3", 2000L)
            , createCountryPopulationData("Country4", 4000L)
            , createCountryPopulationData("Country5", 10L)
            , createCountryPopulationData("Country6", 6000L));
    }
}
