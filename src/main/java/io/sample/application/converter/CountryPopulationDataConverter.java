package io.sample.application.converter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;
import io.sample.application.data.CountryPopulationData;

@Component
public class CountryPopulationDataConverter implements Converter<JsonNode, List<CountryPopulationData>>
{
    @Override
    public List<CountryPopulationData> convert(final JsonNode jsonNode)
    {
        final List<CountryPopulationData> countriesPopulation = new ArrayList<>();
        jsonNode.forEach(countryPopulationJson -> createCountryPopulationData(countryPopulationJson).ifPresent(countriesPopulation::add));
        return countriesPopulation;
    }

    private Optional<CountryPopulationData> createCountryPopulationData(final JsonNode jsonNode)
    {
        if (jsonNode.hasNonNull("country")
            && jsonNode.get("country").hasNonNull("value")
            && jsonNode.has("countryiso3code"))
        {
            final CountryPopulationData countryPopulationData = new CountryPopulationData();
            countryPopulationData.setCountry(jsonNode.get("country").get("value").asText());
            countryPopulationData.setCountryIso3Code(jsonNode.get("countryiso3code").asText());
            if (jsonNode.hasNonNull("value"))
            {
                countryPopulationData.setPopulation(jsonNode.get("value").asLong());
            }
            else
            {
                countryPopulationData.setPopulation(0L);
            }
            return Optional.of(countryPopulationData);
        }
        return Optional.empty();
    }

}
