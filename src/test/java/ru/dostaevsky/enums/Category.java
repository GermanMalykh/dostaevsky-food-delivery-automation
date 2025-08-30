package ru.dostaevsky.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Category {
    BREAKFASTS("Завтраки"),
    BOWLS("Боулы"),
    WOKS("Wok"),
    NEW("Новинки"),
    DESSERTS("Десерты");

    private final String value;
}
