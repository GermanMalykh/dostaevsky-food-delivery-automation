package ru.dostaevsky.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Bowl {
    SALMON_BOWL("Боул с лососем", 469, 499, 499, 499),
    VEGGIE_BOWL("Боул вегетарианский", 379, 399, 389, 369),
    BEEF_BOWL("Боул с говядиной", 429, 449, 465, 439),
    TUNA_BOWL("Боул с тунцом", 449, 489, 499, 469),
    SHAWARMA_BOWL("Шаурма-боул", 379, 359, 329, 409);

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
