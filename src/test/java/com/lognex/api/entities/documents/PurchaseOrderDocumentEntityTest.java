package com.lognex.api.entities.documents;

import com.lognex.api.clients.ApiClient;
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

public class PurchaseOrderDocumentEntityTest extends DocumentWithPositionsTestBase {
    @Test
    public void createTest() throws IOException, LognexApiException {
        PurchaseOrderDocumentEntity purchaseOrder = new PurchaseOrderDocumentEntity();
        purchaseOrder.setName("purchaseorder_" + randomString(3) + "_" + new Date().getTime());
        purchaseOrder.setDescription(randomString());
        purchaseOrder.setVatEnabled(true);
        purchaseOrder.setVatIncluded(true);
        purchaseOrder.setMoment(LocalDateTime.now());
        purchaseOrder.setOrganization(simpleEntityFactory.getOwnOrganization());
        purchaseOrder.setAgent(simpleEntityFactory.createSimpleCounterparty());
        purchaseOrder.setStore(simpleEntityFactory.getMainStore());

        api.entity().purchaseorder().post(purchaseOrder);

        ListEntity<PurchaseOrderDocumentEntity> updatedEntitiesList = api.entity().purchaseorder().get(filterEq("name", purchaseOrder.getName()));
        assertEquals(1, updatedEntitiesList.getRows().size());

        PurchaseOrderDocumentEntity retrievedEntity = updatedEntitiesList.getRows().get(0);
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
    public void getTest() throws IOException, LognexApiException {
        PurchaseOrderDocumentEntity purchaseOrder = simpleEntityFactory.createSimplePurchaseOrder();

        PurchaseOrderDocumentEntity retrievedEntity = api.entity().purchaseorder().get(purchaseOrder.getId());
        getAsserts(purchaseOrder, retrievedEntity);

        retrievedEntity = api.entity().purchaseorder().get(purchaseOrder);
        getAsserts(purchaseOrder, retrievedEntity);
    }

    @Test
    public void putTest() throws IOException, LognexApiException {
        PurchaseOrderDocumentEntity purchaseOrder = simpleEntityFactory.createSimplePurchaseOrder();

        PurchaseOrderDocumentEntity retrievedOriginalEntity = api.entity().purchaseorder().get(purchaseOrder.getId());
        String name = "purchaseorder_" + randomString(3) + "_" + new Date().getTime();
        purchaseOrder.setName(name);
        api.entity().purchaseorder().put(purchaseOrder.getId(), purchaseOrder);
        putAsserts(purchaseOrder, retrievedOriginalEntity, name);

        retrievedOriginalEntity.set(purchaseOrder);

        name = "purchaseorder_" + randomString(3) + "_" + new Date().getTime();
        purchaseOrder.setName(name);
        api.entity().purchaseorder().put(purchaseOrder);
        putAsserts(purchaseOrder, retrievedOriginalEntity, name);
    }

    @Test
    public void deleteTest() throws IOException, LognexApiException {
        PurchaseOrderDocumentEntity purchaseOrder = simpleEntityFactory.createSimplePurchaseOrder();

        ListEntity<PurchaseOrderDocumentEntity> entitiesList = api.entity().purchaseorder().get(filterEq("name", purchaseOrder.getName()));
        assertEquals((Integer) 1, entitiesList.getMeta().getSize());

        api.entity().purchaseorder().delete(purchaseOrder.getId());

        entitiesList = api.entity().purchaseorder().get(filterEq("name", purchaseOrder.getName()));
        assertEquals((Integer) 0, entitiesList.getMeta().getSize());
    }

    @Test
    public void deleteByIdTest() throws IOException, LognexApiException {
        PurchaseOrderDocumentEntity purchaseOrder = simpleEntityFactory.createSimplePurchaseOrder();

        ListEntity<PurchaseOrderDocumentEntity> entitiesList = api.entity().purchaseorder().get(filterEq("name", purchaseOrder.getName()));
        assertEquals((Integer) 1, entitiesList.getMeta().getSize());

        api.entity().purchaseorder().delete(purchaseOrder);

        entitiesList = api.entity().purchaseorder().get(filterEq("name", purchaseOrder.getName()));
        assertEquals((Integer) 0, entitiesList.getMeta().getSize());
    }

    @Test
    public void metadataTest() throws IOException, LognexApiException {
        MetadataAttributeSharedStatesResponse response = api.entity().purchaseorder().metadata().get();

        assertFalse(response.getCreateShared());
    }

    @Test
    public void newTest() throws IOException, LognexApiException {
        PurchaseOrderDocumentEntity purchaseOrder = api.entity().purchaseorder().newDocument();
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

        assertEquals(purchaseOrder.getOrganization().getMeta().getHref(), simpleEntityFactory.getOwnOrganization().getMeta().getHref());
        assertEquals(purchaseOrder.getStore().getMeta().getHref(), simpleEntityFactory.getMainStore().getMeta().getHref());
    }

    @Test
    public void newByInternalOrderTest() throws IOException, LognexApiException {
        InternalOrderDocumentEntity internalOrder = simpleEntityFactory.createSimpleInternalOrder();

        PurchaseOrderDocumentEntity purchaseOrder = api.entity().purchaseorder().newDocument("internalOrder", internalOrder);
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
        CustomerOrderDocumentEntity customerOrder = simpleEntityFactory.createSimpleCustomerOrder();

        PurchaseOrderDocumentEntity purchaseOrder = api.entity().purchaseorder().newDocument("customerOrders", Collections.singletonList(customerOrder));
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

    private void getAsserts(PurchaseOrderDocumentEntity purchaseOrder, PurchaseOrderDocumentEntity retrievedEntity) {
        assertEquals(purchaseOrder.getName(), retrievedEntity.getName());
        assertEquals(purchaseOrder.getDescription(), retrievedEntity.getDescription());
        assertEquals(purchaseOrder.getOrganization().getMeta().getHref(), retrievedEntity.getOrganization().getMeta().getHref());
        assertEquals(purchaseOrder.getAgent().getMeta().getHref(), retrievedEntity.getAgent().getMeta().getHref());
    }

    private void putAsserts(PurchaseOrderDocumentEntity purchaseOrder, PurchaseOrderDocumentEntity retrievedOriginalEntity, String name) throws IOException, LognexApiException {
        PurchaseOrderDocumentEntity retrievedUpdatedEntity = api.entity().purchaseorder().get(purchaseOrder.getId());

        assertNotEquals(retrievedOriginalEntity.getName(), retrievedUpdatedEntity.getName());
        assertEquals(name, retrievedUpdatedEntity.getName());
        assertEquals(retrievedOriginalEntity.getDescription(), retrievedUpdatedEntity.getDescription());
        assertEquals(retrievedOriginalEntity.getOrganization().getMeta().getHref(), retrievedUpdatedEntity.getOrganization().getMeta().getHref());
        assertEquals(retrievedOriginalEntity.getAgent().getMeta().getHref(), retrievedUpdatedEntity.getAgent().getMeta().getHref());
    }

    @Override
    protected ApiClient entityClient() {
        return api.entity().purchaseorder();
    }

    @Override
    protected Class<? extends MetaEntity> entityClass() {
        return PurchaseOrderDocumentEntity.class;
    }
}
