package com.lognex.api.schema;

import com.lognex.api.LognexApi;
import com.lognex.api.entities.Meta;
import com.lognex.api.entities.MetaEntity;
import com.lognex.api.responses.ErrorResponse;
import com.lognex.api.utils.HttpRequestExecutor;
import com.lognex.api.utils.LognexApiException;
import com.lognex.api.utils.TestAsserts;
import com.lognex.api.utils.TestRandomizers;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.joining;
import static org.junit.Assert.*;

public class SchemaValidator<T extends MetaEntity> implements TestAsserts, TestRandomizers {
    private static final Logger logger = LoggerFactory.getLogger(SchemaTest.class);
    private final SchemaReport schemaReport = new SchemaReport();

    private static final List<String> filterOperators;
    static {
        List<String> operators = new ArrayList<>();
        operators.add("=");
        operators.add("!=");
        operators.add(">");
        operators.add(">=");
        operators.add("<");
        operators.add("<=");
        operators.add("~");
        operators.add("~=");
        operators.add("=~");
        filterOperators = Collections.unmodifiableList(operators);
    }

    private final Schema schema;
    private final LognexApi api;
    private final String path;
    private final Class<T> clazz;

    private final SchemaFiller<T> schemaFiller;
    private Map<SchemaField, Field> fieldsMap = new HashMap<>();

    public SchemaValidator(Schema schema, LognexApi api, String path, Class<T> clazz) throws Exception {
        this.schema = schema;
        this.api = api;
        this.path = path;
        this.clazz = clazz;
        this.schemaFiller = new SchemaFiller<>(api);
    }

    public void check() throws Exception {
        fieldsMap.putAll(SchemaReflectionUtils.allFields(schema, clazz));
        schemaReport.chapter("Start checking");
        checkRequired();
        checkNotRequiredFields();
//        checkIncorrectValues();
        checkUpdate();
        checkFilter();
        checkSort();
        schemaReport.endChapter();

        if (logger.isDebugEnabled()) {
            logger.debug(schemaReport.allLog());
        } else {
            logger.info(schemaReport.problemLog());
        }
    }

    private T post(T entity) throws Exception {
        T response = HttpRequestExecutor.
                        path(api, path).
                        body(entity).
                        post(clazz);
        Thread.sleep(500); // Защита от лимитов
        return response;
    }

    private T put(T entity) throws Exception {
        T response = HttpRequestExecutor.
                        path(api, path + "/" + entity.getId()).
                        body(entity).
                        put(clazz);
        Thread.sleep(500); // Защита от лимитов
        return response;
    }

    private T put(T entity, String id) throws Exception {
        T response = HttpRequestExecutor.
                path(api, path + id + "/").
                body(entity).
                put(clazz);
        Thread.sleep(500); // Защита от лимитов
        return response;
    }

    private T get(String id) throws Exception {
        T response = HttpRequestExecutor.
                path(api, path + id + "/").
                get(clazz);
        Thread.sleep(500); // Защита от лимитов
        return response;
    }

    private List<T> getAll() throws Exception {
        List<T> response = HttpRequestExecutor.
                path(api, path).
                list(clazz).
                getRows();
        Thread.sleep(500);
        return response;
    }

    private void checkRequired() throws Exception {
        schemaReport.chapter("Проверяем обязательность полей");
        List<List<AbstractMap.SimpleEntry<SchemaField, Field>>> requiredFields = requiredFields();
        positiveCheckRequired(requiredFields);
        negativeCheckRequired(requiredFields);
        schemaReport.endChapter();
    }

