package ru.dostaevsky.tests.android.pages.components;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.CollectionCondition.sizeGreaterThan;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static io.appium.java_client.AppiumBy.id;
import static java.time.Duration.*;

public class SearchComponents {
    private static final ElementsCollection
            searchResults = $$(id("ru.dostaevsky.android:id/rootProductView"));
    private static final SelenideElement
            searchField = $(id("ru.dostaevsky.android:id/search_button")),
            searchInputField = $(id("ru.dostaevsky.android:id/search_src_text"));

    @Step("Переходим к поиску")
    public SearchComponents navigateToSearch() {
        searchField
                .shouldBe(visible, ofSeconds(10))
                .click();
        return this;
    }

    @Step("Указываем параметры поиска")
    public SearchComponents inputSearchText(String product) {
        searchInputField.sendKeys(product);
        return this;
    }

    @Step("Проверяем, что результаты поиска не меньше чем \"{size}\"")
    public SearchComponents inputSearchText(Integer size) {
        searchResults.shouldHave(sizeGreaterThan(size));
        return this;
    }

}
