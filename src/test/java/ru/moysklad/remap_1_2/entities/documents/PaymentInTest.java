package ru.moysklad.remap_1_2.entities.documents;

import com.google.common.collect.ImmutableList;
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
import java.util.List;

import static ru.moysklad.remap_1_2.utils.params.FilterParam.filterEq;
import static org.junit.Assert.*;

public class PaymentInTest extends EntityGetUpdateDeleteTest implements FilesTest<PaymentIn> {
    @Test
    public void createTest() throws IOException, ApiClientException {
        PaymentIn paymentIn = new PaymentIn();
        paymentIn.setName("paymentin_" + randomString(3) + "_" + new Date().getTime());
        paymentIn.setMoment(LocalDateTime.now());
        paymentIn.setSum(randomLong(10, 10000));
        paymentIn.setOrganization(simpleEntityManager.getOwnOrganization());
        paymentIn.setAgent(simpleEntityManager.createSimpleCounterparty());

        api.entity().paymentin().create(paymentIn);

        ListEntity<PaymentIn> updatedEntitiesList = api.entity().paymentin().get(filterEq("name", paymentIn.getName()));
        assertEquals(1, updatedEntitiesList.getRows().size());

        PaymentIn retrievedEntity = updatedEntitiesList.getRows().get(0);
        assertEquals(paymentIn.getName(), retrievedEntity.getName());
        assertEquals(paymentIn.getMoment(), retrievedEntity.getMoment());
        assertEquals(paymentIn.getSum(), retrievedEntity.getSum());
        assertEquals(paymentIn.getOrganization().getMeta().getHref(), retrievedEntity.getOrganization().getMeta().getHref());
        assertEquals(paymentIn.getAgent().getMeta().getHref(), retrievedEntity.getAgent().getMeta().getHref());
    }

    @Test
    public void metadataTest() throws IOException, ApiClientException {
        MetadataAttributeSharedStatesResponse<DocumentAttribute> response = api.entity().paymentin().metadata().get();

        assertFalse(response.getCreateShared());
    }

    @Test
    public void attributesTest() throws IOException, ApiClientException{
        ListEntity<Attribute> attributes = api.entity().paymentin().metadataAttributes();
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
        DocumentAttribute created = api.entity().paymentin().createMetadataAttribute(attribute);
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
        DocumentAttribute created = api.entity().paymentin().createMetadataAttribute(attribute);

        String name = "field" + randomString(3) + "_" + new Date().getTime();
        created.setName(name);
        created.setRequired(false);
        created.setShow(false);
        DocumentAttribute updated = api.entity().paymentin().updateMetadataAttribute(created);
        assertNotNull(created);
        assertEquals(name, updated.getName());
        assertNull(updated.getType());
        assertEquals(Meta.Type.PRODUCT, updated.getEntityType());
        assertFalse(updated.getRequired());
        assertFalse(updated.getShow());
    }

    @Test
    public void linkDemandToPaymentInWithLinkedSumTest() throws ApiClientException, IOException {
        Demand demand = new Demand();
        demand.setName("demand_" + randomString(3) + "_" + new Date().getTime());
        demand.setDescription(randomString());
        demand.setVatEnabled(true);
        demand.setVatIncluded(true);
        demand.setMoment(LocalDateTime.now());
        demand.setOrganization(simpleEntityManager.getOwnOrganization());
        demand.setAgent(simpleEntityManager.createSimpleCounterparty());
        demand.setStore(simpleEntityManager.getMainStore());
        PaymentIn paymentIn = new PaymentIn();
        paymentIn.setName("paymentin_" + randomString(3) + "_" + new Date().getTime());
        paymentIn.setMoment(LocalDateTime.now());
        paymentIn.setSum(randomLong(10, 10000));
        paymentIn.setOrganization(simpleEntityManager.getOwnOrganization());
        paymentIn.setAgent(simpleEntityManager.createSimpleCounterparty());

        Demand createdDemand = api.entity().demand().create(demand);
        PaymentIn createdPaymentIn = api.entity().paymentin().create(paymentIn);
        LinkedOperation operation = new LinkedOperation(createdDemand, paymentIn.getSum().doubleValue());
        createdPaymentIn.setLinkedOperations(ImmutableList.of(operation));
        List<PaymentIn> updatedPaymentIns = api.entity().paymentin().createOrUpdate(ImmutableList.of(createdPaymentIn));

        assertNotNull(updatedPaymentIns);
        assertEquals(1, updatedPaymentIns.size());
        assertEquals(paymentIn.getName(), updatedPaymentIns.get(0).getName());
        assertEquals(1, updatedPaymentIns.get(0).getOperations().size());
        assertEquals(operation.getMeta().getHref(), updatedPaymentIns.get(0).getOperations().get(0).getMeta().getHref());
    }

