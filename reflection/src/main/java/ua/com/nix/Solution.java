package ua.com.nix;

import ua.com.nix.Annotation.PropertyKey;
import ua.com.nix.properties.AppProperties;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.lang.reflect.Field;
import java.util.Properties;

public class Solution {
    public void run() {
        Object instance = new AppProperties();

        Properties props = new Properties();
        try (InputStream input = Main.class.getResourceAsStream("/app.properties")) {
            props.load(input);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
        Class<?> classOfInstance = instance.getClass();
        Field[] fields = classOfInstance.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            if(field.isAnnotationPresent(PropertyKey.class))
            {
                field.setAccessible(true);
                try {
                    field.set(instance,Integer.parseInt(props.getProperty(field.getAnnotation(PropertyKey.class).value())));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        System.out.println(instance);
    }
}

