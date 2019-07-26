package com.lognex.api.entities.documents;

import com.lognex.api.clients.EntityClientBase;
import com.lognex.api.entities.MetaEntity;
import com.lognex.api.responses.ListEntity;
import com.lognex.api.responses.metadata.MetadataAttributeSharedStatesResponse;
import com.lognex.api.utils.ApiClientException;
import org.junit.Test;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Date;

import static com.lognex.api.utils.params.FilterParam.filterEq;
import static org.junit.Assert.*;

public class EnterTest extends DocumentWithPositionsTestBase {
    @Test
    public void createTest() throws IOException, ApiClientException {
        Enter enter = new Enter();
        enter.setName("enter_" + randomString(3) + "_" + new Date().getTime());
        enter.setDescription(randomString());
        enter.setMoment(LocalDateTime.now());
        enter.setOrganization(simpleEntityManager.getOwnOrganization());
        enter.setStore(simpleEntityManager.getMainStore());

        api.entity().enter().create(enter);

        ListEntity<Enter> updatedEntitiesList = api.entity().enter().get(filterEq("name", enter.getName()));
        assertEquals(1, updatedEntitiesList.getRows().size());

        Enter retrievedEntity = updatedEntitiesList.getRows().get(0);
        assertEquals(enter.getName(), retrievedEntity.getName());
        assertEquals(enter.getDescription(), retrievedEntity.getDescription());
        assertEquals(enter.getMoment(), retrievedEntity.getMoment());
        assertEquals(enter.getOrganization().getMeta().getHref(), retrievedEntity.getOrganization().getMeta().getHref());
        assertEquals(enter.getStore().getMeta().getHref(), retrievedEntity.getStore().getMeta().getHref());
    }

    @Test
    public void metadataTest() throws IOException, ApiClientException {
        MetadataAttributeSharedStatesResponse response = api.entity().enter().metadata().get();

        assertFalse(response.getCreateShared());
    }

    @Override
    protected void getAsserts(MetaEntity originalEntity, MetaEntity retrievedEntity) {
        Enter originalEnter = (Enter) originalEntity;
        Enter retrievedEnter = (Enter) retrievedEntity;

        assertEquals(originalEnter.getName(), retrievedEnter.getName());
        assertEquals(originalEnter.getDescription(), retrievedEnter.getDescription());
        assertEquals(originalEnter.getOrganization().getMeta().getHref(), retrievedEnter.getOrganization().getMeta().getHref());
        assertEquals(originalEnter.getStore().getMeta().getHref(), retrievedEnter.getStore().getMeta().getHref());
    }

    @Override
    protected void putAsserts(MetaEntity originalEntity, MetaEntity updatedEntity, Object changedField) {
        Enter originalEnter = (Enter) originalEntity;
        Enter updatedEnter = (Enter) updatedEntity;

        assertNotEquals(originalEnter.getName(), updatedEnter.getName());
        assertEquals(changedField, updatedEnter.getName());
        assertEquals(originalEnter.getDescription(), updatedEnter.getDescription());
        assertEquals(originalEnter.getOrganization().getMeta().getHref(), updatedEnter.getOrganization().getMeta().getHref());
        assertEquals(originalEnter.getStore().getMeta().getHref(), updatedEnter.getStore().getMeta().getHref());
    }

    @Override
    protected EntityClientBase entityClient() {
        return api.entity().enter();
    }

    @Override
    protected Class<? extends MetaEntity> entityClass() {
        return Enter.class;
    }
}