    private void checkNotRequiredFields() throws Exception {
        schemaReport.chapter("Проверяем необязательные поля полей");
        List<List<AbstractMap.SimpleEntry<SchemaField, Field>>> requiredFields = requiredFields();
        List<AbstractMap.SimpleEntry<SchemaField, Field>> updatableFields = updatableFields();

        for (List<AbstractMap.SimpleEntry<SchemaField, Field>> requiredFieldSet : requiredFields) {
            schemaReport.chapter("Набор обязательных " + requiredFieldSet);
            for (AbstractMap.SimpleEntry<SchemaField, Field> updatableField : updatableFields) {
                if (requiredFields.contains(updatableField)) {
                    continue;
                }
                schemaReport.chapter("Необязательное поле " + updatableField.getKey().getName());
                List<AbstractMap.SimpleEntry<SchemaField, Field>> expandedFiledSet = new ArrayList<>(requiredFieldSet);
                expandedFiledSet.add(updatableField);
                List<T> entities = schemaFiller.prepareEntities(clazz, expandedFiledSet);
                for (T entity : entities) {
                    Object value = updatableField.getValue().get(entity);
                    try {
                        T created = post(entity);
                        assertMinimumEntity(created);
                        assertFieldEquals("Создание сущности с одним необязательным полем " + updatableField.getKey().getName(), value, updatableField.getValue().get(created));
                    } catch (LognexApiException e) {
                        schemaReport.log(e.getMessage());
                    }
                }
                schemaReport.endChapter();
            }
            schemaReport.endChapter();
        }
        schemaReport.endChapter();
    }

    private void checkUpdate() throws Exception {
        schemaReport.chapter("Проверка обновления полей");
        List<AbstractMap.SimpleEntry<SchemaField, Field>> updatableFields = fieldsMap.keySet().stream()
                .filter(SchemaField::isUpdatable)
                .map(sf -> new AbstractMap.SimpleEntry<>(sf, fieldsMap.get(sf)))
                .collect(Collectors.toList());
        List<AbstractMap.SimpleEntry<SchemaField, Field>> notUpdatableFields = fieldsMap.keySet().stream()
                .filter(sf -> !sf.isUpdatable())
                .map(sf -> new AbstractMap.SimpleEntry<>(sf, fieldsMap.get(sf)))
                .collect(Collectors.toList());
        List<T> entities = schemaFiller.prepareEntities(clazz, updatableFields);//todo возможно нет смысла генерировать множество сущностей.
        for (T entity : entities) {
            schemaReport.chapter("Создается отдельная сущность");
            try {
                entity = post(entity);
                String id = extractId(entity);
                //positive update
                schemaReport.chapter("Обновление сущности");
                for (AbstractMap.SimpleEntry<SchemaField, Field> entry : updatableFields) {
                    T current = get(id);
                    schemaFiller.set(current, entry);//todo возможно должно быть prepareToUpdate, чтобы учитывать доп условия
                    Object expected = entry.getValue().get(current);
                    try {
                        T updated = put(current);
                        assertFieldEquals("Обновление " + entry.getKey().getName(), expected, entry.getValue().get(updated));
                    } catch (LognexApiException e) {
                        schemaReport.log(e.getMessage());
                    }
                }
                schemaReport.endChapter();
                //negative update
                schemaReport.chapter("Попытка обновления необновляемых полей");
                for (AbstractMap.SimpleEntry<SchemaField, Field> entry : notUpdatableFields) {
                    T current = get(id);
                    Object expected = entry.getValue().get(current);
                    schemaFiller.set(current, entry);
                    try {
                        T updated = put(current, id);
                        assertFieldEquals("Обновление не обновляемого поля " + entry.getKey().getName(), expected, entry.getValue().get(updated));
                    } catch (LognexApiException e) {
                        schemaReport.log(e.getMessage());
                    }
                }
                schemaReport.endChapter();
            } catch (LognexApiException e) {
                schemaReport.log(e.getMessage());
            }
            schemaReport.endChapter();
            break;//todo описано выше
        }
        schemaReport.endChapter();
    }

