package io.sample.application.data;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CountryPopulationData
{
    IndicatorData indicator;

    CountryData country;

    @JsonProperty("countryiso3code")
    String countryIso3Code;

    @JsonProperty("date")
    String year;

    @JsonProperty("value")
    Long population;

    String unit;

    @JsonProperty("obs_status")
    String obsStatus;

    Integer decimal;

    public void setPopulation(final Long population)
    {
        this.population = Objects.isNull(population) ? 0L : population;
    }
}
