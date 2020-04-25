package io.sample.application.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;
import io.sample.application.data.PaginationData;

@Component
public class PaginationDataConverter implements Converter<JsonNode, PaginationData>
{
    @Override
    public PaginationData convert(final JsonNode jsonNode)
    {
        final PaginationData paginationData = new PaginationData();
        if (jsonNode.hasNonNull("page"))
        {
            paginationData.setCurrentPage(jsonNode.get("page").asInt());
        }
        if (jsonNode.hasNonNull("pages"))
        {
            paginationData.setTotalPages(jsonNode.get("pages").asInt());
        }
        return paginationData;
    }
}