    @Test
    public void deleteAttributeTest() throws IOException, ApiClientException{
        DocumentAttribute attribute = new DocumentAttribute();
        attribute.setEntityType(Meta.Type.PRODUCT);
        attribute.setName("field" + randomString(3) + "_" + new Date().getTime());
        attribute.setRequired(true);
        attribute.setShow(true);
        DocumentAttribute created = api.entity().paymentin().createMetadataAttribute(attribute);

        api.entity().paymentin().deleteMetadataAttribute(created);

        try {
            api.entity().paymentin().metadataAttributes(created.getId());
        } catch (ApiClientException e) {
            assertEquals(404, e.getStatusCode());
        }
    }

    @Test
    public void newTest() throws IOException, ApiClientException {
        PaymentIn paymentIn = api.entity().paymentin().templateDocument();
        LocalDateTime time = LocalDateTime.now();

        assertEquals("", paymentIn.getName());
        assertEquals(Long.valueOf(0), paymentIn.getSum());
        assertFalse(paymentIn.getShared());
        assertTrue(paymentIn.getApplicable());
        assertFalse(paymentIn.getPublished());
        assertFalse(paymentIn.getPrinted());
        assertTrue(ChronoUnit.MILLIS.between(time, paymentIn.getMoment()) < 1000);

        assertEquals(paymentIn.getOrganization().getMeta().getHref(), simpleEntityManager.getOwnOrganization().getMeta().getHref());
        assertEquals(paymentIn.getGroup().getMeta().getHref(), simpleEntityManager.getMainGroup().getMeta().getHref());
    }

    @Test
    public void newByCustomerOrdersTest() throws IOException, ApiClientException {
        CustomerOrder customerOrder = simpleEntityManager.createSimple(CustomerOrder.class);

        PaymentIn paymentIn = api.entity().paymentin().templateDocument("operations", Collections.singletonList(customerOrder));
        LocalDateTime time = LocalDateTime.now();

        assertEquals("", paymentIn.getName());
        assertEquals(customerOrder.getSum(), paymentIn.getSum());
        assertFalse(paymentIn.getShared());
        assertTrue(paymentIn.getApplicable());
        assertTrue(ChronoUnit.MILLIS.between(time, paymentIn.getMoment()) < 1000);
        assertEquals(1, paymentIn.getOperations().size());
        assertEquals(customerOrder.getMeta().getHref(), paymentIn.getOperations().get(0).getMeta().getHref());
        assertEquals(customerOrder.getGroup().getMeta().getHref(), paymentIn.getGroup().getMeta().getHref());
        assertEquals(customerOrder.getAgent().getMeta().getHref(), paymentIn.getAgent().getMeta().getHref());
        assertEquals(customerOrder.getOrganization().getMeta().getHref(), paymentIn.getOrganization().getMeta().getHref());
    }

    @Test
    public void newByPurchaseReturnsTest() throws IOException, ApiClientException {
        PurchaseReturn purchaseReturn = simpleEntityManager.createSimple(PurchaseReturn.class);

        PaymentIn paymentIn = api.entity().paymentin().templateDocument("operations", Collections.singletonList(purchaseReturn));
        LocalDateTime time = LocalDateTime.now();

        assertEquals("", paymentIn.getName());
        assertEquals(purchaseReturn.getSum(), paymentIn.getSum());
        assertFalse(paymentIn.getShared());
        assertTrue(paymentIn.getApplicable());
        assertTrue(ChronoUnit.MILLIS.between(time, paymentIn.getMoment()) < 1000);
        assertEquals(1, paymentIn.getOperations().size());
        assertEquals(purchaseReturn.getMeta().getHref(), paymentIn.getOperations().get(0).getMeta().getHref());
        assertEquals(purchaseReturn.getGroup().getMeta().getHref(), paymentIn.getGroup().getMeta().getHref());
        assertEquals(purchaseReturn.getAgent().getMeta().getHref(), paymentIn.getAgent().getMeta().getHref());
        assertEquals(purchaseReturn.getOrganization().getMeta().getHref(), paymentIn.getOrganization().getMeta().getHref());
    }

    @Test
    public void newByDemandsTest() throws IOException, ApiClientException {
        Demand demand = simpleEntityManager.createSimple(Demand.class);

        PaymentIn paymentIn = api.entity().paymentin().templateDocument("operations", Collections.singletonList(demand));
        LocalDateTime time = LocalDateTime.now();

        assertEquals("", paymentIn.getName());
        assertEquals(demand.getSum(), paymentIn.getSum());
        assertFalse(paymentIn.getShared());
        assertTrue(paymentIn.getApplicable());
        assertTrue(ChronoUnit.MILLIS.between(time, paymentIn.getMoment()) < 1000);
        assertEquals(1, paymentIn.getOperations().size());
        assertEquals(demand.getMeta().getHref(), paymentIn.getOperations().get(0).getMeta().getHref());
        assertEquals(demand.getGroup().getMeta().getHref(), paymentIn.getGroup().getMeta().getHref());
        assertEquals(demand.getAgent().getMeta().getHref(), paymentIn.getAgent().getMeta().getHref());
        assertEquals(demand.getOrganization().getMeta().getHref(), paymentIn.getOrganization().getMeta().getHref());
    }

