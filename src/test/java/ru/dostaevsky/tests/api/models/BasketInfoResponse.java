package ru.dostaevsky.tests.api.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class BasketInfoResponse {

    @JsonProperty("price")
    private int price;

    @JsonProperty("price_wo_sale")
    private int priceWoSale;

    @JsonProperty("items")
    private BasketInfoItemsDetails[] items;

    @JsonProperty("shares")
    private BasketInfoSharesDetails[] shares;

    @JsonProperty("is_order_possible")
    private boolean isOrderPossible;

    @JsonProperty("provide_sauces")
    private boolean provideSauces;

    @JsonProperty("provide_cutlery")
    private boolean provideCutlery;

    @JsonProperty("min_cart_price")
    private int minCartPrice;

    @JsonProperty("status")
    private String status;

}
