package ru.dostaevsky.tests.api.constants;

public class ResponseData {

    public final static String ERROR_RESPONSE = "    \"status\": \"error\",\n" +
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
            "        \"minAmountCheck\": false,\n" +
            "        \"minAmountSumm\": 500,\n" +
            "        \"max_amount_check\": false";

    public final static String SUCCESS_RESPONSE_WITH_ITEM = "{\n" +
            "    \"status\": \"success\",\n" +
            "    \"is_order_possible\": true,\n" +
            "    \"order_possible_text\": \"\",\n" +
            "    \"content\": \"\",\n" +
            "    \"cartStatus\": {\n" +
            "        \"price\": \"379\",\n" +
            "        \"priceDiscount\": \"379\",\n" +
            "        \"num\": 1,\n" +
            "        \"points\": 0,\n" +
            "        \"items\": [\n" +
            "            {\n" +
            "                \"id\": \"13460\",\n";

    public final static String SUCCESS_RESPONSE_WITHOUT_ITEM = "    \"status\": \"success\",\n" +
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
            "        \"minAmountCheck\": false,\n" +
            "        \"minAmountSumm\": 500,\n" +
            "        \"max_amount_check\": false";
}
