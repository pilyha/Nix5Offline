package ua.com.nix.csv;

import ua.com.nix.annotation.CSVCell;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Mapper {
    public static <T> List<T> map(CSV data, Class<T> type) {
        List<T> objects = new ArrayList<>();

        T instance = null;
        for (int i = 0; i < data.size(); i++) {
            try {
                instance = type.getConstructor().newInstance();
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                e.printStackTrace();
            }
            Class<?> classOfInstance = Objects.requireNonNull(instance).getClass();
            Field[] fields = classOfInstance.getDeclaredFields();
            for (Field field : fields) {
                if (field.isAnnotationPresent(CSVCell.class)) {
                    field.setAccessible(true);
                    Class<?> fieldType = field.getType();
                    CSVCell csvCell = field.getAnnotation(CSVCell.class);
                    String header = csvCell.value();
                    try {
                        if (fieldType == Integer.class || fieldType == int.class) {
                            field.set(instance, Integer.parseInt(data.get(i, header)));
                        } else if (fieldType == Boolean.class || fieldType == boolean.class) {
                            field.set(instance, Boolean.parseBoolean(data.get(i, header)));
                        } else if (fieldType == Double.class || fieldType == double.class) {
                            field.set(instance, Double.parseDouble(data.get(i, header)));
                        } else if (fieldType == String.class) {
                            field.set(instance, (data.get(i, header)));
                        } else if (fieldType.isEnum()) {
                            field.set(instance, Enum.valueOf((Class<Enum>) field.getType(), data.get(i, header)));
                        }
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
            objects.add(instance);
        }

        return objects;
    }
}
