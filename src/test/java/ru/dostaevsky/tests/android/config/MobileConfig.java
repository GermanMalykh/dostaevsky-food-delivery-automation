package ru.dostaevsky.tests.android.config;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import ru.dostaevsky.drivers.LocalMobileDriver;
import ru.dostaevsky.helpers.Attach;

import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.WebDriverRunner.closeWebDriver;

public class MobileConfig {

    @BeforeAll
    public static void setup() throws Exception {
        Configuration.browser = LocalMobileDriver.class.getName();
        Configuration.browserSize = null;
    }

    @BeforeEach
    void addListener() {
        SelenideLogger.addListener("AllureSelenide", new AllureSelenide());
        open();
    }

    @AfterEach
    void afterEach() {
        Attach.screenshotAs("Last screenshot");
        closeWebDriver();
    }
}
