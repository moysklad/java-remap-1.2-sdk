package com.lognex.api.entities.documents;

import com.lognex.api.clients.ApiClient;
import com.lognex.api.entities.EntityGetUpdateDeleteTest;
import com.lognex.api.entities.MetaEntity;
import com.lognex.api.responses.ListEntity;
import com.lognex.api.responses.metadata.MetadataAttributeSharedStatesResponse;
import com.lognex.api.utils.LognexApiException;
import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Date;

import static com.lognex.api.utils.params.FilterParam.filterEq;
import static org.junit.Assert.*;

public class RetailDrawerCashOutDocumentEntityTest extends EntityGetUpdateDeleteTest {
    @Ignore
    @Test
    public void createTest() throws IOException, LognexApiException {
        RetailDrawerCashOutEntity retailDrawerCashOut = new RetailDrawerCashOutEntity();
        retailDrawerCashOut.setName("retaildrawercashout_" + randomString(3) + "_" + new Date().getTime());
        retailDrawerCashOut.setDescription(randomString());
        retailDrawerCashOut.setMoment(LocalDateTime.now());

        retailDrawerCashOut.setOrganization(simpleEntityManager.getOwnOrganization());
        retailDrawerCashOut.setAgent(simpleEntityManager.createSimpleCounterparty());

        api.entity().retaildrawercashout().post(retailDrawerCashOut);

        ListEntity<RetailDrawerCashOutEntity> updatedEntitiesList = api.entity().retaildrawercashout().get(filterEq("name", retailDrawerCashOut.getName()));
        assertEquals(1, updatedEntitiesList.getRows().size());

        RetailDrawerCashOutEntity retrievedEntity = updatedEntitiesList.getRows().get(0);
        assertEquals(retailDrawerCashOut.getName(), retrievedEntity.getName());
        assertEquals(retailDrawerCashOut.getDescription(), retrievedEntity.getDescription());
        assertEquals(retailDrawerCashOut.getMoment(), retrievedEntity.getMoment());
        assertEquals(retailDrawerCashOut.getOrganization().getMeta().getHref(), retrievedEntity.getOrganization().getMeta().getHref());
        assertEquals(retailDrawerCashOut.getAgent().getMeta().getHref(), retrievedEntity.getAgent().getMeta().getHref());
    }

    @Ignore
    @Test
    public void metadataTest() throws IOException, LognexApiException {
        MetadataAttributeSharedStatesResponse response = api.entity().retaildrawercashout().metadata().get();

        assertFalse(response.getCreateShared());
    }

    @Ignore
    @Test
    @Override
    public void putTest() throws IOException, LognexApiException {
    }

    @Ignore
    @Test
    @Override
    public void deleteTest() throws IOException, LognexApiException {
    }

    @Ignore
    @Test
    @Override
    public void getTest() throws IOException, LognexApiException {
    }

    @Override
    protected void getAsserts(MetaEntity originalEntity, MetaEntity retrievedEntity) {
        RetailDrawerCashOutEntity originalRetailDrawerCashOut = (RetailDrawerCashOutEntity) originalEntity;
        RetailDrawerCashOutEntity retrievedRetailDrawerCashOut = (RetailDrawerCashOutEntity) retrievedEntity;

        assertEquals(originalRetailDrawerCashOut.getName(), retrievedRetailDrawerCashOut.getName());
        assertEquals(originalRetailDrawerCashOut.getDescription(), retrievedRetailDrawerCashOut.getDescription());
        assertEquals(originalRetailDrawerCashOut.getOrganization().getMeta().getHref(), retrievedRetailDrawerCashOut.getOrganization().getMeta().getHref());
        assertEquals(originalRetailDrawerCashOut.getAgent().getMeta().getHref(), retrievedRetailDrawerCashOut.getAgent().getMeta().getHref());
    }

    @Override
    protected void putAsserts(MetaEntity originalEntity, MetaEntity updatedEntity, Object changedField) {
        RetailDrawerCashOutEntity originalRetailDrawerCashOut = (RetailDrawerCashOutEntity) originalEntity;
        RetailDrawerCashOutEntity updatedRetailDrawerCashOut = (RetailDrawerCashOutEntity) updatedEntity;

        assertNotEquals(originalRetailDrawerCashOut.getName(), updatedRetailDrawerCashOut.getName());
        assertEquals(changedField, updatedRetailDrawerCashOut.getName());
        assertEquals(originalRetailDrawerCashOut.getDescription(), updatedRetailDrawerCashOut.getDescription());
        assertEquals(originalRetailDrawerCashOut.getOrganization().getMeta().getHref(), updatedRetailDrawerCashOut.getOrganization().getMeta().getHref());
        assertEquals(originalRetailDrawerCashOut.getAgent().getMeta().getHref(), updatedRetailDrawerCashOut.getAgent().getMeta().getHref());
    }

    @Override
    protected ApiClient entityClient() {
        return api.entity().retaildrawercashout();
    }

    @Override
    protected Class<? extends MetaEntity> entityClass() {
        return RetailDrawerCashOutEntity.class;
    }
}
