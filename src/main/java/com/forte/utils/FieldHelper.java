package com.forte.utils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Reflection help
 * @author wf https://stackoverflow.com/users/1497139/wolfgang-fahl
 * @author Cassio L Z F Felippe cassioluis12@gmail.com
 */
public class FieldHelper {

    /**
     * get a Field including superclasses
     *
     * @param c
     * @param fieldName
     * @return
     */
    public Field getField(final Class<?> c, final String fieldName) throws NoSuchFieldException {
        if (fieldName.contains(".")) {
            final String[] fields = fieldName.split("\\.");
            Field field = null;
            Class<?> clazz = c;
            for (int i = 0; i < fields.length; i++) {
                field = clazz.getDeclaredField(fields[i]);
                clazz = field.getType();
            }
            return field;
        } else {
            Field result = null;
            try {
                result = c.getDeclaredField(fieldName);
            } catch (NoSuchFieldException e) {
                Class<?> sc = c.getSuperclass();
                result = getField(sc, fieldName);
            }
            return result;
        }
    }

    /**
     * set a field Value by name
     *
     * @param fieldName
     * @param value
     * @throws Exception
     */
    public void setFieldValue(final Object target, final String fieldName, Object value) throws Exception {
        Class<? extends Object> c = target.getClass();

        if (fieldName.contains(".")) {
            final String[] fields = fieldName.split("\\.");
            Field field = null;
            Class<?> clazz = c;

            Object currentObject = target;
            List<Object> objects = new ArrayList<>();
            objects.add(currentObject);

            System.out.println(">> "+Arrays.asList(fields));
            
            for (int i = 0; i < fields.length; i++) {
                field = getField(clazz, fields[i]);

                if (i > 0) {
                    currentObject = getFieldValue(currentObject, fields[i - 1]);
                }

                if (i + 1 == fields.length) {
                    field.setAccessible(true);
                    field.set(currentObject, value);
                    return;
                }

                objects.add(currentObject);

                clazz = field.getType();
            }
        } else {
            Field field = getField(c, fieldName);
            field.setAccessible(true);
            field.set(target, value);
        }
    }

    /**
     * recupera o valor de um campo pelo seu nome, recursivamente, se necessário
     *
     * @param fieldName
     * @return
     * @throws Exception
     */
    public Object getFieldValue(final Object target, final String fieldName) throws Exception {
        if (fieldName.contains(".")) {
            final String[] fields = fieldName.split("\\.");
            Object field = target;
            for (int i = 0; i < fields.length; i++) {
                field = getFieldValue(field, fields[i]);
            }
            return field;
        } else {
            Class<? extends Object> c = target.getClass();
            Field field = getField(c, fieldName);
            field.setAccessible(true);
            Object result = field.get(target);
            return result;
        }
    }
}