package ru.dostaevsky.tests.api.constants;

public class ResponseData {
    public final static String USER_NOT_FOUND_ERROR = "user_not_found";
    public final static String INVALID_DATA_ERROR = "The given data was invalid.";
    public final static String INVALID_REQUEST_ERROR = "Некорректный запрос, попробуйте изменить отправляемые данные.";
    public final static String FAILED_AUTHENTICATE_USER_MESSAGE = "Не удалось идентифицировать пользователя.";
    public final static String INVALID_PHONE_NUMBER_MESSAGE = "Некорректный номер телефона.";
    public final static String INVALID_ITEM_ID_MESSAGE = "The selected item id is invalid.";
    public final static String REQUIRED_SMS_FIELD_MESSAGE = "Поле смс кода подтверждения является обязательным.";
    public final static String REQUIRED_ITEM_ID_FIELD_MESSAGE = "The item id field is required.";
    public final static String REQUIRED_QUANTITY_FIELD_MESSAGE = "The quantity field is required.";
    public final static String NOT_EXIST_TOKEN_MESSAGE = "Токен не существует";
    public final static String EXCEEDING_LIMIT_MESSAGE = "The limit may not be greater than 100.";
    public final static String INVALID_LIMIT_FIELD_TYPE_MESSAGE = "The limit must be an integer.";
    public final static String ERROR_RESPONSE_BODY = "    \"status\": \"error\",\n" +
            "    \"is_order_possible\": true,\n" +
            "    \"order_possible_text\": \"\",\n" +
            "    \"content\": \"\",\n" +
            "    \"cartStatus\": {\n" +
            "        \"price\": \"0\",\n" +
            "        \"priceDiscount\": \"0\",\n" +
            "        \"num\": 0,\n" +
            "        \"points\": 0,\n" +
            "        \"items\": [\n" +
            "            \n" +
            "        ],\n" +
            "        \"deliveryPrice\": \"0\",\n" +
            "        \"minAmountCheck\": false,\n" +
            "        \"minAmountSumm\": 500,\n" +
            "        \"max_amount_check\": false";
    public final static String SUCCESS_RESPONSE_BODY_WITH_ITEM = "{\n" +
            "    \"status\": \"error\",\n" +
            "    \"is_order_possible\": true,\n" +
            "    \"order_possible_text\": \"\",\n" +
            "    \"content\": \"\",\n" +
            "    \"cartStatus\": {\n" +
            "        \"price\": \"439\",\n" +
            "        \"priceDiscount\": \"439\",\n" +
            "        \"num\": 1,\n" +
            "        \"points\": 0,\n" +
            "        \"items\": [\n" +
            "            {\n" +
            "                \"id\": \"13460\",\n";
    public final static String SUCCESS_RESPONSE_BODY_WITHOUT_ITEM = "    \"status\": \"success\",\n" +
            "    \"is_order_possible\": true,\n" +
            "    \"order_possible_text\": \"\",\n" +
            "    \"content\": \"\",\n" +
            "    \"cartStatus\": {\n" +
            "        \"price\": \"0\",\n" +
            "        \"priceDiscount\": \"0\",\n" +
            "        \"num\": 0,\n" +
            "        \"points\": 0,\n" +
            "        \"items\": [\n" +
            "            \n" +
            "        ],\n" +
            "        \"deliveryPrice\": \"0\",\n" +
            "        \"minAmountCheck\": false,\n" +
            "        \"minAmountSumm\": 500,\n" +
            "        \"max_amount_check\": false";
}
