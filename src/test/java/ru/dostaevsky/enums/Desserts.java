package ru.dostaevsky.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Desserts {
    CARAMEL_CHEESECAKE("Карамельный чизкейк");

    private final String value;
}
