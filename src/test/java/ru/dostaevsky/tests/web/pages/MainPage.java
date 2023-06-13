package ru.dostaevsky.tests.web.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverConditions.url;
import static org.assertj.core.api.Assertions.assertThat;

public class MainPage {

    private final static String
            MAIN_URL = "https://dostaevsky.ru/";

    @Step("Переходим на главную страницу")
    public MainPage openMainPage() {
        open(MAIN_URL);
        executeJavaScript("arguments[0].setAttribute('hidden', 'true')", $(".city-confirm"));
        return this;
    }

    @Step("Выбираем город")
    public MainPage selectCityFromList(String link, String city) {
        $(".main-nav__city").hover()
                .$("a[href*='" + link + "']")
                .shouldHave(Condition.text(city)).click();
        return this;
    }

    @Step("Проверяем, что для выбранного города отображается его ссылка")
    public MainPage checkCityUrl(String link) {
        webdriver().shouldHave(url(link));
        return this;
    }

    @Step("Проверяем, что наименование выбранного города отображается на странице")
    public MainPage checkCityNameOnPage(String link) {
        executeJavaScript("arguments[0].setAttribute('hidden', 'true')", $(".city-confirm"));
        $("a[href*='" + link + "']").shouldHave(Condition.attribute("data-city", "selected"));
        return this;
    }

    @Step("Проверяем, что для выбранного города отображается правильный номер для связи")
    public MainPage checkCityPhoneOnPage(String phone) {
        $(".header__phone").shouldHave(Condition.text(phone));
        return this;
    }

    // TODO: Добавить словарь по категориям
    @Step("Переходим в выбранную в меню категорию")
    public MainPage navigateTo(String category) {
        $$(".main-nav__link").findBy(Condition.text(category)).click();
        return this;
    }

    @Step("Удаляем информационную панель со страницы")
    public MainPage removeInfoFromPage() {
        executeJavaScript("$('.info').remove()");
        return this;
    }

}
