package com.tim.scientific.portal.back.config;

import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Deprecated
@Component
@PropertySource("classpath:application.properties")
public class ConfigProperties {
    private final Environment env;

    public ConfigProperties(Environment env) {
        this.env = env;
    }

    public String getConfigValue(String configKey) {
        return env.getProperty(configKey);
    }
}
