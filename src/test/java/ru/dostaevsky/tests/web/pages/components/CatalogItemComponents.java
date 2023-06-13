package ru.dostaevsky.tests.web.pages.components;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static com.codeborne.selenide.Selenide.$$;
import static org.assertj.core.api.Assertions.assertThat;

public class CatalogItemComponents {
    private static final ElementsCollection items = $$(".catalog-list__item");
    private static final List<SelenideElement> itemsList = items;

    //    TODO: Вынести в класс с переменными
    private static final String
            attributeItemPrice = "data-price",
            attributeItemName = "data-name";

    @Step("Добавляем позицию в корзину")
    public CatalogItemComponents addItemToCart() {
        items.first().$("[type='button']").click();
        return this;
    }

    @Step("Извлекаем значение аттрибута \"{attributeName}\"")
    public String gettingDataFromAttribute(String attributeName) {
        return Objects.requireNonNull(
                items.first().getAttribute(attributeName)
        );
    }

    @Step("Проверяем наличие всех позиций и соответствие цен в выбранном городе")
    public CatalogItemComponents checkSize(Map<String, Integer> expectedPrices) {
        assertThat(items).hasSize(expectedPrices.size());
        return this;
    }

    @Step("Проверяем наличие всех позиций и соответствие цен в выбранном городе")
    public Map<String, Integer> getActualPrices() {
        Map<String, Integer> actualPrices = new HashMap<>();
        for (SelenideElement item : itemsList) {
            actualPrices.put(
                    item.getAttribute(attributeItemName),
                    Integer.valueOf(Objects.requireNonNull(item.getAttribute(attributeItemPrice)))
            );
        }
        return actualPrices;
    }

    @Step("Проверяем наличие всех позиций и соответствие цен в выбранном городе")
    public CatalogItemComponents assertPrices(Map<String, Integer> expectedPrices, Map<String, Integer> actualPrices) {
        assertThat(expectedPrices).containsAllEntriesOf(actualPrices);
        return this;
    }

}
