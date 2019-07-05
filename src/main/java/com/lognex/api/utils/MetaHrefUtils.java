package com.lognex.api.utils;

import com.lognex.api.entities.CustomEntityElement;
import com.lognex.api.entities.Meta;
import com.lognex.api.entities.MetaEntity;
import com.lognex.api.entities.TemplateEntity;
import com.lognex.api.responses.ListEntity;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static lombok.AccessLevel.PRIVATE;
import static org.apache.commons.lang3.StringUtils.isNotEmpty;

@NoArgsConstructor(access = PRIVATE)
public final class MetaHrefUtils {

    public static String getId(String href) {
        String[] split = href.split("/");
        return split[split.length-1];
    }

    public static <T> String makeHref(Meta.Type type, T entity, @NonNull String host) {
        StringBuilder sb = new StringBuilder(host);
        if (entity instanceof MetaEntity) {
            sb.append("/entity");
            switch (type) {
                case CUSTOM_TEMPLATE:
                case EMBEDDED_TEMPLATE:
                    Meta.Type entityType = ((TemplateEntity) entity).getEntityType();
                    if (entityType != null) {
                        sb.append('/').append(entityType.getApiName()).append('/').append("metadata");
                    }
                    break;
                default:
                    break;
            }
        }
        sb.append("/").append(type.getApiName());
        if (entity instanceof MetaEntity){
            switch (type) {
                case CUSTOM_ENTITY:
                    sb.append('/').append(((CustomEntityElement) entity).getCustomDictionaryId())
                            .append('/').append(((MetaEntity) entity).getId());
                    break;
                default:
                    sb.append('/').append(((MetaEntity) entity).getId());
                    break;
            }
        }
        return sb.toString();
    }

    public static <T extends MetaEntity> String makeMetadataHref(Meta.Type type, T entity, String host) {
        if (entity != null) {
            return host + "/entity/" + type.getApiName() + "/metadata";
        }
        return null;
    }

    public static <T extends MetaEntity> T fillMeta(T entity, String host) {
        Class<? extends MetaEntity> clazz = entity.getClass();
        List<MetaEntity> fields = getAllMetaFields(clazz, entity);
        fields.addAll(getAllListMetaFields(clazz, entity));
        fields.forEach(f -> f.setMeta(new Meta(f, host)));
        return entity;
    }

    private static <T extends MetaEntity> List<MetaEntity> getAllMetaFields(Class<?> clazz, T entity) {
        if (clazz == null || entity == null || clazz == MetaEntity.class) {
            return Collections.emptyList();
        }

        List<MetaEntity> result = new ArrayList<>(getAllMetaFields(clazz.getSuperclass(), entity));
        List<MetaEntity> filteredFields = Arrays.stream(clazz.getDeclaredFields())
                .filter(f -> MetaEntity.class.isAssignableFrom(f.getType()))
                .map(f -> (MetaEntity) getFieldValueByName(f, clazz, entity))
                .filter(f -> f != null && isNotEmpty(f.getId()) && f.getMeta() == null)
                .collect(Collectors.toList());
        result.addAll(filteredFields);
        return result;
    }

    private static <T extends MetaEntity> Object getFieldValueByName(Field f, Class<?> clazz, T entity) {
        try {
            String fieldName = f.getName().substring(0, 1).toUpperCase() + f.getName().substring(1);
            return clazz.getDeclaredMethod("get" + fieldName).invoke(entity);
        } catch (IllegalAccessException e) {
            throw new IllegalArgumentException("Cannot access getter for field " + f.getName() + " of class " + clazz.getName(), e);
        } catch (InvocationTargetException e) {
            throw new IllegalArgumentException("Cannot invoke getter for field " + f.getName() + " of class " + clazz.getName(), e);
        } catch (NoSuchMethodException e) {
            throw new IllegalArgumentException("Cannot find getter for field " + f.getName() + " of class " + clazz.getName(), e);
        }
    }

    private static <T extends MetaEntity> List<MetaEntity> getAllListMetaFields(Class<?> clazz, T entity) {
        if (clazz == null || entity == null) {
            return Collections.emptyList();
        }

        Field[] fields = clazz.getDeclaredFields();
        List<MetaEntity> filteredFields = Arrays.stream(fields)
                .filter(f -> List.class.isAssignableFrom(f.getType()))
                .map(f -> (List) getFieldValueByName(f, clazz, entity))
                .flatMap(f -> {
                    if (f != null && f.size() > 0 && MetaEntity.class.isAssignableFrom(f.get(0).getClass())) {
                        return (Stream<MetaEntity>) f.stream();
                    }
                    return Stream.empty();
                })
                .filter(f -> f != null && isNotEmpty(f.getId()) && f.getMeta() == null)
                .collect(Collectors.toList());

        filteredFields.addAll(Arrays.stream(fields)
                .filter(f -> ListEntity.class.isAssignableFrom(f.getType()))
                .map(f -> (ListEntity) getFieldValueByName(f, clazz, entity))
                .flatMap(f -> {
                    if (f != null && f.getRows() != null) {
                        return (Stream<MetaEntity>) f.getRows().stream();
                    }
                    return Stream.empty();
                })
                .filter(f -> f != null && isNotEmpty(f.getId()) && f.getMeta() == null)
                .collect(Collectors.toList())
        );

        return filteredFields;
    }
}
