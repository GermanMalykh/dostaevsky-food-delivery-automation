package ru.dostaevsky.tests.api.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class BasketInfoSharesDetails {

    @JsonProperty("id")
    private int id;

    @JsonProperty("is_checked")
    private boolean isChecked;

    @JsonProperty("title")
    private String title;

    @JsonProperty("sub_title")
    private String subTitle;

    @JsonProperty("type")
    private String type;

    @JsonProperty("is_gift")
    private boolean isGift;

    @JsonProperty("image")
    private String image;
}
