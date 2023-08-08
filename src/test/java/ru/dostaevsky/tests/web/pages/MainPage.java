package ru.dostaevsky.tests.web.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverConditions;
import com.codeborne.selenide.WebDriverRunner;
import io.qameta.allure.Step;
import org.openqa.selenium.Cookie;
import ru.dostaevsky.data.AttributeData;
import ru.dostaevsky.data.AuthData;
import ru.dostaevsky.enums.Category;
import ru.dostaevsky.enums.CityLink;

public class MainPage {
    private final ElementsCollection navigationMenu = Selenide.$$(".main-nav__link");
    private final SelenideElement cityList = Selenide.$(".main-nav__city");
    private final SelenideElement confirmCityMessage = Selenide.$(".city-confirm");

    @Step("Переходим на главную страницу")
    public MainPage openMainPage() {
        Selenide.open(CityLink.SPB_LINK.getValue());
        return this;
    }

    @Step("Выбираем город")
    public MainPage selectCityFromList(String link, String city) {
        cityList.hover()
                .$("a[href*='" + link + "']")
                .shouldHave(Condition.text(city)).click();
        return this;
    }

    @Step("Проверяем, что для выбранного города отображается его ссылка")
    public MainPage checkCityUrl(String link) {
        Selenide.webdriver().shouldHave(WebDriverConditions.url(link));
        return this;
    }

    @Step("Проверяем, что у выбранного города отображается аттрибут выбора")
    public MainPage checkSelectedAttributeInCity(String link) {
        Selenide.$("a[href*='" + link + "']")
                .shouldHave(Condition.attribute(AttributeData.ATTRIBUTE_CITY, AttributeData.ATTRIBUTE_CITY_VALUE));
        return this;
    }

    @Step("Переходим в выбранную в меню категорию")
    public MainPage navigateToCategory(Category category) {
        navigationMenu
                .findBy(Condition.text(category.getValue())).click();
        return this;
    }

    @Step("Удаляем информационную панель со страницы")
    public MainPage removeInfoFromPage() {
        Selenide.executeJavaScript("$('.info').remove()");
        return this;
    }

    @Step("Скрываем сообщение подтверждения города")
    public MainPage hideConfirmCityMessage() {
        Selenide.executeJavaScript("arguments[0].setAttribute('hidden', 'true')", confirmCityMessage);
        return this;
    }

    @Step("Добавляем куку пользователя")
    public MainPage addingUserCookie() {
        Selenide.open(AuthData.SPB_URL_FOR_ADDING_COOKIE);
        WebDriverRunner.getWebDriver().manage().addCookie(new Cookie("user_basket_sessid", AuthData.WEB_SPB_UNREGISTERED_USER_COOKIE));
        return this;
    }

    @Step("Переходим на \"{url}\"")
    public MainPage openDesiredPage(String url) {
        Selenide.open(url);
        return this;
    }

}
