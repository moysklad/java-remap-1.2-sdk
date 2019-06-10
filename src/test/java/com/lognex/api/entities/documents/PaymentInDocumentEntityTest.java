package com.lognex.api.entities.documents;

import com.lognex.api.entities.EntityTestBase;
import com.lognex.api.responses.ListEntity;
import com.lognex.api.responses.metadata.MetadataAttributeSharedStatesResponse;
import com.lognex.api.utils.LognexApiException;
import org.junit.Test;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.Date;

import static com.lognex.api.utils.params.FilterParam.filterEq;
import static org.junit.Assert.*;

public class PaymentInDocumentEntityTest extends EntityTestBase {
    @Test
    public void createTest() throws IOException, LognexApiException {
        PaymentInDocumentEntity paymentIn = new PaymentInDocumentEntity();
        paymentIn.setName("paymentin_" + randomString(3) + "_" + new Date().getTime());
        paymentIn.setMoment(LocalDateTime.now());
        paymentIn.setSum(randomLong(10, 10000));
        paymentIn.setOrganization(simpleEntityFactory.getOwnOrganization());
        paymentIn.setAgent(simpleEntityFactory.createSimpleCounterparty());

        api.entity().paymentin().post(paymentIn);

        ListEntity<PaymentInDocumentEntity> updatedEntitiesList = api.entity().paymentin().get(filterEq("name", paymentIn.getName()));
        assertEquals(1, updatedEntitiesList.getRows().size());

        PaymentInDocumentEntity retrievedEntity = updatedEntitiesList.getRows().get(0);
        assertEquals(paymentIn.getName(), retrievedEntity.getName());
        assertEquals(paymentIn.getMoment(), retrievedEntity.getMoment());
        assertEquals(paymentIn.getSum(), retrievedEntity.getSum());
        assertEquals(paymentIn.getOrganization().getMeta().getHref(), retrievedEntity.getOrganization().getMeta().getHref());
        assertEquals(paymentIn.getAgent().getMeta().getHref(), retrievedEntity.getAgent().getMeta().getHref());
    }

    @Test
    public void getTest() throws IOException, LognexApiException {
        PaymentInDocumentEntity paymentIn = simpleEntityFactory.createSimplePaymentIn();

        PaymentInDocumentEntity retrievedEntity = api.entity().paymentin().get(paymentIn.getId());
        getAsserts(paymentIn, retrievedEntity);

        retrievedEntity = api.entity().paymentin().get(paymentIn);
        getAsserts(paymentIn, retrievedEntity);
    }

    @Test
    public void putTest() throws IOException, LognexApiException {
        PaymentInDocumentEntity paymentIn = simpleEntityFactory.createSimplePaymentIn();

        PaymentInDocumentEntity retrievedOriginalEntity = api.entity().paymentin().get(paymentIn.getId());
        String name = "paymentin_" + randomString(3) + "_" + new Date().getTime();
        paymentIn.setName(name);
        api.entity().paymentin().put(paymentIn.getId(), paymentIn);
        putAsserts(paymentIn, retrievedOriginalEntity, name);

        retrievedOriginalEntity.set(paymentIn);

        name = "paymentin_" + randomString(3) + "_" + new Date().getTime();
        paymentIn.setName(name);
        api.entity().paymentin().put(paymentIn);
        putAsserts(paymentIn, retrievedOriginalEntity, name);
    }

    @Test
    public void deleteTest() throws IOException, LognexApiException {
        PaymentInDocumentEntity paymentIn = simpleEntityFactory.createSimplePaymentIn();

        ListEntity<PaymentInDocumentEntity> entitiesList = api.entity().paymentin().get(filterEq("name", paymentIn.getName()));
        assertEquals((Integer) 1, entitiesList.getMeta().getSize());

        api.entity().paymentin().delete(paymentIn.getId());

        entitiesList = api.entity().paymentin().get(filterEq("name", paymentIn.getName()));
        assertEquals((Integer) 0, entitiesList.getMeta().getSize());
    }

