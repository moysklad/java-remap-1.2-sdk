package com.lognex.api.schema;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.lognex.api.LognexApi;
import com.lognex.api.TestRandomizers;
import com.lognex.api.entities.CompanyType;
import com.lognex.api.entities.GroupEntity;
import com.lognex.api.entities.MetaEntity;
import com.lognex.api.entities.agents.AgentEntity;
import com.lognex.api.entities.agents.CounterpartyEntity;
import com.lognex.api.entities.agents.EmployeeEntity;
import com.lognex.api.entities.agents.OrganizationEntity;
import com.lognex.api.utils.LognexApiException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

class SchemaFiller<T extends MetaEntity> implements TestRandomizers {
    private static final Logger logger = LogManager.getLogger(SchemaFiller.class);

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");

    private EmployeeEntity employeeEntity;
    private GroupEntity groupEntity;
    private CounterpartyEntity counterpartyEntity;
    private OrganizationEntity organizationEntity;

    public SchemaFiller(LognexApi api) throws IOException, LognexApiException {
        employeeEntity = api.entity().employee().get().getRows().get(0);
        groupEntity = api.entity().group().get().getRows().get(0);
        counterpartyEntity = api.entity().counterparty().get().getRows().get(0);
        organizationEntity = api.entity().organization().get().getRows().get(0);
        //todo get state, and other metadata
    }

    T prepareEntity(Class<T> clazz, List<Map.Entry<SchemaField, Field>> fields) throws Exception {
        T entity = clazz.newInstance();
        for (Map.Entry<SchemaField, Field> entry : fields) {
            fill(entity, entry);
        }
        return entity;
    }
    List<T> prepareEntities(Class<T> clazz, List<Map.Entry<SchemaField,Field>> fields) throws Exception {
        Multimap<Field, Object> valuesMap = ArrayListMultimap.create();
        for (Map.Entry<SchemaField, Field> entry : fields) {
            valuesMap.putAll(entry.getValue(), formValues(entry));
        }
        List<Map<Field, Object>> valuesSets = split(valuesMap);
        List<T> entities = new ArrayList<>();
        for (Map<Field, Object> valuesSet : valuesSets) {
            entities.add(formEntity(clazz, valuesSet));
        }
        return entities;
    }

    private T formEntity(Class<T> clazz, Map<Field, Object> valuesSet) throws Exception {
        T entity = clazz.newInstance();
        for (Map.Entry<Field, Object> entry : valuesSet.entrySet()) {
            entry.getKey().set(entity, entry.getValue());
        }
        return entity;
    }

    private List<Map<Field, Object>> split(Multimap<Field, Object> valuesMap) {
        List<Map<Field, Object>> valuesSets = new ArrayList<>();
        for (Map.Entry<Field, Collection<Object>> entry : valuesMap.asMap().entrySet()) {
            if (valuesSets.isEmpty()) {
                for (Object value : entry.getValue()) {
                    Map<Field, Object> valueSet = new HashMap<>();
                    valueSet.put(entry.getKey(), value);
                    valuesSets.add(valueSet);
                }
            } else {
                List<Map<Field, Object>> splitted = new ArrayList<>();
                for (Map<Field, Object> valuesSet : valuesSets) {
                    splitted.addAll(split(valuesSet, entry.getKey(), entry.getValue()));
                }
                valuesSets = splitted;
            }
        }
        return valuesSets;
    }
    private List<Map<Field, Object>> split(Map<Field, Object> valuesSet, Field field, Collection<Object> values) {
        if (values.isEmpty()) {
            return Collections.singletonList(valuesSet);
        }
        List<Map<Field, Object>> splitted = new ArrayList<>();
        for (Object value : values) {
            Map<Field, Object> newValuesSet = new HashMap<>(valuesSet);
            newValuesSet.put(field, value);
            splitted.add(newValuesSet);
        }
        return splitted;
    }


