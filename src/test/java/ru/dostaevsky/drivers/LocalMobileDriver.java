package ru.dostaevsky.drivers;

//import browserstack.sample.config.DeviceConfig;
//import browserstack.sample.config.UserConfig;
//import browserstack.sample.helpers.WikiApkDownloader;

import com.codeborne.selenide.WebDriverProvider;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
//import org.aeonbits.owner.ConfigFactory;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;

import javax.annotation.Nonnull;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import static io.appium.java_client.remote.AutomationName.ANDROID_UIAUTOMATOR2;
import static io.appium.java_client.remote.MobilePlatform.ANDROID;
import static org.apache.commons.io.FileUtils.copyInputStreamToFile;

public class LocalMobileDriver implements WebDriverProvider {

//    WikiApkDownloader wikiApkDownloader = new WikiApkDownloader();
//    static DeviceConfig deviceConfig = ConfigFactory.create(DeviceConfig.class, System.getProperties());
//    static UserConfig userConfig = ConfigFactory.create(UserConfig.class, System.getProperties());

    public static URL getAppiumServerUrl() {
        try {
            return new URL("http://localhost:4723/wd/hub");
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

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
                .setDeviceName("Pixel 4 API 31")
                .setPlatformVersion("12.0")
                .setApp(getAppPath())
                .setAppPackage("ru.dostaevsky.android")
                .setAppActivity("ru.dostaevsky.android.ui.deeplink.DeepLinkActivity");

        return new AndroidDriver(getAppiumServerUrl(), options);
    }

}
