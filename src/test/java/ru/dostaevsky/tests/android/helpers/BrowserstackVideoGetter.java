package ru.dostaevsky.tests.android.helpers;

import ru.dostaevsky.helpers.CustomAllureListener;

import static io.restassured.RestAssured.given;
import static java.lang.String.format;

public class BrowserstackVideoGetter {

    private final static String
            user = "germanmalykh_eCmyoJ",
            key = "p5pfiQNqXsytd2TaqStP";

    public static String videoUrl(String sessionId) {
        String url = format("https://api.browserstack.com/app-automate/sessions/%s.json", sessionId);

        return given()
                .log().all()
                .filter(CustomAllureListener.withCustomTemplates())
                .auth().basic(user, key)
                .when()
                .get(url)
                .then()
                .statusCode(200)
                .extract().path("automation_session.video_url");
    }
}
