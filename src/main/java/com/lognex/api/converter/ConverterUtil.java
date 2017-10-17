package com.lognex.api.converter;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.lognex.api.converter.base.Converter;
import com.lognex.api.model.base.AbstractEntity;
import com.lognex.api.util.DateUtils;
import com.lognex.api.util.ID;
import com.lognex.api.util.MetaHrefUtils;

import java.util.*;

import static org.apache.http.util.TextUtils.isEmpty;

public class ConverterUtil {
    private static final String HREF = "href";
    private static final String META = "meta";
    private static final String ROWS = "rows";

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
        Optional<JsonNode> metaElement = getElement(node, META);
        if (metaElement.isPresent()) {
            String href = getString(metaElement.get(), HREF);
            if (!isEmpty(href)) {
                return MetaHrefUtils.getId(href);
            }
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

    public static <T extends AbstractEntity> T getObject(JsonNode node, String fieldName, Converter<T> converter) {
        Optional<JsonNode> element = getElement(node, fieldName);
        return element.map(jsonNode -> converter.convert(jsonNode.toString())).orElse(null);
    }

    public static <T extends AbstractEntity> List<T> getList(JsonNode node, String fieldName, Converter<T> converter) {
        List<T> result = new ArrayList<>();
        ArrayNode array = getArray(node, fieldName);
        if (array != null) {
            for (JsonNode item : array) {
                result.add(converter.convert(item.toString()));
            }
        }
        return result;
    }

    public static <T extends AbstractEntity> List<T> getListFromExpand(JsonNode node, String fieldName, Converter<T> converter) {
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
