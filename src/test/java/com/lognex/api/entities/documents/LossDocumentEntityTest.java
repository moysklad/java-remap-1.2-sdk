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

public class LossDocumentEntityTest extends DocumentWithPositionsTestBase {
    @Test
    public void createTest() throws IOException, LognexApiException {
        LossDocumentEntity loss = new LossDocumentEntity();
        loss.setName("loss_" + randomString(3) + "_" + new Date().getTime());
        loss.setDescription(randomString());
        loss.setMoment(LocalDateTime.now());
        loss.setOrganization(simpleEntityFactory.getOwnOrganization());
        loss.setStore(simpleEntityFactory.getMainStore());

        api.entity().loss().post(loss);

        ListEntity<LossDocumentEntity> updatedEntitiesList = api.entity().loss().get(filterEq("name", loss.getName()));
        assertEquals(1, updatedEntitiesList.getRows().size());

        LossDocumentEntity retrievedEntity = updatedEntitiesList.getRows().get(0);
        assertEquals(loss.getName(), retrievedEntity.getName());
        assertEquals(loss.getDescription(), retrievedEntity.getDescription());
        assertEquals(loss.getMoment(), retrievedEntity.getMoment());
        assertEquals(loss.getOrganization().getMeta().getHref(), retrievedEntity.getOrganization().getMeta().getHref());
        assertEquals(loss.getStore().getMeta().getHref(), retrievedEntity.getStore().getMeta().getHref());
    }

    @Test
    public void getTest() throws IOException, LognexApiException {
        LossDocumentEntity loss = simpleEntityFactory.createSimpleLoss();

        LossDocumentEntity retrievedEntity = api.entity().loss().get(loss.getId());
        getAsserts(loss, retrievedEntity);

        retrievedEntity = api.entity().loss().get(loss);
        getAsserts(loss, retrievedEntity);
    }

    @Test
    public void putTest() throws IOException, LognexApiException {
        LossDocumentEntity loss = simpleEntityFactory.createSimpleLoss();

        LossDocumentEntity retrievedOriginalEntity = api.entity().loss().get(loss.getId());
        String name = "loss_" + randomString(3) + "_" + new Date().getTime();
        loss.setName(name);
        api.entity().loss().put(loss.getId(), loss);
        putAsserts(loss, retrievedOriginalEntity, name);

        retrievedOriginalEntity.set(loss);

        name = "loss_" + randomString(3) + "_" + new Date().getTime();
        loss.setName(name);
        api.entity().loss().put(loss);
        putAsserts(loss, retrievedOriginalEntity, name);
    }

    @Test
    public void deleteTest() throws IOException, LognexApiException {
        LossDocumentEntity loss = simpleEntityFactory.createSimpleLoss();

        ListEntity<LossDocumentEntity> entitiesList = api.entity().loss().get(filterEq("name", loss.getName()));
        assertEquals((Integer) 1, entitiesList.getMeta().getSize());

        api.entity().loss().delete(loss.getId());

        entitiesList = api.entity().loss().get(filterEq("name", loss.getName()));
        assertEquals((Integer) 0, entitiesList.getMeta().getSize());
    }

    @Test
    public void deleteByIdTest() throws IOException, LognexApiException {
        LossDocumentEntity loss = simpleEntityFactory.createSimpleLoss();

        ListEntity<LossDocumentEntity> entitiesList = api.entity().loss().get(filterEq("name", loss.getName()));
        assertEquals((Integer) 1, entitiesList.getMeta().getSize());

        api.entity().loss().delete(loss);

        entitiesList = api.entity().loss().get(filterEq("name", loss.getName()));
        assertEquals((Integer) 0, entitiesList.getMeta().getSize());
    }

    @Test
    public void metadataTest() throws IOException, LognexApiException {
        MetadataAttributeSharedStatesResponse response = api.entity().loss().metadata().get();

        assertFalse(response.getCreateShared());
    }

    @Test
    public void newTest() throws IOException, LognexApiException {
        LossDocumentEntity loss = api.entity().loss().newDocument();
        LocalDateTime time = LocalDateTime.now();

        assertEquals("", loss.getName());
        assertEquals(Long.valueOf(0), loss.getSum());
        assertFalse(loss.getShared());
        assertTrue(loss.getApplicable());
        assertTrue(ChronoUnit.MILLIS.between(time, loss.getMoment()) < 1000);

        assertEquals(loss.getOrganization().getMeta().getHref(), simpleEntityFactory.getOwnOrganization().getMeta().getHref());
        assertEquals(loss.getStore().getMeta().getHref(), simpleEntityFactory.getMainStore().getMeta().getHref());
        assertEquals(loss.getGroup().getMeta().getHref(), simpleEntityFactory.getMainGroup().getMeta().getHref());
    }

    @Test
    public void newBySalesReturnTest() throws IOException, LognexApiException {
        SalesReturnDocumentEntity salesReturn = simpleEntityFactory.createSimpleSalesReturn();

        LossDocumentEntity loss = api.entity().loss().newDocument("salesReturn", salesReturn);
        LocalDateTime time = LocalDateTime.now();

        assertEquals("", loss.getName());
        assertEquals(salesReturn.getSum(), loss.getSum());
        assertFalse(loss.getShared());
        assertTrue(loss.getApplicable());
        assertTrue(ChronoUnit.MILLIS.between(time, loss.getMoment()) < 1000);
        assertEquals(salesReturn.getMeta().getHref(), loss.getSalesReturn().getMeta().getHref());
        assertEquals(salesReturn.getStore().getMeta().getHref(), loss.getStore().getMeta().getHref());
        assertEquals(salesReturn.getGroup().getMeta().getHref(), loss.getGroup().getMeta().getHref());
        assertEquals(salesReturn.getOrganization().getMeta().getHref(), loss.getOrganization().getMeta().getHref());
    }

    private void getAsserts(LossDocumentEntity loss, LossDocumentEntity retrievedEntity) {
        assertEquals(loss.getName(), retrievedEntity.getName());
        assertEquals(loss.getDescription(), retrievedEntity.getDescription());
        assertEquals(loss.getOrganization().getMeta().getHref(), retrievedEntity.getOrganization().getMeta().getHref());
        assertEquals(loss.getStore().getMeta().getHref(), retrievedEntity.getStore().getMeta().getHref());
    }

    private void putAsserts(LossDocumentEntity loss, LossDocumentEntity retrievedOriginalEntity, String name) throws IOException, LognexApiException {
        LossDocumentEntity retrievedUpdatedEntity = api.entity().loss().get(loss.getId());

        assertNotEquals(retrievedOriginalEntity.getName(), retrievedUpdatedEntity.getName());
        assertEquals(name, retrievedUpdatedEntity.getName());
        assertEquals(retrievedOriginalEntity.getDescription(), retrievedUpdatedEntity.getDescription());
        assertEquals(retrievedOriginalEntity.getOrganization().getMeta().getHref(), retrievedUpdatedEntity.getOrganization().getMeta().getHref());
        assertEquals(retrievedOriginalEntity.getStore().getMeta().getHref(), retrievedUpdatedEntity.getStore().getMeta().getHref());
    }

    @Override
    protected ApiClient entityClient() {
        return api.entity().loss();
    }

    @Override
    protected Class<? extends MetaEntity> entityClass() {
        return LossDocumentEntity.class;
    }
}
