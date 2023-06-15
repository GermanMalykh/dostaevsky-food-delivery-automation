package ru.dostaevsky.tests.android;

import io.qameta.allure.Severity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import ru.dostaevsky.tests.android.config.TestBaseMobile;
import ru.dostaevsky.tests.android.pages.AdditionalInfoPage;
import ru.dostaevsky.tests.android.pages.MainPage;
import ru.dostaevsky.tests.android.pages.components.NavigationComponents;
import ru.dostaevsky.tests.android.pages.components.SearchComponents;

import static io.qameta.allure.SeverityLevel.*;
import static ru.dostaevsky.data.MenuItemsData.ADDITIONAL_INFO;

@Tag("android")
@DisplayName("Android Tests")
public class CityInfoTests extends TestBaseMobile {
    MainPage main = new MainPage();
    SearchComponents search = new SearchComponents();
    NavigationComponents navigation = new NavigationComponents();
    AdditionalInfoPage infoPage = new AdditionalInfoPage();

    @Severity(CRITICAL)
    @DisplayName("Проверка результатов поиска. ")
    @ParameterizedTest(name = "Для города \"{0}\", результаты поиска по слову \"{1}\" не равны нулю")
    @CsvSource({"Санкт-Петербург, пицца", "Москва, пицца", "Сочи, пицца", "Краснодар, пицца", "Новосибирск, пицца"})
    void successfulProductSearchTest(String city, String product) {
        main.selectByText(city);
        navigation.backNavigation();
        search.navigateToSearch()
                .inputSearchText(product)
                .inputSearchText(0);
    }

    @Severity(CRITICAL)
    @DisplayName("Выбор и отображение информации города. ")
    @ParameterizedTest(name = "Для города \"{0}\" отображается информация с контактным номером телефона \"{1}\"")
    @CsvFileSource(resources = "/csv/cityMobileInfo.csv")
    void selectingCityFromTheListAvailableAndCheckingDisplayInfoTest(String city, String phoneWithPlus) {
        main.selectByText(city);
        navigation.backNavigation();
        main.selectByText(ADDITIONAL_INFO);
        infoPage.checkCityName(city)
                .checkCityPhoneNumber(phoneWithPlus);
    }
}
