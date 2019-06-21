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

public class RetailDrawerCashInDocumentEntityTest extends EntityGetUpdateDeleteTest {
    @Ignore
    @Test
    public void createTest() throws IOException, LognexApiException {
        RetailDrawerCashInEntity retailDrawerCashIn = new RetailDrawerCashInEntity();
        retailDrawerCashIn.setName("retaildrawercashin_" + randomString(3) + "_" + new Date().getTime());
        retailDrawerCashIn.setDescription(randomString());
        retailDrawerCashIn.setMoment(LocalDateTime.now());

        retailDrawerCashIn.setOrganization(simpleEntityFactory.getOwnOrganization());
        retailDrawerCashIn.setAgent(simpleEntityFactory.createSimpleCounterparty());

        api.entity().retaildrawercashin().post(retailDrawerCashIn);

        ListEntity<RetailDrawerCashInEntity> updatedEntitiesList = api.entity().retaildrawercashin().get(filterEq("name", retailDrawerCashIn.getName()));
        assertEquals(1, updatedEntitiesList.getRows().size());

        RetailDrawerCashInEntity retrievedEntity = updatedEntitiesList.getRows().get(0);
        assertEquals(retailDrawerCashIn.getName(), retrievedEntity.getName());
        assertEquals(retailDrawerCashIn.getDescription(), retrievedEntity.getDescription());
        assertEquals(retailDrawerCashIn.getMoment(), retrievedEntity.getMoment());
        assertEquals(retailDrawerCashIn.getOrganization().getMeta().getHref(), retrievedEntity.getOrganization().getMeta().getHref());
        assertEquals(retailDrawerCashIn.getAgent().getMeta().getHref(), retrievedEntity.getAgent().getMeta().getHref());
    }

    @Ignore
    @Test
    public void metadataTest() throws IOException, LognexApiException {
        MetadataAttributeSharedStatesResponse response = api.entity().retaildrawercashin().metadata().get();

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
        RetailDrawerCashInEntity originalRetailDrawerCashIn = (RetailDrawerCashInEntity) originalEntity;
        RetailDrawerCashInEntity retrievedRetailDrawerCashIn = (RetailDrawerCashInEntity) retrievedEntity;

        assertEquals(originalRetailDrawerCashIn.getName(), retrievedRetailDrawerCashIn.getName());
        assertEquals(originalRetailDrawerCashIn.getDescription(), retrievedRetailDrawerCashIn.getDescription());
        assertEquals(originalRetailDrawerCashIn.getOrganization().getMeta().getHref(), retrievedRetailDrawerCashIn.getOrganization().getMeta().getHref());
        assertEquals(originalRetailDrawerCashIn.getAgent().getMeta().getHref(), retrievedRetailDrawerCashIn.getAgent().getMeta().getHref());
    }

    @Override
    protected void putAsserts(MetaEntity originalEntity, MetaEntity updatedEntity, Object changedField) {
        RetailDrawerCashInEntity originalRetailDrawerCashIn = (RetailDrawerCashInEntity) originalEntity;
        RetailDrawerCashInEntity updatedRetailDrawerCashIn = (RetailDrawerCashInEntity) updatedEntity;

        assertNotEquals(originalRetailDrawerCashIn.getName(), updatedRetailDrawerCashIn.getName());
        assertEquals(changedField, updatedRetailDrawerCashIn.getName());
        assertEquals(originalRetailDrawerCashIn.getDescription(), updatedRetailDrawerCashIn.getDescription());
        assertEquals(originalRetailDrawerCashIn.getOrganization().getMeta().getHref(), updatedRetailDrawerCashIn.getOrganization().getMeta().getHref());
        assertEquals(originalRetailDrawerCashIn.getAgent().getMeta().getHref(), updatedRetailDrawerCashIn.getAgent().getMeta().getHref());
    }

    @Override
    protected ApiClient entityClient() {
        return api.entity().retaildrawercashin();
    }

    @Override
    protected Class<? extends MetaEntity> entityClass() {
        return RetailDrawerCashInEntity.class;
    }
}
