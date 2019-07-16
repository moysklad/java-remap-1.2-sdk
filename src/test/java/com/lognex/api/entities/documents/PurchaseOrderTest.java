package com.lognex.api.entities.documents;

import com.lognex.api.clients.EntityApiClient;
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

public class PurchaseOrderTest extends DocumentWithPositionsTestBase {
    @Test
    public void createTest() throws IOException, LognexApiException {
        PurchaseOrder purchaseOrder = new PurchaseOrder();
        purchaseOrder.setName("purchaseorder_" + randomString(3) + "_" + new Date().getTime());
        purchaseOrder.setDescription(randomString());
        purchaseOrder.setVatEnabled(true);
        purchaseOrder.setVatIncluded(true);
        purchaseOrder.setMoment(LocalDateTime.now());
        purchaseOrder.setOrganization(simpleEntityManager.getOwnOrganization());
        purchaseOrder.setAgent(simpleEntityManager.createSimpleCounterparty());
        purchaseOrder.setStore(simpleEntityManager.getMainStore());

        api.entity().purchaseorder().create(purchaseOrder);

        ListEntity<PurchaseOrder> updatedEntitiesList = api.entity().purchaseorder().get(filterEq("name", purchaseOrder.getName()));
        assertEquals(1, updatedEntitiesList.getRows().size());

        PurchaseOrder retrievedEntity = updatedEntitiesList.getRows().get(0);
        assertEquals(purchaseOrder.getName(), retrievedEntity.getName());
        assertEquals(purchaseOrder.getDescription(), retrievedEntity.getDescription());
        assertEquals(purchaseOrder.getVatEnabled(), retrievedEntity.getVatEnabled());
        assertEquals(purchaseOrder.getVatIncluded(), retrievedEntity.getVatIncluded());
        assertEquals(purchaseOrder.getMoment(), retrievedEntity.getMoment());
        assertEquals(purchaseOrder.getOrganization().getMeta().getHref(), retrievedEntity.getOrganization().getMeta().getHref());
        assertEquals(purchaseOrder.getAgent().getMeta().getHref(), retrievedEntity.getAgent().getMeta().getHref());
        assertEquals(purchaseOrder.getStore().getMeta().getHref(), retrievedEntity.getStore().getMeta().getHref());
    }

    @Test
    public void metadataTest() throws IOException, LognexApiException {
        MetadataAttributeSharedStatesResponse response = api.entity().purchaseorder().metadata().get();

        assertFalse(response.getCreateShared());
    }

    @Test
    public void newTest() throws IOException, LognexApiException {
        PurchaseOrder purchaseOrder = api.entity().purchaseorder().templateDocument();
        LocalDateTime time = LocalDateTime.now();

        assertEquals("", purchaseOrder.getName());
        assertTrue(purchaseOrder.getVatEnabled());
        assertTrue(purchaseOrder.getVatIncluded());
        assertEquals(Long.valueOf(0), purchaseOrder.getPayedSum());
        assertEquals(Long.valueOf(0), purchaseOrder.getShippedSum());
        assertEquals(Long.valueOf(0), purchaseOrder.getInvoicedSum());
        assertEquals(Long.valueOf(0), purchaseOrder.getSum());
        assertFalse(purchaseOrder.getShared());
        assertTrue(purchaseOrder.getApplicable());
        assertTrue(ChronoUnit.MILLIS.between(time, purchaseOrder.getMoment()) < 1000);

        assertEquals(purchaseOrder.getOrganization().getMeta().getHref(), simpleEntityManager.getOwnOrganization().getMeta().getHref());
        assertEquals(purchaseOrder.getStore().getMeta().getHref(), simpleEntityManager.getMainStore().getMeta().getHref());
    }

