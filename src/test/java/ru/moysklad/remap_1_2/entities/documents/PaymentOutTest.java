package ru.moysklad.remap_1_2.entities.documents;

import org.junit.Test;
import ru.moysklad.remap_1_2.clients.EntityClientBase;
import ru.moysklad.remap_1_2.entities.*;
import ru.moysklad.remap_1_2.entities.EntityGetUpdateDeleteTest;
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

public class PaymentOutTest extends EntityGetUpdateDeleteTest {
    @Test
    public void createTest() throws IOException, ApiClientException {
        PaymentOut paymentOut = new PaymentOut();
        paymentOut.setName("paymentout_" + randomString(3) + "_" + new Date().getTime());
        paymentOut.setMoment(LocalDateTime.now());
        paymentOut.setSum(randomLong(10, 10000));
        paymentOut.setOrganization(simpleEntityManager.getOwnOrganization());
        paymentOut.setAgent(simpleEntityManager.createSimpleCounterparty());
        paymentOut.setExpenseItem(simpleEntityManager.createSimpleExpenseItem());

        api.entity().paymentout().create(paymentOut);

        ListEntity<PaymentOut> updatedEntitiesList = api.entity().paymentout().get(filterEq("name", paymentOut.getName()));
        assertEquals(1, updatedEntitiesList.getRows().size());

        PaymentOut retrievedEntity = updatedEntitiesList.getRows().get(0);
        assertEquals(paymentOut.getName(), retrievedEntity.getName());
        assertEquals(paymentOut.getMoment(), retrievedEntity.getMoment());
        assertEquals(paymentOut.getSum(), retrievedEntity.getSum());
        assertEquals(paymentOut.getOrganization().getMeta().getHref(), retrievedEntity.getOrganization().getMeta().getHref());
        assertEquals(paymentOut.getAgent().getMeta().getHref(), retrievedEntity.getAgent().getMeta().getHref());
        assertEquals(paymentOut.getExpenseItem().getMeta().getHref(), retrievedEntity.getExpenseItem().getMeta().getHref());
    }

    @Test
    public void metadataTest() throws IOException, ApiClientException {
        MetadataAttributeSharedStatesResponse<DocumentAttribute> response = api.entity().paymentout().metadata().get();

        assertFalse(response.getCreateShared());
    }
    
    @Test
    public void attributesTest() throws IOException, ApiClientException{
        ListEntity<Attribute> attributes = api.entity().paymentout().metadataAttributes();
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
        DocumentAttribute created = api.entity().paymentout().createMetadataAttribute(attribute);
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
        DocumentAttribute created = api.entity().paymentout().createMetadataAttribute(attribute);

        String name = "field" + randomString(3) + "_" + new Date().getTime();
        created.setName(name);
        created.setRequired(false);
        created.setShow(false);
        DocumentAttribute updated = api.entity().paymentout().updateMetadataAttribute(created);
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
        DocumentAttribute created = api.entity().paymentout().createMetadataAttribute(attribute);

        api.entity().paymentout().deleteMetadataAttribute(created);

        try {
            api.entity().paymentout().metadataAttributes(created.getId());
        } catch (ApiClientException e) {
            assertEquals(404, e.getStatusCode());
        }
    }

    @Test
    public void newTest() throws IOException, ApiClientException {
        PaymentOut paymentOut = api.entity().paymentout().templateDocument();
        LocalDateTime time = LocalDateTime.now();

        assertEquals("", paymentOut.getName());
        assertEquals(Long.valueOf(0), paymentOut.getSum());
        assertFalse(paymentOut.getShared());
        assertTrue(paymentOut.getApplicable());
        assertFalse(paymentOut.getPublished());
        assertFalse(paymentOut.getPrinted());
        assertTrue(ChronoUnit.MILLIS.between(time, paymentOut.getMoment()) < 1000);

        assertEquals(paymentOut.getOrganization().getMeta().getHref(), simpleEntityManager.getOwnOrganization().getMeta().getHref());
        assertEquals(paymentOut.getGroup().getMeta().getHref(), simpleEntityManager.getMainGroup().getMeta().getHref());
    }

