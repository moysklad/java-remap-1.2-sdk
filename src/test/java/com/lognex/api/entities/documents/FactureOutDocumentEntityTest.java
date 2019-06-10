package com.lognex.api.entities.documents;

import com.lognex.api.entities.EntityTestBase;
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
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static com.lognex.api.utils.params.FilterParam.filterEq;
import static org.junit.Assert.*;

public class FactureOutDocumentEntityTest extends EntityTestBase {
    @Test
    public void createTest() throws IOException, LognexApiException {
        FactureOutDocumentEntity factureOut = new FactureOutDocumentEntity();
        factureOut.setName("factureout_" + randomString(3) + "_" + new Date().getTime());
        factureOut.setDescription(randomString());
        factureOut.setMoment(LocalDateTime.now());
        factureOut.setPaymentNumber(randomString());
        factureOut.setPaymentDate(LocalDateTime.now());
        List<DemandDocumentEntity> demands = new ArrayList<>();
        demands.add(simpleEntityFactory.createSimpleDemand());
        factureOut.setDemands(demands);

        api.entity().factureout().post(factureOut);

        ListEntity<FactureOutDocumentEntity> updatedEntitiesList = api.entity().factureout().get(filterEq("name", factureOut.getName()));
        assertEquals(1, updatedEntitiesList.getRows().size());

        FactureOutDocumentEntity retrievedEntity = updatedEntitiesList.getRows().get(0);
        assertEquals(factureOut.getName(), retrievedEntity.getName());
        assertEquals(factureOut.getDescription(), retrievedEntity.getDescription());
        assertEquals(factureOut.getMoment(), retrievedEntity.getMoment());
        assertEquals(factureOut.getPaymentNumber(), retrievedEntity.getPaymentNumber());
        assertEquals(factureOut.getPaymentDate(), retrievedEntity.getPaymentDate());
        assertEquals(factureOut.getOrganization().getMeta().getHref(), retrievedEntity.getOrganization().getMeta().getHref());
        assertEquals(factureOut.getDemands().get(0).getMeta().getHref(), retrievedEntity.getDemands().get(0).getMeta().getHref());
    }

    @Test
    public void getTest() throws IOException, LognexApiException {
        FactureOutDocumentEntity factureOut = simpleEntityFactory.createSimpleFactureOut();

        FactureOutDocumentEntity retrievedEntity = api.entity().factureout().get(factureOut.getId());
        getAsserts(factureOut, retrievedEntity);

        retrievedEntity = api.entity().factureout().get(factureOut);
        getAsserts(factureOut, retrievedEntity);
    }

    @Test
    public void putTest() throws IOException, LognexApiException {
        FactureOutDocumentEntity factureOut = simpleEntityFactory.createSimpleFactureOut();

        FactureOutDocumentEntity retrievedOriginalEntity = api.entity().factureout().get(factureOut.getId());
        String name = "factureout_" + randomString(3) + "_" + new Date().getTime();
        factureOut.setName(name);
        api.entity().factureout().put(factureOut.getId(), factureOut);
        putAsserts(factureOut, retrievedOriginalEntity, name);

        retrievedOriginalEntity.set(factureOut);

        name = "factureout_" + randomString(3) + "_" + new Date().getTime();
        factureOut.setName(name);
        api.entity().factureout().put(factureOut);
        putAsserts(factureOut, retrievedOriginalEntity, name);
    }

    @Test
    public void deleteTest() throws IOException, LognexApiException {
        FactureOutDocumentEntity factureOut = simpleEntityFactory.createSimpleFactureOut();

        ListEntity<FactureOutDocumentEntity> entitiesList = api.entity().factureout().get(filterEq("name", factureOut.getName()));
        assertEquals((Integer) 1, entitiesList.getMeta().getSize());

        api.entity().factureout().delete(factureOut.getId());

        entitiesList = api.entity().factureout().get(filterEq("name", factureOut.getName()));
        assertEquals((Integer) 0, entitiesList.getMeta().getSize());
    }

    @Test
    public void deleteByIdTest() throws IOException, LognexApiException {
        FactureOutDocumentEntity factureOut = simpleEntityFactory.createSimpleFactureOut();

        ListEntity<FactureOutDocumentEntity> entitiesList = api.entity().factureout().get(filterEq("name", factureOut.getName()));
        assertEquals((Integer) 1, entitiesList.getMeta().getSize());

        api.entity().factureout().delete(factureOut);

        entitiesList = api.entity().factureout().get(filterEq("name", factureOut.getName()));
        assertEquals((Integer) 0, entitiesList.getMeta().getSize());
    }

    @Test
    public void metadataTest() throws IOException, LognexApiException {
        MetadataAttributeSharedStatesResponse response = api.entity().factureout().metadata().get();

        assertFalse(response.getCreateShared());
    }

