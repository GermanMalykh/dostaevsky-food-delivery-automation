package ru.dostaevsky.tests.android.helpers;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.aeonbits.owner.ConfigFactory;
import ru.dostaevsky.helpers.CustomAllureListener;
import ru.dostaevsky.tests.android.config.EnvConfig;

public class BrowserstackGetter {
    static EnvConfig env = ConfigFactory.create(EnvConfig.class, System.getProperties());

    public static String videoUrl(String sessionId) {
        return getSessionInfo(sessionId)
                .path("automation_session.video_url");
    }

    public static String fullInfoPublicUrl(String sessionId) {
        return getSessionInfo(sessionId)
                .path("automation_session.public_url");
    }

    public static ExtractableResponse<Response> getSessionInfo(String sessionId) {
        String url = String.format("https://api.browserstack.com/app-automate/sessions/%s.json", sessionId);

        return RestAssured.given()
                .log().all()
                .filter(CustomAllureListener.withCustomTemplates())
                .auth().basic(env.user(), env.key())
                .when()
                .get(url)
                .then()
                .statusCode(200)
                .extract();
    }
}
