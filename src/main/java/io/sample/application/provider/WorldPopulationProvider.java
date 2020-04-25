package io.sample.application.provider;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.databind.JsonNode;

@Service
public class WorldPopulationProvider
{
    private static final String RESPONSE_FORMAT = "json";

    @Value("${world.population.endpoint.uri}")
    private String worldPopulationEndpoint;

    public JsonNode getCountryPopulationData(final String year, final Integer page)
    {
        final RestTemplate restTemplate = new RestTemplate();

        final HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);

        final UriComponentsBuilder endpointBuilder = UriComponentsBuilder.fromHttpUrl(worldPopulationEndpoint)
            .queryParam("date", year)
            .queryParam("format", RESPONSE_FORMAT)
            .queryParam("page", page);

        final ResponseEntity<JsonNode> worldPopulationResponse = restTemplate.exchange(
            endpointBuilder.toUriString(),
            HttpMethod.GET,
            new HttpEntity<>(headers),
            JsonNode.class
        );

        return worldPopulationResponse.getBody();
    }
}