    @Test
    public void newByDemandsTest() throws IOException, LognexApiException {
        DemandDocumentEntity demand = new DemandDocumentEntity();
        demand.setName("demand_" + randomString(3) + "_" + new Date().getTime());

        ListEntity<OrganizationEntity> orgList = api.entity().organization().get();
        assertNotEquals(0, orgList.getRows().size());
        demand.setOrganization(orgList.getRows().get(0));

        CounterpartyEntity agent = new CounterpartyEntity();
        agent.setName(randomString());
        api.entity().counterparty().post(agent);
        demand.setAgent(agent);

        ListEntity<StoreEntity> store = api.entity().store().get(filterEq("name", "Основной склад"));
        assertEquals(1, store.getRows().size());
        demand.setStore(store.getRows().get(0));

        api.entity().demand().post(demand);

        LocalDateTime time = LocalDateTime.now();
        FactureOutDocumentEntity factureOut = api.entity().factureout().newDocument("demands", Collections.singletonList(demand));

        assertEquals("", factureOut.getName());
        assertEquals(demand.getSum(), factureOut.getSum());
        assertFalse(factureOut.getShared());
        assertTrue(factureOut.getApplicable());
        assertTrue(ChronoUnit.MILLIS.between(time, factureOut.getMoment()) < 1000);
        assertEquals(1, factureOut.getDemands().size());
        assertEquals(demand.getMeta().getHref(), factureOut.getDemands().get(0).getMeta().getHref());
        assertEquals(demand.getGroup().getMeta().getHref(), factureOut.getGroup().getMeta().getHref());
        assertEquals(demand.getAgent().getMeta().getHref(), factureOut.getAgent().getMeta().getHref());
        assertEquals(demand.getOrganization().getMeta().getHref(), factureOut.getOrganization().getMeta().getHref());
    }

    @Test
    public void newByPurchaseReturnsTest() throws IOException, LognexApiException {
        PurchaseReturnDocumentEntity purchaseReturn = simpleEntityFactory.createSimplePurchaseReturn();

        LocalDateTime time = LocalDateTime.now();
        FactureOutDocumentEntity factureOut = api.entity().factureout().newDocument("returns", Collections.singletonList(purchaseReturn));

        assertEquals("", factureOut.getName());
        assertEquals(purchaseReturn.getSum(), factureOut.getSum());
        assertFalse(factureOut.getShared());
        assertTrue(factureOut.getApplicable());
        assertTrue(ChronoUnit.MILLIS.between(time, factureOut.getMoment()) < 1000);
        assertEquals(1, factureOut.getReturns().size());
        assertEquals(purchaseReturn.getMeta().getHref(), factureOut.getReturns().get(0).getMeta().getHref());
        assertEquals(purchaseReturn.getGroup().getMeta().getHref(), factureOut.getGroup().getMeta().getHref());
        assertEquals(purchaseReturn.getAgent().getMeta().getHref(), factureOut.getAgent().getMeta().getHref());
        assertEquals(purchaseReturn.getOrganization().getMeta().getHref(), factureOut.getOrganization().getMeta().getHref());
    }

    @Test
    public void newByPaymentsInTest() throws IOException, LognexApiException {
        PaymentInDocumentEntity paymentIn = simpleEntityFactory.createSimplePaymentIn();

        LocalDateTime time = LocalDateTime.now();
        FactureOutDocumentEntity factureOut = api.entity().factureout().newDocument("payments", Collections.singletonList(paymentIn));

        assertEquals("", factureOut.getName());
        assertEquals(paymentIn.getSum(), factureOut.getSum());
        assertFalse(factureOut.getShared());
        assertTrue(factureOut.getApplicable());
        assertTrue(ChronoUnit.MILLIS.between(time, factureOut.getMoment()) < 1000);
        assertEquals(1, factureOut.getPayments().size());
        assertEquals(paymentIn.getMeta().getHref(), ((PaymentInDocumentEntity) factureOut.getPayments().get(0)).getMeta().getHref());
        assertEquals(paymentIn.getGroup().getMeta().getHref(), factureOut.getGroup().getMeta().getHref());
        assertEquals(paymentIn.getAgent().getMeta().getHref(), factureOut.getAgent().getMeta().getHref());
        assertEquals(paymentIn.getOrganization().getMeta().getHref(), factureOut.getOrganization().getMeta().getHref());
    }

    private void getAsserts(FactureOutDocumentEntity factureOut, FactureOutDocumentEntity retrievedEntity) {
        assertEquals(factureOut.getName(), retrievedEntity.getName());
        assertEquals(factureOut.getPaymentNumber(), retrievedEntity.getPaymentNumber());
        assertEquals(factureOut.getPaymentDate(), retrievedEntity.getPaymentDate());
        assertEquals(factureOut.getOrganization().getMeta().getHref(), retrievedEntity.getOrganization().getMeta().getHref());
        assertEquals(factureOut.getDemands().get(0).getMeta().getHref(), retrievedEntity.getDemands().get(0).getMeta().getHref());
    }

    private void putAsserts(FactureOutDocumentEntity factureOut, FactureOutDocumentEntity retrievedOriginalEntity, String name) throws IOException, LognexApiException {
        FactureOutDocumentEntity retrievedUpdatedEntity = api.entity().factureout().get(factureOut.getId());

        assertNotEquals(retrievedOriginalEntity.getName(), retrievedUpdatedEntity.getName());
        assertEquals(name, retrievedUpdatedEntity.getName());
        assertEquals(retrievedOriginalEntity.getPaymentNumber(), retrievedUpdatedEntity.getPaymentNumber());
        assertEquals(retrievedOriginalEntity.getPaymentDate(), retrievedUpdatedEntity.getPaymentDate());
        assertEquals(retrievedOriginalEntity.getOrganization().getMeta().getHref(), retrievedUpdatedEntity.getOrganization().getMeta().getHref());
        assertEquals(retrievedOriginalEntity.getDemands().get(0).getMeta().getHref(), retrievedUpdatedEntity.getDemands().get(0).getMeta().getHref());
    }
}
