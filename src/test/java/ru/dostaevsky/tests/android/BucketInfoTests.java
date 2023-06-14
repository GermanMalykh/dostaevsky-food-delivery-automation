package ru.dostaevsky.tests.android;

import io.qameta.allure.Severity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import ru.dostaevsky.tests.android.config.TestBaseMobile;
import ru.dostaevsky.tests.android.pages.BucketPage;
import ru.dostaevsky.tests.android.pages.MainPage;
import ru.dostaevsky.tests.android.pages.components.ItemComponents;
import ru.dostaevsky.tests.android.pages.components.NavigationComponents;

import static io.qameta.allure.SeverityLevel.*;
import static ru.dostaevsky.enums.CityName.SPB;

@Tag("android")
@DisplayName("Android Tests")
public class BucketInfoTests extends TestBaseMobile {
    BucketPage bucket = new BucketPage();
    MainPage main = new MainPage();
    NavigationComponents navigation = new NavigationComponents();
    ItemComponents item = new ItemComponents();
//TODO: Вынести текстовые значения в переменные
    @Severity(MINOR)
    @DisplayName("Отображение информации в пустой корзине")
    @Test
    void checkInfoInEmptyCart() {
        main.selectByText(SPB.getDisplayName());
        navigation.backNavigation();
        main.selectByText("Корзина");
        bucket.checkBucketEmptyInfo()
                .checkBucketEmptyImage();
    }

    @Severity(NORMAL)
    @DisplayName("Отображение информации о минимальной сумме заказа")
    @Test
    void checkMinimalPriceToDeliveryInfo() {
        main.selectByText(SPB.getDisplayName());
        navigation.backNavigation();
        main.selectByText("Фастфуд");
        item.addProductToBucket();
        main.selectByText("Корзина");
        bucket.checkMinimalPriceTitle();
    }

    @Severity(BLOCKER)
    @DisplayName("Добавление позиции в корзину и проверка отображения цены, количества и наименования товара")
    @Test
    void addItemToCartAndCheckValueTest() {
        main.selectByText(SPB.getDisplayName());
        navigation.backNavigation();
        main.selectByText("Новинки");
        item.addProductToBucket()
                .addMoreProductsToBucket();

        int price = item.getProductPrice();
        String name = item.getProductName();
        int count = item.getProductCount();

        item.checkTotalItemsInBucketNotification(String.valueOf(count));
        main.selectByText("Корзина");
        bucket.checkItemNameInTheBucket(name)
                .checkItemPriceInTheBucket(price, count)
                .checkItemCountInTheBucket(String.valueOf(count));
    }
}
