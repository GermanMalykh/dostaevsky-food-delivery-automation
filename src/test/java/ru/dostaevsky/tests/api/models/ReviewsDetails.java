package ru.dostaevsky.tests.api.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ReviewsDetails {

    @JsonProperty("id")
    private Integer id;

    @JsonProperty("author_name")
    private String authorName;

    @JsonProperty("header")
    private String header;

    @JsonProperty("rate")
    private Integer rate;

    @JsonProperty("tags_header")
    private String tagsHeader;

    @JsonProperty("rate_name")
    private String rateName;

    @JsonProperty("rate_img")
    private String rateImg;

    @JsonProperty("date_created")
    private String dateCreated;

    @JsonProperty("message")
    private String message;

    @JsonProperty("emoji")
    private String emoji;

    @JsonProperty("tags")
    private String[] tags;

    @JsonProperty("footer_text")
    private String footerText;

    @JsonProperty("button")
    private String button;

}
