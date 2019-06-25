package com.lognex.api.entities;

import com.lognex.api.clients.ApiClient;
import com.lognex.api.responses.ListEntity;
import com.lognex.api.utils.LognexApiException;
import org.junit.Test;

import java.io.IOException;
import java.util.Date;

import static com.lognex.api.utils.params.FilterParam.filterEq;
import static org.junit.Assert.*;

public class UomEntityTest extends EntityGetUpdateDeleteTest {
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

    @Override
    protected void getAsserts(MetaEntity originalEntity, MetaEntity retrievedEntity) {
        UomEntity originalUom = (UomEntity) originalEntity;
        UomEntity retrievedUom = (UomEntity) retrievedEntity;

        assertEquals(originalUom.getName(), retrievedUom.getName());
        assertEquals(originalUom.getCode(), retrievedUom.getCode());
        assertEquals(originalUom.getExternalCode(), retrievedUom.getExternalCode());
    }

    @Override
    protected void putAsserts(MetaEntity originalEntity, MetaEntity updatedEntity, Object changedField) {
        UomEntity originalUom = (UomEntity) originalEntity;
        UomEntity updatedUom = (UomEntity) updatedEntity;

        assertNotEquals(originalUom.getName(), updatedUom.getName());
        assertEquals(changedField, updatedUom.getName());
        assertEquals(originalUom.getCode(), updatedUom.getCode());
        assertEquals(originalUom.getExternalCode(), updatedUom.getExternalCode());
    }

    @Override
    protected ApiClient entityClient() {
        return api.entity().uom();
    }

    @Override
    protected Class<? extends MetaEntity> entityClass() {
        return UomEntity.class;
    }
}