    @Test
    public void newByInvoicesOutTest() throws IOException, ApiClientException {
        InvoiceOut invoiceOut = simpleEntityManager.createSimple(InvoiceOut.class);

        PaymentIn paymentIn = api.entity().paymentin().templateDocument("operations", Collections.singletonList(invoiceOut));
        LocalDateTime time = LocalDateTime.now();

        assertEquals("", paymentIn.getName());
        assertEquals(invoiceOut.getSum(), paymentIn.getSum());
        assertFalse(paymentIn.getShared());
        assertTrue(paymentIn.getApplicable());
        assertTrue(ChronoUnit.MILLIS.between(time, paymentIn.getMoment()) < 1000);
        assertEquals(1, paymentIn.getOperations().size());
        assertEquals(invoiceOut.getMeta().getHref(), paymentIn.getOperations().get(0).getMeta().getHref());
        assertEquals(invoiceOut.getGroup().getMeta().getHref(), paymentIn.getGroup().getMeta().getHref());
        assertEquals(invoiceOut.getAgent().getMeta().getHref(), paymentIn.getAgent().getMeta().getHref());
        assertEquals(invoiceOut.getOrganization().getMeta().getHref(), paymentIn.getOrganization().getMeta().getHref());
    }

    @Test
    public void newByCommissionReportsInTest() throws IOException, ApiClientException {
        CommissionReportIn commissionReportIn = simpleEntityManager.createSimple(CommissionReportIn.class);

        PaymentIn paymentIn = api.entity().paymentin().templateDocument("operations", Collections.singletonList(commissionReportIn));
        LocalDateTime time = LocalDateTime.now();

        assertEquals("", paymentIn.getName());
        assertEquals(commissionReportIn.getSum(), paymentIn.getSum());
        assertFalse(paymentIn.getShared());
        assertTrue(paymentIn.getApplicable());
        assertTrue(ChronoUnit.MILLIS.between(time, paymentIn.getMoment()) < 1000);
        assertEquals(1, paymentIn.getOperations().size());
        assertEquals(commissionReportIn.getMeta().getHref(), paymentIn.getOperations().get(0).getMeta().getHref());
        assertEquals(commissionReportIn.getGroup().getMeta().getHref(), paymentIn.getGroup().getMeta().getHref());
        assertEquals(commissionReportIn.getContract().getMeta().getHref(), paymentIn.getContract().getMeta().getHref());
        assertEquals(commissionReportIn.getAgent().getMeta().getHref(), paymentIn.getAgent().getMeta().getHref());
        assertEquals(commissionReportIn.getOrganization().getMeta().getHref(), paymentIn.getOrganization().getMeta().getHref());
    }

    @Override
    protected void getAsserts(MetaEntity originalEntity, MetaEntity retrievedEntity) {
        PaymentIn originalPaymentIn = (PaymentIn) originalEntity;
        PaymentIn retrievedPaymentIn = (PaymentIn) retrievedEntity;

        assertEquals(originalPaymentIn.getName(), retrievedPaymentIn.getName());
        assertEquals(originalPaymentIn.getOrganization().getMeta().getHref(), retrievedPaymentIn.getOrganization().getMeta().getHref());
        assertEquals(originalPaymentIn.getAgent().getMeta().getHref(), retrievedPaymentIn.getAgent().getMeta().getHref());
    }

    @Override
    protected void putAsserts(MetaEntity originalEntity, MetaEntity updatedEntity, Object changedField) {
        PaymentIn originalPaymentIn = (PaymentIn) originalEntity;
        PaymentIn updatedPaymentIn = (PaymentIn) updatedEntity;

        assertNotEquals(originalPaymentIn.getName(), updatedPaymentIn.getName());
        assertEquals(changedField, updatedPaymentIn.getName());
        assertEquals(originalPaymentIn.getOrganization().getMeta().getHref(), updatedPaymentIn.getOrganization().getMeta().getHref());
        assertEquals(originalPaymentIn.getAgent().getMeta().getHref(), updatedPaymentIn.getAgent().getMeta().getHref());
    }

    @Test
    public void testFiles() throws IOException, ApiClientException {
        doTestFiles();
    }

    @Override
    public EntityClientBase entityClient() {
        return api.entity().paymentin();
    }

    @Override
    public Class<? extends MetaEntity> entityClass() {
        return PaymentIn.class;
    }
}
