package com.lognex.api.converter;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.lognex.api.converter.base.Converter;
import com.lognex.api.model.base.Entity;
import com.lognex.api.util.DateUtils;
import com.lognex.api.util.ID;
import com.lognex.api.util.MetaHrefUtils;
import com.lognex.api.util.Type;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.*;

import static org.apache.http.util.TextUtils.isEmpty;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ConverterUtil {
    private static final String HREF = "href";
    private static final String META = "meta";
    private static final String ROWS = "rows";
    private static final String TYPE = "type";

    public static String getString(JsonNode node, String fieldName) {
        return getElement(node, fieldName)
                .map(JsonNode::asText).orElse(null);
    }

    public static boolean getBoolean(JsonNode node, String fieldName) {
        return getElement(node, fieldName)
                .map(JsonNode::asBoolean).orElse(false);
    }

    public static long getLong(JsonNode node, String fieldName) {
        return getElement(node, fieldName)
                .map(JsonNode::asLong).orElse(0L);
    }

    public static double getDouble(JsonNode node, String fieldName) {
        return getElement(node, fieldName)
                .map(JsonNode::asDouble).orElse(0d);
    }

    public static int getInt(JsonNode node, String fieldName) {
        return getElement(node, fieldName)
                .map(JsonNode::asInt).orElse(0);
    }

    public static ArrayNode getArray(JsonNode node, String fieldName) {
        Optional<JsonNode> colnElement = getElement(node, fieldName);
        if (colnElement.isPresent()) {
            JsonNode arrayNode = colnElement.get();
            if (arrayNode.isArray()) {
                return ((ArrayNode) arrayNode);
            }
        }
        return null;
    }

    public static ID getIdFromMetaHref(JsonNode node) {
        String href = getMetaHref(node);
        return !isEmpty(href) ? MetaHrefUtils.getId(href) : null;
    }

    public static String getMetaHref(JsonNode node) {
        Optional<JsonNode> metaElement = getElement(node, META);
        if (metaElement.isPresent()) {
            return getString(metaElement.get(), HREF);
        }
        return null;
    }

    public static ID getId(JsonNode node, String fieldName) {
        String idValue = getString(node, fieldName);
        return !isEmpty(idValue) ? new ID(idValue) : null;
    }

    public static Date getDate(JsonNode node, String fieldName) {
        String dateValue = getString(node, fieldName);
        return !isEmpty(dateValue) ? DateUtils.parseDate(dateValue) : null;
    }

    public static <T extends Entity> T getObject(JsonNode node, String fieldName, Converter<T> converter) {
        Optional<JsonNode> element = getElement(node, fieldName);
        return element.map(jsonNode -> converter.convert(jsonNode.toString())).orElse(null);
    }

    public static <T extends Entity> T getObject(JsonNode node, String fieldName, Converter<T> converter, T entity){
        Optional<JsonNode> element = getElement(node, fieldName);
        if (element.isPresent()) {
            JsonNode field = element.get();
            if (field.has("id")) {
                return element.map(jsonNode -> converter.convert(jsonNode.toString())).orElse(null);
            } else if (field.has(META)) {
                ID id = MetaHrefUtils.getId(field.get(META).get(HREF).asText());
                entity.setId(id);
                return entity;
            }
        }
        return null;
    }

    public static <T extends Entity> T getObject(JsonNode node, String fieldName) {
        Optional<JsonNode> element = getElement(node, fieldName);
        if (element.isPresent()) {
            JsonNode typeElement = element.get().findValue(TYPE);
            if (typeElement != null && !isEmpty(typeElement.asText())) {
                return ((T) ConverterFactory.getConverter(Type.find(typeElement.asText()).getModelClass())
                        .convert(node.toString()));
            }
        }
        return null;
    }

    public static <T extends Entity> List<T> getList(JsonNode node, String fieldName, Converter<T> converter) {
        List<T> result = new ArrayList<>();
        ArrayNode array = ConverterUtil.getArray(node, fieldName);
        if (array != null) {
            for (JsonNode item : array) {
                result.add(converter.convert(item.toString()));
            }
        }
        return result;
    }

    public static <T extends Entity> List<T> getListFromExpand(JsonNode node, String fieldName, Converter<T> converter) {
        Optional<JsonNode> expandElement = getElement(node, fieldName);
        if (expandElement.isPresent()) {
            return getList(expandElement.get(), ROWS, converter);
        }
        return Collections.emptyList();
    }

    private static Optional<JsonNode> getElement(JsonNode node, String fieldName) {
        return Optional.ofNullable(!isEmpty(fieldName) && node != null && node.has(fieldName) ? node.get(fieldName) : null);
    }

}
