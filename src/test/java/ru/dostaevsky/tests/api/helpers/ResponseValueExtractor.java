package ru.dostaevsky.tests.api.helpers;

import io.qameta.allure.Description;
import org.json.JSONArray;
import org.json.JSONObject;

public class ResponseValueExtractor {

    @Description("Извлекаем идентификатор продукции из ответа после добавления продукции в корзину")
    public String getItemUidAfterAddToBasket(String response) {
        JSONObject json_data = new JSONObject(response);
        JSONArray items = json_data.getJSONObject("cartStatus").getJSONArray("items");
        return items.getJSONObject(0).getString("uid");
    }

    @Description("Извлекаем идентификатор продукции из ответа после получения информации о продукции в корзине")
    public String getItemUidAfterGettingBasketInfo(String response) {
        JSONObject json_data = new JSONObject(response);
        JSONArray items = json_data.getJSONArray("items");
        return items.getJSONObject(0).getString("uid");
    }

}