    private void checkFilter() throws Exception {
        schemaReport.chapter("Проверка фильтрации");
        List<AbstractMap.SimpleEntry<SchemaField, Field>> filterableFields = fieldsMap.keySet().stream()
                .filter(sf -> !sf.getFilters().isEmpty())
                .map(sf -> new AbstractMap.SimpleEntry<>(sf, fieldsMap.get(sf)))
                .collect(Collectors.toList());
        List<AbstractMap.SimpleEntry<SchemaField, Field>> notFilterableFields = fieldsMap.keySet().stream()
                .filter(sf -> sf.getFilters().isEmpty())
                .map(sf -> new AbstractMap.SimpleEntry<>(sf, fieldsMap.get(sf)))
                .collect(Collectors.toList());
        List<T> allEntities = getAll();
        if (allEntities == null)
            fail("No entities present");
        for (AbstractMap.SimpleEntry<SchemaField, Field> entry : filterableFields) {
            schemaReport.chapter("Фильтрация по " + entry.getKey().getName());
            // positive filtration
            schemaReport.chapter("По значению");
            Optional<T> entityOptional = allEntities.stream().filter(e -> {
                try {
                    return entry.getValue().get(e) != null;
                } catch (IllegalAccessException e1) {
                    return false;
                }
            }).findAny();
            if (entityOptional.isPresent()) {
                for (String filterOperator : entry.getKey().getFilters()) {
                    String filter = entry.getKey().getName() + filterOperator + schemaFiller.getFilterValue(entityOptional.get(), entry);
                    try {
                        List<T> filtered = HttpRequestExecutor.
                                path(api, path).
                                query("filter", filter).
                                list(clazz).getRows();
                        Integer filteredSize = filtered == null ? 0 : filtered.size();
                        List<T> expectedFiltered = filter(filterOperator, allEntities, entry.getValue().get(entityOptional.get()), entry);
                        assertWrap(() -> assertEquals("Сравниваем размер отфильтрованного списка (" + filter + ") ", (Integer) expectedFiltered.size(), filteredSize));
                    } catch (LognexApiException e) {
                        schemaReport.log(e.getMessage());
                    } catch (UnsupportedOperationException e) {
                        schemaReport.log(entry.getKey().getName() + "(" + filter + ") " + e.getMessage());
                    }
                }
            } else {
                schemaReport.log("Не найдено не null значение для проверки фильтрации");
            }
            schemaReport.endChapter();
            // positive filtration with null
            schemaReport.chapter("На null");
            //todo фильтрация массива tags
            for (String filterOperator : entry.getKey().getFilters()) {
                if ("=".equals(filterOperator) || "!=".equals(filterOperator)) {
                    String filter = entry.getKey().getName() + filterOperator;
                    try {
                        List<T> filtered = HttpRequestExecutor.
                                path(api, path).
                                query("filter", filter).
                                list(clazz).getRows();
                        Integer filteredSize = filtered == null ? 0 : filtered.size();
                        List<T> expectedFiltered = filter(filterOperator, allEntities, null, entry);
                        assertWrap(() -> assertEquals("Сравниваем размер отфильтрованного списка (" + filter + ") ", (Integer) expectedFiltered.size(), filteredSize));
                    } catch (LognexApiException e) {
                        schemaReport.log(e.getMessage());
                    }  catch (UnsupportedOperationException e) {
                        schemaReport.log(entry.getKey().getName() + "(" + filter + ") " + e.getMessage());
                    }
                }
            }
            schemaReport.endChapter();
            // negative filtration (incorrect filter operators)
            schemaReport.chapter("Проверка недопустимых операторов фильтрации");
            for (String filterOperator : inverseFilterOperators(entry.getKey().getFilters())) {
                String filter = entry.getKey().getName() + filterOperator + "stub";
                try {
                    HttpRequestExecutor.
                            path(api, path).
                            query("filter", filter).
                            list(clazz).getRows();
                    assertWrap(() -> fail("Корректно отработал недопустимый фильтр (" + filter + ") "));
                } catch (LognexApiException e) {
                    assertWrap(() -> {
                        Optional<ErrorResponse.Error> error = e.getErrorResponse().getErrors().stream()
                                .filter(err -> err.getError().matches(".*Оператор.*не совместим.*"))
                                .findAny();
                        assertTrue("Ожидалась ошибка на неверный оператор фильтрации " + filter +
                                ", а получены " +
                                e.getErrorResponse().getErrors().stream().map(ErrorResponse.Error::getError).collect(joining("; ")),
                                error.isPresent());
                    });
                }
            }
            schemaReport.endChapter().endChapter();
        }
        // negative filtration (filter on not filterable fields)
        schemaReport.chapter("Попытка фильтрации по нефильтруемым полям");
        for (AbstractMap.SimpleEntry<SchemaField, Field> entry : notFilterableFields) {
            schemaReport.chapter("По " + entry.getKey().getName());
            for (String filterOperator : filterOperators) {
                String filter = entry.getKey().getName() + filterOperator + "stub";
                try {
                    HttpRequestExecutor.
                            path(api, path).
                            query("filter", filter).
                            list(clazz).getRows();
                    assertWrap(() -> fail("Корректно отработал недопустимый фильтр (" + filter + ") "));
                } catch (LognexApiException e) {
                    assertWrap(() -> {
                        Optional<ErrorResponse.Error> error = e.getErrorResponse().getErrors().stream()
                                .filter(err -> err.getError().contains("Неизвестное поле фильтрации"))
                                .findAny();
                        assertTrue("Ожидалась ошибка на неизвестное поле фильтрации " + filter +
                                ", а получены " +
                                e.getErrorResponse().getErrors().stream().map(ErrorResponse.Error::getError).collect(joining("; ")),
                                error.isPresent());
                    });
                }
            }
            schemaReport.endChapter();
        }
        schemaReport.endChapter();
        //todo фильтрация по нескольким полям, значениям
        schemaReport.endChapter();
    }


