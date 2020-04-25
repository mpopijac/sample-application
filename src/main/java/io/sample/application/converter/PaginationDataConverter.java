package io.sample.application.converter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.sample.application.data.PaginationData;

@Component
public class PaginationDataConverter implements Converter<JsonNode, PaginationData>
{
    private static final Logger LOG = LoggerFactory.getLogger(PaginationDataConverter.class);

    private final DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    public PaginationData convert(final JsonNode jsonNode)
    {
        final ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setDateFormat(dateFormatter);
        PaginationData paginationData = null;
        try
        {
            paginationData = objectMapper.treeToValue(jsonNode, PaginationData.class);
        }
        catch (final JsonProcessingException e)
        {
            LOG.error(e.getMessage(), e);
        }

        return paginationData;
    }
}