    @Test
    public void newByPurchaseOrdersTest() throws IOException, ApiClientException {
        PurchaseOrder purchaseOrder = simpleEntityManager.createSimple(PurchaseOrder.class);

        PaymentOut paymentOut = api.entity().paymentout().templateDocument("operations", Collections.singletonList(purchaseOrder));
        LocalDateTime time = LocalDateTime.now();

        assertEquals("", paymentOut.getName());
        assertEquals(purchaseOrder.getSum(), paymentOut.getSum());
        assertFalse(paymentOut.getShared());
        assertTrue(paymentOut.getApplicable());
        assertTrue(ChronoUnit.MILLIS.between(time, paymentOut.getMoment()) < 1000);
        assertEquals(1, paymentOut.getOperations().size());
        assertEquals(purchaseOrder.getMeta().getHref(), paymentOut.getOperations().get(0).getMeta().getHref());
        assertEquals(purchaseOrder.getGroup().getMeta().getHref(), paymentOut.getGroup().getMeta().getHref());
        assertEquals(purchaseOrder.getAgent().getMeta().getHref(), paymentOut.getAgent().getMeta().getHref());
        assertEquals(purchaseOrder.getOrganization().getMeta().getHref(), paymentOut.getOrganization().getMeta().getHref());
    }

    @Test
    public void newBySalesReturnsTest() throws IOException, ApiClientException {
        SalesReturn salesReturn = simpleEntityManager.createSimple(SalesReturn.class);

        PaymentOut paymentOut = api.entity().paymentout().templateDocument("operations", Collections.singletonList(salesReturn));
        LocalDateTime time = LocalDateTime.now();

        assertEquals("", paymentOut.getName());
        assertEquals(salesReturn.getSum(), paymentOut.getSum());
        assertFalse(paymentOut.getShared());
        assertTrue(paymentOut.getApplicable());
        assertTrue(ChronoUnit.MILLIS.between(time, paymentOut.getMoment()) < 1000);
        assertEquals(1, paymentOut.getOperations().size());
        assertEquals(salesReturn.getMeta().getHref(), paymentOut.getOperations().get(0).getMeta().getHref());
        assertEquals(salesReturn.getGroup().getMeta().getHref(), paymentOut.getGroup().getMeta().getHref());
        assertEquals(salesReturn.getAgent().getMeta().getHref(), paymentOut.getAgent().getMeta().getHref());
        assertEquals(salesReturn.getOrganization().getMeta().getHref(), paymentOut.getOrganization().getMeta().getHref());
    }

    @Test
    public void newBySuppliesTest() throws IOException, ApiClientException {
        Supply supply = simpleEntityManager.createSimple(Supply.class);

        PaymentOut paymentOut = api.entity().paymentout().templateDocument("operations", Collections.singletonList(supply));
        LocalDateTime time = LocalDateTime.now();

        assertEquals("", paymentOut.getName());
        assertEquals(supply.getSum(), paymentOut.getSum());
        assertFalse(paymentOut.getShared());
        assertTrue(paymentOut.getApplicable());
        assertTrue(ChronoUnit.MILLIS.between(time, paymentOut.getMoment()) < 1000);
        assertEquals(1, paymentOut.getOperations().size());
        assertEquals(supply.getMeta().getHref(), paymentOut.getOperations().get(0).getMeta().getHref());
        assertEquals(supply.getGroup().getMeta().getHref(), paymentOut.getGroup().getMeta().getHref());
        assertEquals(supply.getAgent().getMeta().getHref(), paymentOut.getAgent().getMeta().getHref());
        assertEquals(supply.getOrganization().getMeta().getHref(), paymentOut.getOrganization().getMeta().getHref());
    }

