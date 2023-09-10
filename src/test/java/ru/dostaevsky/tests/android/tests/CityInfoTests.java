package ru.dostaevsky.tests.android.tests;

import io.qameta.allure.Severity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import ru.dostaevsky.tests.android.config.PreRunConfig;
import ru.dostaevsky.tests.android.pages.AdditionalInfoPage;
import ru.dostaevsky.tests.android.pages.MainPage;

import static io.qameta.allure.SeverityLevel.*;
import static ru.dostaevsky.data.MenuItemsData.ADDITIONAL_INFO;

@Tag("android")
@DisplayName("Android Tests")
public class CityInfoTests extends PreRunConfig {
    MainPage main = new MainPage();
    AdditionalInfoPage infoPage = new AdditionalInfoPage();

    @Severity(CRITICAL)
    @DisplayName("[Android] Выбор и отображение информации города. ")
    @ParameterizedTest(name = "Для города \"{0}\" отображается информация с контактным номером телефона \"{1}\"")
    @CsvFileSource(resources = "/csv/cityMobileInfo.csv")
    void selectingCityFromTheListAvailableAndCheckingDisplayInfoTest(String city, String phoneWithPlus) {
        main.selectByText(city).closingTechInfo();
        main.selectByText(ADDITIONAL_INFO);
        infoPage.checkCityName(city)
                .checkCityPhoneNumber(phoneWithPlus);
    }
}
