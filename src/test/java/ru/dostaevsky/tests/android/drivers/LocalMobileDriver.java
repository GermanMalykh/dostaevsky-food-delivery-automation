package ru.dostaevsky.tests.android.drivers;

import com.codeborne.selenide.WebDriverProvider;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import org.aeonbits.owner.ConfigFactory;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import ru.dostaevsky.tests.android.config.EnvConfig;

import javax.annotation.Nonnull;
import java.io.File;

import java.net.MalformedURLException;
import java.net.URL;

import static io.appium.java_client.remote.AutomationName.ANDROID_UIAUTOMATOR2;
import static io.appium.java_client.remote.MobilePlatform.ANDROID;

public class LocalMobileDriver implements WebDriverProvider {
    static EnvConfig env = ConfigFactory.create(EnvConfig.class, System.getProperties());

    public static URL getAppiumServerUrl() {
        try {
            return new URL(env.appiumUrl());
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    //TODO: Перед прогоном тестов, необходимо распаковать  zip архив
    public String getAppPath() {
        String appPath = "src/test/resources/apps/ru.dostaevsky.android.apk";
        File app = new File(appPath);
        return app.getAbsolutePath();
    }

    @Nonnull
    @Override
    public WebDriver createDriver(@Nonnull Capabilities capabilities) {
        UiAutomator2Options options = new UiAutomator2Options();
        options.merge(capabilities);

        options.setAutomationName(ANDROID_UIAUTOMATOR2)
                .setPlatformName(ANDROID)
                .setDeviceName(env.device())
                .setPlatformVersion(env.os_version())
                .setApp(getAppPath())
                .setAppPackage(env.appPackage())
                .setAppActivity(env.appActivity());
        return new AndroidDriver(getAppiumServerUrl(), options);
    }

}
