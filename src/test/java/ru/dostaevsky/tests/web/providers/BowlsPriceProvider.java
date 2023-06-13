package ru.dostaevsky.tests.web.providers;

import org.junit.jupiter.params.provider.Arguments;
import ru.dostaevsky.tests.web.enums.CityLinks;
import ru.dostaevsky.tests.web.enums.CityName;
import ru.dostaevsky.tests.web.enums.Bowls;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

@SuppressWarnings("unused")
public class BowlsPriceProvider {
    public static Stream<Arguments> provide() {
        return Stream.of(Arguments.of(CityName.MSK, CityLinks.MSK_LINK, getPriceMapForCity(CityName.MSK)),
                Arguments.of(CityName.SOCHI, CityLinks.SOCHI_LINK, getPriceMapForCity(CityName.SOCHI)),
                Arguments.of(CityName.KRD, CityLinks.KRD_LINK, getPriceMapForCity(CityName.KRD)),
                Arguments.of(CityName.NSK, CityLinks.NSK_LINK, getPriceMapForCity(CityName.NSK))
        );
    }

    private static Map<String, Integer> getPriceMapForCity(CityName cityName) {
        Map<String, Integer> prices = new HashMap<>();
        for (Bowls bowls : Bowls.values()) {
            prices.put(bowls.getName(), bowls.getPrice(cityName));
        }
        return prices;
    }
}
