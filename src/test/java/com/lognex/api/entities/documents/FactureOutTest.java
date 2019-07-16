package com.lognex.api.entities.documents;

import com.lognex.api.clients.EntityApiClient;
import com.lognex.api.entities.EntityGetUpdateDeleteTest;
import com.lognex.api.entities.MetaEntity;
import com.lognex.api.entities.Store;
import com.lognex.api.entities.agents.Counterparty;
import com.lognex.api.entities.agents.Organization;
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

public class FactureOutTest extends EntityGetUpdateDeleteTest {
    @Test
    public void createTest() throws IOException, LognexApiException {
        FactureOut factureOut = new FactureOut();
        factureOut.setName("factureout_" + randomString(3) + "_" + new Date().getTime());
        factureOut.setDescription(randomString());
        factureOut.setMoment(LocalDateTime.now());
        factureOut.setPaymentNumber(randomString());
        factureOut.setPaymentDate(LocalDateTime.now());
        List<Demand> demands = new ArrayList<>();
        demands.add(simpleEntityManager.createSimpleDemand());
        factureOut.setDemands(demands);

        api.entity().factureout().create(factureOut);

        ListEntity<FactureOut> updatedEntitiesList = api.entity().factureout().get(filterEq("name", factureOut.getName()));
        assertEquals(1, updatedEntitiesList.getRows().size());

        FactureOut retrievedEntity = updatedEntitiesList.getRows().get(0);
        assertEquals(factureOut.getName(), retrievedEntity.getName());
        assertEquals(factureOut.getDescription(), retrievedEntity.getDescription());
        assertEquals(factureOut.getMoment(), retrievedEntity.getMoment());
        assertEquals(factureOut.getPaymentNumber(), retrievedEntity.getPaymentNumber());
        assertEquals(factureOut.getPaymentDate(), retrievedEntity.getPaymentDate());
        assertEquals(factureOut.getOrganization().getMeta().getHref(), retrievedEntity.getOrganization().getMeta().getHref());
        assertEquals(factureOut.getDemands().get(0).getMeta().getHref(), retrievedEntity.getDemands().get(0).getMeta().getHref());
    }

    @Test
    public void metadataTest() throws IOException, LognexApiException {
        MetadataAttributeSharedStatesResponse response = api.entity().factureout().metadata().get();

        assertFalse(response.getCreateShared());
    }

    @Test
    public void newByDemandsTest() throws IOException, LognexApiException {
        Demand demand = new Demand();
        demand.setName("demand_" + randomString(3) + "_" + new Date().getTime());

        ListEntity<Organization> orgList = api.entity().organization().get();
        assertNotEquals(0, orgList.getRows().size());
        demand.setOrganization(orgList.getRows().get(0));

        Counterparty agent = new Counterparty();
        agent.setName(randomString());
        api.entity().counterparty().create(agent);
        demand.setAgent(agent);

        ListEntity<Store> store = api.entity().store().get(filterEq("name", "Основной склад"));
        assertEquals(1, store.getRows().size());
        demand.setStore(store.getRows().get(0));

        api.entity().demand().create(demand);

        LocalDateTime time = LocalDateTime.now();
        FactureOut factureOut = api.entity().factureout().templateDocument("demands", Collections.singletonList(demand));

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
        PurchaseReturn purchaseReturn = simpleEntityManager.createSimple(PurchaseReturn.class);

        LocalDateTime time = LocalDateTime.now();
        FactureOut factureOut = api.entity().factureout().templateDocument("returns", Collections.singletonList(purchaseReturn));

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
        PaymentIn paymentIn = simpleEntityManager.createSimple(PaymentIn.class);

        LocalDateTime time = LocalDateTime.now();
        FactureOut factureOut = api.entity().factureout().templateDocument("payments", Collections.singletonList(paymentIn));

        assertEquals("", factureOut.getName());
        assertEquals(paymentIn.getSum(), factureOut.getSum());
        assertFalse(factureOut.getShared());
        assertTrue(factureOut.getApplicable());
        assertTrue(ChronoUnit.MILLIS.between(time, factureOut.getMoment()) < 1000);
        assertEquals(1, factureOut.getPayments().size());
        assertEquals(paymentIn.getMeta().getHref(), ((PaymentIn) factureOut.getPayments().get(0)).getMeta().getHref());
        assertEquals(paymentIn.getGroup().getMeta().getHref(), factureOut.getGroup().getMeta().getHref());
        assertEquals(paymentIn.getAgent().getMeta().getHref(), factureOut.getAgent().getMeta().getHref());
        assertEquals(paymentIn.getOrganization().getMeta().getHref(), factureOut.getOrganization().getMeta().getHref());
    }

    @Override
    protected void getAsserts(MetaEntity originalEntity, MetaEntity retrievedEntity) {
        FactureOut originalFactureOut = (FactureOut) originalEntity;
        FactureOut retrievedFactureOut = (FactureOut) retrievedEntity;

        assertEquals(originalFactureOut.getName(), retrievedFactureOut.getName());
        assertEquals(originalFactureOut.getPaymentNumber(), retrievedFactureOut.getPaymentNumber());
        assertEquals(originalFactureOut.getPaymentDate(), retrievedFactureOut.getPaymentDate());
        assertEquals(originalFactureOut.getOrganization().getMeta().getHref(), retrievedFactureOut.getOrganization().getMeta().getHref());
        assertEquals(originalFactureOut.getDemands().get(0).getMeta().getHref(), retrievedFactureOut.getDemands().get(0).getMeta().getHref());
    }

    @Override
    protected void putAsserts(MetaEntity originalEntity, MetaEntity updatedEntity, Object changedField) {
        FactureOut originalFactureOut = (FactureOut) originalEntity;
        FactureOut updatedFactureOut = (FactureOut) updatedEntity;

        assertNotEquals(originalFactureOut.getName(), updatedFactureOut.getName());
        assertEquals(changedField, updatedFactureOut.getName());
        assertEquals(originalFactureOut.getPaymentNumber(), updatedFactureOut.getPaymentNumber());
        assertEquals(originalFactureOut.getPaymentDate(), updatedFactureOut.getPaymentDate());
        assertEquals(originalFactureOut.getOrganization().getMeta().getHref(), updatedFactureOut.getOrganization().getMeta().getHref());
        assertEquals(originalFactureOut.getDemands().get(0).getMeta().getHref(), updatedFactureOut.getDemands().get(0).getMeta().getHref());
    }

    @Override
    protected EntityApiClient entityClient() {
        return api.entity().factureout();
    }

    @Override
    protected Class<? extends MetaEntity> entityClass() {
        return FactureOut.class;
    }
}
