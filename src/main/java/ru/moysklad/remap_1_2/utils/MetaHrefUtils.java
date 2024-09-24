package ru.moysklad.remap_1_2.utils;

import lombok.NoArgsConstructor;
import ru.moysklad.remap_1_2.entities.*;
import ru.moysklad.remap_1_2.entities.permissions.EmployeeRole;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static lombok.AccessLevel.PRIVATE;
import static org.apache.commons.lang3.StringUtils.isEmpty;
import static org.apache.commons.lang3.StringUtils.isNotEmpty;

@NoArgsConstructor(access = PRIVATE)
public final class MetaHrefUtils {

    public static Optional<String> getIdFromHref(String href) {
        String[] split = href.split("/");
        return split.length == 0 ? Optional.empty() : Optional.of(split[split.length-1]);
    }

    public static <T extends MetaEntity> String makeHref(Meta.Type type, T entity, String host) {
        if (type == null || entity == null || host == null) {
            return null;
        }
        String id = entity.getId();
        if (id == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder(host);
        if (type == Meta.Type.PRICE_TYPE) {
            sb.append("/context/companysettings");
        } else {
            sb.append("/entity");
        }
        switch (type) {
            case CUSTOM_TEMPLATE:
            case EMBEDDED_TEMPLATE:
                Meta.Type entityType = ((Template) entity).getEntityType();
                if (entityType == null) {
                    return null;
                }
                sb.append('/').append(entityType.getApiName()).append('/').append("metadata");
                break;
            case ATTRIBUTE_METADATA:
                if (((AttributeOperation) entity).getAttributeEntityType() == null) {
                    return null;
                }
                sb.append('/').append(((AttributeOperation) entity).getAttributeEntityType().getApiName())
                        .append("/metadata/attributes");
            default:
                break;
        }
        sb.append('/');
        switch (type) {
            case CUSTOM_ENTITY:
                sb.append(type.getApiName());
                if (((CustomEntityElement) entity).getCustomDictionaryId() == null) {
                    return null;
                }
                sb.append('/').append(((CustomEntityElement) entity).getCustomDictionaryId())
                        .append('/');
                break;
            case ATTRIBUTE_METADATA:
                break;
            default:
                sb.append(type.getApiName()).append('/');
                break;
        }
        return sb.append(id).toString();
    }

    public static <T extends MetaEntity> String makeMetadataHref(Meta.Type type, T entity, String host) {
        if (type != null && entity != null && host != null) {
            return host + "/entity/" + type.getApiName() + "/metadata";
        }
        return null;
    }

    public static <T extends MetaEntity> String makeRoleHref(String name, String host) {
        if (name != null && host != null) {
            return host + "/entity/role/" + name;
        }
        return null;
    }

    public static String getCustomDictionaryIdFromHref(String href) {
        if (isEmpty(href)) {
            return null;
        }
        String[] hrefSplit = href.split("/");
        if (hrefSplit.length < 2 || !href.contains("/entity/customentity/") ||
                hrefSplit[hrefSplit.length - 2].equals("customentity")) {
            return null;
        }
        return hrefSplit[hrefSplit.length - 2];
    }

    public static <T extends MetaEntity> T fillMeta(T entity, String host) {
        if (entity != null && entity.getClass() != MetaEntity.class && !(entity instanceof EmployeeRole.DefaultRole)) {
            if (entity.getId() != null && entity.getMeta() == null) {
                entity.setMeta(new Meta(entity, host));
            }
            Class<? extends MetaEntity> clazz = entity.getClass();
            List<MetaEntity> fields = getAllMetaFields(clazz, entity);
            fields.addAll(getAllListMetaFields(clazz, entity));
            fields.forEach(f -> f.setMeta(new Meta(f, host)));
        } else if (entity instanceof EmployeeRole.DefaultRole) {
            final Meta meta = new Meta();
            final EmployeeRole.DefaultRole defaultRole = (EmployeeRole.DefaultRole) entity;
            meta.setHref(makeRoleHref(defaultRole.name(), host));
            meta.setType(defaultRole.type());
            entity.setMeta(meta);
        }
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

        return Arrays.stream(fields)
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
    }
}
