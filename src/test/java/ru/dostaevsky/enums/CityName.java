package ru.dostaevsky.enums;

import lombok.Getter;

@Getter
public enum CityName {
    SPB("Санкт-Петербург"),
    MSK("Москва"),
    SOCHI("Сочи"),
    KRD("Краснодар"),
    NSK("Новосибирск");

    private final String displayName;

    CityName(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }
}