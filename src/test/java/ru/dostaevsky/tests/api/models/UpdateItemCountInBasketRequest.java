package ru.dostaevsky.tests.api.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class UpdateItemCountInBasketRequest {

    @JsonProperty("item_id")
    private Integer itemId;

    @JsonProperty("quantity")
    private Integer quantity;
}
