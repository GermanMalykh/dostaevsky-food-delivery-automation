package ru.dostaevsky.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Breakfast {
    MANGO_OATMEAL_COCONUT_MILK("Овсяная каша с манго на кокосовом молоке", 259, 279),
    BLUEBERRY_OATMEAL_COCONUT_MILK("Овсяная каша с черникой на кокосовом молоке", 259, 279),
    CAESAR_SANDWICH("Цезарь Сэндвич", 249, 249),
    PEPPERONI_SANDWICH("Пепперони Сэндвич", 249, 249),
    HAM_CHEESE_SANDWICH("Ветчина-Сыр Сэндвич", 239, 249),
    STRAWBERRY_PANCAKES("Блинчики сладкие с клубникой", 389, 409),
    CHICKEN_PANCAKES("Блинчики сытные с курицей", 499, 509),
    POTATO_PANCAKES("Драники", 319, 289),
    SYRNIKI_CARAMEL_SYRUP("Сырники с карамельным сиропом", 339, 359),
    SYRNIKI_CONDENSED_MILK("Сырники со сгущённым молоком", 339, 359),
    HEARTY_BREAKFAST_SAUSAGES_TOMATOES("Сытный завтрак с колбасками и томатами", 419, 479);

    private final String name;
    private final int spbPrice;
    private final int mskPrice;

    public int getPrice(CityName cityName) {
        switch (cityName) {
            case SPB:
                return spbPrice;
            case MSK:
                return mskPrice;
            default:
                throw new IllegalArgumentException("Unknown city name");
        }
    }
}