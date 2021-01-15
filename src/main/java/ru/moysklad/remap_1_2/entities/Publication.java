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
public class Publication extends MetaEntity {
    private Template template;
    private String href;

    private transient PublicationType type;

    public enum PublicationType {
        OPERATION,
        CONTRACT
    }

    public static class Deserializer implements JsonDeserializer<Publication> {
        private final Gson gson = JsonUtils.createGsonWithMetaAdapter();

        @Override
        public Publication deserialize(JsonElement json, java.lang.reflect.Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            Publication publication = gson.fromJson(json, Publication.class);

            if (publication.getMeta() != null) {
                if (publication.getMeta().getType() == null) {
                    throw new JsonParseException("Can't parse publication: meta.type is null");
                }
                switch (publication.getMeta().getType()) {
                    case CONTRACT_PUBLICATION:
                        publication.setType(PublicationType.CONTRACT);
                        break;
                    case OPERATION_PUBLICATION:
                        publication.setType(PublicationType.OPERATION);
                        break;
                    default:
                        throw new JsonParseException("Can't parse publication: meta.type must be one of [\"contractpublication\", \"operationpublication\"]");
                }
            }

            return publication;
        }
    }
}
