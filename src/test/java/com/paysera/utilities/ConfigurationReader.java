package com.paysera.utilities;

import java.io.FileInputStream;
import java.util.Properties;

public class ConfigurationReader {

    private static Properties properties;

    static {

        try {
            String configPath = "configuration.properties";
            FileInputStream input = new FileInputStream(configPath);
            properties = new Properties();
            properties.load(input);

            input.close();
        } catch (Exception exception) {
            exception.printStackTrace();

        }
    }

    public static String get(String keyName) {
        return properties.getProperty(keyName);
    }

}