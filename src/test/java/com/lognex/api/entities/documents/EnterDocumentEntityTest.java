package com.lognex.api.entities.documents;

import com.lognex.api.clients.ApiClient;
import com.lognex.api.entities.MetaEntity;
import com.lognex.api.responses.ListEntity;
import com.lognex.api.responses.metadata.MetadataAttributeSharedStatesResponse;
import com.lognex.api.utils.LognexApiException;
import org.junit.Test;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Date;

import static com.lognex.api.utils.params.FilterParam.filterEq;
import static org.junit.Assert.*;

public class EnterDocumentEntityTest extends DocumentWithPositionsTestBase {
    @Test
    public void createTest() throws IOException, LognexApiException {
        EnterDocumentEntity enter = new EnterDocumentEntity();
        enter.setName("enter_" + randomString(3) + "_" + new Date().getTime());
        enter.setDescription(randomString());
        enter.setMoment(LocalDateTime.now());
        enter.setOrganization(simpleEntityManager.getOwnOrganization());
        enter.setStore(simpleEntityManager.getMainStore());

        api.entity().enter().post(enter);

        ListEntity<EnterDocumentEntity> updatedEntitiesList = api.entity().enter().get(filterEq("name", enter.getName()));
        assertEquals(1, updatedEntitiesList.getRows().size());

        EnterDocumentEntity retrievedEntity = updatedEntitiesList.getRows().get(0);
        assertEquals(enter.getName(), retrievedEntity.getName());
        assertEquals(enter.getDescription(), retrievedEntity.getDescription());
        assertEquals(enter.getMoment(), retrievedEntity.getMoment());
        assertEquals(enter.getOrganization().getMeta().getHref(), retrievedEntity.getOrganization().getMeta().getHref());
        assertEquals(enter.getStore().getMeta().getHref(), retrievedEntity.getStore().getMeta().getHref());
    }

    @Test
    public void metadataTest() throws IOException, LognexApiException {
        MetadataAttributeSharedStatesResponse response = api.entity().enter().metadata().get();

        assertFalse(response.getCreateShared());
    }

    @Override
    protected void getAsserts(MetaEntity originalEntity, MetaEntity retrievedEntity) {
        EnterDocumentEntity originalEnter = (EnterDocumentEntity) originalEntity;
        EnterDocumentEntity retrievedEnter = (EnterDocumentEntity) retrievedEntity;

        assertEquals(originalEnter.getName(), retrievedEnter.getName());
        assertEquals(originalEnter.getDescription(), retrievedEnter.getDescription());
        assertEquals(originalEnter.getOrganization().getMeta().getHref(), retrievedEnter.getOrganization().getMeta().getHref());
        assertEquals(originalEnter.getStore().getMeta().getHref(), retrievedEnter.getStore().getMeta().getHref());
    }

    @Override
    protected void putAsserts(MetaEntity originalEntity, MetaEntity updatedEntity, Object changedField) {
        EnterDocumentEntity originalEnter = (EnterDocumentEntity) originalEntity;
        EnterDocumentEntity updatedEnter = (EnterDocumentEntity) updatedEntity;

        assertNotEquals(originalEnter.getName(), updatedEnter.getName());
        assertEquals(changedField, updatedEnter.getName());
        assertEquals(originalEnter.getDescription(), updatedEnter.getDescription());
        assertEquals(originalEnter.getOrganization().getMeta().getHref(), updatedEnter.getOrganization().getMeta().getHref());
        assertEquals(originalEnter.getStore().getMeta().getHref(), updatedEnter.getStore().getMeta().getHref());
    }

    @Override
    protected ApiClient entityClient() {
        return api.entity().enter();
    }

    @Override
    protected Class<? extends MetaEntity> entityClass() {
        return EnterDocumentEntity.class;
    }
}
