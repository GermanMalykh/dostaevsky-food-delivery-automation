package ru.dostaevsky.tests.android.pages.components;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import io.appium.java_client.AppiumBy;
import io.qameta.allure.Step;

import java.time.Duration;

public class SearchComponents {
    private final ElementsCollection searchResults = Selenide.$$(AppiumBy.id("ru.dostaevsky.android:id/rootProductView"));
    private final SelenideElement searchField = Selenide.$(AppiumBy.id("ru.dostaevsky.android:id/search_button"));
    private final SelenideElement searchInputField = Selenide.$(AppiumBy.id("ru.dostaevsky.android:id/search_src_text"));

    @Step("Переходим к поиску")
    public SearchComponents navigateToSearch() {
        searchField.shouldBe(Condition.visible, Duration.ofSeconds(10)).click();
        return this;
    }

    @Step("Указываем параметры поиска")
    public SearchComponents inputSearchText(String product) {
        searchInputField.sendKeys(product);
        return this;
    }

    @Step("Проверяем, что результаты поиска не меньше чем \"{size}\"")
    public void inputSearchText(Integer size) {
        searchResults.shouldHave(CollectionCondition.sizeGreaterThan(size));
    }

}
