package com.assessment.favouriterecipes.specification;

import com.assessment.favouriterecipes.constant.Constants;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.domain.Sort;

import javax.validation.constraints.Min;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class PageableCriteria {

    @Min(value = 0, message = Constants.MIN_PAGE)
    private int page = 0;

    @Min(value = 10, message = Constants.MIN_SIZE)
    private int size = 10;

    private Sort.Direction direction = Sort.Direction.DESC;

}