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
import java.util.Collections;
import java.util.Date;

import static com.lognex.api.utils.params.FilterParam.filterEq;
import static org.junit.Assert.*;

public class PurchaseOrderDocumentEntityTest extends EntityTestBase {
    @Test
    public void createTest() throws IOException, LognexApiException {
        PurchaseOrderDocumentEntity e = new PurchaseOrderDocumentEntity();
        e.setName("purchaseorder_" + randomString(3) + "_" + new Date().getTime());
        e.setDescription(randomString());
        e.setVatEnabled(true);
        e.setVatIncluded(true);
        e.setMoment(LocalDateTime.now());

        ListEntity<OrganizationEntity> orgList = api.entity().organization().get();
        assertNotEquals(0, orgList.getRows().size());
        e.setOrganization(orgList.getRows().get(0));

        CounterpartyEntity agent = new CounterpartyEntity();
        agent.setName(randomString());
        api.entity().counterparty().post(agent);
        e.setAgent(agent);

        ListEntity<StoreEntity> store = api.entity().store().get(filterEq("name", "Основной склад"));
        assertEquals(1, store.getRows().size());
        e.setStore(store.getRows().get(0));

        api.entity().purchaseorder().post(e);

        ListEntity<PurchaseOrderDocumentEntity> updatedEntitiesList = api.entity().purchaseorder().get(filterEq("name", e.getName()));
        assertEquals(1, updatedEntitiesList.getRows().size());

        PurchaseOrderDocumentEntity retrievedEntity = updatedEntitiesList.getRows().get(0);
        assertEquals(e.getName(), retrievedEntity.getName());
        assertEquals(e.getDescription(), retrievedEntity.getDescription());
        assertEquals(e.getVatEnabled(), retrievedEntity.getVatEnabled());
        assertEquals(e.getVatIncluded(), retrievedEntity.getVatIncluded());
        assertEquals(e.getMoment(), retrievedEntity.getMoment());
        assertEquals(e.getOrganization().getMeta().getHref(), retrievedEntity.getOrganization().getMeta().getHref());
        assertEquals(e.getAgent().getMeta().getHref(), retrievedEntity.getAgent().getMeta().getHref());
        assertEquals(e.getStore().getMeta().getHref(), retrievedEntity.getStore().getMeta().getHref());
        assertEquals(e.getCreated().withNano(0), retrievedEntity.getCreated().withNano(0));
        assertEquals(e.getUpdated().withNano(0), retrievedEntity.getUpdated().withNano(0));
    }

    @Test
    public void getTest() throws IOException, LognexApiException {
        PurchaseOrderDocumentEntity e = createSimpleDocumentPurchaseOrder();

        PurchaseOrderDocumentEntity retrievedEntity = api.entity().purchaseorder().get(e.getId());
        getAsserts(e, retrievedEntity);

        retrievedEntity = api.entity().purchaseorder().get(e);
        getAsserts(e, retrievedEntity);
    }

    @Test
    public void putTest() throws IOException, LognexApiException, InterruptedException {
        PurchaseOrderDocumentEntity e = createSimpleDocumentPurchaseOrder();

        PurchaseOrderDocumentEntity retrievedOriginalEntity = api.entity().purchaseorder().get(e.getId());
        String name = "purchaseorder_" + randomString(3) + "_" + new Date().getTime();
        e.setName(name);
        Thread.sleep(1500);
        api.entity().purchaseorder().put(e.getId(), e);
        putAsserts(e, retrievedOriginalEntity, name);

        retrievedOriginalEntity.set(e);

        name = "purchaseorder_" + randomString(3) + "_" + new Date().getTime();
        e.setName(name);
        Thread.sleep(1500);
        api.entity().purchaseorder().put(e);
        putAsserts(e, retrievedOriginalEntity, name);
    }

