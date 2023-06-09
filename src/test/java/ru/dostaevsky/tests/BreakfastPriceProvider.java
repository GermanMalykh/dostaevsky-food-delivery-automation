package ru.dostaevsky.tests;

import org.junit.jupiter.params.provider.Arguments;
import ru.dostaevsky.enums.Breakfast;
import ru.dostaevsky.enums.CityLinks;
import ru.dostaevsky.enums.CityName;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

@SuppressWarnings("unused")
public class BreakfastPriceProvider {
    public static Stream<Arguments> provide() {
        return Stream.of(
                Arguments.of(CityName.SPB, CityLinks.SPB_LINK, getPricesMap(CityName.SPB)),
                Arguments.of(CityName.MSK, CityLinks.MSK_LINK, getPricesMap(CityName.MSK)),
                Arguments.of(CityName.SOCHI, CityLinks.SOCHI_LINK, getPricesMap(CityName.SOCHI)),
                Arguments.of(CityName.KRD, CityLinks.KRD_LINK, getPricesMap(CityName.KRD)),
                Arguments.of(CityName.NSK, CityLinks.NSK_LINK, getPricesMap(CityName.NSK))
        );
    }

    private static Map<String, Integer> getPricesMap(CityName cityName) {
        Map<String, Integer> prices = new HashMap<>();
        for (Breakfast breakfast : Breakfast.values()) {
            prices.put(breakfast.getName(), breakfast.getPrice(cityName));
        }
        return prices;
    }
}
