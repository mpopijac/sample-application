package io.sample.application.data;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PaginationData
{
    @JsonProperty("page")
    private Integer currentPage;

    @JsonProperty("pages")
    private Integer totalPages;

    @JsonProperty("per_page")
    public Long perPage;

    @JsonProperty("total")
    public Long total;

    @JsonProperty("sourceid")
    public String sourceid;

    @JsonProperty("lastupdated")
    public Date lastupdated;
}
