package com.lognex.api.entities;

import com.lognex.api.responses.ListEntity;
import com.lognex.api.utils.LognexApiException;
import org.junit.Test;

import java.io.IOException;
import java.util.Date;

import static com.lognex.api.utils.params.FilterParam.filterEq;
import static org.junit.Assert.*;

public class UomEntityTest extends EntityTestBase {
    @Test
    public void createTest() throws IOException, LognexApiException {
        UomEntity e = new UomEntity();
        e.setName("uom_" + randomString(3) + "_" + new Date().getTime());
        e.setDescription(randomString());
        e.setCode(randomString());
        e.setExternalCode(randomString());

        api.entity().uom().post(e);

        ListEntity<UomEntity> updatedEntitiesList = api.entity().uom().get(filterEq("name", e.getName()));
        assertEquals(1, updatedEntitiesList.getRows().size());

        UomEntity retrievedEntity = updatedEntitiesList.getRows().get(0);
        assertEquals(e.getName(), retrievedEntity.getName());
        assertEquals(e.getDescription(), retrievedEntity.getDescription());
        assertEquals(e.getCode(), retrievedEntity.getCode());
        assertEquals(e.getExternalCode(), retrievedEntity.getExternalCode());
    }

    @Test
    public void getTest() throws IOException, LognexApiException {
        UomEntity e = createSimpleUom();

        UomEntity retrievedEntity = api.entity().uom().get(e.getId());
        getAsserts(e, retrievedEntity);

        retrievedEntity = api.entity().uom().get(e);
        getAsserts(e, retrievedEntity);
    }

    @Test
    public void putTest() throws IOException, LognexApiException, InterruptedException {
        UomEntity e = createSimpleUom();

        UomEntity retrievedOriginalEntity = api.entity().uom().get(e.getId());
        String name = "uom_" + randomString(3) + "_" + new Date().getTime();
        e.setName(name);
        api.entity().uom().put(e.getId(), e);
        putAsserts(e, retrievedOriginalEntity, name);

        retrievedOriginalEntity.set(e);

        name = "uom_" + randomString(3) + "_" + new Date().getTime();
        e.setName(name);
        api.entity().uom().put(e);
        putAsserts(e, retrievedOriginalEntity, name);
    }

    @Test
    public void deleteTest() throws IOException, LognexApiException {
        UomEntity e = createSimpleUom();

        ListEntity<UomEntity> entitiesList = api.entity().uom().get(filterEq("name", e.getName()));
        assertEquals(1, entitiesList.getRows().size());

        api.entity().uom().delete(e.getId());

        entitiesList = api.entity().uom().get(filterEq("name", e.getName()));
        assertEquals(0, entitiesList.getRows().size());
    }

    @Test
    public void deleteByIdTest() throws IOException, LognexApiException {
        UomEntity e = createSimpleUom();

        ListEntity<UomEntity> entitiesList = api.entity().uom().get(filterEq("name", e.getName()));
        assertEquals(1, entitiesList.getRows().size());

        api.entity().uom().delete(e);

        entitiesList = api.entity().uom().get(filterEq("name", e.getName()));
        assertEquals(0, entitiesList.getRows().size());
    }

    private UomEntity createSimpleUom() throws IOException, LognexApiException {
        UomEntity e = new UomEntity();
        e.setName("uom_" + randomString(3) + "_" + new Date().getTime());
        e.setDescription(randomString());
        e.setCode(randomString());
        e.setExternalCode(randomString());

        api.entity().uom().post(e);

        return e;
    }

    private void getAsserts(UomEntity e, UomEntity retrievedEntity) {
        assertEquals(e.getName(), retrievedEntity.getName());
        assertEquals(e.getDescription(), retrievedEntity.getDescription());
        assertEquals(e.getCode(), retrievedEntity.getCode());
        assertEquals(e.getExternalCode(), retrievedEntity.getExternalCode());
    }

    private void putAsserts(UomEntity e, UomEntity retrievedOriginalEntity, String name) throws IOException, LognexApiException {
        UomEntity retrievedUpdatedEntity = api.entity().uom().get(e.getId());

        assertNotEquals(retrievedOriginalEntity.getName(), retrievedUpdatedEntity.getName());
        assertEquals(name, retrievedUpdatedEntity.getName());
        assertEquals(retrievedOriginalEntity.getDescription(), retrievedUpdatedEntity.getDescription());
        assertEquals(retrievedOriginalEntity.getCode(), retrievedUpdatedEntity.getCode());
        assertEquals(retrievedOriginalEntity.getExternalCode(), retrievedUpdatedEntity.getExternalCode());
    }
}
