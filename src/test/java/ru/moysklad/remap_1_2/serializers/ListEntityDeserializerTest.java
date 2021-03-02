package ru.moysklad.remap_1_2.serializers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.internal.LinkedTreeMap;
import org.junit.Test;
import ru.moysklad.remap_1_2.ApiClient;
import ru.moysklad.remap_1_2.entities.Context;
import ru.moysklad.remap_1_2.entities.Meta;
import ru.moysklad.remap_1_2.entities.MetaEntity;
import ru.moysklad.remap_1_2.entities.agents.Employee;
import ru.moysklad.remap_1_2.entities.documents.DocumentPosition;
import ru.moysklad.remap_1_2.entities.documents.positions.DemandDocumentPosition;
import ru.moysklad.remap_1_2.responses.ListEntity;
import ru.moysklad.remap_1_2.utils.TestAsserts;
import ru.moysklad.remap_1_2.utils.TestRandomizers;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class ListEntityDeserializerTest implements TestAsserts, TestRandomizers {
    @Test
    public void test_deserialize() {
        Gson gson = new GsonBuilder().create();
        Gson gsonCustom = ApiClient.createGson(true);

        ListEntity<MetaEntity> e = new ListEntity<>();

        e.setMeta(new Meta());
        e.getMeta().setHref(randomString());
        e.getMeta().setMetadataHref(randomString());

        e.setContext(new Context());
        e.getContext().setEmployee(new Employee());
        e.getContext().getEmployee().setId(randomString());

        e.setRows(new ArrayList<>());

        DocumentPosition m1 = new DemandDocumentPosition();
        m1.setMeta(new Meta());
        m1.getMeta().setHref(randomString());
        m1.getMeta().setType(Meta.Type.DEMAND_POSITION);
        e.getRows().add(m1);

        DocumentPosition m2 = new DemandDocumentPosition();
        m2.setMeta(new Meta());
        m2.getMeta().setHref(randomString());
        m2.getMeta().setType(Meta.Type.DEMAND_POSITION);
        e.getRows().add(m2);

        DocumentPosition m3 = new DemandDocumentPosition();
        m3.setMeta(new Meta());
        m3.getMeta().setHref(randomString());
        m3.getMeta().setType(Meta.Type.DEMAND_POSITION);
        e.getRows().add(m3);

        String data = gsonCustom.toJson(e);

        ListEntity<DemandDocumentPosition> parsed1 = gson.fromJson(data, ListEntity.class);
        ListEntity<DemandDocumentPosition> parsed2 = gsonCustom.fromJson(data, ListEntity.class);

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
            assertTrue(o instanceof DemandDocumentPosition);
        }
    }

    @Test
    public void test_deserializeListOfChildClasses() {
        Gson gsonCustom = ApiClient.createGson(true);
        ListEntity<MetaEntity> e = new ListEntity<>();

        e.setMeta(new Meta());
        e.getMeta().setHref(randomString());
        e.getMeta().setMetadataHref(randomString());

        e.setContext(new Context());
        e.getContext().setEmployee(new Employee());
        e.getContext().getEmployee().setId(randomString());

        e.setRows(new ArrayList<>());

        DemandDocumentPosition m1 = new DemandDocumentPosition();
        m1.setMeta(new Meta());
        m1.getMeta().setHref(randomString());
        m1.getMeta().setType(Meta.Type.DEMAND_POSITION);
        m1.setDiscount(10d);
        m1.setVat(15);
        m1.setQuantity(2d);
        e.getRows().add(m1);
        String data = gsonCustom.toJson(e);

        ListEntity<DemandDocumentPosition> parsed = gsonCustom.fromJson(data, ListEntity.class);
        assertEquals(1, parsed.getRows().size());
        assertEquals(Double.valueOf(10), parsed.getRows().get(0).getDiscount());
        assertEquals(Integer.valueOf(15), parsed.getRows().get(0).getVat());
        assertEquals(Double.valueOf(2), parsed.getRows().get(0).getQuantity());
    }
}
