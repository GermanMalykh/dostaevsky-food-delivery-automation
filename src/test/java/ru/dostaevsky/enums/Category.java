package ru.dostaevsky.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Category {
    BREAKFASTS("Завтраки"),
    BOWLS("Боулы"),
    WOKS("Wok"),
    FAST_FOOD("Фастфуд"),
    NEW("Новинки"),
    ONIGIRI("Онигири");

    private final String value;
}
