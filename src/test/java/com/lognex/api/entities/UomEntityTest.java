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
        UomEntity uomEntity = new UomEntity();
        uomEntity.setName("uom_" + randomString(3) + "_" + new Date().getTime());
        uomEntity.setDescription(randomString());
        uomEntity.setCode(randomString());
        uomEntity.setExternalCode(randomString());

        api.entity().uom().post(uomEntity);

        ListEntity<UomEntity> updatedEntitiesList = api.entity().uom().get(filterEq("name", uomEntity.getName()));
        assertEquals(1, updatedEntitiesList.getRows().size());

        UomEntity retrievedEntity = updatedEntitiesList.getRows().get(0);
        assertEquals(uomEntity.getName(), retrievedEntity.getName());
        assertEquals(uomEntity.getDescription(), retrievedEntity.getDescription());
        assertEquals(uomEntity.getCode(), retrievedEntity.getCode());
        assertEquals(uomEntity.getExternalCode(), retrievedEntity.getExternalCode());
    }

    @Test
    public void getTest() throws IOException, LognexApiException {
        UomEntity uomEntity = simpleEntityFactory.createSimpleUom();

        UomEntity retrievedEntity = api.entity().uom().get(uomEntity.getId());
        getAsserts(uomEntity, retrievedEntity);

        retrievedEntity = api.entity().uom().get(uomEntity);
        getAsserts(uomEntity, retrievedEntity);
    }

    @Test
    public void putTest() throws IOException, LognexApiException {
        UomEntity uomEntity = simpleEntityFactory.createSimpleUom();

        UomEntity retrievedOriginalEntity = api.entity().uom().get(uomEntity.getId());
        String name = "uom_" + randomString(3) + "_" + new Date().getTime();
        uomEntity.setName(name);
        api.entity().uom().put(uomEntity.getId(), uomEntity);
        putAsserts(uomEntity, retrievedOriginalEntity, name);

        retrievedOriginalEntity.set(uomEntity);

        name = "uom_" + randomString(3) + "_" + new Date().getTime();
        uomEntity.setName(name);
        api.entity().uom().put(uomEntity);
        putAsserts(uomEntity, retrievedOriginalEntity, name);
    }

    @Test
    public void deleteTest() throws IOException, LognexApiException {
        UomEntity uomEntity = simpleEntityFactory.createSimpleUom();

        ListEntity<UomEntity> entitiesList = api.entity().uom().get(filterEq("name", uomEntity.getName()));
        assertEquals((Integer) 1, entitiesList.getMeta().getSize());

        api.entity().uom().delete(uomEntity.getId());

        entitiesList = api.entity().uom().get(filterEq("name", uomEntity.getName()));
        assertEquals((Integer) 0, entitiesList.getMeta().getSize());
    }

    @Test
    public void deleteByIdTest() throws IOException, LognexApiException {
        UomEntity uomEntity = simpleEntityFactory.createSimpleUom();

        ListEntity<UomEntity> entitiesList = api.entity().uom().get(filterEq("name", uomEntity.getName()));
        assertEquals((Integer) 1, entitiesList.getMeta().getSize());

        api.entity().uom().delete(uomEntity);

        entitiesList = api.entity().uom().get(filterEq("name", uomEntity.getName()));
        assertEquals((Integer) 0, entitiesList.getMeta().getSize());
    }

    private void getAsserts(UomEntity uomEntity, UomEntity retrievedEntity) {
        assertEquals(uomEntity.getName(), retrievedEntity.getName());
        assertEquals(uomEntity.getCode(), retrievedEntity.getCode());
        assertEquals(uomEntity.getExternalCode(), retrievedEntity.getExternalCode());
    }

    private void putAsserts(UomEntity uomEntity, UomEntity retrievedOriginalEntity, String name) throws IOException, LognexApiException {
        UomEntity retrievedUpdatedEntity = api.entity().uom().get(uomEntity.getId());

        assertNotEquals(retrievedOriginalEntity.getName(), retrievedUpdatedEntity.getName());
        assertEquals(name, retrievedUpdatedEntity.getName());
        assertEquals(retrievedOriginalEntity.getCode(), retrievedUpdatedEntity.getCode());
        assertEquals(retrievedOriginalEntity.getExternalCode(), retrievedUpdatedEntity.getExternalCode());
    }
}
