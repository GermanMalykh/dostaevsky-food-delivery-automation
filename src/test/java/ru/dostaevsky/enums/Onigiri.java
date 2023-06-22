package ru.dostaevsky.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Onigiri {
    SNOW_CRAB_ONIGIRI("Онигири со снежным крабом"),
    ONIGIRI_KOMBO("Онигири комбо");

    private final String value;
}
