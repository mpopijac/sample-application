package io.sample.application.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;
import io.sample.application.data.WorldPopulationResponseData;

@Component
public class WorldPopulationResponseConverter implements Converter<JsonNode, WorldPopulationResponseData>
{
    private final PaginationDataConverter paginationDataConverter;

    private final CountryPopulationDataConverter countryPopulationDataConverter;

    @Autowired
    public WorldPopulationResponseConverter(final PaginationDataConverter paginationDataConverter,
                                            final CountryPopulationDataConverter countryPopulationDataConverter)
    {
        this.paginationDataConverter = paginationDataConverter;
        this.countryPopulationDataConverter = countryPopulationDataConverter;
    }

    @Override
    public WorldPopulationResponseData convert(final JsonNode jsonNode)
    {
        final WorldPopulationResponseData worldPopulationResponseData = new WorldPopulationResponseData();
        if (jsonNode.size() == 2 && jsonNode.get(0).isObject())
        {
            worldPopulationResponseData.setPaginationData(paginationDataConverter.convert(jsonNode.get(0)));
        }
        if (jsonNode.size() == 2 && jsonNode.get(1).isArray())
        {
            worldPopulationResponseData.setCountriesPopulationData(countryPopulationDataConverter.convert(jsonNode.get(1)));
        }
        return worldPopulationResponseData;
    }
}