    @Test
    public void deleteByIdTest() throws IOException, LognexApiException {
        PaymentInDocumentEntity paymentIn = simpleEntityFactory.createSimplePaymentIn();

        ListEntity<PaymentInDocumentEntity> entitiesList = api.entity().paymentin().get(filterEq("name", paymentIn.getName()));
        assertEquals((Integer) 1, entitiesList.getMeta().getSize());

        api.entity().paymentin().delete(paymentIn);

        entitiesList = api.entity().paymentin().get(filterEq("name", paymentIn.getName()));
        assertEquals((Integer) 0, entitiesList.getMeta().getSize());
    }

    @Test
    public void metadataTest() throws IOException, LognexApiException {
        MetadataAttributeSharedStatesResponse response = api.entity().paymentin().metadata().get();

        assertFalse(response.getCreateShared());
    }

    @Test
    public void newTest() throws IOException, LognexApiException {
        PaymentInDocumentEntity paymentIn = api.entity().paymentin().newDocument();
        LocalDateTime time = LocalDateTime.now();

        assertEquals("", paymentIn.getName());
        assertEquals(Long.valueOf(0), paymentIn.getSum());
        assertFalse(paymentIn.getShared());
        assertTrue(paymentIn.getApplicable());
        assertTrue(ChronoUnit.MILLIS.between(time, paymentIn.getMoment()) < 1000);

        assertEquals(paymentIn.getOrganization().getMeta().getHref(), simpleEntityFactory.getOwnOrganization().getMeta().getHref());
        assertEquals(paymentIn.getGroup().getMeta().getHref(), simpleEntityFactory.getMainGroup().getMeta().getHref());
    }

    @Test
    public void newByCustomerOrdersTest() throws IOException, LognexApiException {
        CustomerOrderDocumentEntity customerOrder = simpleEntityFactory.createSimpleCustomerOrder();

        PaymentInDocumentEntity paymentIn = api.entity().paymentin().newDocument("operations", Collections.singletonList(customerOrder));
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
    public void newByPurchaseReturnsTest() throws IOException, LognexApiException {
        PurchaseReturnDocumentEntity purchaseReturn = simpleEntityFactory.createSimplePurchaseReturn();

        PaymentInDocumentEntity paymentIn = api.entity().paymentin().newDocument("operations", Collections.singletonList(purchaseReturn));
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
    public void newByDemandsTest() throws IOException, LognexApiException {
        DemandDocumentEntity demand = simpleEntityFactory.createSimpleDemand();

        PaymentInDocumentEntity paymentIn = api.entity().paymentin().newDocument("operations", Collections.singletonList(demand));
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
    public void newByInvoicesOutTest() throws IOException, LognexApiException {
        InvoiceOutDocumentEntity invoiceOut = simpleEntityFactory.createSimpleInvoiceOut();

        PaymentInDocumentEntity paymentIn = api.entity().paymentin().newDocument("operations", Collections.singletonList(invoiceOut));
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
    public void newByCommissionReportsInTest() throws IOException, LognexApiException {
        CommissionReportInDocumentEntity commissionReportIn = simpleEntityFactory.createSimpleCommissionReportIn();

        PaymentInDocumentEntity paymentIn = api.entity().paymentin().newDocument("operations", Collections.singletonList(commissionReportIn));
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

    private void getAsserts(PaymentInDocumentEntity paymentIn, PaymentInDocumentEntity retrievedEntity) {
        assertEquals(paymentIn.getName(), retrievedEntity.getName());
        assertEquals(paymentIn.getOrganization().getMeta().getHref(), retrievedEntity.getOrganization().getMeta().getHref());
        assertEquals(paymentIn.getAgent().getMeta().getHref(), retrievedEntity.getAgent().getMeta().getHref());
    }

    private void putAsserts(PaymentInDocumentEntity paymentIn, PaymentInDocumentEntity retrievedOriginalEntity, String name) throws IOException, LognexApiException {
        PaymentInDocumentEntity retrievedUpdatedEntity = api.entity().paymentin().get(paymentIn.getId());

        assertNotEquals(retrievedOriginalEntity.getName(), retrievedUpdatedEntity.getName());
        assertEquals(name, retrievedUpdatedEntity.getName());
        assertEquals(retrievedOriginalEntity.getOrganization().getMeta().getHref(), retrievedUpdatedEntity.getOrganization().getMeta().getHref());
        assertEquals(retrievedOriginalEntity.getAgent().getMeta().getHref(), retrievedUpdatedEntity.getAgent().getMeta().getHref());
    }
}