    private void checkSort() throws Exception {
        schemaReport.chapter("Проверка сортировки");
        List<AbstractMap.SimpleEntry<SchemaField, Field>> orderableFields = fieldsMap.keySet().stream()
                .filter(SchemaField::isOrderable)
                .map(sf -> new AbstractMap.SimpleEntry<>(sf, fieldsMap.get(sf)))
                .collect(Collectors.toList());
        List<AbstractMap.SimpleEntry<SchemaField, Field>> notOrderableFields = fieldsMap.keySet().stream()
                .filter(sf -> !sf.isOrderable())
                .map(sf -> new AbstractMap.SimpleEntry<>(sf, fieldsMap.get(sf)))
                .collect(Collectors.toList());
        List<T> allEntities = getAll();
        if (allEntities == null)
            fail("No entities present");
        //Сортировка по одному полю в разных направлениях
        for (AbstractMap.SimpleEntry<SchemaField, Field> entry : orderableFields) {
            schemaReport.chapter("Сортировка по " + entry.getKey().getName());
            try {
                assertOrder(allEntities, Collections.singletonMap(entry.getValue(), true));
                assertOrder(allEntities, Collections.singletonMap(entry.getValue(), false));
            } catch (LognexApiException e) {
                schemaReport.log(e.getMessage());
            }
            schemaReport.endChapter();
        }
        schemaReport.endChapter();
    }

    //При вызове следует использовать Map, сохраняющий порядок ключей, например LinkedHashMap
    private void assertOrder(List<T> allEntities, Map<Field, Boolean> orderMap) throws Exception {
        List<T> expectedOrdered = expectedOrder(allEntities, orderMap);
        String order = orderMap.entrySet().stream().map(entry -> entry.getKey().getName() + "," + (entry.getValue() ? "asc" : "desc")).collect(joining(";"));

        List<T> ordered = HttpRequestExecutor.
                path(api, path).
                query("order", order).
                list(clazz).getRows();
        if (ordered != null) {
            compareOrder(order, expectedOrdered, ordered, orderMap.keySet());
        } else {
            assertEquals(0, expectedOrdered.size());
        }
        if (order.contains(",asc")) {
            order = order.replaceAll(",asc", "");
            ordered = HttpRequestExecutor.
                    path(api, path).
                    query("order", order).
                    list(clazz).getRows();
            if (ordered != null) {
                compareOrder(order, expectedOrdered, ordered, orderMap.keySet());
            } else {
                assertEquals(0, expectedOrdered.size());
            }
        }
    }

    private void compareOrder(String order, List<T> expectedOrdered, List<T> ordered, Set<Field> fields) throws Exception{
        List<String> expectedIds = expectedOrdered.stream().map(this::extractId).collect(Collectors.toList());
        List<String> ids = ordered.stream().map(this::extractId).collect(Collectors.toList());
        if (expectedIds.size() != ids.size()) {
            schemaReport.log("Разная длина отсортированных списков (" + order + ")\n");
            return;
        }
        for (int i = 0; i < expectedIds.size(); i++) {
            try {
                assertEquals("Сравниваем отсортированный список (" + order + ") позиция " + i, expectedIds.get(i), ids.get(i));
            } catch (AssertionError e) {
                if (compareOrderPosition(expectedOrdered, ordered, fields, i)) {
                    break;
                }
            }
        }
    }

