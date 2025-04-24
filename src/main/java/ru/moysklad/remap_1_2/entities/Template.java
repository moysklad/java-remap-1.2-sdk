package ru.moysklad.remap_1_2.entities;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.moysklad.remap_1_2.utils.json.JsonUtils;

import java.io.IOException;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Template extends MetaEntity {
    private Type type;
    private String content;
    private transient Meta.Type entityType;

    public Template(String id) {
        super(id);
    }

    public enum Type {
        /**
         * Документ
         */
        entity,

        /**
         * Ценник/этикетка
         */
        pricetype,

        /**
         * Ценник/этикетка нового формата
         */
        mxtemplate
    }

    private transient Boolean isEmbedded;

    public static class Deserializer extends JsonDeserializer<Template> {
        private final ObjectMapper objectMapper = JsonUtils.createObjectMapperWithMetaAdapter();

        @Override
        public Template deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
            JsonNode node = p.getCodec().readTree(p);
            Template template = objectMapper.treeToValue(node, Template.class);

            if (template.getMeta() != null) {
                if (template.getMeta().getType() == null) {
                    throw new JsonParseException(p, "Can't parse template: meta.type is null");
                }
                template.setIsEmbedded(template.getMeta().getType() != Meta.Type.CUSTOM_TEMPLATE);
            }

            return template;
        }
    }
}
