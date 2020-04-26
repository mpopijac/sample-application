package io.sample.application.controller;

import java.util.Arrays;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import io.sample.application.data.CountryPopulationData;
import io.sample.application.facade.WorldPopulationFacade;

import static io.sample.application.TestDataHelper.createCountryPopulationData;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(WorldPopulationController.class)
public class WorldPopulationControllerTest
{
    private static final String GET_POPULATION_RESPOND = "[{\"country\":{\"id\":\"Country1\",\"value\":\"Country1\"},\"value\":50},{\"country\":{\"id\":\"Country2\",\"value\":\"Country2\"},\"value\":60},{\"country\":{\"id\":\"Country3\",\"value\":\"Country3\"},\"value\":100}]";

    @Autowired
    private MockMvc mvc;

    @MockBean
    private WorldPopulationFacade worldPopulationFacade;

    @Before
    public void setUp()
    {
        final CountryPopulationData countryPopulationData1 = createCountryPopulationData("Country1", 50L);
        final CountryPopulationData countryPopulationData2 = createCountryPopulationData("Country2", 60L);
        final CountryPopulationData countryPopulationData3 = createCountryPopulationData("Country3", 100L);
        when(worldPopulationFacade.getCountriesPopulation(any(), any(), any())).thenReturn(Arrays.asList(countryPopulationData1, countryPopulationData2, countryPopulationData3));
    }

    @Test
    public void test_getCountryPopulationWithoutParameters() throws Exception
    {
        mvc.perform(get(ApiConstants.API + ApiConstants.GET_COUNTRIES_POPULATION)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().string(GET_POPULATION_RESPOND));
    }

    @Test
    public void test_getCountryPopulationWithSortParameter() throws Exception
    {
        mvc.perform(get(ApiConstants.API + ApiConstants.GET_COUNTRIES_POPULATION)
            .contentType(MediaType.APPLICATION_JSON).queryParam("sort", "asc"))
            .andExpect(status().isOk())
            .andExpect(content().string(GET_POPULATION_RESPOND));
    }

    @Test
    public void test_getCountryPopulationWithSortAndLimitParameter() throws Exception
    {
        mvc.perform(get(ApiConstants.API + ApiConstants.GET_COUNTRIES_POPULATION)
            .contentType(MediaType.APPLICATION_JSON)
            .queryParam("sort", "asc")
            .queryParam("limit", "3"))
            .andExpect(status().isOk())
            .andExpect(content().string(GET_POPULATION_RESPOND));
    }
}
