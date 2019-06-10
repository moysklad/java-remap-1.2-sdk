package com.lognex.api.entities.documents;

import com.lognex.api.entities.*;
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

public class PaymentOutDocumentEntityTest extends EntityTestBase {
    @Test
    public void createTest() throws IOException, LognexApiException {
        PaymentOutDocumentEntity paymentOut = new PaymentOutDocumentEntity();
        paymentOut.setName("paymentout_" + randomString(3) + "_" + new Date().getTime());
        paymentOut.setMoment(LocalDateTime.now());
        paymentOut.setSum(randomLong(10, 10000));
        paymentOut.setOrganization(simpleEntityFactory.getOwnOrganization());
        paymentOut.setAgent(simpleEntityFactory.createSimpleCounterparty());
        paymentOut.setExpenseItem(simpleEntityFactory.createSimpleExpenseItem());

        api.entity().paymentout().post(paymentOut);

        ListEntity<PaymentOutDocumentEntity> updatedEntitiesList = api.entity().paymentout().get(filterEq("name", paymentOut.getName()));
        assertEquals(1, updatedEntitiesList.getRows().size());

        PaymentOutDocumentEntity retrievedEntity = updatedEntitiesList.getRows().get(0);
        assertEquals(paymentOut.getName(), retrievedEntity.getName());
        assertEquals(paymentOut.getMoment(), retrievedEntity.getMoment());
        assertEquals(paymentOut.getSum(), retrievedEntity.getSum());
        assertEquals(paymentOut.getOrganization().getMeta().getHref(), retrievedEntity.getOrganization().getMeta().getHref());
        assertEquals(paymentOut.getAgent().getMeta().getHref(), retrievedEntity.getAgent().getMeta().getHref());
        assertEquals(paymentOut.getExpenseItem().getMeta().getHref(), retrievedEntity.getExpenseItem().getMeta().getHref());
    }

    @Test
    public void getTest() throws IOException, LognexApiException {
        PaymentOutDocumentEntity paymentOut = simpleEntityFactory.createSimplePaymentOut();

        PaymentOutDocumentEntity retrievedEntity = api.entity().paymentout().get(paymentOut.getId());
        getAsserts(paymentOut, retrievedEntity);

        retrievedEntity = api.entity().paymentout().get(paymentOut);
        getAsserts(paymentOut, retrievedEntity);
    }

    @Test
    public void putTest() throws IOException, LognexApiException {
        PaymentOutDocumentEntity paymentOut = simpleEntityFactory.createSimplePaymentOut();

        PaymentOutDocumentEntity retrievedOriginalEntity = api.entity().paymentout().get(paymentOut.getId());
        String name = "paymentout_" + randomString(3) + "_" + new Date().getTime();
        paymentOut.setName(name);
        api.entity().paymentout().put(paymentOut.getId(), paymentOut);
        putAsserts(paymentOut, retrievedOriginalEntity, name);

        retrievedOriginalEntity.set(paymentOut);

        name = "paymentout_" + randomString(3) + "_" + new Date().getTime();
        paymentOut.setName(name);
        api.entity().paymentout().put(paymentOut);
        putAsserts(paymentOut, retrievedOriginalEntity, name);
    }

    @Test
    public void deleteTest() throws IOException, LognexApiException {
        PaymentOutDocumentEntity paymentOut = simpleEntityFactory.createSimplePaymentOut();

        ListEntity<PaymentOutDocumentEntity> entitiesList = api.entity().paymentout().get(filterEq("name", paymentOut.getName()));
        assertEquals((Integer) 1, entitiesList.getMeta().getSize());

        api.entity().paymentout().delete(paymentOut.getId());

        entitiesList = api.entity().paymentout().get(filterEq("name", paymentOut.getName()));
        assertEquals((Integer) 0, entitiesList.getMeta().getSize());
    }

    @Test
    public void deleteByIdTest() throws IOException, LognexApiException {
        PaymentOutDocumentEntity paymentOut = simpleEntityFactory.createSimplePaymentOut();

        ListEntity<PaymentOutDocumentEntity> entitiesList = api.entity().paymentout().get(filterEq("name", paymentOut.getName()));
        assertEquals((Integer) 1, entitiesList.getMeta().getSize());

        api.entity().paymentout().delete(paymentOut);

        entitiesList = api.entity().paymentout().get(filterEq("name", paymentOut.getName()));
        assertEquals((Integer) 0, entitiesList.getMeta().getSize());
    }

