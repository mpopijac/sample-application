package io.sample.application.data;

import lombok.Data;

@Data
public class PaginationData
{
    private Integer currentPage;

    private Integer totalPages;
}
