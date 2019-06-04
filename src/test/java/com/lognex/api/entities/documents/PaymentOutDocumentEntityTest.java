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
        PaymentOutDocumentEntity e = new PaymentOutDocumentEntity();
        e.setName("paymentout_" + randomString(3) + "_" + new Date().getTime());
        e.setMoment(LocalDateTime.now());
        e.setSum(randomLong(10, 10000));
        e.setOrganization(getOwnOrganization());
        e.setAgent(createSimpleCounterparty());
        e.setExpenseItem(createSimpleExpenseItem());

        api.entity().paymentout().post(e);

        ListEntity<PaymentOutDocumentEntity> updatedEntitiesList = api.entity().paymentout().get(filterEq("name", e.getName()));
        assertEquals(1, updatedEntitiesList.getRows().size());

        PaymentOutDocumentEntity retrievedEntity = updatedEntitiesList.getRows().get(0);
        assertEquals(e.getName(), retrievedEntity.getName());
        assertEquals(e.getMoment(), retrievedEntity.getMoment());
        assertEquals(e.getSum(), retrievedEntity.getSum());
        assertEquals(e.getOrganization().getMeta().getHref(), retrievedEntity.getOrganization().getMeta().getHref());
        assertEquals(e.getAgent().getMeta().getHref(), retrievedEntity.getAgent().getMeta().getHref());
        assertEquals(e.getExpenseItem().getMeta().getHref(), retrievedEntity.getExpenseItem().getMeta().getHref());
    }

    @Test
    public void getTest() throws IOException, LognexApiException {
        PaymentOutDocumentEntity e = createSimplePaymentOut();

        PaymentOutDocumentEntity retrievedEntity = api.entity().paymentout().get(e.getId());
        getAsserts(e, retrievedEntity);

        retrievedEntity = api.entity().paymentout().get(e);
        getAsserts(e, retrievedEntity);
    }

    @Test
    public void putTest() throws IOException, LognexApiException, InterruptedException {
        PaymentOutDocumentEntity e = createSimplePaymentOut();

        PaymentOutDocumentEntity retrievedOriginalEntity = api.entity().paymentout().get(e.getId());
        String name = "paymentout_" + randomString(3) + "_" + new Date().getTime();
        e.setName(name);
        api.entity().paymentout().put(e.getId(), e);
        putAsserts(e, retrievedOriginalEntity, name);

        retrievedOriginalEntity.set(e);

        name = "paymentout_" + randomString(3) + "_" + new Date().getTime();
        e.setName(name);
        api.entity().paymentout().put(e);
        putAsserts(e, retrievedOriginalEntity, name);
    }

    @Test
    public void deleteTest() throws IOException, LognexApiException {
        PaymentOutDocumentEntity e = createSimplePaymentOut();

        ListEntity<PaymentOutDocumentEntity> entitiesList = api.entity().paymentout().get(filterEq("name", e.getName()));
        assertEquals((Integer) 1, entitiesList.getMeta().getSize());

        api.entity().paymentout().delete(e.getId());

        entitiesList = api.entity().paymentout().get(filterEq("name", e.getName()));
        assertEquals((Integer) 0, entitiesList.getMeta().getSize());
    }

    @Test
    public void deleteByIdTest() throws IOException, LognexApiException {
        PaymentOutDocumentEntity e = createSimplePaymentOut();

        ListEntity<PaymentOutDocumentEntity> entitiesList = api.entity().paymentout().get(filterEq("name", e.getName()));
        assertEquals((Integer) 1, entitiesList.getMeta().getSize());

        api.entity().paymentout().delete(e);

        entitiesList = api.entity().paymentout().get(filterEq("name", e.getName()));
        assertEquals((Integer) 0, entitiesList.getMeta().getSize());
    }

    @Test
    public void metadataTest() throws IOException, LognexApiException {
        MetadataAttributeSharedStatesResponse response = api.entity().paymentout().metadata().get();

        assertFalse(response.getCreateShared());
    }

    @Test
    public void newTest() throws IOException, LognexApiException {
        PaymentOutDocumentEntity e = api.entity().paymentout().newDocument();
        LocalDateTime time = LocalDateTime.now();

        assertEquals("", e.getName());
        assertEquals(Long.valueOf(0), e.getSum());
        assertFalse(e.getShared());
        assertTrue(e.getApplicable());
        assertTrue(ChronoUnit.MILLIS.between(time, e.getMoment()) < 1000);

        assertEquals(e.getOrganization().getMeta().getHref(), getOwnOrganization().getMeta().getHref());
        assertEquals(e.getGroup().getMeta().getHref(), getMainGroup().getMeta().getHref());
    }

    @Test
    public void newByPurchaseOrdersTest() throws IOException, LognexApiException {
        PurchaseOrderDocumentEntity purchaseOrder = createSimplePurchaseOrder();

        PaymentOutDocumentEntity e = api.entity().paymentout().newDocument("operations", Collections.singletonList(purchaseOrder));
        LocalDateTime time = LocalDateTime.now();

        assertEquals("", e.getName());
        assertEquals(purchaseOrder.getSum(), e.getSum());
        assertFalse(e.getShared());
        assertTrue(e.getApplicable());
        assertTrue(ChronoUnit.MILLIS.between(time, e.getMoment()) < 1000);
        assertEquals(1, e.getOperations().size());
        assertEquals(purchaseOrder.getMeta().getHref(), e.getOperations().get(0).getMeta().getHref());
        assertEquals(purchaseOrder.getGroup().getMeta().getHref(), e.getGroup().getMeta().getHref());
        assertEquals(purchaseOrder.getAgent().getMeta().getHref(), e.getAgent().getMeta().getHref());
        assertEquals(purchaseOrder.getOrganization().getMeta().getHref(), e.getOrganization().getMeta().getHref());
    }

    @Test
    public void newBySalesReturnsTest() throws IOException, LognexApiException {
        SalesReturnDocumentEntity salesReturn = createSimpleSalesReturn();

        PaymentOutDocumentEntity e = api.entity().paymentout().newDocument("operations", Collections.singletonList(salesReturn));
        LocalDateTime time = LocalDateTime.now();

        assertEquals("", e.getName());
        assertEquals(salesReturn.getSum(), e.getSum());
        assertFalse(e.getShared());
        assertTrue(e.getApplicable());
        assertTrue(ChronoUnit.MILLIS.between(time, e.getMoment()) < 1000);
        assertEquals(1, e.getOperations().size());
        assertEquals(salesReturn.getMeta().getHref(), e.getOperations().get(0).getMeta().getHref());
        assertEquals(salesReturn.getGroup().getMeta().getHref(), e.getGroup().getMeta().getHref());
        assertEquals(salesReturn.getAgent().getMeta().getHref(), e.getAgent().getMeta().getHref());
        assertEquals(salesReturn.getOrganization().getMeta().getHref(), e.getOrganization().getMeta().getHref());
    }

    @Test
    public void newBySuppliesTest() throws IOException, LognexApiException {
        SupplyDocumentEntity supply = createSimpleSupply();

        PaymentOutDocumentEntity e = api.entity().paymentout().newDocument("operations", Collections.singletonList(supply));
        LocalDateTime time = LocalDateTime.now();

        assertEquals("", e.getName());
        assertEquals(supply.getSum(), e.getSum());
        assertFalse(e.getShared());
        assertTrue(e.getApplicable());
        assertTrue(ChronoUnit.MILLIS.between(time, e.getMoment()) < 1000);
        assertEquals(1, e.getOperations().size());
        assertEquals(supply.getMeta().getHref(), e.getOperations().get(0).getMeta().getHref());
        assertEquals(supply.getGroup().getMeta().getHref(), e.getGroup().getMeta().getHref());
        assertEquals(supply.getAgent().getMeta().getHref(), e.getAgent().getMeta().getHref());
        assertEquals(supply.getOrganization().getMeta().getHref(), e.getOrganization().getMeta().getHref());
    }

    @Test
    public void newByInvoicesInTest() throws IOException, LognexApiException {
        InvoiceInDocumentEntity invoiceIn = createSimpleInvoiceIn();

        PaymentOutDocumentEntity e = api.entity().paymentout().newDocument("operations", Collections.singletonList(invoiceIn));
        LocalDateTime time = LocalDateTime.now();

        assertEquals("", e.getName());
        assertEquals(invoiceIn.getSum(), e.getSum());
        assertFalse(e.getShared());
        assertTrue(e.getApplicable());
        assertTrue(ChronoUnit.MILLIS.between(time, e.getMoment()) < 1000);
        assertEquals(1, e.getOperations().size());
        assertEquals(invoiceIn.getMeta().getHref(), e.getOperations().get(0).getMeta().getHref());
        assertEquals(invoiceIn.getGroup().getMeta().getHref(), e.getGroup().getMeta().getHref());
        assertEquals(invoiceIn.getAgent().getMeta().getHref(), e.getAgent().getMeta().getHref());
        assertEquals(invoiceIn.getOrganization().getMeta().getHref(), e.getOrganization().getMeta().getHref());
    }

    @Test
    public void newByCommissionReportsOutTest() throws IOException, LognexApiException {
        CommissionReportOutDocumentEntity commissionReportOut = createSimpleCommissionReportOut();

        PaymentOutDocumentEntity e = api.entity().paymentout().newDocument("operations", Collections.singletonList(commissionReportOut));
        LocalDateTime time = LocalDateTime.now();

        assertEquals("", e.getName());
        assertEquals(commissionReportOut.getSum(), e.getSum());
        assertFalse(e.getShared());
        assertTrue(e.getApplicable());
        assertTrue(ChronoUnit.MILLIS.between(time, e.getMoment()) < 1000);
        assertEquals(1, e.getOperations().size());
        assertEquals(commissionReportOut.getMeta().getHref(), e.getOperations().get(0).getMeta().getHref());
        assertEquals(commissionReportOut.getGroup().getMeta().getHref(), e.getGroup().getMeta().getHref());
        assertEquals(commissionReportOut.getContract().getMeta().getHref(), e.getContract().getMeta().getHref());
        assertEquals(commissionReportOut.getAgent().getMeta().getHref(), e.getAgent().getMeta().getHref());
        assertEquals(commissionReportOut.getOrganization().getMeta().getHref(), e.getOrganization().getMeta().getHref());
    }

    private void getAsserts(PaymentOutDocumentEntity e, PaymentOutDocumentEntity retrievedEntity) {
        assertEquals(e.getName(), retrievedEntity.getName());
        assertEquals(e.getOrganization().getMeta().getHref(), retrievedEntity.getOrganization().getMeta().getHref());
        assertEquals(e.getAgent().getMeta().getHref(), retrievedEntity.getAgent().getMeta().getHref());
        assertEquals(e.getExpenseItem().getMeta().getHref(), retrievedEntity.getExpenseItem().getMeta().getHref());
    }

    private void putAsserts(PaymentOutDocumentEntity e, PaymentOutDocumentEntity retrievedOriginalEntity, String name) throws IOException, LognexApiException {
        PaymentOutDocumentEntity retrievedUpdatedEntity = api.entity().paymentout().get(e.getId());

        assertNotEquals(retrievedOriginalEntity.getName(), retrievedUpdatedEntity.getName());
        assertEquals(name, retrievedUpdatedEntity.getName());
        assertEquals(retrievedOriginalEntity.getOrganization().getMeta().getHref(), retrievedUpdatedEntity.getOrganization().getMeta().getHref());
        assertEquals(retrievedOriginalEntity.getAgent().getMeta().getHref(), retrievedUpdatedEntity.getAgent().getMeta().getHref());
        assertEquals(retrievedOriginalEntity.getExpenseItem().getMeta().getHref(), retrievedUpdatedEntity.getExpenseItem().getMeta().getHref());
    }
}
