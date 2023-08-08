package ru.dostaevsky.tests.web.pages.components;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.assertj.core.api.Assertions;
import ru.dostaevsky.data.AttributeData;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class CatalogItemComponents {
    private static final ElementsCollection items = Selenide.$$(".catalog-list__item");
    private static final SelenideElement itemCount = Selenide.$(".counter-buttons__wrap[style*=flex]");
    private static final List<SelenideElement> itemsList = items;

    @Step("Добавляем позицию в корзину")
    public CatalogItemComponents addItemToCart() {
        items.first().$("[type='button']").click();
        return this;
    }

    @Step("Добавляем дополнительную позицию в корзину")
    public CatalogItemComponents addMoreProductsToCart() {
        itemCount.$(".counter-buttons__pl").click();
        Selenide.sleep(1000);
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
        Assertions.assertThat(items).hasSize(expectedPrices.size());
        return this;
    }

    @Step("Извлекаем и сохраняем значения наименования, цены позиций")
    public Map<String, Integer> getActualPrices() {
        Map<String, Integer> actualPrices = new HashMap<>();
        for (SelenideElement item : itemsList) {
            actualPrices.put(
                    item.getAttribute(AttributeData.ATTRIBUTE_ITEM_NAME),
                    Integer.valueOf(Objects.requireNonNull(item.getAttribute(AttributeData.ATTRIBUTE_ITEM_PRICE)))
            );
        }
        return actualPrices;
    }

    @Step("Проверяем, что актуальная цена на странице равна цене из словаря")
    public CatalogItemComponents assertPrices(Map<String, Integer> expectedPrices, Map<String, Integer> actualPrices) {
        Assertions.assertThat(expectedPrices).containsAllEntriesOf(actualPrices);
        return this;
    }

    @Step("Извлекаем значение количества добавленного в корзину товара")
    public String getItemCount() {
        return itemCount.$(".counter-buttons__count").getText();
    }

}
