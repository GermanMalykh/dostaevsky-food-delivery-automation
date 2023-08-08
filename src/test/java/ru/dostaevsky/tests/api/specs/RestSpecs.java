package ru.dostaevsky.tests.api.specs;

import io.restassured.RestAssured;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import ru.dostaevsky.helpers.CustomAllureListener;

public class RestSpecs {

    public static RequestSpecification requestSpecification = RestAssured.with()
            .filter(CustomAllureListener.withCustomTemplates())
            .log().all();

    public static ResponseSpecification responseSpecification = new ResponseSpecBuilder()
            .log(LogDetail.ALL)
            .build();
}
