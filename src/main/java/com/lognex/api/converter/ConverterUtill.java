package com.lognex.api.converter;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.lognex.api.util.DateUtils;
import com.lognex.api.util.ID;
import com.lognex.api.util.MetaHrefParser;

import java.util.Date;
import java.util.Optional;

import static org.apache.http.util.TextUtils.isEmpty;

public class ConverterUtill {
    private static String HREF = "href";
    private static String META = "meta";

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
                return MetaHrefParser.getId(href);
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

    private static Optional<JsonNode> getElement(JsonNode node, String fieldName) {
        return Optional.ofNullable(!isEmpty(fieldName) && node != null && node.has(fieldName) ? node.get(fieldName) : null);
    }

}
