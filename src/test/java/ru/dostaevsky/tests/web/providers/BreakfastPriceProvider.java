package ru.dostaevsky.tests.web.providers;

import org.junit.jupiter.params.provider.Arguments;
import ru.dostaevsky.tests.web.enums.Breakfast;
import ru.dostaevsky.tests.web.enums.CityLinks;
import ru.dostaevsky.tests.web.enums.CityName;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

@SuppressWarnings("unused")
public class BreakfastPriceProvider {
    public static Stream<Arguments> provide() {
        return Stream.of(
                Arguments.of(CityName.SPB, CityLinks.SPB_LINK, getPriceMapForCity(CityName.SPB)),
                Arguments.of(CityName.MSK, CityLinks.MSK_LINK, getPriceMapForCity(CityName.MSK)),
                Arguments.of(CityName.SOCHI, CityLinks.SOCHI_LINK, getPriceMapForCity(CityName.SOCHI)),
                Arguments.of(CityName.KRD, CityLinks.KRD_LINK, getPriceMapForCity(CityName.KRD)),
                Arguments.of(CityName.NSK, CityLinks.NSK_LINK, getPriceMapForCity(CityName.NSK))
        );
    }

    private static Map<String, Integer> getPriceMapForCity(CityName cityName) {
        Map<String, Integer> prices = new HashMap<>();
        for (Breakfast breakfast : Breakfast.values()) {
            prices.put(breakfast.getName(), breakfast.getPrice(cityName));
        }
        return prices;
    }
}
