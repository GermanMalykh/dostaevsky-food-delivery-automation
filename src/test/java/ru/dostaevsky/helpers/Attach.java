package ru.dostaevsky.helpers;

import com.codeborne.selenide.Selenide;
import io.qameta.allure.Attachment;
import org.aeonbits.owner.ConfigFactory;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.remote.RemoteWebDriver;
import ru.dostaevsky.tests.android.helpers.BrowserstackGetter;
import ru.dostaevsky.tests.web.config.EnvConfig;

import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static org.openqa.selenium.logging.LogType.BROWSER;

public class Attach {

    private static final EnvConfig config = ConfigFactory.create(EnvConfig.class, System.getProperties());

    @Attachment(value = "{attachName}", type = "image/png")
    public static byte[] screenshotAs(String attachName) {
        return ((TakesScreenshot) getWebDriver()).getScreenshotAs(OutputType.BYTES);
    }

    @Attachment(value = "Page source", type = "text/plain")
    public static byte[] pageSource() {
        return getWebDriver().getPageSource().getBytes(StandardCharsets.UTF_8);
    }

    @Attachment(value = "{attachName}", type = "text/plain")
    public static String attachAsText(String attachName, String message) {
        return message;
    }

    public static void browserConsoleLogs() {
        attachAsText(
                "Browser console logs",
                String.join("\n", Selenide.getWebDriverLogs(BROWSER))
        );
    }

    @Attachment(value = "Selenoid Video", type = "text/html", fileExtension = ".html")
    public static String addVideo() {
        return "<html><body><video width='100%' height='100%' controls autoplay><source src='"
                + getVideoUrl()
                + "' type='video/mp4'></video></body></html>";
    }

    @Attachment(value = "BrowserStack Video", type = "text/html", fileExtension = ".html")
    public static String getVideoBrowserstack(String sessionId) {
        return "<html><body><video width='100%' height='100%' controls autoplay><source src='"
                + BrowserstackGetter.videoUrl(sessionId)
                + "' type='video/mp4'></video></body></html>";
    }

    @Attachment(value = "Browserstack full info link", type = "text/html", fileExtension = ".html")
    public static String browserstackFullInfoLink(String sessionId) {
        return "<html><body><a href='"
                + BrowserstackGetter.fullInfoPublicUrl(sessionId)
                + "'>Full info link</a></body></html>";
    }

    public static URL getVideoUrl() {
        String videoUrl = config.selenoid_url() + "/video/" + getSessionId() + ".mp4";
        try {
            return new URL(videoUrl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getSessionId() {
        return ((RemoteWebDriver) getWebDriver()).getSessionId().toString();
    }
}