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
public class Publication extends MetaEntity {
    private Template template;
    private String href;

    private transient PublicationType type;

    public enum PublicationType {
        OPERATION,
        CONTRACT
    }

    public static class Deserializer extends JsonDeserializer<Publication> {
        private final ObjectMapper objectMapper = JsonUtils.createObjectMapperWithMetaAdapter();

        @Override
        public Publication deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
            JsonNode node = p.getCodec().readTree(p);
            Publication publication = objectMapper.treeToValue(node, Publication.class);

            if (publication.getMeta() != null) {
                if (publication.getMeta().getType() == null) {
                    throw new JsonParseException(p, "Can't parse publication: meta.type is null");
                }
                switch (publication.getMeta().getType()) {
                    case CONTRACT_PUBLICATION:
                        publication.setType(PublicationType.CONTRACT);
                        break;
                    case OPERATION_PUBLICATION:
                        publication.setType(PublicationType.OPERATION);
                        break;
                    default:
                        throw new JsonParseException(p, "Can't parse publication: meta.type must be one of [\"contractpublication\", \"operationpublication\"]");
                }
            }

            return publication;
        }
    }
}
