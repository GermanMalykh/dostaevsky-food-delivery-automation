package ru.dostaevsky.tests.android.config;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import ru.dostaevsky.tests.android.drivers.LocalMobileDriver;
import ru.dostaevsky.helpers.Attach;
import ru.dostaevsky.tests.android.drivers.RemoteMobileDriver;

import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Selenide.sessionId;
import static com.codeborne.selenide.WebDriverRunner.closeWebDriver;

public class PreRunConfig {

    static String env = System.getProperty("env", "remote");

    @BeforeAll
    public static void setup() throws Exception {
        switch (env) {
            case "remote":
                Configuration.browser = RemoteMobileDriver.class.getName();
                break;
            case "local":
                Configuration.browser = LocalMobileDriver.class.getName();
                break;
            default:
                throw new Exception("Unrecognised env");
        }
        Configuration.browserSize = null;
    }

    @BeforeEach
    void addListener() {
        SelenideLogger.addListener("AllureSelenide", new AllureSelenide());
        open();
    }

    @AfterEach
    void addAttachments() {
        String sessionId = sessionId().toString();
        if (env.equals("local")) {
            Attach.screenshotAs("Last screenshot");
        }
        Attach.pageSource();
        closeWebDriver();
        if (env.equals("remote")) {
            Attach.getVideoBrowserstack(sessionId);
        }
    }
}
