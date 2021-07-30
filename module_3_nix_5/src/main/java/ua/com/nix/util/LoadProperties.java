package ua.com.nix.util;

import ua.com.nix.Main;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.util.Properties;

public class LoadProperties {
    public static Properties load() {
        Properties props = new Properties();

        try (InputStream input = LoadProperties.class.getResourceAsStream("/jdbc.properties")) {
            props.load(input);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }

        return props;
    }
}
