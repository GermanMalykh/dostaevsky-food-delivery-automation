package ru.dostaevsky.tests.api.config;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;

public class TestBaseApi {

    @BeforeAll
    public static void setUp() {
        RestAssured.baseURI = "https://dostaevsky.ru";
    }
}