    /**
     * Сравнивает конкретные объекты по полям сортировки (порядок по id может быть разным, если совпадают поля сортировки)
     * @return true, если объекты не совпадают по полям сортировки, иначе false
     */
    private boolean compareOrderPosition(List<T> expectedOrdered, List<T> ordered, Set<Field> fields, int position) throws IllegalAccessException{
        T expectedEntity = expectedOrdered.get(position);
        T actualEntity = ordered.get(position);
        List<AssertionError> fieldComparisonErrors = new ArrayList<>();
        for (Field field : fields) {
            //todo error cathed in assertFieldEquals
            try {
                assertFieldEquals("Сравниваем " + position + " позицию, поле " + field.getName(), field.get(expectedEntity), field.get(actualEntity));
            } catch (AssertionError assertionError) {
                fieldComparisonErrors.add(assertionError);
                List<Object> expectedOrderFields = toFieldsList(expectedOrdered, field);
                List<Object> actualOrderFields = toFieldsList(ordered, field);
                fieldComparisonErrors.add(new AssertionError(
                        "expected\n"
                        + expectedOrderFields
                        +"\nactual\n"
                        + actualOrderFields));
            }
        }
        if (!fieldComparisonErrors.isEmpty()) {
            for (AssertionError error : fieldComparisonErrors) {
                schemaReport.log(error.getMessage());
            }
            return true;
        }
        return false;
    }

    private List<Object> toFieldsList(List<T> entitiesList, Field field) {
        return entitiesList.stream().map(en -> {
            try {
                return field.get(en);
            } catch (IllegalAccessException e1) {
                schemaReport.log("Проблема с доступом к полю " + field.getName());
            }
            return null;
        }).collect(Collectors.toList());
    }

    private List<T> expectedOrder(List<T> entities, Map <Field, Boolean> map) {
        List<T> ordered = new ArrayList<>(entities);
        ordered.sort((o1, o2) -> {
            int result = 0;
            for (Map.Entry<Field, Boolean> entry : map.entrySet()) {
                result = compare(o1, o2, entry.getKey());
                if (!entry.getValue()) {
                    result *= -1;
                }
                if (result != 0) {
                    return result;
                }
            }
            return result;
        });
        return ordered;
    }

    private int compare(T o1, T o2, Field field) {
        try {
            Object v1 = field.get(o1);
            Object v2 = field.get(o2);
            if (v1 == null) {
                if (v2 == null) {
                    return 0;
                } else {
                    return -1;
                }
            }
            if (v2 == null) {
                return 1;
            }
            if (v1 instanceof String && v2 instanceof String) {
                String s1 = ((String) v1).toLowerCase().replaceAll("-", "");
                String s2 = ((String) v2).toLowerCase().replaceAll("-", "");
                return s1.compareTo(s2);
            }
            if (v1 instanceof Comparable) {
                return ((Comparable) v1).compareTo(v2);
            }
        } catch (IllegalAccessException e) {
            schemaReport.log("Проблема с доступом к полю " + field.getName());
        }
        return 0;
    }

    private List<String> inverseFilterOperators(List<String> filters) {
        return filterOperators.stream().filter(f -> !filters.contains(f)).collect(Collectors.toList());
    }

    private List<T> filter(String filterOperator, List<T> entities, Object value, AbstractMap.SimpleEntry<SchemaField, Field> entry) {
        if (value == null) {
            return entities.stream().filter(e -> {
                try {
                    switch (filterOperator) {
                        case "=":
                            return entry.getValue().get(e) == null;
                        case "!=":
                            return entry.getValue().get(e) != null;
                        default:
                            throw new IllegalArgumentException();//не должно быть достижимо
                    }
                } catch (IllegalAccessException e1) {
                    schemaReport.log("Проблема с доступом к полю " + entry.getKey().getName());
                }
                return false;
            }).collect(Collectors.toList());
        }
        return entities.stream().filter(e -> {
            try {
                switch (filterOperator) {
                    case "=":
                        return value.equals(entry.getValue().get(e));
                    case "!=":
                        return !value.equals(entry.getValue().get(e)) && entry.getValue().get(e) != null;
                    case ">":
                    case ">=":
                    case "<":
                    case "<=":
                        return numberFilter(entry.getKey(), filterOperator, value, entry.getValue().get(e));
                    case "~":
                    case  "~=":
                    case "=~":
                        return likeFilter(filterOperator, value, entry.getValue().get(e));
                    default:
                        throw new IllegalArgumentException();//не должно быть достижимо
                }
            } catch (IllegalAccessException e1) {
                schemaReport.log("Проблема с доступом к полю " + entry.getKey().getName());
            }
            return false;
        }).collect(Collectors.toList());
    }