    @Test
    public void newByInternalOrderTest() throws IOException, LognexApiException {
        InternalOrder internalOrder = simpleEntityManager.createSimple(InternalOrder.class);

        PurchaseOrder purchaseOrder = api.entity().purchaseorder().templateDocument("internalOrder", internalOrder);
        LocalDateTime time = LocalDateTime.now();

        assertEquals("", purchaseOrder.getName());
        assertEquals(internalOrder.getSum(), purchaseOrder.getSum());
        assertEquals(internalOrder.getVatEnabled(), purchaseOrder.getVatEnabled());
        assertEquals(internalOrder.getVatIncluded(), purchaseOrder.getVatIncluded());
        assertFalse(purchaseOrder.getShared());
        assertTrue(purchaseOrder.getApplicable());
        assertTrue(ChronoUnit.MILLIS.between(time, purchaseOrder.getMoment()) < 1000);
        assertEquals(internalOrder.getMeta().getHref(), purchaseOrder.getInternalOrder().getMeta().getHref());
        assertEquals(internalOrder.getStore().getMeta().getHref(), purchaseOrder.getStore().getMeta().getHref());
        assertEquals(internalOrder.getGroup().getMeta().getHref(), purchaseOrder.getGroup().getMeta().getHref());
        assertEquals(internalOrder.getOrganization().getMeta().getHref(), purchaseOrder.getOrganization().getMeta().getHref());
    }

    @Test
    public void newByCustomerOrdersTest() throws IOException, LognexApiException {
        CustomerOrder customerOrder = simpleEntityManager.createSimple(CustomerOrder.class);

        PurchaseOrder purchaseOrder = api.entity().purchaseorder().templateDocument("customerOrders", Collections.singletonList(customerOrder));
        LocalDateTime time = LocalDateTime.now();

        assertEquals("", purchaseOrder.getName());
        assertEquals(customerOrder.getVatEnabled(), purchaseOrder.getVatEnabled());
        assertEquals(customerOrder.getVatIncluded(), purchaseOrder.getVatIncluded());
        assertEquals(customerOrder.getPayedSum(), purchaseOrder.getPayedSum());
        assertEquals(customerOrder.getSum(), purchaseOrder.getSum());
        assertFalse(purchaseOrder.getShared());
        assertTrue(purchaseOrder.getApplicable());
        assertTrue(ChronoUnit.MILLIS.between(time, purchaseOrder.getMoment()) < 1000);
        assertEquals(1, purchaseOrder.getCustomerOrders().size());
        assertEquals(customerOrder.getMeta().getHref(), purchaseOrder.getCustomerOrders().get(0).getMeta().getHref());
        assertEquals(customerOrder.getGroup().getMeta().getHref(), purchaseOrder.getGroup().getMeta().getHref());
        assertEquals(customerOrder.getOrganization().getMeta().getHref(), purchaseOrder.getOrganization().getMeta().getHref());
    }

    @Override
    protected void getAsserts(MetaEntity originalEntity, MetaEntity retrievedEntity) {
        PurchaseOrder originalPurchaseOrder = (PurchaseOrder) originalEntity;
        PurchaseOrder retrievedPurchaseOrder = (PurchaseOrder) retrievedEntity;

        assertEquals(originalPurchaseOrder.getName(), retrievedPurchaseOrder.getName());
        assertEquals(originalPurchaseOrder.getDescription(), retrievedPurchaseOrder.getDescription());
        assertEquals(originalPurchaseOrder.getOrganization().getMeta().getHref(), retrievedPurchaseOrder.getOrganization().getMeta().getHref());
        assertEquals(originalPurchaseOrder.getAgent().getMeta().getHref(), retrievedPurchaseOrder.getAgent().getMeta().getHref());
    }

    @Override
    protected void putAsserts(MetaEntity originalEntity, MetaEntity updatedEntity, Object changedField) {
        PurchaseOrder originalPurchaseOrder = (PurchaseOrder) originalEntity;
        PurchaseOrder updatedPurchaseOrder = (PurchaseOrder) updatedEntity;

        assertNotEquals(originalPurchaseOrder.getName(), updatedPurchaseOrder.getName());
        assertEquals(changedField, updatedPurchaseOrder.getName());
        assertEquals(originalPurchaseOrder.getDescription(), updatedPurchaseOrder.getDescription());
        assertEquals(originalPurchaseOrder.getOrganization().getMeta().getHref(), updatedPurchaseOrder.getOrganization().getMeta().getHref());
        assertEquals(originalPurchaseOrder.getAgent().getMeta().getHref(), updatedPurchaseOrder.getAgent().getMeta().getHref());
    }

    @Override
    protected EntityApiClient entityClient() {
        return api.entity().purchaseorder();
    }

    @Override
    protected Class<? extends MetaEntity> entityClass() {
        return PurchaseOrder.class;
    }
}
