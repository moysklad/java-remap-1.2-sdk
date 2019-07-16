package com.lognex.api.entities;

import com.lognex.api.clients.EntityClientBase;
import com.lognex.api.responses.ListEntity;
import com.lognex.api.utils.LognexApiException;
import org.junit.Test;

import java.io.IOException;
import java.util.Date;

import static com.lognex.api.utils.params.FilterParam.filterEq;
import static org.junit.Assert.*;

public class UomTest extends EntityGetUpdateDeleteTest {
    @Test
    public void createTest() throws IOException, LognexApiException {
        Uom uom = new Uom();
        uom.setName("uom_" + randomString(3) + "_" + new Date().getTime());
        uom.setDescription(randomString());
        uom.setCode(randomString());
        uom.setExternalCode(randomString());

        api.entity().uom().create(uom);

        ListEntity<Uom> updatedEntitiesList = api.entity().uom().get(filterEq("name", uom.getName()));
        assertEquals(1, updatedEntitiesList.getRows().size());

        Uom retrievedEntity = updatedEntitiesList.getRows().get(0);
        assertEquals(uom.getName(), retrievedEntity.getName());
        assertEquals(uom.getDescription(), retrievedEntity.getDescription());
        assertEquals(uom.getCode(), retrievedEntity.getCode());
        assertEquals(uom.getExternalCode(), retrievedEntity.getExternalCode());
    }

    @Override
    protected void getAsserts(MetaEntity originalEntity, MetaEntity retrievedEntity) {
        Uom originalUom = (Uom) originalEntity;
        Uom retrievedUom = (Uom) retrievedEntity;

        assertEquals(originalUom.getName(), retrievedUom.getName());
        assertEquals(originalUom.getCode(), retrievedUom.getCode());
        assertEquals(originalUom.getExternalCode(), retrievedUom.getExternalCode());
    }

    @Override
    protected void putAsserts(MetaEntity originalEntity, MetaEntity updatedEntity, Object changedField) {
        Uom originalUom = (Uom) originalEntity;
        Uom updatedUom = (Uom) updatedEntity;

        assertNotEquals(originalUom.getName(), updatedUom.getName());
        assertEquals(changedField, updatedUom.getName());
        assertEquals(originalUom.getCode(), updatedUom.getCode());
        assertEquals(originalUom.getExternalCode(), updatedUom.getExternalCode());
    }

    @Override
    protected EntityClientBase entityClient() {
        return api.entity().uom();
    }

    @Override
    protected Class<? extends MetaEntity> entityClass() {
        return Uom.class;
    }
}