    private boolean likeFilter(String filterOperator, Object value, Object actual) {
        if (value == null || actual == null) {
            return false;
        }
        if (value instanceof String && actual instanceof String) {
            switch (filterOperator) {
                case "~":
                    return ((String) actual).contains((String) value);
                case  "~=":
                    return ((String) actual).startsWith((String) value);
                case "=~":
                    return ((String) actual).endsWith((String) value);
                default:
                    throw new IllegalArgumentException();//не должно быть достижимо
            }
        }
        throw new UnsupportedOperationException("Невозможно применить операторы подобия не для строки: " + value.getClass().getName() + " " + actual.getClass().getName());
    }

    private boolean numberFilter(SchemaField schemaField, String filterOperator, Object value, Object actual) {
        if (value == null || actual == null) {
            return false;
        }
        if (value instanceof Comparable) {
            int result = ((Comparable) value).compareTo(actual);
            if ("uuid".equals(schemaField.getFormat()) || value instanceof LocalDateTime) {
                result *= -1;
            }
            switch (filterOperator) {
                case ">":
                    return result > 0;
                case ">=":
                    return result >= 0;
                case "<":
                    return result < 0;
                case "<=":
                    return result <= 0;
                default:
                    throw new IllegalArgumentException();//не должно быть достижимо
            }
        }
        throw new UnsupportedOperationException("Невозможно сравнить для " + value.getClass().getName() + " " + actual.getClass().getName());
    }


    private void assertFieldEquals(String message, Object expected, Object actual) {
        if (expected == null) {
            assertWrap(() -> assertNull(message, actual));
            return;
        }
        if (expected instanceof String ||
                expected.getClass().isEnum() ||
                expected instanceof Boolean ||
                expected instanceof Double) {
            assertWrap(() -> assertEquals(message, expected, actual));
            return;
        }
        if (expected instanceof MetaEntity) {
            assertWrap(() ->  {
                assertTrue(message, actual instanceof MetaEntity);
                assertEquals(message, extractId((MetaEntity) expected), extractId((MetaEntity) actual));
            });
            return;
        }
        if (expected instanceof Meta) {
            assertWrap(() ->  {
                assertTrue(message, actual instanceof Meta);
                assertEquals(message, extractId((Meta) expected), extractId((Meta) actual));
            });
            return;
        }
        if (expected instanceof List) {
            assertWrap(() ->  {
                assertTrue(message, actual instanceof List);
                assertEquals(message, ((List) expected).size(), ((List) actual).size());
            });
            int i = 0;
            for (Object o : (List) expected) {
                assertFieldEquals(message + ": сравнение " + i + " позиции списка", o, ((List) actual).get(i++));
            }
            return;
        }
        if (expected instanceof LocalDateTime) {
            assertWrap(() ->  {
                assertTrue(message, actual instanceof LocalDateTime);
                assertEquals(message, expected, actual);
            });
            return;
        }

        logger.warn("Cannot assert for " + message);
        schemaReport.log("Cannot assert for " + message);
    }

    private String extractId(MetaEntity metaEntity) {
        if (metaEntity == null) {
            return null;
        }
        String id = metaEntity.getId();
        if (id != null) {
            return id;
        }
        return extractId(metaEntity.getMeta());
    }

    private String extractId(Meta meta) {
        if (meta == null) {
            return null;
        }
        String href = meta.getHref();
        return href.substring(href.lastIndexOf("/") + 1);
    }

    private List<AbstractMap.SimpleEntry<SchemaField, Field>> updatableFields() {
        List<AbstractMap.SimpleEntry<SchemaField, Field>> updatableFields = new ArrayList<>();
        for (SchemaField schemaField : schema.getFields()) {
            if (schemaField.isUpdatable()) {
                try {
                    updatableFields.add(new AbstractMap.SimpleEntry<>(schemaField, SchemaReflectionUtils.getField(clazz, schemaField.getName())));
                } catch (NoSuchFieldException e) {
                    logger.warn("SDK entity does not have not nullable field " + schemaField.getName());
                }
            }
        }
        return updatableFields;
    }

