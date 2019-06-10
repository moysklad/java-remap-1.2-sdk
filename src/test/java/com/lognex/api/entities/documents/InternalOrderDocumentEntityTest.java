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
    public void getTest() throws IOException, LognexApiException {
        InternalOrderDocumentEntity internalOrder = simpleEntityFactory.createSimpleInternalOrder();

        InternalOrderDocumentEntity retrievedEntity = api.entity().internalorder().get(internalOrder.getId());
        getAsserts(internalOrder, retrievedEntity);

        retrievedEntity = api.entity().internalorder().get(internalOrder);
        getAsserts(internalOrder, retrievedEntity);
    }

    @Test
    public void putTest() throws IOException, LognexApiException {
        InternalOrderDocumentEntity internalOrder = simpleEntityFactory.createSimpleInternalOrder();

        InternalOrderDocumentEntity retrievedOriginalEntity = api.entity().internalorder().get(internalOrder.getId());
        String name = "internalorder_" + randomString(3) + "_" + new Date().getTime();
        internalOrder.setName(name);
        api.entity().internalorder().put(internalOrder.getId(), internalOrder);
        putAsserts(internalOrder, retrievedOriginalEntity, name);

        retrievedOriginalEntity.set(internalOrder);

        name = "internalorder_" + randomString(3) + "_" + new Date().getTime();
        internalOrder.setName(name);
        api.entity().internalorder().put(internalOrder);
        putAsserts(internalOrder, retrievedOriginalEntity, name);
    }

    @Test
    public void deleteTest() throws IOException, LognexApiException {
        InternalOrderDocumentEntity internalOrder = simpleEntityFactory.createSimpleInternalOrder();

        ListEntity<InternalOrderDocumentEntity> entitiesList = api.entity().internalorder().get(filterEq("name", internalOrder.getName()));
        assertEquals((Integer) 1, entitiesList.getMeta().getSize());

        api.entity().internalorder().delete(internalOrder.getId());

        entitiesList = api.entity().internalorder().get(filterEq("name", internalOrder.getName()));
        assertEquals((Integer) 0, entitiesList.getMeta().getSize());
    }

    @Test
    public void deleteByIdTest() throws IOException, LognexApiException {
        InternalOrderDocumentEntity internalOrder = simpleEntityFactory.createSimpleInternalOrder();

        ListEntity<InternalOrderDocumentEntity> entitiesList = api.entity().internalorder().get(filterEq("name", internalOrder.getName()));
        assertEquals((Integer) 1, entitiesList.getMeta().getSize());

        api.entity().internalorder().delete(internalOrder);

        entitiesList = api.entity().internalorder().get(filterEq("name", internalOrder.getName()));
        assertEquals((Integer) 0, entitiesList.getMeta().getSize());
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

    private void getAsserts(InternalOrderDocumentEntity internalOrder, InternalOrderDocumentEntity retrievedEntity) {
        assertEquals(internalOrder.getName(), retrievedEntity.getName());
        assertEquals(internalOrder.getDescription(), retrievedEntity.getDescription());
        assertEquals(internalOrder.getOrganization().getMeta().getHref(), retrievedEntity.getOrganization().getMeta().getHref());
        assertEquals(internalOrder.getStore().getMeta().getHref(), retrievedEntity.getStore().getMeta().getHref());
    }

    private void putAsserts(InternalOrderDocumentEntity internalOrder, InternalOrderDocumentEntity retrievedOriginalEntity, String name) throws IOException, LognexApiException {
        InternalOrderDocumentEntity retrievedUpdatedEntity = api.entity().internalorder().get(internalOrder.getId());

        assertNotEquals(retrievedOriginalEntity.getName(), retrievedUpdatedEntity.getName());
        assertEquals(name, retrievedUpdatedEntity.getName());
        assertEquals(retrievedOriginalEntity.getDescription(), retrievedUpdatedEntity.getDescription());
        assertEquals(retrievedOriginalEntity.getOrganization().getMeta().getHref(), retrievedUpdatedEntity.getOrganization().getMeta().getHref());
        assertEquals(retrievedOriginalEntity.getStore().getMeta().getHref(), retrievedUpdatedEntity.getStore().getMeta().getHref());
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


