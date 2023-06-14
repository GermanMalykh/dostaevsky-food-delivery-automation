package ru.dostaevsky.tests.web;

import io.qameta.allure.Severity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import ru.dostaevsky.tests.web.config.TestBaseWeb;
import ru.dostaevsky.tests.web.pages.MainPage;
import ru.dostaevsky.tests.web.pages.components.HeaderComponents;

import static io.qameta.allure.SeverityLevel.CRITICAL;

@Tag("web")
@DisplayName("Web Tests")
public class CityInfoTests extends TestBaseWeb {
    MainPage mainPage = new MainPage();
    HeaderComponents headerComponents = new HeaderComponents();

    @Severity(CRITICAL)
    @DisplayName("Выбор и отображение информации города. ")
    @ParameterizedTest(name = "Для города \"{1}\" отображается информация по ссылке \"{0}\" с контактным номером телефона \"{2}\"")
    @CsvFileSource(resources = "/csv/cityWebInfo.csv")
    void selectingCityFromTheListAvailableAndCheckingDisplayInfoTest(String link, String city, String phone) {
        mainPage.openMainPage()
                .hideConfirmCityMessage()
                .selectCityFromList(link, city)
                .checkCityUrl(link)
                .hideConfirmCityMessage()
                .checkSelectedAttributeInCity(link);
        headerComponents.checkCityPhoneNumberOnPage(phone);
    }

}
