package ru.dostaevsky.tests.web.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Bowls {
    salmon_bowl("Боул с лососем", 469, 499, 499, 499),
    veggie_bowl("Боул вегетарианский", 379, 399, 389, 369),
    beef_bowl("Боул с говядиной", 429, 439, 465, 439),
    tuna_bowl("Боул с тунцом", 449, 489, 499, 469),
    shawarma_bowl("Шаурма-боул", 379, 349, 329, 409);

    private final String name;
    private final int mskPrice;
    private final int sochiPrice;
    private final int krdPrice;
    private final int nskPrice;

    public int getPrice(CityName cityName) {
        switch (cityName) {
            case MSK:
                return mskPrice;
            case SOCHI:
                return sochiPrice;
            case KRD:
                return krdPrice;
            case NSK:
                return nskPrice;
            default:
                throw new IllegalArgumentException("Unknown city name");
        }
    }
}