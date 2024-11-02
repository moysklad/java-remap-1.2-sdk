package ru.moysklad.remap_1_2.entities.documents;

import org.junit.Test;
import ru.moysklad.remap_1_2.clients.EntityClientBase;
import ru.moysklad.remap_1_2.entities.*;
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

public class CashOutTest extends EntityGetUpdateDeleteTest implements FilesTest<CashOut> {
    @Test
    public void createTest() throws IOException, ApiClientException {
        CashOut cashOut = new CashOut();
        cashOut.setName("cashout_" + randomString(3) + "_" + new Date().getTime());
        cashOut.setDescription(randomString());
        cashOut.setMoment(LocalDateTime.now());
        cashOut.setSum(randomLong(10, 10000));
        cashOut.setOrganization(simpleEntityManager.getOwnOrganization());
        cashOut.setAgent(simpleEntityManager.createSimpleCounterparty());
        cashOut.setExpenseItem(simpleEntityManager.createSimpleExpenseItem());

        api.entity().cashout().create(cashOut);

        ListEntity<CashOut> updatedEntitiesList = api.entity().cashout().get(filterEq("name", cashOut.getName()));
        assertEquals(1, updatedEntitiesList.getRows().size());

        CashOut retrievedEntity = updatedEntitiesList.getRows().get(0);
        assertEquals(cashOut.getName(), retrievedEntity.getName());
        assertEquals(cashOut.getDescription(), retrievedEntity.getDescription());
        assertEquals(cashOut.getMoment(), retrievedEntity.getMoment());
        assertEquals(cashOut.getSum(), retrievedEntity.getSum());
        assertEquals(cashOut.getOrganization().getMeta().getHref(), retrievedEntity.getOrganization().getMeta().getHref());
        assertEquals(cashOut.getAgent().getMeta().getHref(), retrievedEntity.getAgent().getMeta().getHref());
        assertEquals(cashOut.getExpenseItem().getMeta().getHref(), retrievedEntity.getExpenseItem().getMeta().getHref());
    }

    @Test
    public void metadataTest() throws IOException, ApiClientException {
        MetadataAttributeSharedStatesResponse<DocumentAttribute> response = api.entity().cashout().metadata().get();

        assertFalse(response.getCreateShared());
    }

    @Test
    public void attributesTest() throws IOException, ApiClientException{
        ListEntity<Attribute> attributes = api.entity().cashout().metadataAttributes();
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
        DocumentAttribute created = api.entity().cashout().createMetadataAttribute(attribute);
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
        DocumentAttribute created = api.entity().cashout().createMetadataAttribute(attribute);

        String name = "field" + randomString(3) + "_" + new Date().getTime();
        created.setName(name);
        created.setRequired(false);
        created.setShow(false);
        DocumentAttribute updated = api.entity().cashout().updateMetadataAttribute(created);
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
        DocumentAttribute created = api.entity().cashout().createMetadataAttribute(attribute);

        api.entity().cashout().deleteMetadataAttribute(created);

        try {
            api.entity().cashout().metadataAttributes(created.getId());
        } catch (ApiClientException e) {
            assertEquals(404, e.getStatusCode());
        }
    }

    @Test
    public void newTest() throws IOException, ApiClientException {
        CashOut cashOut = api.entity().cashout().templateDocument();
        LocalDateTime time = LocalDateTime.now();

        assertEquals("", cashOut.getName());
        assertEquals(Long.valueOf(0), cashOut.getSum());
        assertFalse(cashOut.getShared());
        assertTrue(cashOut.getApplicable());
        assertFalse(cashOut.getPublished());
        assertFalse(cashOut.getPrinted());
        assertTrue(ChronoUnit.MILLIS.between(time, cashOut.getMoment()) < 1000);

        assertEquals(cashOut.getOrganization().getMeta().getHref(), simpleEntityManager.getOwnOrganization().getMeta().getHref());
        assertEquals(cashOut.getGroup().getMeta().getHref(), simpleEntityManager.getMainGroup().getMeta().getHref());
    }

