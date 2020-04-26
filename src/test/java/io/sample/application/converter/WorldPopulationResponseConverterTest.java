package io.sample.application.converter;

import java.util.Arrays;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import com.fasterxml.jackson.databind.JsonNode;
import io.sample.application.data.CountryPopulationData;
import io.sample.application.data.PaginationData;
import io.sample.application.data.WorldPopulationResponseData;

import static io.sample.application.TestDataHelper.createCountryPopulationData;
import static io.sample.application.TestDataHelper.createPaginationData;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class WorldPopulationResponseConverterTest
{
    @InjectMocks
    private WorldPopulationResponseConverter worldPopulationResponseConverter;

    @Mock
    private PaginationDataConverter paginationDataConverter;

    @Mock
    private CountryPopulationDataConverter countryPopulationDataConverter;

    @Mock
    private JsonNode responseData;

    private PaginationData paginationData;

    private List<CountryPopulationData> countriesPopulationData;

    @Before
    public void setUp()
    {
        final JsonNode paginationDataNode = mock(JsonNode.class);
        when(paginationDataNode.isObject()).thenReturn(true);

        final JsonNode countriesPopulationDataNode = mock(JsonNode.class);
        when(countriesPopulationDataNode.isArray()).thenReturn(true);

        when(responseData.size()).thenReturn(2);
        when(responseData.get(0)).thenReturn(paginationDataNode);
        when(responseData.get(1)).thenReturn(countriesPopulationDataNode);

        paginationData = createPaginationData(1, 5);
        countriesPopulationData = Arrays.asList(createCountryPopulationData("Country1", 1000L)
            , createCountryPopulationData("Country2", 2000L));

        when(paginationDataConverter.convert(paginationDataNode)).thenReturn(paginationData);
        when(countryPopulationDataConverter.convert(countriesPopulationDataNode)).thenReturn(countriesPopulationData);
    }

    @Test
    public void test_convert()
    {
        final WorldPopulationResponseData worldPopulationResponseData = worldPopulationResponseConverter.convert(responseData);
        assertThat(worldPopulationResponseData.getPaginationData(), equalTo(paginationData));
        assertThat(worldPopulationResponseData.getCountriesPopulationData(), equalTo(countriesPopulationData));
    }
}
