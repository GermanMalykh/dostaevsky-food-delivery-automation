package ru.dostaevsky.tests.android.config;

import org.aeonbits.owner.Config;

@Config.Sources("classpath:configs/mobile/${env}.properties")
public interface EnvConfig extends Config {

    String app();

    String device();

    String os_version();

    String appPackage();

    String appActivity();

    @Key("browserstack.user")
    String user();

    @Key("browserstack.key")
    String key();

    String appiumUrl();

}
