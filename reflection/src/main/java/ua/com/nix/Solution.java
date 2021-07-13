package ua.com.nix;

import ua.com.nix.annotation.PropertyKey;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Properties;

public class Solution {
    public static <T> T run(String properties, Class<T> type) {
        Properties props = load(properties);
        T instance = null;
        try {
            instance = type.getConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        Class<?> classOfInstance = instance.getClass();
        Field[] fields = classOfInstance.getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(PropertyKey.class)) {
                field.setAccessible(true);
                Class<?> fieldType = field.getType();
                try {
                    if (fieldType == Integer.class || fieldType == int.class) {
                        field.set(instance, Integer.parseInt(props.getProperty(field.getAnnotation(PropertyKey.class).value())));
                    } else if (fieldType == Boolean.class || fieldType == boolean.class) {
                        field.set(instance, Boolean.parseBoolean(props.getProperty(field.getAnnotation(PropertyKey.class).value())));
                    } else if (fieldType == Double.class || fieldType == double.class) {
                        field.set(instance, Double.parseDouble(props.getProperty(field.getAnnotation(PropertyKey.class).value())));
                    } else if (fieldType == String.class) {
                        field.set(instance, (props.getProperty(field.getAnnotation(PropertyKey.class).value())));
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        return (T) instance;
    }

    private static Properties load(String properties) {
        Properties props = new Properties();
        try (InputStream input = Main.class.getResourceAsStream(properties)) {
            props.load(input);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
        return props;
    }
}

