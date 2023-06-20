package ru.dostaevsky.tests.api.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class BasketInfoItemsDetails {

    @JsonProperty("id")
    private int id;

    @JsonProperty("elem_id")
    private int elemId;

    @JsonProperty("share_id")
    private int shareId;

    @JsonProperty("price")
    private String price;

    @JsonProperty("price_wo_sale")
    private String priceWoSale;

    @JsonProperty("quantity")
    private int quantity;

    @JsonProperty("title")
    private String title;

    @JsonProperty("weight")
    private int weight;

    @JsonProperty("callorical")
    private int callorical;

    @JsonProperty("uid")
    private String uid;

    @JsonProperty("category")
    private String category;

    @JsonProperty("available_toppings")
    private String[] availableToppings;

    @JsonProperty("available_ingredients")
    private String[] availableIngredients;

    @JsonProperty("selected_toppings")
    private String[] selectedToppings;

    @JsonProperty("excluded_ingredients")
    private String[] excludedIngredients;

    @JsonProperty("is_can_buy")
    private boolean isCanBuy;

    @JsonProperty("is_gift")
    private boolean isGift;

    @JsonProperty("image")
    private String image;

    @JsonProperty("coupon_code")
    private String couponCode;

    @JsonProperty("dost_external_item_no")
    private String dostExternalItemNo;

    @JsonProperty("share_name")
    private String shareName;
}
