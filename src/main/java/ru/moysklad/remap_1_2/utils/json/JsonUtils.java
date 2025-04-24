package ru.moysklad.remap_1_2.utils.json;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import lombok.NoArgsConstructor;
import ru.moysklad.remap_1_2.entities.Meta;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public final class JsonUtils {
    public static ObjectMapper createObjectMapperWithMetaAdapter() {
        ObjectMapper objectMapper = new ObjectMapper();

        objectMapper.configure(com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        SimpleModule module = new SimpleModule();

        module.addSerializer(Meta.Type.class, new Meta.Type.Serializer());
        module.addDeserializer(Meta.Type.class, new Meta.Type.Deserializer());

        objectMapper.registerModule(module);

        return objectMapper;
    }
}
