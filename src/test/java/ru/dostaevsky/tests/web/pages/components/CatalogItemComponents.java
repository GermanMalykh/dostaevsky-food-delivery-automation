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
import static ru.dostaevsky.data.AttributeData.*;

public class CatalogItemComponents {
    private static final ElementsCollection items = $$(".catalog-list__item");
    private static final List<SelenideElement> itemsList = items;

    @Step("Добавляем позицию в корзину")
    public CatalogItemComponents addItemToBucket() {
        items.first().$("[type='button']").click();
        return this;
    }

    @Step("Извлекаем значение аттрибута \"{attributeName}\"")
    public String getAttributeValue(String attributeName) {
        return Objects.requireNonNull(
                items.first().getAttribute(attributeName)
        );
    }

    @Step("Проверяем, что количество отображаемых на странице позиций равно количеству из словаря")
    public CatalogItemComponents checkSizeComponentsOnPage(Map<String, Integer> expectedPrices) {
        assertThat(items).hasSize(expectedPrices.size());
        return this;
    }

    @Step("Извлекаем и сохраняем значения наименования/цены позиций")
    public Map<String, Integer> getActualPrices() {
        Map<String, Integer> actualPrices = new HashMap<>();
        for (SelenideElement item : itemsList) {
            actualPrices.put(
                    item.getAttribute(ATTRIBUTE_ITEM_NAME),
                    Integer.valueOf(Objects.requireNonNull(item.getAttribute(ATTRIBUTE_ITEM_PRICE)))
            );
        }
        return actualPrices;
    }

    @Step("Проверяем, что актуальная цена на странице равна цене из словаря")
    public CatalogItemComponents assertPrices(Map<String, Integer> expectedPrices, Map<String, Integer> actualPrices) {
        assertThat(expectedPrices).containsAllEntriesOf(actualPrices);
        return this;
    }

}
