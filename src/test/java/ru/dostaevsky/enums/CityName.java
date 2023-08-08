package ru.dostaevsky.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CityName {
    SPB("Санкт-Петербург"),
    MSK("Москва"),
    SOCHI("Сочи"),
    KRD("Краснодар"),
    NSK("Новосибирск");

    private final String displayName;

    @Override
    public String toString() {
        return displayName;
    }
}
