package com.lognex.api.entities.documents;

import com.lognex.api.clients.ApiClient;
import com.lognex.api.entities.MetaEntity;
import com.lognex.api.entities.StoreEntity;
import com.lognex.api.entities.agents.CounterpartyEntity;
import com.lognex.api.entities.agents.OrganizationEntity;
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

public class SalesReturnDocumentEntityTest extends DocumentWithPositionsTestBase {
    @Test
    public void createTest() throws IOException, LognexApiException {
        SalesReturnDocumentEntity salesReturn = new SalesReturnDocumentEntity();
        salesReturn.setName("salesreturn_" + randomString(3) + "_" + new Date().getTime());
        salesReturn.setDescription(randomString());
        salesReturn.setVatEnabled(true);
        salesReturn.setVatIncluded(true);
        salesReturn.setMoment(LocalDateTime.now());
        OrganizationEntity organization = simpleEntityManager.getOwnOrganization();
        salesReturn.setOrganization(organization);
        CounterpartyEntity agent = simpleEntityManager.createSimpleCounterparty();
        salesReturn.setAgent(agent);
        StoreEntity mainStore = simpleEntityManager.getMainStore();
        salesReturn.setStore(mainStore);

        DemandDocumentEntity demand = new DemandDocumentEntity();
        demand.setName("demand_" + randomString(3) + "_" + new Date().getTime());
        demand.setDescription(randomString());
        demand.setOrganization(organization);
        demand.setAgent(agent);
        demand.setStore(mainStore);
        
        api.entity().demand().post(demand);
        salesReturn.setDemand(demand);

        api.entity().salesreturn().post(salesReturn);

        ListEntity<SalesReturnDocumentEntity> updatedEntitiesList = api.entity().salesreturn().get(filterEq("name", salesReturn.getName()));
        assertEquals(1, updatedEntitiesList.getRows().size());

        SalesReturnDocumentEntity retrievedEntity = updatedEntitiesList.getRows().get(0);
        assertEquals(salesReturn.getName(), retrievedEntity.getName());
        assertEquals(salesReturn.getDescription(), retrievedEntity.getDescription());
        assertEquals(salesReturn.getVatEnabled(), retrievedEntity.getVatEnabled());
        assertEquals(salesReturn.getVatIncluded(), retrievedEntity.getVatIncluded());
        assertEquals(salesReturn.getMoment(), retrievedEntity.getMoment());
        assertEquals(salesReturn.getOrganization().getMeta().getHref(), retrievedEntity.getOrganization().getMeta().getHref());
        assertEquals(salesReturn.getAgent().getMeta().getHref(), retrievedEntity.getAgent().getMeta().getHref());
        assertEquals(salesReturn.getStore().getMeta().getHref(), retrievedEntity.getStore().getMeta().getHref());
        assertEquals(salesReturn.getDemand().getMeta().getHref(), retrievedEntity.getDemand().getMeta().getHref());
    }

    @Test
    public void metadataTest() throws IOException, LognexApiException {
        MetadataAttributeSharedStatesResponse response = api.entity().salesreturn().metadata().get();

        assertFalse(response.getCreateShared());
    }

    @Test
    public void newTest() throws IOException, LognexApiException {
        SalesReturnDocumentEntity salesReturn = api.entity().salesreturn().newDocument();
        LocalDateTime time = LocalDateTime.now();

        assertEquals("", salesReturn.getName());
        assertTrue(salesReturn.getVatEnabled());
        assertTrue(salesReturn.getVatIncluded());
        assertEquals(Long.valueOf(0), salesReturn.getSum());
        assertFalse(salesReturn.getShared());
        assertTrue(salesReturn.getApplicable());
        assertTrue(ChronoUnit.MILLIS.between(time, salesReturn.getMoment()) < 1000);

        assertEquals(salesReturn.getOrganization().getMeta().getHref(), simpleEntityManager.getOwnOrganization().getMeta().getHref());
        assertEquals(salesReturn.getStore().getMeta().getHref(), simpleEntityManager.getMainStore().getMeta().getHref());
    }

    @Test
    public void newByDemandTest() throws IOException, LognexApiException {
        DemandDocumentEntity demand = simpleEntityManager.createSimpleDemand();

        SalesReturnDocumentEntity salesReturn = api.entity().salesreturn().newDocument("demand", demand);
        LocalDateTime time = LocalDateTime.now();

        assertEquals("", salesReturn.getName());
        assertEquals(demand.getVatEnabled(), salesReturn.getVatEnabled());
        assertEquals(demand.getVatIncluded(), salesReturn.getVatIncluded());
        assertEquals(demand.getPayedSum(), salesReturn.getPayedSum());
        assertEquals(demand.getSum(), salesReturn.getSum());
        assertFalse(salesReturn.getShared());
        assertTrue(salesReturn.getApplicable());
        assertTrue(ChronoUnit.MILLIS.between(time, salesReturn.getMoment()) < 1000);
        assertEquals(demand.getMeta().getHref(), salesReturn.getDemand().getMeta().getHref());
        assertEquals(demand.getAgent().getMeta().getHref(), salesReturn.getAgent().getMeta().getHref());
        assertEquals(demand.getStore().getMeta().getHref(), salesReturn.getStore().getMeta().getHref());
        assertEquals(demand.getOrganization().getMeta().getHref(), salesReturn.getOrganization().getMeta().getHref());
    }

    @Override
    protected void getAsserts(MetaEntity originalEntity, MetaEntity retrievedEntity) {
        SalesReturnDocumentEntity originalSalesReturn = (SalesReturnDocumentEntity) originalEntity;
        SalesReturnDocumentEntity retrievedSalesReturn = (SalesReturnDocumentEntity) retrievedEntity;

        assertEquals(originalSalesReturn.getName(), retrievedSalesReturn.getName());
        assertEquals(originalSalesReturn.getOrganization().getMeta().getHref(), retrievedSalesReturn.getOrganization().getMeta().getHref());
        assertEquals(originalSalesReturn.getAgent().getMeta().getHref(), retrievedSalesReturn.getAgent().getMeta().getHref());
        assertEquals(originalSalesReturn.getStore().getMeta().getHref(), retrievedSalesReturn.getStore().getMeta().getHref());
    }

    @Override
    protected void putAsserts(MetaEntity originalEntity, MetaEntity updatedEntity, Object changedField) {
        SalesReturnDocumentEntity originalSalesReturn = (SalesReturnDocumentEntity) originalEntity;
        SalesReturnDocumentEntity updatedSalesReturn = (SalesReturnDocumentEntity) updatedEntity;

        assertNotEquals(originalSalesReturn.getName(), updatedSalesReturn.getName());
        assertEquals(changedField, updatedSalesReturn.getName());
        assertEquals(originalSalesReturn.getOrganization().getMeta().getHref(), updatedSalesReturn.getOrganization().getMeta().getHref());
        assertEquals(originalSalesReturn.getAgent().getMeta().getHref(), updatedSalesReturn.getAgent().getMeta().getHref());
        assertEquals(originalSalesReturn.getStore().getMeta().getHref(), updatedSalesReturn.getStore().getMeta().getHref());
    }

    @Override
    protected ApiClient entityClient() {
        return api.entity().salesreturn();
    }

    @Override
    protected Class<? extends MetaEntity> entityClass() {
        return SalesReturnDocumentEntity.class;
    }
}
