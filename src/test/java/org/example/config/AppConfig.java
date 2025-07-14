package org.example.config;

import org.aeonbits.owner.Config;
@Config.Sources({
        "classpath:app.properties",
        "file:local.properties"
})
public interface AppConfig extends Config {
    @Key("baseUrl")
    String baseUrl();

    @Key("username")
    String username();

    @Key("password")
    String password();

}
