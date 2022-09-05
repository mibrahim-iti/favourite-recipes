package com.assessment.favouriterecipes.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaginationResponse<T> {

    private List<T> data;

    private Long totalElements;

    private Integer totalPages;

}
