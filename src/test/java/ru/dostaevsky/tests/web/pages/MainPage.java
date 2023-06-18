package ru.dostaevsky.tests.web.pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.openqa.selenium.Cookie;
import ru.dostaevsky.enums.Categories;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverConditions.url;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static ru.dostaevsky.data.AttributeData.*;
import static ru.dostaevsky.data.CookieData.WEB_SPB_UNREGISTERED_USER_COOKIE;
import static ru.dostaevsky.data.CookieData.SPB_URL_FOR_ADDING_COOKIE;
import static ru.dostaevsky.enums.CityLinks.*;

public class MainPage {
    private final static ElementsCollection navigationMenu = $$(".main-nav__link");
    private final static SelenideElement
            cityList = $(".main-nav__city"),
            confirmCityMessage = $(".city-confirm");

    @Step("Переходим на главную страницу")
    public MainPage openMainPage() {
        open(SPB_LINK.getValue());
        return this;
    }

    @Step("Выбираем город")
    public MainPage selectCityFromList(String link, String city) {
        cityList.hover()
                .$("a[href*='" + link + "']")
                .shouldHave(text(city)).click();
        return this;
    }

    @Step("Проверяем, что для выбранного города отображается его ссылка")
    public MainPage checkCityUrl(String link) {
        webdriver().shouldHave(url(link));
        return this;
    }

    @Step("Проверяем, что у выбранного города отображается аттрибут выбора")
    public MainPage checkSelectedAttributeInCity(String link) {
        $("a[href*='" + link + "']")
                .shouldHave(attribute(ATTRIBUTE_CITY, ATTRIBUTE_CITY_VALUE));
        return this;
    }

    @Step("Переходим в выбранную в меню категорию")
    public MainPage navigateToCategory(Categories category) {
        navigationMenu
                .findBy(text(category.getValue())).click();
        return this;
    }

    @Step("Удаляем информационную панель со страницы")
    public MainPage removeInfoFromPage() {
        executeJavaScript("$('.info').remove()");
        return this;
    }

    @Step("Скрываем сообщение подтверждения города")
    public MainPage hideConfirmCityMessage() {
        executeJavaScript("arguments[0].setAttribute('hidden', 'true')", confirmCityMessage);
        return this;
    }

    @Step("Добавляем куку пользователя")
    public MainPage addingUserCookie() {
        open(SPB_URL_FOR_ADDING_COOKIE);
        getWebDriver().manage().addCookie(new Cookie("user_basket_sessid", WEB_SPB_UNREGISTERED_USER_COOKIE));
        return this;
    }

    @Step("Переходим на \"{url}\"")
    public MainPage openDesiredPage(String url) {
        open(url);
        return this;
    }

}
