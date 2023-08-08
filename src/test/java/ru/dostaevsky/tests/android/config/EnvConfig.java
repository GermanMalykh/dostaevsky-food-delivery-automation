package ru.dostaevsky.tests.android.config;

import org.aeonbits.owner.Config;

@Config.Sources("classpath:configs/mobile/${env}.properties")
public interface EnvConfig extends Config {

    @Key("app")
    String app();

    @Key("device")
    String device();

    @Key("os_version")
    String os_version();

    @Key("appPackage")
    String appPackage();

    @Key("appActivity")
    String appActivity();

    @Key("browserstack.user")
    String user();

    @Key("browserstack.key")
    String key();

    @Key("appiumUrl")
    String appiumUrl();

}
