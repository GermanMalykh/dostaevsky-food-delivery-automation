package ru.dostaevsky.tests.web.providers;

import org.junit.jupiter.params.provider.Arguments;
import ru.dostaevsky.enums.CityLink;
import ru.dostaevsky.enums.CityName;
import ru.dostaevsky.enums.Bowl;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

@SuppressWarnings("unused")
public class BowlsPriceProvider {
    public static Stream<Arguments> provide() {
        return Stream.of(Arguments.of(CityName.MSK, CityLink.MSK_LINK, getPriceMapForCity(CityName.MSK)),
                Arguments.of(CityName.SOCHI, CityLink.SOCHI_LINK, getPriceMapForCity(CityName.SOCHI)),
                Arguments.of(CityName.KRD, CityLink.KRD_LINK, getPriceMapForCity(CityName.KRD)),
                Arguments.of(CityName.NSK, CityLink.NSK_LINK, getPriceMapForCity(CityName.NSK))
        );
    }

    private static Map<String, Integer> getPriceMapForCity(CityName cityName) {
        Map<String, Integer> prices = new HashMap<>();
        for (Bowl bowl : Bowl.values()) {
            prices.put(bowl.getName(), bowl.getPrice(cityName));
        }
        return prices;
    }
}
