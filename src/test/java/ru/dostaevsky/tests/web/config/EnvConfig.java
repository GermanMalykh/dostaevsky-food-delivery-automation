package ru.dostaevsky.tests.web.config;

import org.aeonbits.owner.Config;


@Config.Sources("classpath:configs/web/${env}.properties")
public interface EnvConfig extends Config {

    @Config.Key("browser_name")
    String browser_name();

    @Config.Key("browser_version")
    String browser_version();

    @Config.Key("browser_size")
    String browser_size();

    @Config.Key("selenoid_url")
    String selenoid_url();

}
