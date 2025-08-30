package ru.dostaevsky.tests.android.drivers;

import com.codeborne.selenide.WebDriverProvider;
import org.aeonbits.owner.ConfigFactory;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import ru.dostaevsky.tests.android.config.EnvConfig;

import javax.annotation.Nonnull;
import java.net.MalformedURLException;
import java.net.URL;

public class RemoteMobileDriver implements WebDriverProvider {
    static EnvConfig env = ConfigFactory.create(EnvConfig.class, System.getProperties());

    @Nonnull
    @Override
    public WebDriver createDriver(@Nonnull Capabilities capabilities) {
        MutableCapabilities mutableCapabilities = new MutableCapabilities();
        mutableCapabilities.merge(capabilities);

        mutableCapabilities.setCapability("browserstack.user", env.user());
        mutableCapabilities.setCapability("browserstack.key", env.key());

        mutableCapabilities.setCapability("app", env.app());

        mutableCapabilities.setCapability("device", env.device());
        mutableCapabilities.setCapability("os_version", env.os_version());

        mutableCapabilities.setCapability("project", "Dostaevsky Food Delivery");
        mutableCapabilities.setCapability("buildName", "Dostaevsky | BuildDateAndTime: " + getCurrentDateTime());
        mutableCapabilities.setCapability("buildTag", "automated_tests");
        mutableCapabilities.setCapability("name", env.device() + "_" + env.os_version() + "_Dostaevsky_Test");

        try {
            return new RemoteWebDriver(
                    new URL(env.appiumUrl()), mutableCapabilities);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    private String getCurrentDateTime() {
        return java.time.LocalDateTime.now().format(java.time.format.DateTimeFormatter.ofPattern("dd.MM.yyyy_HH:mm:ss"));
    }
}
