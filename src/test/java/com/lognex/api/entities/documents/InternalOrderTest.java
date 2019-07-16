package com.lognex.api.entities.documents;

import com.lognex.api.clients.EntityClientBase;
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

public class InternalOrderTest extends DocumentWithPositionsTestBase {
    @Test
    public void createTest() throws IOException, LognexApiException {
        InternalOrder internalOrder = new InternalOrder();
        internalOrder.setName("internalorder_" + randomString(3) + "_" + new Date().getTime());
        internalOrder.setDescription(randomString());
        internalOrder.setVatEnabled(true);
        internalOrder.setVatIncluded(true);
        internalOrder.setMoment(LocalDateTime.now());
        internalOrder.setOrganization(simpleEntityManager.getOwnOrganization());
        internalOrder.setStore(simpleEntityManager.getMainStore());

        api.entity().internalorder().create(internalOrder);

        ListEntity<InternalOrder> updatedEntitiesList = api.entity().internalorder().get(filterEq("name", internalOrder.getName()));
        assertEquals(1, updatedEntitiesList.getRows().size());

        InternalOrder retrievedEntity = updatedEntitiesList.getRows().get(0);
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
        InternalOrder internalOrder = api.entity().internalorder().templateDocument();
        LocalDateTime time = LocalDateTime.now();

        assertEquals("", internalOrder.getName());
        assertTrue(internalOrder.getVatEnabled());
        assertTrue(internalOrder.getVatIncluded());
        assertEquals(Long.valueOf(0), internalOrder.getSum());
        assertFalse(internalOrder.getShared());
        assertTrue(internalOrder.getApplicable());
        assertTrue(ChronoUnit.MILLIS.between(time, internalOrder.getMoment()) < 1000);

        assertEquals(internalOrder.getOrganization().getMeta().getHref(), simpleEntityManager.getOwnOrganization().getMeta().getHref());
        assertEquals(internalOrder.getStore().getMeta().getHref(), simpleEntityManager.getMainStore().getMeta().getHref());
        assertEquals(internalOrder.getGroup().getMeta().getHref(), simpleEntityManager.getMainGroup().getMeta().getHref());
    }

    @Override
    protected void getAsserts(MetaEntity originalEntity, MetaEntity retrievedEntity) {
        InternalOrder originalInternalOrder = (InternalOrder) originalEntity;
        InternalOrder retrievedInternalOrder = (InternalOrder) retrievedEntity;

        assertEquals(originalInternalOrder.getName(), retrievedInternalOrder.getName());
        assertEquals(originalInternalOrder.getDescription(), retrievedInternalOrder.getDescription());
        assertEquals(originalInternalOrder.getOrganization().getMeta().getHref(), retrievedInternalOrder.getOrganization().getMeta().getHref());
        assertEquals(originalInternalOrder.getStore().getMeta().getHref(), retrievedInternalOrder.getStore().getMeta().getHref());
    }

    @Override
    protected void putAsserts(MetaEntity originalEntity, MetaEntity updatedEntity, Object changedField) {
        InternalOrder originalInternalOrder = (InternalOrder) originalEntity;
        InternalOrder updatedInternalOrder = (InternalOrder) updatedEntity;

        assertNotEquals(originalInternalOrder.getName(), updatedInternalOrder.getName());
        assertEquals(changedField, updatedInternalOrder.getName());
        assertEquals(originalInternalOrder.getDescription(), updatedInternalOrder.getDescription());
        assertEquals(originalInternalOrder.getOrganization().getMeta().getHref(), updatedInternalOrder.getOrganization().getMeta().getHref());
        assertEquals(originalInternalOrder.getStore().getMeta().getHref(), updatedInternalOrder.getStore().getMeta().getHref());
    }

    @Override
    protected EntityClientBase entityClient() {
        return api.entity().internalorder();
    }

    @Override
    protected Class<? extends MetaEntity> entityClass() {
        return InternalOrder.class;
    }
}


