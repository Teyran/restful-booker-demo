package org.example.config;

import org.aeonbits.owner.ConfigCache;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class AppConfigProvider {
    public static final AppConfig config;

    static {
        Properties props = new Properties();
        try (FileInputStream fis = new FileInputStream("local.properties")) {
            props.load(fis);
        } catch (IOException e) {
            System.err.println("WARNING: local.properties not found, falling back to classpath app.properties");
        }

        config = ConfigCache.getOrCreate(AppConfig.class, props);
    }
}
