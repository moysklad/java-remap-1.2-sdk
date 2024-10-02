package ru.moysklad.remap_1_2.entities.documents;

import org.junit.Ignore;
import org.junit.Test;
import ru.moysklad.remap_1_2.clients.EntityClientBase;
import ru.moysklad.remap_1_2.entities.*;
import ru.moysklad.remap_1_2.entities.agents.Counterparty;
import ru.moysklad.remap_1_2.entities.agents.Organization;
import ru.moysklad.remap_1_2.responses.ListEntity;
import ru.moysklad.remap_1_2.responses.metadata.MetadataAttributeSharedStatesResponse;
import ru.moysklad.remap_1_2.utils.ApiClientException;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static ru.moysklad.remap_1_2.utils.params.FilterParam.filterEq;
import static org.junit.Assert.*;

public class FactureOutTest extends EntityGetUpdateDeleteTest implements FilesTest<FactureOut> {
    @Test
    public void createTest() throws IOException, ApiClientException {
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
    public void metadataTest() throws IOException, ApiClientException {
        MetadataAttributeSharedStatesResponse<DocumentAttribute> response = api.entity().factureout().metadata().get();

        assertFalse(response.getCreateShared());
    }

    @Test
    public void attributesTest() throws IOException, ApiClientException{
        ListEntity<Attribute> attributes = api.entity().factureout().metadataAttributes();
        assertNotNull(attributes);
    }

    @Test
    public void createAttributeTest() throws IOException, ApiClientException {
        DocumentAttribute attribute = new DocumentAttribute();
        attribute.setType(DocumentAttribute.Type.textValue);
        String name = "field" + randomString(3) + "_" + new Date().getTime();
        attribute.setName(name);
        attribute.setRequired(false);
        attribute.setShow(true);
        attribute.setDescription("description");
        DocumentAttribute created = api.entity().factureout().createMetadataAttribute(attribute);
        assertNotNull(created);
        assertEquals(name, created.getName());
        assertEquals(DocumentAttribute.Type.textValue, created.getType());
        assertFalse(created.getRequired());
        assertTrue(created.getShow());
        assertEquals("description", created.getDescription());
    }

    @Test
    public void updateAttributeTest() throws IOException, ApiClientException {
        DocumentAttribute attribute = new DocumentAttribute();
        attribute.setEntityType(Meta.Type.PRODUCT);
        attribute.setName("field" + randomString(3) + "_" + new Date().getTime());
        attribute.setRequired(true);
        attribute.setShow(true);
        DocumentAttribute created = api.entity().factureout().createMetadataAttribute(attribute);

        String name = "field" + randomString(3) + "_" + new Date().getTime();
        created.setName(name);
        created.setRequired(false);
        created.setShow(false);
        DocumentAttribute updated = api.entity().factureout().updateMetadataAttribute(created);
        assertNotNull(created);
        assertEquals(name, updated.getName());
        assertNull(updated.getType());
        assertEquals(Meta.Type.PRODUCT, updated.getEntityType());
        assertFalse(updated.getRequired());
        assertFalse(updated.getShow());
    }

    @Test
    public void deleteAttributeTest() throws IOException, ApiClientException{
        DocumentAttribute attribute = new DocumentAttribute();
        attribute.setEntityType(Meta.Type.PRODUCT);
        attribute.setName("field" + randomString(3) + "_" + new Date().getTime());
        attribute.setRequired(true);
        attribute.setShow(true);
        DocumentAttribute created = api.entity().factureout().createMetadataAttribute(attribute);

        api.entity().factureout().deleteMetadataAttribute(created);

        try {
            api.entity().factureout().metadataAttributes(created.getId());
        } catch (ApiClientException e) {
            assertEquals(404, e.getStatusCode());
        }
    }

    @Test
    public void newByDemandsTest() throws IOException, ApiClientException {
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
        assertFalse(factureOut.getPublished());
        assertFalse(factureOut.getPrinted());
        assertTrue(ChronoUnit.MILLIS.between(time, factureOut.getMoment()) < 1000);
        assertEquals(1, factureOut.getDemands().size());
        assertEquals(demand.getMeta().getHref(), factureOut.getDemands().get(0).getMeta().getHref());
        assertEquals(demand.getGroup().getMeta().getHref(), factureOut.getGroup().getMeta().getHref());
        assertEquals(demand.getAgent().getMeta().getHref(), factureOut.getAgent().getMeta().getHref());
        assertEquals(demand.getOrganization().getMeta().getHref(), factureOut.getOrganization().getMeta().getHref());
    }

    @Test
    public void newByPurchaseReturnsTest() throws IOException, ApiClientException {
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
    public void newByPaymentsInTest() throws IOException, ApiClientException {
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

    @Ignore // https://lognex.atlassian.net/browse/MC-46799
    @Test
    public void testFiles() throws IOException, ApiClientException {
        doTestFiles();
    }

    @Override
    public EntityClientBase entityClient() {
        return api.entity().factureout();
    }

    @Override
    public Class<? extends MetaEntity> entityClass() {
        return FactureOut.class;
    }
}