    @Test
    public void newByInvoicesInTest() throws IOException, ApiClientException {
        InvoiceIn invoiceIn = simpleEntityManager.createSimple(InvoiceIn.class);

        PaymentOut paymentOut = api.entity().paymentout().templateDocument("operations", Collections.singletonList(invoiceIn));
        LocalDateTime time = LocalDateTime.now();

        assertEquals("", paymentOut.getName());
        assertEquals(invoiceIn.getSum(), paymentOut.getSum());
        assertFalse(paymentOut.getShared());
        assertTrue(paymentOut.getApplicable());
        assertTrue(ChronoUnit.MILLIS.between(time, paymentOut.getMoment()) < 1000);
        assertEquals(1, paymentOut.getOperations().size());
        assertEquals(invoiceIn.getMeta().getHref(), paymentOut.getOperations().get(0).getMeta().getHref());
        assertEquals(invoiceIn.getGroup().getMeta().getHref(), paymentOut.getGroup().getMeta().getHref());
        assertEquals(invoiceIn.getAgent().getMeta().getHref(), paymentOut.getAgent().getMeta().getHref());
        assertEquals(invoiceIn.getOrganization().getMeta().getHref(), paymentOut.getOrganization().getMeta().getHref());
    }

    @Test
    public void newByCommissionReportsOutTest() throws IOException, ApiClientException {
        CommissionReportOut commissionReportOut = simpleEntityManager.createSimple(CommissionReportOut.class);

        PaymentOut paymentOut = api.entity().paymentout().templateDocument("operations", Collections.singletonList(commissionReportOut));
        LocalDateTime time = LocalDateTime.now();

        assertEquals("", paymentOut.getName());
        assertEquals(commissionReportOut.getSum(), paymentOut.getSum());
        assertFalse(paymentOut.getShared());
        assertTrue(paymentOut.getApplicable());
        assertTrue(ChronoUnit.MILLIS.between(time, paymentOut.getMoment()) < 1000);
        assertEquals(1, paymentOut.getOperations().size());
        assertEquals(commissionReportOut.getMeta().getHref(), paymentOut.getOperations().get(0).getMeta().getHref());
        assertEquals(commissionReportOut.getGroup().getMeta().getHref(), paymentOut.getGroup().getMeta().getHref());
        assertEquals(commissionReportOut.getContract().getMeta().getHref(), paymentOut.getContract().getMeta().getHref());
        assertEquals(commissionReportOut.getAgent().getMeta().getHref(), paymentOut.getAgent().getMeta().getHref());
        assertEquals(commissionReportOut.getOrganization().getMeta().getHref(), paymentOut.getOrganization().getMeta().getHref());
    }

    @Override
    protected void getAsserts(MetaEntity originalEntity, MetaEntity retrievedEntity) {
        PaymentOut originalPaymentOut = (PaymentOut) originalEntity;
        PaymentOut retrievedPaymentOut = (PaymentOut) retrievedEntity;

        assertEquals(originalPaymentOut.getName(), retrievedPaymentOut.getName());
        assertEquals(originalPaymentOut.getOrganization().getMeta().getHref(), retrievedPaymentOut.getOrganization().getMeta().getHref());
        assertEquals(originalPaymentOut.getAgent().getMeta().getHref(), retrievedPaymentOut.getAgent().getMeta().getHref());
        assertEquals(originalPaymentOut.getExpenseItem().getMeta().getHref(), retrievedPaymentOut.getExpenseItem().getMeta().getHref());
    }

    @Override
    protected void putAsserts(MetaEntity originalEntity, MetaEntity updatedEntity, Object changedField) {
        PaymentOut originalPaymentOut = (PaymentOut) originalEntity;
        PaymentOut updatedPaymentOut = (PaymentOut) updatedEntity;

        assertNotEquals(originalPaymentOut.getName(), updatedPaymentOut.getName());
        assertEquals(changedField, updatedPaymentOut.getName());
        assertEquals(originalPaymentOut.getOrganization().getMeta().getHref(), updatedPaymentOut.getOrganization().getMeta().getHref());
        assertEquals(originalPaymentOut.getAgent().getMeta().getHref(), updatedPaymentOut.getAgent().getMeta().getHref());
        assertEquals(originalPaymentOut.getExpenseItem().getMeta().getHref(), updatedPaymentOut.getExpenseItem().getMeta().getHref());
    }

    @Override
    public EntityClientBase entityClient() {
        return api.entity().paymentout();
    }

    @Override
    public Class<? extends MetaEntity> entityClass() {
        return PaymentOut.class;
    }
}
