package ru.moysklad.remap_1_2.serializers;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ru.moysklad.remap_1_2.ApiClient;
import ru.moysklad.remap_1_2.entities.CustomEntity;
import ru.moysklad.remap_1_2.entities.Meta;
import ru.moysklad.remap_1_2.responses.metadata.CompanySettingsMetadata.CustomEntityMetadata;
import ru.moysklad.remap_1_2.utils.TestAsserts;
import ru.moysklad.remap_1_2.utils.TestRandomizers;
import org.junit.Test;

import static org.junit.Assert.*;

public class CustomEntityMetadataDeserializerTest implements TestAsserts, TestRandomizers {
    @Test
    public void test_deserialize() throws JsonProcessingException {
        ObjectMapper objectMapperCustom = ApiClient.createObjectMapper(false);

        CustomEntityMetadata e = new CustomEntityMetadata();

        String id = randomString();
        e.setMeta(new Meta());
        e.getMeta().setHref(randomString() + "/" + id);

        e.setEntityMeta(new CustomEntity());
        e.getEntityMeta().setMeta(new Meta());
        e.getEntityMeta().getMeta().setHref(randomString() + "/" + id);
        e.getEntityMeta().getMeta().setUuidHref(randomString() + "/#custom_" + id);
        e.setCreateShared(true);
        e.setName(randomString());

        String data = "{\n" +
                "            \"meta\": {\n" +
                "                \"href\": \"" + e.getMeta().getHref() + "\",\n" +
                "                \"type\": \"customentitymetadata\",\n" +
                "                \"mediaType\": \"application/json\"\n" +
                "            },\n" +
                "            \"entityMeta\": {\n" +
                "                \"href\": \"" + e.getEntityMeta().getMeta().getHref() + "\",\n" +
                "                \"type\": \"customentity\",\n" +
                "                \"mediaType\": \"application/json\",\n" +
                "                \"uuidHref\": \"" + e.getEntityMeta().getMeta().getUuidHref() + "\"\n" +
                "            },\n" +
                "            \"name\": \"" + e.getName() + "\",\n" +
                "            \"createShared\": " + e.getCreateShared().toString() + "\n" +
                "        }";

        CustomEntityMetadata parsed = objectMapperCustom.readValue(data, CustomEntityMetadata.class);

        assertEquals(e.getMeta().getHref(), parsed.getMeta().getHref());

        assertEquals(e.getName(), parsed.getName());

        assertEquals(e.getCreateShared(), parsed.getCreateShared());

        assertNotNull(parsed.getEntityMeta().getMeta());

        assertEquals(e.getEntityMeta().getMeta().getHref(), parsed.getEntityMeta().getMeta().getHref());
        assertEquals(e.getName(), parsed.getEntityMeta().getName());
        assertEquals(e.getEntityMeta().getMeta().getUuidHref(), parsed.getEntityMeta().getMeta().getUuidHref());
        assertEquals(id, parsed.getEntityMeta().getId());
    }
}