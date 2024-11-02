package ru.moysklad.remap_1_2.entities.documents;

import org.junit.Test;
import ru.moysklad.remap_1_2.clients.EntityClientBase;
import ru.moysklad.remap_1_2.entities.*;
import ru.moysklad.remap_1_2.entities.Meta;
import ru.moysklad.remap_1_2.entities.MetaEntity;
import ru.moysklad.remap_1_2.responses.ListEntity;
import ru.moysklad.remap_1_2.responses.metadata.MetadataAttributeSharedStatesResponse;
import ru.moysklad.remap_1_2.utils.ApiClientException;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.Date;

import static ru.moysklad.remap_1_2.utils.params.FilterParam.filterEq;
import static org.junit.Assert.*;

public class PurchaseOrderTest extends DocumentWithPositionsTestBase {
    @Test
    public void createTest() throws IOException, ApiClientException {
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
    public void metadataTest() throws IOException, ApiClientException {
        MetadataAttributeSharedStatesResponse<DocumentAttribute> response = api.entity().purchaseorder().metadata().get();

        assertFalse(response.getCreateShared());
    }

    @Test
    public void attributesTest() throws IOException, ApiClientException{
        ListEntity<Attribute> attributes = api.entity().purchaseorder().metadataAttributes();
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
        DocumentAttribute created = api.entity().purchaseorder().createMetadataAttribute(attribute);
        assertNotNull(created);
        assertEquals(name, created.getName());
        assertEquals(DocumentAttribute.Type.textValue, created.getType());
        assertFalse(created.getRequired());
        assertTrue(created.getShow());
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
        DocumentAttribute created = api.entity().purchaseorder().createMetadataAttribute(attribute);

        String name = "field" + randomString(3) + "_" + new Date().getTime();
        created.setName(name);
        created.setRequired(false);
        created.setShow(false);
        DocumentAttribute updated = api.entity().purchaseorder().updateMetadataAttribute(created);
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
        DocumentAttribute created = api.entity().purchaseorder().createMetadataAttribute(attribute);

        api.entity().purchaseorder().deleteMetadataAttribute(created);

        try {
            api.entity().purchaseorder().metadataAttributes(created.getId());
        } catch (ApiClientException e) {
            assertEquals(404, e.getStatusCode());
        }
    }

    @Test
    public void newTest() throws IOException, ApiClientException {
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
        assertFalse(purchaseOrder.getPublished());
        assertFalse(purchaseOrder.getPrinted());
        assertTrue(ChronoUnit.MILLIS.between(time, purchaseOrder.getMoment()) < 1000);

        assertEquals(purchaseOrder.getOrganization().getMeta().getHref(), simpleEntityManager.getOwnOrganization().getMeta().getHref());
        assertEquals(purchaseOrder.getStore().getMeta().getHref(), simpleEntityManager.getMainStore().getMeta().getHref());
    }

    @Test
    public void newByInternalOrderTest() throws IOException, ApiClientException {
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
    public void newByCustomerOrdersTest() throws IOException, ApiClientException {
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
    public EntityClientBase entityClient() {
        return api.entity().purchaseorder();
    }

    @Override
    public Class<? extends MetaEntity> entityClass() {
        return PurchaseOrder.class;
    }
}
