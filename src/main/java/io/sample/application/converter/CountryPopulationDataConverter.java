package io.sample.application.converter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.sample.application.data.CountryPopulationData;

@Component
public class CountryPopulationDataConverter implements Converter<JsonNode, List<CountryPopulationData>>
{
    private static final Logger LOG = LoggerFactory.getLogger(CountryPopulationDataConverter.class);

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public List<CountryPopulationData> convert(final JsonNode jsonNode)
    {
        final List<CountryPopulationData> countriesPopulation = new ArrayList<>();
        jsonNode.forEach(countryPopulationJson -> createCountryPopulationData(countryPopulationJson).ifPresent(countriesPopulation::add));
        return countriesPopulation;
    }

    private Optional<CountryPopulationData> createCountryPopulationData(final JsonNode jsonNode)
    {
        try
        {
            return Optional.of(objectMapper.treeToValue(jsonNode, CountryPopulationData.class));
        }
        catch (final JsonProcessingException e)
        {
            LOG.error(e.getMessage(), e);
        }
        return Optional.empty();
    }

}
