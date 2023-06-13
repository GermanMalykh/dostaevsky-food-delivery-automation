package ru.dostaevsky.tests.web.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Categories {
    BREAKFASTS("Завтраки"),
    BOWLS("Боулы"),
    WOKS("Wok");

    private final String value;
}