    private List<Object> formValues(Map.Entry<SchemaField, Field> entry) {
        //todo fill value according to type and format
        //todo формировать даты
        Field field = entry.getValue();
        if (String.class.equals(field.getType())) {
            return Collections.singletonList(randomString(10));
        }
        if (EmployeeEntity.class.equals(field.getType())) {
            return Collections.singletonList(employeeEntity);
        }
        if (Boolean.class.equals(field.getType())) {
            return Arrays.asList(Boolean.TRUE, Boolean.FALSE);
        }
        if (field.getType().isEnum()) {
            return Arrays.asList(field.getType().getEnumConstants());
        }
        if (GroupEntity.class.equals(field.getType())) {
            return Collections.singletonList(groupEntity);
        }
        if (OrganizationEntity.class.equals(field.getType())) {
            return Collections.singletonList(organizationEntity);
        }
        if (AgentEntity.class.equals(field.getType())) {
            return Collections.singletonList(counterpartyEntity);
        }
        if (field.getType().isAssignableFrom(List.class)) {
            if (String.class.equals(((ParameterizedType) field.getGenericType()).getActualTypeArguments()[0])) {
                List<String> result = new ArrayList<>();
                result.add(randomString(10));
                return Collections.singletonList(result);
            }
        }
//        if (field.getType().equals(StateEntity.class)) {
//            return CompanyType.entrepreneur;
//        }
        logger.warn("Unable to form value for " + field.getName());
        return Collections.emptyList();
    }



    private void fill(T entity, Map.Entry<SchemaField, Field> entry) throws IllegalAccessException {
        //todo fill value according to type and format
        entry.getValue().set(entity, formFieldValue(entry));
    }

    private Object formFieldValue(Map.Entry<SchemaField, Field> entry) {
        if (String.class.equals(entry.getValue().getType())) {
            if ("uuid".equals(entry.getKey().getFormat())) {
                return UUID.randomUUID().toString();
            }
            return randomString(10);
        }
        if (EmployeeEntity.class.equals(entry.getValue().getType())) {
            return employeeEntity;
        }
        if (Boolean.class.equals(entry.getValue().getType())) {
            return Boolean.TRUE;
        }
        if (CompanyType.class.equals(entry.getValue().getType())) {
            return CompanyType.entrepreneur;
        }
        if (GroupEntity.class.equals(entry.getValue().getType())) {
            return groupEntity;
        }
        if (OrganizationEntity.class.equals(entry.getValue().getType())) {
            return organizationEntity;
        }
        if (AgentEntity.class.equals(entry.getValue().getType())) {
            return counterpartyEntity;
        }
        if (LocalDateTime.class.equals(entry.getValue().getType())) {
            return LocalDateTime.now();
        }
        if (entry.getValue().getType().isAssignableFrom(List.class)) {
            if (String.class.equals(((ParameterizedType) entry.getValue().getGenericType()).getActualTypeArguments()[0])) {
                List<String> result = new ArrayList<>();
                result.add(randomString(10));
                return result;
            }
        }
        //todo attributes states
//        if (field.getType().equals(StateEntity.class)) {
//            return CompanyType.entrepreneur;
//        }
        logger.warn("Unable to form value for " + entry.getValue().getName());
        return null;
    }

    public void set(T entity, Map.Entry<SchemaField, Field> entry) throws Exception {
        entry.getValue().set(entity, formFieldValue(entry));
    }

    //todo for ~ filters cut value
    public String getFilterValue(T entity, Map.Entry<SchemaField, Field> entry) throws Exception {
        Field field = entry.getValue();
        if (String.class.equals(field.getType())) {
            return (String) field.get(entity);
        }
        if (MetaEntity.class.isAssignableFrom(field.getType())) {
            return ((MetaEntity) field.get(entity)).getMeta().getHref();
        }
        if (Boolean.class.equals(field.getType())) {
            return field.get(entity).toString();
        }
        if (field.getType().isEnum()) {
            return field.get(entity).toString();
        }
        if (LocalDateTime.class.equals(field.getType())) {
            return ((LocalDateTime) field.get(entity)).format(formatter);
        }
//        if (field.getType().isAssignableFrom(List.class)) {
//            if (String.class.equals(((ParameterizedType) field.getGenericType()).getActualTypeArguments()[0])) {
//                return ((List<String>) field.get(entity)).get(0);
//            }
//        }
        logger.warn("Unable to form filter value for " + field.getName());
        return null;
    }
}
