package com.lognex.api.serializers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lognex.api.ApiClient;
import com.lognex.api.entities.CustomEntity;
import com.lognex.api.entities.Meta;
import com.lognex.api.responses.metadata.CompanySettingsMetadata.CustomEntityMetadata;
import com.lognex.api.utils.TestAsserts;
import com.lognex.api.utils.TestRandomizers;
import org.junit.Test;

import static org.junit.Assert.*;

public class CustomEntityMetadataDeserializerTest implements TestAsserts, TestRandomizers {
    @Test
    public void test_deserialize() {
        Gson gson = new GsonBuilder().create();
        Gson gsonCustom = ApiClient.createGson(true);

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

        CustomEntityMetadata parsed1 = gson.fromJson(data, CustomEntityMetadata.class);
        CustomEntityMetadata parsed2 = gsonCustom.fromJson(data, CustomEntityMetadata.class);

        assertEquals(e.getMeta().getHref(), parsed1.getMeta().getHref());
        assertEquals(e.getMeta().getHref(), parsed2.getMeta().getHref());

        assertEquals(e.getName(), parsed1.getName());
        assertEquals(e.getName(), parsed2.getName());

        assertEquals(e.getCreateShared(), parsed1.getCreateShared());
        assertEquals(e.getCreateShared(), parsed2.getCreateShared());

        assertNull(parsed1.getEntityMeta().getMeta());
        assertNotNull(parsed2.getEntityMeta().getMeta());

        assertEquals(e.getEntityMeta().getMeta().getHref(), parsed2.getEntityMeta().getMeta().getHref());
        assertEquals(e.getName(), parsed2.getEntityMeta().getName());
        assertEquals(e.getEntityMeta().getMeta().getUuidHref(), parsed2.getEntityMeta().getMeta().getUuidHref());
        assertEquals(id, parsed2.getEntityMeta().getId());
    }
}