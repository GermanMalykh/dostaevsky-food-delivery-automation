package ru.dostaevsky.tests.web.config;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.aeonbits.owner.ConfigFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.remote.DesiredCapabilities;
import ru.dostaevsky.helpers.Attach;

import java.util.Map;

public class PreRunConfig {
    private static final String env = System.getProperty("env", "remote");

    private static final EnvConfig config = ConfigFactory.create(EnvConfig.class, System.getProperties());

    @BeforeAll
    public static void setup() {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        if (env.equals("remote")) {
            capabilities.setCapability("selenoid:options", Map.<String, Object>of(
                    "enableVNC", true,
                    "enableVideo", true
            ));
            Configuration.remote = config.selenoid_url() + "/wd/hub";
        }
        Configuration.browser = config.browser_name();
        Configuration.browserVersion = config.browser_version();
        Configuration.browserSize = config.browser_size();
        Configuration.pageLoadTimeout = 60000;
        Configuration.pageLoadStrategy = "eager";

        Configuration.browserCapabilities = capabilities;
    }

    @BeforeEach
    void addListener() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @AfterEach
    void addAttachments() {
        Attach.screenshotAs("Last screenshot");
        Attach.pageSource();
        Attach.browserConsoleLogs();
        if (env.equals("remote")) {
            Attach.addVideo();
        }
        Selenide.clearBrowserCookies();
        Selenide.clearBrowserLocalStorage();
    }

}
