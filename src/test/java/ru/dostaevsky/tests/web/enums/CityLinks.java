package ru.dostaevsky.tests.web.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CityLinks {
    SPB_LINK("https://dostaevsky.ru/"),
    MSK_LINK("https://msk.dostaevsky.ru/"),
    SOCHI_LINK("https://sochi.dostaevsky.ru/"),
    KRD_LINK("https://krd.dostaevsky.ru/"),
    NSK_LINK("https://nsk.dostaevsky.ru/");

    private final String value;
}