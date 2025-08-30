package ru.dostaevsky.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Bowl {

    SHAWARMA_BOWL("Шаурма-боул", 469, 479, 459, 519),
    SHAWARMA_BOWL_X2("Шаурма-боул Х2", 909, 899, 879, 949),
    CHICKEN_BOWL("Боул с курочкой", 529, 599, 499, 489),
    CHICKEN_BOWL_X2("Боул с курочкой Х2", 1009, 1099, 959, 949),
    SALMON_BOWL("Боул с лососем", 659, 679, 629, 649),
    SALMON_BOWL_X2("Боул с лососем Х2", 1259, 1259, 1199, 1199),
    SHRIMP_BOWL("Боул с креветками", 569, 619, 559, 569),
    SHRIMP_BOWL_X2("Боул с креветками Х2", 1009, 1099, 1079, 1049),
    VEGGIE_BOWL("Боул вегетарианский", 359, 549, 369, 459),
    TUNA_BOWL("Боул с тунцом", 569, 659, 529, 549);

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