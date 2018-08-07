package com.lognex.api.schema;

import com.lognex.api.entities.MetaEntity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Field;
import java.util.*;

public class SchemaReflectionUtils {
    private static final Logger logger = LogManager.getLogger(SchemaReflectionUtils.class);

    public static Field getField(Class<?> clazz, String fieldName) throws NoSuchFieldException {
        while (clazz.getSuperclass() != null) { // we don't want to process Object.class
            for (Field field : clazz.getDeclaredFields()) {
                if (field.getName().equals(fieldName)) {
                    field.setAccessible(true);
                    return field;
                }
            }
            clazz = clazz.getSuperclass();
        }
        throw new NoSuchFieldException();
    }

    public static Map<SchemaField, Field> allFields(Schema schema, Class<? extends MetaEntity> clazz) {
        List<SchemaField> schemaFields = schema.getFields();
        List<Field> fields = allFields(clazz);
        return map(clazz, schemaFields, fields);
    }

    private static Map<SchemaField, Field> map(Class<?> clazz, List<SchemaField> schemaFields, List<Field> fields) {
        Map<SchemaField, Field> fieldsMap= new HashMap<>();
        for (SchemaField schemaField : schemaFields) {
            Optional<Field> field = fields.stream().filter(f -> f.getName().equals(schemaField.getName())).findAny();
            if (field.isPresent()) {
                fieldsMap.put(schemaField, field.get());
                fields.remove(field.get());
            } else {
                logger.warn("Class " + clazz.getName() + " does not contain schema field " + schemaField.getName());
            }
        }
        if (!fields.isEmpty()) {
            logger.warn("Schema does not contains fields of class " + clazz.getName() + ": " + fields);
        }
        return fieldsMap;
    }

    private static List<Field> allFields(Class<?> clazz) {
        List<Field> fields = new ArrayList<>();
        while (MetaEntity.class.isAssignableFrom(clazz)) { // we don't want to process Object.class
            for (Field field : clazz.getDeclaredFields()) {
                    field.setAccessible(true);
                    fields.add(field);
            }
            clazz = clazz.getSuperclass();
        }
        return fields;
    }
}
