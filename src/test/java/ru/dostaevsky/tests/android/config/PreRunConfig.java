package ru.dostaevsky.tests.android.config;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import ru.dostaevsky.tests.android.drivers.LocalMobileDriver;
import ru.dostaevsky.helpers.Attach;
import ru.dostaevsky.tests.android.drivers.RemoteMobileDriver;

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
        Selenide.open();
    }

    @AfterEach
    void addAttachments() {
        String sessionId = Selenide.sessionId().toString();
        if (env.equals("local")) {
            Attach.screenshotAs("Last screenshot");
        }
        Attach.pageSource();
        WebDriverRunner.closeWebDriver();
        if (env.equals("remote")) {
            Attach.getVideoBrowserstack(sessionId);
            Attach.browserstackFullInfoLink(sessionId);
        }
    }
}
