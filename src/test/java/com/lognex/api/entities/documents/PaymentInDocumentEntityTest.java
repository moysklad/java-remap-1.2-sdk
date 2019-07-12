package com.lognex.api.entities.documents;

import com.lognex.api.clients.ApiClient;
import com.lognex.api.entities.EntityGetUpdateDeleteTest;
import com.lognex.api.entities.MetaEntity;
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

public class PaymentInDocumentEntityTest extends EntityGetUpdateDeleteTest {
    @Test
    public void createTest() throws IOException, LognexApiException {
        PaymentInDocumentEntity paymentIn = new PaymentInDocumentEntity();
        paymentIn.setName("paymentin_" + randomString(3) + "_" + new Date().getTime());
        paymentIn.setMoment(LocalDateTime.now());
        paymentIn.setSum(randomLong(10, 10000));
        paymentIn.setOrganization(simpleEntityManager.getOwnOrganization());
        paymentIn.setAgent(simpleEntityManager.createSimpleCounterparty());

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

        assertEquals(paymentIn.getOrganization().getMeta().getHref(), simpleEntityManager.getOwnOrganization().getMeta().getHref());
        assertEquals(paymentIn.getGroup().getMeta().getHref(), simpleEntityManager.getMainGroup().getMeta().getHref());
    }

    @Test
    public void newByCustomerOrdersTest() throws IOException, LognexApiException {
        CustomerOrderDocumentEntity customerOrder = simpleEntityManager.createSimpleCustomerOrder();

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
        PurchaseReturnDocumentEntity purchaseReturn = simpleEntityManager.createSimplePurchaseReturn();

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
        DemandDocumentEntity demand = simpleEntityManager.createSimpleDemand();

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
        InvoiceOutDocumentEntity invoiceOut = simpleEntityManager.createSimpleInvoiceOut();

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
        CommissionReportInDocumentEntity commissionReportIn = simpleEntityManager.createSimpleCommissionReportIn();

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

    @Override
    protected void getAsserts(MetaEntity originalEntity, MetaEntity retrievedEntity) {
        PaymentInDocumentEntity originalPaymentIn = (PaymentInDocumentEntity) originalEntity;
        PaymentInDocumentEntity retrievedPaymentIn = (PaymentInDocumentEntity) retrievedEntity;

        assertEquals(originalPaymentIn.getName(), retrievedPaymentIn.getName());
        assertEquals(originalPaymentIn.getOrganization().getMeta().getHref(), retrievedPaymentIn.getOrganization().getMeta().getHref());
        assertEquals(originalPaymentIn.getAgent().getMeta().getHref(), retrievedPaymentIn.getAgent().getMeta().getHref());
    }

    @Override
    protected void putAsserts(MetaEntity originalEntity, MetaEntity updatedEntity, Object changedField) {
        PaymentInDocumentEntity originalPaymentIn = (PaymentInDocumentEntity) originalEntity;
        PaymentInDocumentEntity updatedPaymentIn = (PaymentInDocumentEntity) updatedEntity;

        assertNotEquals(originalPaymentIn.getName(), updatedPaymentIn.getName());
        assertEquals(changedField, updatedPaymentIn.getName());
        assertEquals(originalPaymentIn.getOrganization().getMeta().getHref(), updatedPaymentIn.getOrganization().getMeta().getHref());
        assertEquals(originalPaymentIn.getAgent().getMeta().getHref(), updatedPaymentIn.getAgent().getMeta().getHref());
    }

    @Override
    protected ApiClient entityClient() {
        return api.entity().paymentin();
    }

    @Override
    protected Class<? extends MetaEntity> entityClass() {
        return PaymentInDocumentEntity.class;
    }
}
