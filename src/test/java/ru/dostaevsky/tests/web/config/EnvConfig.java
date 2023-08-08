package ru.dostaevsky.tests.web.config;

import org.aeonbits.owner.Config;

@Config.Sources("classpath:configs/web/${env}.properties")
public interface EnvConfig extends Config {

    String browser_name();

    String browser_version();

    String browser_size();

    String selenoid_url();

}
