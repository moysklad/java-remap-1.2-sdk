package com.lognex.api.serializers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.internal.LinkedTreeMap;
import com.lognex.api.LognexApi;
import com.lognex.api.entities.ContextEntity;
import com.lognex.api.entities.Meta;
import com.lognex.api.entities.MetaEntity;
import com.lognex.api.entities.agents.EmployeeEntity;
import com.lognex.api.entities.documents.DocumentPosition;
import com.lognex.api.responses.ListEntity;
import com.lognex.api.utils.TestAsserts;
import com.lognex.api.utils.TestRandomizers;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class ListEntityDeserializerTest implements TestAsserts, TestRandomizers {
    @Test
    public void test_deserialize() {
        Gson gson = new GsonBuilder().create();
        Gson gsonCustom = LognexApi.createGson(true);

        ListEntity<MetaEntity> e = new ListEntity<>();

        e.setMeta(new Meta());
        e.getMeta().setHref(randomString());
        e.getMeta().setMetadataHref(randomString());

        e.setContext(new ContextEntity());
        e.getContext().setEmployee(new EmployeeEntity());
        e.getContext().getEmployee().setId(randomString());

        e.setRows(new ArrayList<>());

        DocumentPosition m1 = new DocumentPosition();
        m1.setMeta(new Meta());
        m1.getMeta().setHref(randomString());
        m1.getMeta().setType(Meta.Type.demandposition);
        e.getRows().add(m1);

        DocumentPosition m2 = new DocumentPosition();
        m2.setMeta(new Meta());
        m2.getMeta().setHref(randomString());
        m2.getMeta().setType(Meta.Type.demandposition);
        e.getRows().add(m2);

        DocumentPosition m3 = new DocumentPosition();
        m3.setMeta(new Meta());
        m3.getMeta().setHref(randomString());
        m3.getMeta().setType(Meta.Type.demandposition);
        e.getRows().add(m3);

        String data = gson.toJson(e);

        ListEntity<DocumentPosition> parsed1 = gson.fromJson(data, ListEntity.class);
        ListEntity<DocumentPosition> parsed2 = gsonCustom.fromJson(data, ListEntity.class);

        assertEquals(e.getMeta(), parsed1.getMeta());
        assertEquals(e.getMeta(), parsed2.getMeta());

        assertEquals(e.getContext(), parsed1.getContext());
        assertEquals(e.getContext(), parsed2.getContext());

        assertTrue(parsed1.getRows() instanceof ArrayList);
        assertTrue(parsed2.getRows() instanceof ArrayList);

        assertFalse(parsed1.getRows().equals(e.getRows()));
        for (Object ee : parsed1.getRows()) {
            assertTrue(ee instanceof LinkedTreeMap);
        }

        assertTrue(parsed2.getRows().equals(e.getRows()));
        for (Object o : parsed2.getRows()) {
            assertTrue(o instanceof DocumentPosition);
        }
    }
}
