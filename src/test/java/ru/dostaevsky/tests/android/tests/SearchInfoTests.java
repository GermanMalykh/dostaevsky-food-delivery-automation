package ru.dostaevsky.tests.android.tests;

import io.qameta.allure.Severity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import ru.dostaevsky.tests.android.config.PreRunConfig;
import ru.dostaevsky.tests.android.pages.MainPage;
import ru.dostaevsky.tests.android.pages.components.SearchComponents;

import static io.qameta.allure.SeverityLevel.CRITICAL;

@Tag("android")
@DisplayName("Android Tests")
public class SearchInfoTests extends PreRunConfig {

    MainPage main = new MainPage();
    SearchComponents search = new SearchComponents();

    @Severity(CRITICAL)
    @DisplayName("Проверка результатов поиска. ")
    @ParameterizedTest(name = "Для города \"{0}\", результаты поиска по слову \"{1}\" не равны нулю")
    @CsvSource({"Санкт-Петербург, пицца", "Москва, пицца", "Сочи, пицца", "Краснодар, пицца", "Новосибирск, пицца"})
    void successfulProductSearchTest(String city, String product) {
        main.selectByText(city)
                .closingTechInfo();
        search.navigateToSearch()
                .inputSearchText(product)
                .inputSearchText(0);
    }
}