    @Test
    public void deleteTest() throws IOException, LognexApiException {
        PurchaseOrderDocumentEntity e = createSimpleDocumentPurchaseOrder();

        ListEntity<PurchaseOrderDocumentEntity> entitiesList = api.entity().purchaseorder().get(filterEq("name", e.getName()));
        assertEquals(1, entitiesList.getRows().size());

        api.entity().purchaseorder().delete(e.getId());

        entitiesList = api.entity().purchaseorder().get(filterEq("name", e.getName()));
        assertEquals(0, entitiesList.getRows().size());
    }

    @Test
    public void deleteByIdTest() throws IOException, LognexApiException {
        PurchaseOrderDocumentEntity e = createSimpleDocumentPurchaseOrder();

        ListEntity<PurchaseOrderDocumentEntity> entitiesList = api.entity().purchaseorder().get(filterEq("name", e.getName()));
        assertEquals(1, entitiesList.getRows().size());

        api.entity().purchaseorder().delete(e);

        entitiesList = api.entity().purchaseorder().get(filterEq("name", e.getName()));
        assertEquals(0, entitiesList.getRows().size());
    }

    @Test
    public void metadataTest() throws IOException, LognexApiException {
        MetadataAttributeSharedStatesResponse response = api.entity().purchaseorder().metadata().get();

        assertFalse(response.getCreateShared());
    }

    @Test
    public void newTest() throws IOException, LognexApiException {
        LocalDateTime time = LocalDateTime.now().withNano(0);
        PurchaseOrderDocumentEntity e = api.entity().purchaseorder().newDocument();

        assertEquals("", e.getName());
        assertTrue(e.getVatEnabled());
        assertTrue(e.getVatIncluded());
        assertEquals(Long.valueOf(0), e.getPayedSum());
        assertEquals(Long.valueOf(0), e.getShippedSum());
        assertEquals(Long.valueOf(0), e.getInvoicedSum());
        assertEquals(Long.valueOf(0), e.getSum());
        assertFalse(e.getShared());
        assertTrue(e.getApplicable());
        assertEquals(time, e.getCreated().withNano(0));

        ListEntity<OrganizationEntity> org = api.entity().organization().get(filterEq("name", "Администратор"));
        assertEquals(1, org.getRows().size());
        assertEquals(e.getOrganization().getMeta().getHref(), org.getRows().get(0).getMeta().getHref());

        ListEntity<StoreEntity> store = api.entity().store().get(filterEq("name", "Основной склад"));
        assertEquals(1, store.getRows().size());
        assertEquals(e.getStore().getMeta().getHref(), store.getRows().get(0).getMeta().getHref());
    }

    @Test
    public void newByInternalOrderTest() throws IOException, LognexApiException {
        InternalOrderDocumentEntity internalOrder = new InternalOrderDocumentEntity();
        internalOrder.setName("internalorder_" + randomString(3) + "_" + new Date().getTime());
        internalOrder.setVatEnabled(true);
        internalOrder.setVatIncluded(true);

        ListEntity<OrganizationEntity> orgList = api.entity().organization().get();
        assertNotEquals(0, orgList.getRows().size());
        internalOrder.setOrganization(orgList.getRows().get(0));

        ListEntity<StoreEntity> store = api.entity().store().get(filterEq("name", "Основной склад"));
        assertEquals(1, store.getRows().size());
        internalOrder.setStore(store.getRows().get(0));

        api.entity().internalorder().post(internalOrder);

        LocalDateTime time = LocalDateTime.now().withNano(0);
        PurchaseOrderDocumentEntity e = api.entity().purchaseorder().newDocument("internalOrder", internalOrder);

        assertEquals("", e.getName());
        assertEquals(internalOrder.getSum(), e.getSum());
        assertEquals(internalOrder.getVatEnabled(), e.getVatEnabled());
        assertEquals(internalOrder.getVatIncluded(), e.getVatIncluded());
        assertFalse(e.getShared());
        assertTrue(e.getApplicable());
        assertEquals(time, e.getMoment().withNano(0));
        assertEquals(internalOrder.getMeta().getHref(), e.getInternalOrder().getMeta().getHref());
    }

