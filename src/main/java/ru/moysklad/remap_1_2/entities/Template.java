package ru.moysklad.remap_1_2.entities;

import com.google.gson.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.moysklad.remap_1_2.utils.json.JsonUtils;

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

    public static class Deserializer implements JsonDeserializer<Template> {
        private final Gson gson = JsonUtils.createGsonWithMetaAdapter();

        @Override
        public Template deserialize(JsonElement json, java.lang.reflect.Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            Template template = gson.fromJson(json, Template.class);

            if (template.getMeta() != null) {
                if (template.getMeta().getType() == null) {
                    throw new JsonParseException("Can't parse template: meta.type is null");
                }
                template.setIsEmbedded(template.getMeta().getType() != Meta.Type.CUSTOM_TEMPLATE);
            }

            return template;
        }
    }
}
