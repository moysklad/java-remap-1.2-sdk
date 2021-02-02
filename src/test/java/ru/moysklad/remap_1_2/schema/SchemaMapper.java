package ru.moysklad.remap_1_2.schema;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import ru.moysklad.remap_1_2.utils.TestUtils;

import java.io.IOException;
import java.util.*;

import static ru.moysklad.remap_1_2.schema.SchemaMapper.Constants.*;

public class SchemaMapper implements TestUtils {

    private static final ObjectMapper mapper = new ObjectMapper();

    public static Schema readSchema(String fileName) throws IOException {
        return readSchemaFromJson(TestUtils.getFile(fileName));
    }
    public static Schema readSchema(JsonNode schemaNode) throws IOException {
        if (schemaNode == null) return null;
        Schema schema = new Schema();
        if (schemaNode.has(ref)) {
            return readSchema(schemaNode.get(ref).asText());
        }
        schema.setTitle(toString(schemaNode.get(title)));
        schema.getFields().addAll(mapFields(schemaNode, fields));
        schema.getAdditionalFields().addAll(mapFields(schemaNode, additionalFields));
        schema.getRequired().addAll(mapRequired(schemaNode.get(required)));
        schema.getDefaultOrder().addAll(readStringArray(schemaNode.get(defaultOrder)));
        return schema;
    }

    public static Schema readSchemaFromJson(String json) throws IOException {
        JsonNode schemaNode = mapper.readTree(json);
        return readSchema(schemaNode);
    }

    private static List<List<String>> mapRequired(JsonNode node) {
        List<List<String>> result = new ArrayList<>();
        if (node != null && node.isArray()) {
            for (JsonNode element : node) {
                if (element != null && element.isArray()) {
                    result.add(readStringArray(element));
                }
            }
        }
        return result;
    }

    private static List<SchemaField> mapFields(JsonNode schemaNode, String node) throws IOException {
        List<SchemaField> schemaFields = new ArrayList<>();
        if (schemaNode.get(node) != null && schemaNode.get(fields) != null) {
            Iterator<Map.Entry<String, JsonNode>> jsonFields = schemaNode.get(fields).fields();
            while (jsonFields.hasNext()) {
                Map.Entry<String, JsonNode> nodeEntry = jsonFields.next();
                schemaFields.add(mapField(nodeEntry.getKey(), nodeEntry.getValue()));
            }
        }
        return schemaFields;
    }

    private static SchemaField mapField(String name, JsonNode jsonNode) throws IOException {
        if (jsonNode.has(ref)) {
            return mapField(name, mapper.readTree(TestUtils.getFile(jsonNode.get(ref).asText())));
        }
        SchemaField field = new SchemaField();
        field.setName(name);
        field.setType(toString(jsonNode.get(type)));
        field.setFormat(toString(jsonNode.get(format)));
        field.setNullable(toBoolean(jsonNode.get(nullable)));
        field.setOrderable(toBoolean(jsonNode.get(orderable)));
        field.setUpdatable(toBoolean(jsonNode.get(updatable)));
        field.getFields().addAll(mapFields(jsonNode, fields));
        field.setExpand(readSchema(jsonNode.get(expand)));
        field.getFilters().addAll(readStringArray(jsonNode.get(filters)));
        return field;
    }

    private static List<String> readStringArray(JsonNode node) {
        List<String> result = new ArrayList<>();
        if (node != null && node.isArray()) {
            for (JsonNode element : node) {
                result.add(toString(element));
            }
        }
        return result;
    }

    private static boolean toBoolean(JsonNode node) {
        if (node == null) return false;
        return node.asBoolean();
    }

    private static String toString(JsonNode node) {
        if (node == null) return null;
        return node.asText();
    }
    
    public static class Constants {
        public static final String ref = "$ref";
        public static final String title = "title";
        public static final String fields = "fields";
        public static final String additionalFields = "additionalFields";
        public static final String required = "required";
        public static final String defaultOrder = "defaultOrder";

        public static final String type = "type";
        public static final String format = "format";
        public static final String nullable = "nullable";
        public static final String orderable = "orderable";
        public static final String updatable = "updatable";
        public static final String expand = "expand";
        public static final String filters = "filters";
    }
}