    @Test
    public void newByPurchaseOrdersTest() throws IOException, ApiClientException {
        PurchaseOrder purchaseOrder = simpleEntityManager.createSimple(PurchaseOrder.class);

        CashOut cashOut = api.entity().cashout().templateDocument("operations", Collections.singletonList(purchaseOrder));
        LocalDateTime time = LocalDateTime.now();

        assertEquals("", cashOut.getName());
        assertEquals(purchaseOrder.getSum(), cashOut.getSum());
        assertFalse(cashOut.getShared());
        assertTrue(cashOut.getApplicable());
        assertTrue(ChronoUnit.MILLIS.between(time, cashOut.getMoment()) < 1000);
        assertEquals(1, cashOut.getOperations().size());
        assertEquals(purchaseOrder.getMeta().getHref(), cashOut.getOperations().get(0).getMeta().getHref());
        assertEquals(purchaseOrder.getGroup().getMeta().getHref(), cashOut.getGroup().getMeta().getHref());
        assertEquals(purchaseOrder.getAgent().getMeta().getHref(), cashOut.getAgent().getMeta().getHref());
        assertEquals(purchaseOrder.getOrganization().getMeta().getHref(), cashOut.getOrganization().getMeta().getHref());
    }

    @Test
    public void newBySalesReturnsTest() throws IOException, ApiClientException {
        SalesReturn salesReturn = simpleEntityManager.createSimple(SalesReturn.class);

        CashOut cashOut = api.entity().cashout().templateDocument("operations", Collections.singletonList(salesReturn));
        LocalDateTime time = LocalDateTime.now();

        assertEquals("", cashOut.getName());
        assertEquals(salesReturn.getSum(), cashOut.getSum());
        assertFalse(cashOut.getShared());
        assertTrue(cashOut.getApplicable());
        assertTrue(ChronoUnit.MILLIS.between(time, cashOut.getMoment()) < 1000);
        assertEquals(1, cashOut.getOperations().size());
        assertEquals(salesReturn.getMeta().getHref(), cashOut.getOperations().get(0).getMeta().getHref());
        assertEquals(salesReturn.getGroup().getMeta().getHref(), cashOut.getGroup().getMeta().getHref());
        assertEquals(salesReturn.getAgent().getMeta().getHref(), cashOut.getAgent().getMeta().getHref());
        assertEquals(salesReturn.getOrganization().getMeta().getHref(), cashOut.getOrganization().getMeta().getHref());
    }

    @Test
    public void newBySuppliesTest() throws IOException, ApiClientException {
        Supply supply = simpleEntityManager.createSimple(Supply.class);

        CashOut cashOut = api.entity().cashout().templateDocument("operations", Collections.singletonList(supply));
        LocalDateTime time = LocalDateTime.now();

        assertEquals("", cashOut.getName());
        assertEquals(supply.getSum(), cashOut.getSum());
        assertFalse(cashOut.getShared());
        assertTrue(cashOut.getApplicable());
        assertTrue(ChronoUnit.MILLIS.between(time, cashOut.getMoment()) < 1000);
        assertEquals(1, cashOut.getOperations().size());
        assertEquals(supply.getMeta().getHref(), cashOut.getOperations().get(0).getMeta().getHref());
        assertEquals(supply.getGroup().getMeta().getHref(), cashOut.getGroup().getMeta().getHref());
        assertEquals(supply.getAgent().getMeta().getHref(), cashOut.getAgent().getMeta().getHref());
        assertEquals(supply.getOrganization().getMeta().getHref(), cashOut.getOrganization().getMeta().getHref());
    }

