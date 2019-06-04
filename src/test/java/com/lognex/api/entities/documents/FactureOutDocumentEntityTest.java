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
        FactureOutDocumentEntity e = new FactureOutDocumentEntity();
        e.setName("factureout_" + randomString(3) + "_" + new Date().getTime());
        e.setDescription(randomString());
        e.setMoment(LocalDateTime.now());
        e.setPaymentNumber(randomString());
        e.setPaymentDate(LocalDateTime.now());
        List<DemandDocumentEntity> demands = new ArrayList<>();
        demands.add(createSimpleDemand());
        e.setDemands(demands);

        api.entity().factureout().post(e);

        ListEntity<FactureOutDocumentEntity> updatedEntitiesList = api.entity().factureout().get(filterEq("name", e.getName()));
        assertEquals(1, updatedEntitiesList.getRows().size());

        FactureOutDocumentEntity retrievedEntity = updatedEntitiesList.getRows().get(0);
        assertEquals(e.getName(), retrievedEntity.getName());
        assertEquals(e.getDescription(), retrievedEntity.getDescription());
        assertEquals(e.getMoment(), retrievedEntity.getMoment());
        assertEquals(e.getPaymentNumber(), retrievedEntity.getPaymentNumber());
        assertEquals(e.getPaymentDate(), retrievedEntity.getPaymentDate());
        assertEquals(e.getOrganization().getMeta().getHref(), retrievedEntity.getOrganization().getMeta().getHref());
        assertEquals(e.getDemands().get(0).getMeta().getHref(), retrievedEntity.getDemands().get(0).getMeta().getHref());
    }

    @Test
    public void getTest() throws IOException, LognexApiException {
        FactureOutDocumentEntity e = createSimpleFactureOut();

        FactureOutDocumentEntity retrievedEntity = api.entity().factureout().get(e.getId());
        getAsserts(e, retrievedEntity);

        retrievedEntity = api.entity().factureout().get(e);
        getAsserts(e, retrievedEntity);
    }

    @Test
    public void putTest() throws IOException, LognexApiException, InterruptedException {
        FactureOutDocumentEntity e = createSimpleFactureOut();

        FactureOutDocumentEntity retrievedOriginalEntity = api.entity().factureout().get(e.getId());
        String name = "factureout_" + randomString(3) + "_" + new Date().getTime();
        e.setName(name);
        api.entity().factureout().put(e.getId(), e);
        putAsserts(e, retrievedOriginalEntity, name);

        retrievedOriginalEntity.set(e);

        name = "factureout_" + randomString(3) + "_" + new Date().getTime();
        e.setName(name);
        api.entity().factureout().put(e);
        putAsserts(e, retrievedOriginalEntity, name);
    }

    @Test
    public void deleteTest() throws IOException, LognexApiException {
        FactureOutDocumentEntity e = createSimpleFactureOut();

        ListEntity<FactureOutDocumentEntity> entitiesList = api.entity().factureout().get(filterEq("name", e.getName()));
        assertEquals((Integer) 1, entitiesList.getMeta().getSize());

        api.entity().factureout().delete(e.getId());

        entitiesList = api.entity().factureout().get(filterEq("name", e.getName()));
        assertEquals((Integer) 0, entitiesList.getMeta().getSize());
    }

    @Test
    public void deleteByIdTest() throws IOException, LognexApiException {
        FactureOutDocumentEntity e = createSimpleFactureOut();

        ListEntity<FactureOutDocumentEntity> entitiesList = api.entity().factureout().get(filterEq("name", e.getName()));
        assertEquals((Integer) 1, entitiesList.getMeta().getSize());

        api.entity().factureout().delete(e);

        entitiesList = api.entity().factureout().get(filterEq("name", e.getName()));
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
        FactureOutDocumentEntity e = api.entity().factureout().newDocument("demands", Collections.singletonList(demand));

        assertEquals("", e.getName());
        assertEquals(demand.getSum(), e.getSum());
        assertFalse(e.getShared());
        assertTrue(e.getApplicable());
        assertTrue(ChronoUnit.MILLIS.between(time, e.getMoment()) < 1000);
        assertEquals(1, e.getDemands().size());
        assertEquals(demand.getMeta().getHref(), e.getDemands().get(0).getMeta().getHref());
        assertEquals(demand.getGroup().getMeta().getHref(), e.getGroup().getMeta().getHref());
        assertEquals(demand.getAgent().getMeta().getHref(), e.getAgent().getMeta().getHref());
        assertEquals(demand.getOrganization().getMeta().getHref(), e.getOrganization().getMeta().getHref());
    }

    @Test
    public void newByPurchaseReturnsTest() throws IOException, LognexApiException {
        PurchaseReturnDocumentEntity purchaseReturn = createSimplePurchaseReturn();

        LocalDateTime time = LocalDateTime.now();
        FactureOutDocumentEntity e = api.entity().factureout().newDocument("returns", Collections.singletonList(purchaseReturn));

        assertEquals("", e.getName());
        assertEquals(purchaseReturn.getSum(), e.getSum());
        assertFalse(e.getShared());
        assertTrue(e.getApplicable());
        assertTrue(ChronoUnit.MILLIS.between(time, e.getMoment()) < 1000);
        assertEquals(1, e.getReturns().size());
        assertEquals(purchaseReturn.getMeta().getHref(), e.getReturns().get(0).getMeta().getHref());
        assertEquals(purchaseReturn.getGroup().getMeta().getHref(), e.getGroup().getMeta().getHref());
        assertEquals(purchaseReturn.getAgent().getMeta().getHref(), e.getAgent().getMeta().getHref());
        assertEquals(purchaseReturn.getOrganization().getMeta().getHref(), e.getOrganization().getMeta().getHref());
    }

    @Test
    public void newByPaymentsInTest() throws IOException, LognexApiException {
        PaymentInDocumentEntity paymentIn = createSimplePaymentIn();

        LocalDateTime time = LocalDateTime.now();
        FactureOutDocumentEntity e = api.entity().factureout().newDocument("payments", Collections.singletonList(paymentIn));

        assertEquals("", e.getName());
        assertEquals(paymentIn.getSum(), e.getSum());
        assertFalse(e.getShared());
        assertTrue(e.getApplicable());
        assertTrue(ChronoUnit.MILLIS.between(time, e.getMoment()) < 1000);
        assertEquals(1, e.getPayments().size());
        assertEquals(paymentIn.getMeta().getHref(), ((PaymentInDocumentEntity) e.getPayments().get(0)).getMeta().getHref());
        assertEquals(paymentIn.getGroup().getMeta().getHref(), e.getGroup().getMeta().getHref());
        assertEquals(paymentIn.getAgent().getMeta().getHref(), e.getAgent().getMeta().getHref());
        assertEquals(paymentIn.getOrganization().getMeta().getHref(), e.getOrganization().getMeta().getHref());
    }

    private void getAsserts(FactureOutDocumentEntity e, FactureOutDocumentEntity retrievedEntity) {
        assertEquals(e.getName(), retrievedEntity.getName());
        assertEquals(e.getPaymentNumber(), retrievedEntity.getPaymentNumber());
        assertEquals(e.getPaymentDate(), retrievedEntity.getPaymentDate());
        assertEquals(e.getOrganization().getMeta().getHref(), retrievedEntity.getOrganization().getMeta().getHref());
        assertEquals(e.getDemands().get(0).getMeta().getHref(), retrievedEntity.getDemands().get(0).getMeta().getHref());
    }

    private void putAsserts(FactureOutDocumentEntity e, FactureOutDocumentEntity retrievedOriginalEntity, String name) throws IOException, LognexApiException {
        FactureOutDocumentEntity retrievedUpdatedEntity = api.entity().factureout().get(e.getId());

        assertNotEquals(retrievedOriginalEntity.getName(), retrievedUpdatedEntity.getName());
        assertEquals(name, retrievedUpdatedEntity.getName());
        assertEquals(retrievedOriginalEntity.getPaymentNumber(), retrievedUpdatedEntity.getPaymentNumber());
        assertEquals(retrievedOriginalEntity.getPaymentDate(), retrievedUpdatedEntity.getPaymentDate());
        assertEquals(retrievedOriginalEntity.getOrganization().getMeta().getHref(), retrievedUpdatedEntity.getOrganization().getMeta().getHref());
        assertEquals(retrievedOriginalEntity.getDemands().get(0).getMeta().getHref(), retrievedUpdatedEntity.getDemands().get(0).getMeta().getHref());
    }
}
