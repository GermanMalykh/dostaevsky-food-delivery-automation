package ru.dostaevsky.tests.web.providers;

import org.junit.jupiter.params.provider.Arguments;
import ru.dostaevsky.enums.Breakfast;
import ru.dostaevsky.enums.CityLink;
import ru.dostaevsky.enums.CityName;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

@SuppressWarnings("unused")
public class BreakfastsPriceProvider {
    public static Stream<Arguments> provide() {
        return Stream.of(
                Arguments.of(CityName.SPB, CityLink.SPB_LINK, getPriceMapForCity(CityName.SPB)),
                Arguments.of(CityName.MSK, CityLink.MSK_LINK, getPriceMapForCity(CityName.MSK))
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