    @Test
    public void metadataTest() throws IOException, LognexApiException {
        MetadataAttributeSharedStatesResponse response = api.entity().paymentout().metadata().get();

        assertFalse(response.getCreateShared());
    }

    @Test
    public void newTest() throws IOException, LognexApiException {
        PaymentOutDocumentEntity paymentOut = api.entity().paymentout().newDocument();
        LocalDateTime time = LocalDateTime.now();

        assertEquals("", paymentOut.getName());
        assertEquals(Long.valueOf(0), paymentOut.getSum());
        assertFalse(paymentOut.getShared());
        assertTrue(paymentOut.getApplicable());
        assertTrue(ChronoUnit.MILLIS.between(time, paymentOut.getMoment()) < 1000);

        assertEquals(paymentOut.getOrganization().getMeta().getHref(), simpleEntityFactory.getOwnOrganization().getMeta().getHref());
        assertEquals(paymentOut.getGroup().getMeta().getHref(), simpleEntityFactory.getMainGroup().getMeta().getHref());
    }

    @Test
    public void newByPurchaseOrdersTest() throws IOException, LognexApiException {
        PurchaseOrderDocumentEntity purchaseOrder = simpleEntityFactory.createSimplePurchaseOrder();

        PaymentOutDocumentEntity paymentOut = api.entity().paymentout().newDocument("operations", Collections.singletonList(purchaseOrder));
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
    public void newBySalesReturnsTest() throws IOException, LognexApiException {
        SalesReturnDocumentEntity salesReturn = simpleEntityFactory.createSimpleSalesReturn();

        PaymentOutDocumentEntity paymentOut = api.entity().paymentout().newDocument("operations", Collections.singletonList(salesReturn));
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
    public void newBySuppliesTest() throws IOException, LognexApiException {
        SupplyDocumentEntity supply = simpleEntityFactory.createSimpleSupply();

        PaymentOutDocumentEntity paymentOut = api.entity().paymentout().newDocument("operations", Collections.singletonList(supply));
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
    public void newByInvoicesInTest() throws IOException, LognexApiException {
        InvoiceInDocumentEntity invoiceIn = simpleEntityFactory.createSimpleInvoiceIn();

        PaymentOutDocumentEntity paymentOut = api.entity().paymentout().newDocument("operations", Collections.singletonList(invoiceIn));
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
    public void newByCommissionReportsOutTest() throws IOException, LognexApiException {
        CommissionReportOutDocumentEntity commissionReportOut = simpleEntityFactory.createSimpleCommissionReportOut();

        PaymentOutDocumentEntity paymentOut = api.entity().paymentout().newDocument("operations", Collections.singletonList(commissionReportOut));
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

    private void getAsserts(PaymentOutDocumentEntity paymentOut, PaymentOutDocumentEntity retrievedEntity) {
        assertEquals(paymentOut.getName(), retrievedEntity.getName());
        assertEquals(paymentOut.getOrganization().getMeta().getHref(), retrievedEntity.getOrganization().getMeta().getHref());
        assertEquals(paymentOut.getAgent().getMeta().getHref(), retrievedEntity.getAgent().getMeta().getHref());
        assertEquals(paymentOut.getExpenseItem().getMeta().getHref(), retrievedEntity.getExpenseItem().getMeta().getHref());
    }

    private void putAsserts(PaymentOutDocumentEntity paymentOut, PaymentOutDocumentEntity retrievedOriginalEntity, String name) throws IOException, LognexApiException {
        PaymentOutDocumentEntity retrievedUpdatedEntity = api.entity().paymentout().get(paymentOut.getId());

        assertNotEquals(retrievedOriginalEntity.getName(), retrievedUpdatedEntity.getName());
        assertEquals(name, retrievedUpdatedEntity.getName());
        assertEquals(retrievedOriginalEntity.getOrganization().getMeta().getHref(), retrievedUpdatedEntity.getOrganization().getMeta().getHref());
        assertEquals(retrievedOriginalEntity.getAgent().getMeta().getHref(), retrievedUpdatedEntity.getAgent().getMeta().getHref());
        assertEquals(retrievedOriginalEntity.getExpenseItem().getMeta().getHref(), retrievedUpdatedEntity.getExpenseItem().getMeta().getHref());
    }
}
