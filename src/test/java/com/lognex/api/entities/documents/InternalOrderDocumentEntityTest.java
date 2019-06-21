package com.lognex.api.entities.documents;

import com.lognex.api.clients.ApiClient;
import com.lognex.api.entities.MetaEntity;
import com.lognex.api.responses.ListEntity;
import com.lognex.api.responses.metadata.MetadataAttributeSharedStatesResponse;
import com.lognex.api.utils.LognexApiException;
import org.junit.Test;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

import static com.lognex.api.utils.params.FilterParam.filterEq;
import static org.junit.Assert.*;

public class InternalOrderDocumentEntityTest extends DocumentWithPositionsTestBase {
    @Test
    public void createTest() throws IOException, LognexApiException {
        InternalOrderDocumentEntity internalOrder = new InternalOrderDocumentEntity();
        internalOrder.setName("internalorder_" + randomString(3) + "_" + new Date().getTime());
        internalOrder.setDescription(randomString());
        internalOrder.setVatEnabled(true);
        internalOrder.setVatIncluded(true);
        internalOrder.setMoment(LocalDateTime.now());
        internalOrder.setOrganization(simpleEntityFactory.getOwnOrganization());
        internalOrder.setStore(simpleEntityFactory.getMainStore());

        api.entity().internalorder().post(internalOrder);

        ListEntity<InternalOrderDocumentEntity> updatedEntitiesList = api.entity().internalorder().get(filterEq("name", internalOrder.getName()));
        assertEquals(1, updatedEntitiesList.getRows().size());

        InternalOrderDocumentEntity retrievedEntity = updatedEntitiesList.getRows().get(0);
        assertEquals(internalOrder.getName(), retrievedEntity.getName());
        assertEquals(internalOrder.getDescription(), retrievedEntity.getDescription());
        assertEquals(internalOrder.getVatEnabled(), retrievedEntity.getVatEnabled());
        assertEquals(internalOrder.getVatIncluded(), retrievedEntity.getVatIncluded());
        assertEquals(internalOrder.getMoment(), retrievedEntity.getMoment());
        assertEquals(internalOrder.getOrganization().getMeta().getHref(), retrievedEntity.getOrganization().getMeta().getHref());
        assertEquals(internalOrder.getStore().getMeta().getHref(), retrievedEntity.getStore().getMeta().getHref());
    }

    @Test
    public void metadataTest() throws IOException, LognexApiException {
        MetadataAttributeSharedStatesResponse response = api.entity().internalorder().metadata().get();

        assertFalse(response.getCreateShared());
    }

    @Test
    public void newTest() throws IOException, LognexApiException {
        InternalOrderDocumentEntity internalOrder = api.entity().internalorder().newDocument();
        LocalDateTime time = LocalDateTime.now();

        assertEquals("", internalOrder.getName());
        assertTrue(internalOrder.getVatEnabled());
        assertTrue(internalOrder.getVatIncluded());
        assertEquals(Long.valueOf(0), internalOrder.getSum());
        assertFalse(internalOrder.getShared());
        assertTrue(internalOrder.getApplicable());
        assertTrue(ChronoUnit.MILLIS.between(time, internalOrder.getMoment()) < 1000);

        assertEquals(internalOrder.getOrganization().getMeta().getHref(), simpleEntityFactory.getOwnOrganization().getMeta().getHref());
        assertEquals(internalOrder.getStore().getMeta().getHref(), simpleEntityFactory.getMainStore().getMeta().getHref());
        assertEquals(internalOrder.getGroup().getMeta().getHref(), simpleEntityFactory.getMainGroup().getMeta().getHref());
    }

    @Override
    protected void getAsserts(MetaEntity originalEntity, MetaEntity retrievedEntity) {
        InternalOrderDocumentEntity originalInternalOrder = (InternalOrderDocumentEntity) originalEntity;
        InternalOrderDocumentEntity retrievedInternalOrder = (InternalOrderDocumentEntity) retrievedEntity;

        assertEquals(originalInternalOrder.getName(), retrievedInternalOrder.getName());
        assertEquals(originalInternalOrder.getDescription(), retrievedInternalOrder.getDescription());
        assertEquals(originalInternalOrder.getOrganization().getMeta().getHref(), retrievedInternalOrder.getOrganization().getMeta().getHref());
        assertEquals(originalInternalOrder.getStore().getMeta().getHref(), retrievedInternalOrder.getStore().getMeta().getHref());
    }

    @Override
    protected void putAsserts(MetaEntity originalEntity, MetaEntity updatedEntity, Object changedField) {
        InternalOrderDocumentEntity originalInternalOrder = (InternalOrderDocumentEntity) originalEntity;
        InternalOrderDocumentEntity updatedInternalOrder = (InternalOrderDocumentEntity) updatedEntity;

        assertNotEquals(originalInternalOrder.getName(), updatedInternalOrder.getName());
        assertEquals(changedField, updatedInternalOrder.getName());
        assertEquals(originalInternalOrder.getDescription(), updatedInternalOrder.getDescription());
        assertEquals(originalInternalOrder.getOrganization().getMeta().getHref(), updatedInternalOrder.getOrganization().getMeta().getHref());
        assertEquals(originalInternalOrder.getStore().getMeta().getHref(), updatedInternalOrder.getStore().getMeta().getHref());
    }

    @Override
    protected ApiClient entityClient() {
        return api.entity().internalorder();
    }

    @Override
    protected Class<? extends MetaEntity> entityClass() {
        return InternalOrderDocumentEntity.class;
    }
}


