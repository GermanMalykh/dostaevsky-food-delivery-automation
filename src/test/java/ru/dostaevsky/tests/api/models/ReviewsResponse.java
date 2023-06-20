package ru.dostaevsky.tests.api.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ReviewsResponse {

    @JsonProperty("total")
    private Integer total;

    @JsonProperty("reviews")
    private ReviewsDetails[] reviews;
}
