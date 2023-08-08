package ru.dostaevsky.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CityLink {
    SPB_LINK("https://dostaevsky.ru/"),
    MSK_LINK("https://msk.dostaevsky.ru/"),
    SOCHI_LINK("https://sochi.dostaevsky.ru/"),
    KRD_LINK("https://krd.dostaevsky.ru/"),
    NSK_LINK("https://nsk.dostaevsky.ru/");

    private final String value;
}