    @Test
    public void newByCustomerOrdersTest() throws IOException, LognexApiException {
        CustomerOrderDocumentEntity customerOrder = new CustomerOrderDocumentEntity();
        customerOrder.setName("customerorder_" + randomString(3) + "_" + new Date().getTime());

        ListEntity<OrganizationEntity> orgList = api.entity().organization().get();
        assertNotEquals(0, orgList.getRows().size());
        customerOrder.setOrganization(orgList.getRows().get(0));

        CounterpartyEntity agent = new CounterpartyEntity();
        agent.setName(randomString());
        api.entity().counterparty().post(agent);
        customerOrder.setAgent(agent);

        api.entity().customerorder().post(customerOrder);

        LocalDateTime time = LocalDateTime.now().withNano(0);
        PurchaseOrderDocumentEntity e = api.entity().purchaseorder().newDocument("customerOrders", Collections.singletonList(customerOrder));

        assertEquals("", e.getName());
        assertEquals(customerOrder.getVatEnabled(), e.getVatEnabled());
        assertEquals(customerOrder.getVatIncluded(), e.getVatIncluded());
        assertEquals(customerOrder.getPayedSum(), e.getPayedSum());
        assertEquals(customerOrder.getSum(), e.getSum());
        assertFalse(e.getShared());
        assertTrue(e.getApplicable());
        assertEquals(time, e.getMoment().withNano(0));
        assertEquals(1, e.getCustomerOrders().size());
        assertEquals(customerOrder.getMeta().getHref(), e.getCustomerOrders().get(0).getMeta().getHref());
    }

    private PurchaseOrderDocumentEntity createSimpleDocumentPurchaseOrder() throws IOException, LognexApiException {
        PurchaseOrderDocumentEntity e = new PurchaseOrderDocumentEntity();
        e.setName("purchaseorder_" + randomString(3) + "_" + new Date().getTime());
        e.setDescription(randomString());

        ListEntity<OrganizationEntity> orgList = api.entity().organization().get();
        assertNotEquals(0, orgList.getRows().size());
        e.setOrganization(orgList.getRows().get(0));

        CounterpartyEntity agent = new CounterpartyEntity();
        agent.setName(randomString());
        api.entity().counterparty().post(agent);
        e.setAgent(agent);

        api.entity().purchaseorder().post(e);

        return e;
    }

    private void getAsserts(PurchaseOrderDocumentEntity e, PurchaseOrderDocumentEntity retrievedEntity) {
        assertEquals(e.getName(), retrievedEntity.getName());
        assertEquals(e.getDescription(), retrievedEntity.getDescription());
        assertEquals(e.getOrganization().getMeta().getHref(), retrievedEntity.getOrganization().getMeta().getHref());
        assertEquals(e.getAgent().getMeta().getHref(), retrievedEntity.getAgent().getMeta().getHref());
        assertEquals(e.getCreated().withNano(0), retrievedEntity.getCreated().withNano(0));
        assertEquals(e.getUpdated().withNano(0), retrievedEntity.getUpdated().withNano(0));
    }

    private void putAsserts(PurchaseOrderDocumentEntity e, PurchaseOrderDocumentEntity retrievedOriginalEntity, String name) throws IOException, LognexApiException {
        PurchaseOrderDocumentEntity retrievedUpdatedEntity = api.entity().purchaseorder().get(e.getId());

        assertNotEquals(retrievedOriginalEntity.getName(), retrievedUpdatedEntity.getName());
        assertEquals(name, retrievedUpdatedEntity.getName());
        assertEquals(retrievedOriginalEntity.getDescription(), retrievedUpdatedEntity.getDescription());
        assertEquals(retrievedOriginalEntity.getOrganization().getMeta().getHref(), retrievedUpdatedEntity.getOrganization().getMeta().getHref());
        assertEquals(retrievedOriginalEntity.getAgent().getMeta().getHref(), retrievedUpdatedEntity.getAgent().getMeta().getHref());
        assertEquals(retrievedOriginalEntity.getCreated().withNano(0), retrievedUpdatedEntity.getCreated().withNano(0));
        assertNotEquals(retrievedOriginalEntity.getUpdated().withNano(0), retrievedUpdatedEntity.getUpdated().withNano(0));
    }
}
