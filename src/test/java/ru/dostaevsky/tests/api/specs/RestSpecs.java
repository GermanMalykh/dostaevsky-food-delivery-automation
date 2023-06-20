package ru.dostaevsky.tests.api.specs;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static io.restassured.RestAssured.with;
import static io.restassured.filter.log.LogDetail.ALL;
import static ru.dostaevsky.helpers.CustomAllureListener.withCustomTemplates;

public class RestSpecs {

    public static RequestSpecification requestSpecification = with()
            .filter(withCustomTemplates())
            .log().all();

    public static ResponseSpecification responseSpecification = new ResponseSpecBuilder()
            .log(ALL)
            .build();
}