    private void positiveCheckRequired(List<List<AbstractMap.SimpleEntry<SchemaField, Field>>> requiredFields) throws Exception {
        schemaReport.chapter("Создание сущностей со всеми обязательными полями");
        for (List<AbstractMap.SimpleEntry<SchemaField, Field>> requiredFieldSet : requiredFields) {
            T entity = schemaFiller.prepareEntity(clazz, requiredFieldSet);
            try {
                T response = post(entity);
                assertMinimumEntity(response);
            } catch (LognexApiException e) {
                schemaReport.log(e.getMessage());
            }
        }
        schemaReport.endChapter();
    }

    private void assertMinimumEntity(T entity) throws Exception {
        schemaReport.chapter("Проверяем not nullable поля");
        List<Field> notNullableFields = notNullableFields();
        for (Field field : notNullableFields) {
            if (field.get(entity) == null) {
                assertWrap(() -> fail("Сущность не имеет not nullable поля " + field.getName()));
            }
        }
        schemaReport.endChapter();
    }

    private void negativeCheckRequired(List<List<AbstractMap.SimpleEntry<SchemaField, Field>>> requiredFields) throws Exception {
        schemaReport.chapter("Проверяем создание без обязательных полей");
        for (List<AbstractMap.SimpleEntry<SchemaField, Field>> requiredFieldSet : requiredFields) {
            for (AbstractMap.SimpleEntry<SchemaField, Field> removedField : requiredFieldSet) {
                List<AbstractMap.SimpleEntry<SchemaField, Field>> incompleteSet =  new ArrayList<>(requiredFieldSet);
                incompleteSet.remove(removedField);
                T entity = schemaFiller.prepareEntity(clazz, incompleteSet);
                try {
                    post(entity);
                    fail("Ожидалось исключение LognexApiException! На создание " + clazz.getSimpleName() + " не было передано обязательное поле " + removedField.getValue().getName());
                } catch (LognexApiException e) {
                    assertWrap(() -> {
                        Optional<ErrorResponse.Error> error = e.getErrorResponse().getErrors().stream()
                                .filter(err -> err.getError().equals("Ошибка сохранения объекта: поле '" + removedField.getValue().getName() + "' не может быть пустым или отсутствовать"))
                                .findAny();
                        assertTrue("Ожидалась на отсутствие обязательного поля" +
                                        ", а получены " +
                                        e.getErrorResponse().getErrors().stream().map(ErrorResponse.Error::getError).collect(joining("; ")),
                                error.isPresent());
                    });
                }
            }
        }
        schemaReport.endChapter();
    }

    private List<List<AbstractMap.SimpleEntry<SchemaField, Field>>> requiredFields() {
        List<List<AbstractMap.SimpleEntry<SchemaField, Field>>> requiredFields = new ArrayList<>();
        for (List<String> requiredSet : schema.getRequired()) {
            List<AbstractMap.SimpleEntry<SchemaField, Field>> requiredFieldsSet = new ArrayList<>();
            for (String requiredField : requiredSet) {
                try {
                    requiredFieldsSet.add(new AbstractMap.SimpleEntry<>(getSchemaField(requiredField), SchemaReflectionUtils.getField(clazz, requiredField)));
                } catch (NoSuchFieldException e) {
                    fail("SDK entity does not have required field " + requiredField);
                }
            }
            requiredFields.add(requiredFieldsSet);
        }
        return requiredFields;
    }

    private List<Field> notNullableFields() {
        List<Field> fields = new ArrayList<>();
        for (SchemaField schemaField : schema.getFields()) {
            if (!schemaField.isNullable()) {
                try {
                    fields.add(SchemaReflectionUtils.getField(clazz, schemaField.getName()));
                } catch (NoSuchFieldException e) {
                    logger.warn("SDK entity does not have not nullable field " + schemaField.getName());
                }
            }
        }
        return fields;
    }

    private SchemaField getSchemaField(String fieldName) {
        return schema.getFields()
                .stream()
                .filter(f -> f.getName().equals(fieldName))
                .findAny()
                .orElseThrow(() -> new IllegalStateException("Schema fields do not contains " + fieldName));
    }

    private void assertWrap(Runnable procedure){
        try {
            procedure.run();
        } catch (AssertionError assertionError) {
            schemaReport.log(assertionError.getMessage());
        }
    }
}
