package ru.dostaevsky.tests.api.constants;

public class Endpoints {

    /**
     * Basket Endpoints.
     */
    public static final String BASKET_INFO = "/basket/info";
    public static final String ADD_ITEM_TO_BASKET = "/ajax/basket/add_to_basket?item_id=%s";
    public static final String REMOVE_ITEM_FROM_BASKET = "/ajax/basket/basket_item_handler?action=removeItem&item_id=%s&item_uid=%s";
    public static final String UPDATE_ITEM_COUNT_IN_BASKET = "/basket/update-item-count";

    /**
     * Reviews Endpoint.
     */
    public static final String GETTING_REVIEWS = "/api/v4/reviews";

    /**
     * Auth Endpoint.
     */
    public static final String LOGIN = "/auth/login";

}