    @Test
    public void newByInvoicesInTest() throws IOException, ApiClientException {
        InvoiceIn invoiceIn = simpleEntityManager.createSimple(InvoiceIn.class);

        CashOut cashOut = api.entity().cashout().templateDocument("operations", Collections.singletonList(invoiceIn));
        LocalDateTime time = LocalDateTime.now();

        assertEquals("", cashOut.getName());
        assertEquals(invoiceIn.getSum(), cashOut.getSum());
        assertFalse(cashOut.getShared());
        assertTrue(cashOut.getApplicable());
        assertTrue(ChronoUnit.MILLIS.between(time, cashOut.getMoment()) < 1000);
        assertEquals(1, cashOut.getOperations().size());
        assertEquals(invoiceIn.getMeta().getHref(), cashOut.getOperations().get(0).getMeta().getHref());
        assertEquals(invoiceIn.getGroup().getMeta().getHref(), cashOut.getGroup().getMeta().getHref());
        assertEquals(invoiceIn.getAgent().getMeta().getHref(), cashOut.getAgent().getMeta().getHref());
        assertEquals(invoiceIn.getOrganization().getMeta().getHref(), cashOut.getOrganization().getMeta().getHref());
    }

    @Test
    public void newByCommissionReportsOutTest() throws IOException, ApiClientException {
        CommissionReportOut commissionReportOut = simpleEntityManager.createSimple(CommissionReportOut.class);

        CashOut cashOut = api.entity().cashout().templateDocument("operations", Collections.singletonList(commissionReportOut));
        LocalDateTime time = LocalDateTime.now();

        assertEquals("", cashOut.getName());
        assertEquals(commissionReportOut.getSum(), cashOut.getSum());
        assertFalse(cashOut.getShared());
        assertTrue(cashOut.getApplicable());
        assertTrue(ChronoUnit.MILLIS.between(time, cashOut.getMoment()) < 1000);
        assertEquals(1, cashOut.getOperations().size());
        assertEquals(commissionReportOut.getMeta().getHref(), cashOut.getOperations().get(0).getMeta().getHref());
        assertEquals(commissionReportOut.getGroup().getMeta().getHref(), cashOut.getGroup().getMeta().getHref());
        assertEquals(commissionReportOut.getContract().getMeta().getHref(), cashOut.getContract().getMeta().getHref());
        assertEquals(commissionReportOut.getAgent().getMeta().getHref(), cashOut.getAgent().getMeta().getHref());
        assertEquals(commissionReportOut.getOrganization().getMeta().getHref(), cashOut.getOrganization().getMeta().getHref());
    }

    @Override
    protected void getAsserts(MetaEntity originalEntity, MetaEntity retrievedEntity) {
        CashOut originalCashOut = (CashOut) originalEntity;
        CashOut retrievedCashOut = (CashOut) retrievedEntity;

        assertEquals(originalCashOut.getName(), retrievedCashOut.getName());
        assertEquals(originalCashOut.getDescription(), retrievedCashOut.getDescription());
        assertEquals(originalCashOut.getOrganization().getMeta().getHref(), retrievedCashOut.getOrganization().getMeta().getHref());
        assertEquals(originalCashOut.getAgent().getMeta().getHref(), retrievedCashOut.getAgent().getMeta().getHref());
        assertEquals(originalCashOut.getExpenseItem().getMeta().getHref(), retrievedCashOut.getExpenseItem().getMeta().getHref());
    }

    @Override
    protected void putAsserts(MetaEntity originalEntity, MetaEntity updatedEntity, Object changedField) {
        CashOut originalCashOut = (CashOut) originalEntity;
        CashOut updatedCashOut = (CashOut) updatedEntity;

        assertNotEquals(originalCashOut.getName(), updatedCashOut.getName());
        assertEquals(changedField, updatedCashOut.getName());
        assertEquals(originalCashOut.getDescription(), updatedCashOut.getDescription());
        assertEquals(originalCashOut.getOrganization().getMeta().getHref(), updatedCashOut.getOrganization().getMeta().getHref());
        assertEquals(originalCashOut.getAgent().getMeta().getHref(), updatedCashOut.getAgent().getMeta().getHref());
        assertEquals(originalCashOut.getExpenseItem().getMeta().getHref(), updatedCashOut.getExpenseItem().getMeta().getHref());
    }

    @Test
    public void testFiles() throws IOException, ApiClientException {
        doTestFiles();
    }

    @Override
    public EntityClientBase entityClient() {
        return api.entity().cashout();
    }

    @Override
    public Class<? extends MetaEntity> entityClass() {
        return CashOut.class;
    }
}
