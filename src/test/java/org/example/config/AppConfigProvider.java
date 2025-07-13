package org.example.config;

import org.aeonbits.owner.ConfigCache;

public class AppConfigProvider {
    public static AppConfig config = ConfigCache.getOrCreate(AppConfig.class, System.getProperties());
}
