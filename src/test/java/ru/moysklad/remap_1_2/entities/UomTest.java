package ru.moysklad.remap_1_2.entities;

import org.junit.Test;
import ru.moysklad.remap_1_2.clients.EntityClientBase;
import ru.moysklad.remap_1_2.responses.ListEntity;
import ru.moysklad.remap_1_2.utils.ApiClientException;

import java.io.IOException;
import java.util.Date;

import static ru.moysklad.remap_1_2.utils.params.FilterParam.filterEq;
import static org.junit.Assert.*;

public class UomTest extends EntityGetUpdateDeleteTest {
    @Test
    public void createTest() throws IOException, ApiClientException {
        Uom uom = new Uom();
        uom.setName("uom_" + randomString(3) + "_" + new Date().getTime());
        uom.setDescription(randomString());
        uom.setCode(randomString());
        uom.setExternalCode(randomString());
        uom.setShared(true);
        uom.setOwner(simpleEntityManager.createSimpleEmployee());
        uom.setGroup(simpleEntityManager.getMainGroup());

        api.entity().uom().create(uom);

        ListEntity<Uom> updatedEntitiesList = api.entity().uom().get(filterEq("name", uom.getName()));
        assertEquals(1, updatedEntitiesList.getRows().size());

        Uom retrievedEntity = updatedEntitiesList.getRows().get(0);
        assertEquals(uom.getName(), retrievedEntity.getName());
        assertEquals(uom.getDescription(), retrievedEntity.getDescription());
        assertEquals(uom.getCode(), retrievedEntity.getCode());
        assertEquals(uom.getExternalCode(), retrievedEntity.getExternalCode());
        assertEquals(uom.getShared(), retrievedEntity.getShared());
        assertEquals(uom.getOwner(), retrievedEntity.getOwner());
        assertEquals(uom.getGroup(), retrievedEntity.getGroup());
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
