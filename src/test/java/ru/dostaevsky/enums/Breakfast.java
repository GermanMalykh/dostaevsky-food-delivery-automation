package ru.dostaevsky.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Breakfast {
    SAUSAGES_SCRAMBLED_EGG("Скрэмбл с колбасками", 219, 239, 259, 225, 279),
    BACON_SCRAMBLED_EGG("Скрэмбл с беконом", 229, 249, 275, 269, 299),
    CHEESE_SCRAMBLED_EGG("Скрэмбл с сыром", 239, 259, 285, 269, 289),
    SYRNIKI("Сырники со сметаной и малиновым сиропом", 269, 299, 299, 299, 289),
    BANANA_SMOOTHIE("Смузи банан", 199, 219, 285, 259, 269),
    MANGO_SMOOTHIE("Смузи манго", 209, 229, 330, 259, 299),
    STRAWBERRY_SMOOTHIE("Смузи клубника", 209, 229, 299, 259, 279);

    private final String name;
    private final int spbPrice;
    private final int mskPrice;
    private final int sochiPrice;
    private final int krdPrice;
    private final int nskPrice;

    public int getPrice(CityName cityName) {
        switch (cityName) {
            case SPB:
                return spbPrice;
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
