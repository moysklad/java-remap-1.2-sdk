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

public class RetailDemandDocumentEntityTest extends EntityGetUpdateDeleteTest {
    @Ignore
    @Test
    public void createTest() throws IOException, LognexApiException {
        RetailDemandDocumentEntity retailDemand = new RetailDemandDocumentEntity();
        retailDemand.setName("retaildemand_" + randomString(3) + "_" + new Date().getTime());
        retailDemand.setDescription(randomString());
        retailDemand.setVatEnabled(true);
        retailDemand.setVatIncluded(true);
        retailDemand.setMoment(LocalDateTime.now());

        retailDemand.setOrganization(simpleEntityFactory.getOwnOrganization());
        retailDemand.setAgent(simpleEntityFactory.createSimpleCounterparty());
        retailDemand.setStore(simpleEntityFactory.getMainStore());

        api.entity().retaildemand().post(retailDemand);

        ListEntity<RetailDemandDocumentEntity> updatedEntitiesList = api.entity().retaildemand().get(filterEq("name", retailDemand.getName()));
        assertEquals(1, updatedEntitiesList.getRows().size());

        RetailDemandDocumentEntity retrievedEntity = updatedEntitiesList.getRows().get(0);
        assertEquals(retailDemand.getName(), retrievedEntity.getName());
        assertEquals(retailDemand.getDescription(), retrievedEntity.getDescription());
        assertEquals(retailDemand.getVatEnabled(), retrievedEntity.getVatEnabled());
        assertEquals(retailDemand.getVatIncluded(), retrievedEntity.getVatIncluded());
        assertEquals(retailDemand.getMoment(), retrievedEntity.getMoment());
        assertEquals(retailDemand.getOrganization().getMeta().getHref(), retrievedEntity.getOrganization().getMeta().getHref());
        assertEquals(retailDemand.getAgent().getMeta().getHref(), retrievedEntity.getAgent().getMeta().getHref());
        assertEquals(retailDemand.getStore().getMeta().getHref(), retrievedEntity.getStore().getMeta().getHref());
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

    @Ignore
    @Test
    public void metadataTest() throws IOException, LognexApiException {
        MetadataAttributeSharedStatesResponse response = api.entity().retaildemand().metadata().get();

        assertFalse(response.getCreateShared());
    }

    @Override
    protected void getAsserts(MetaEntity originalEntity, MetaEntity retrievedEntity) {
        RetailDemandDocumentEntity originalDemand = (RetailDemandDocumentEntity) originalEntity;
        RetailDemandDocumentEntity retrievedDemand = (RetailDemandDocumentEntity) retrievedEntity;

        assertEquals(originalDemand.getName(), retrievedDemand.getName());
        assertEquals(originalDemand.getDescription(), retrievedDemand.getDescription());
        assertEquals(originalDemand.getOrganization().getMeta().getHref(), retrievedDemand.getOrganization().getMeta().getHref());
        assertEquals(originalDemand.getAgent().getMeta().getHref(), retrievedDemand.getAgent().getMeta().getHref());
        assertEquals(originalDemand.getStore().getMeta().getHref(), retrievedDemand.getStore().getMeta().getHref());
    }

    @Override
    protected void putAsserts(MetaEntity originalEntity, MetaEntity updatedEntity, Object changedField) {
        RetailDemandDocumentEntity originalDemand = (RetailDemandDocumentEntity) originalEntity;
        RetailDemandDocumentEntity updatedDemand = (RetailDemandDocumentEntity) updatedEntity;

        assertNotEquals(originalDemand.getName(), updatedDemand.getName());
        assertEquals(changedField, updatedDemand.getName());
        assertEquals(originalDemand.getDescription(), updatedDemand.getDescription());
        assertEquals(originalDemand.getOrganization().getMeta().getHref(), updatedDemand.getOrganization().getMeta().getHref());
        assertEquals(originalDemand.getAgent().getMeta().getHref(), updatedDemand.getAgent().getMeta().getHref());
        assertEquals(originalDemand.getStore().getMeta().getHref(), updatedDemand.getStore().getMeta().getHref());
    }

    @Override
    protected ApiClient entityClient() {
        return api.entity().retaildemand();
    }

    @Override
    protected Class<? extends MetaEntity> entityClass() {
        return RetailDemandDocumentEntity.class;
    }
}
