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

public class SupplyDocumentEntityTest extends DocumentWithPositionsTestBase {
    @Test
    public void createTest() throws IOException, LognexApiException {
        SupplyDocumentEntity supply = new SupplyDocumentEntity();
        supply.setName("supply_" + randomString(3) + "_" + new Date().getTime());
        supply.setDescription(randomString());
        supply.setVatEnabled(true);
        supply.setVatIncluded(true);
        supply.setMoment(LocalDateTime.now());
        supply.setOrganization(simpleEntityManager.getOwnOrganization());
        supply.setAgent(simpleEntityManager.createSimpleCounterparty());
        supply.setStore(simpleEntityManager.getMainStore());

        api.entity().supply().post(supply);

        ListEntity<SupplyDocumentEntity> updatedEntitiesList = api.entity().supply().get(filterEq("name", supply.getName()));
        assertEquals(1, updatedEntitiesList.getRows().size());

        SupplyDocumentEntity retrievedEntity = updatedEntitiesList.getRows().get(0);
        assertEquals(supply.getName(), retrievedEntity.getName());
        assertEquals(supply.getDescription(), retrievedEntity.getDescription());
        assertEquals(supply.getVatEnabled(), retrievedEntity.getVatEnabled());
        assertEquals(supply.getVatIncluded(), retrievedEntity.getVatIncluded());
        assertEquals(supply.getMoment(), retrievedEntity.getMoment());
        assertEquals(supply.getOrganization().getMeta().getHref(), retrievedEntity.getOrganization().getMeta().getHref());
        assertEquals(supply.getAgent().getMeta().getHref(), retrievedEntity.getAgent().getMeta().getHref());
        assertEquals(supply.getStore().getMeta().getHref(), retrievedEntity.getStore().getMeta().getHref());
    }

    @Test
    public void metadataTest() throws IOException, LognexApiException {
        MetadataAttributeSharedStatesResponse response = api.entity().supply().metadata().get();

        assertFalse(response.getCreateShared());
    }

    @Test
    public void newTest() throws IOException, LognexApiException {
        SupplyDocumentEntity supply = api.entity().supply().newDocument();
        LocalDateTime time = LocalDateTime.now();

        assertEquals("", supply.getName());
        assertTrue(supply.getVatEnabled());
        assertTrue(supply.getVatIncluded());
        assertEquals(Long.valueOf(0), supply.getPayedSum());
        assertEquals(Long.valueOf(0), supply.getSum());
        assertFalse(supply.getShared());
        assertTrue(supply.getApplicable());
        assertTrue(ChronoUnit.MILLIS.between(time, supply.getMoment()) < 1000);

        assertEquals(supply.getOrganization().getMeta().getHref(), simpleEntityManager.getOwnOrganization().getMeta().getHref());
        assertEquals(supply.getStore().getMeta().getHref(), simpleEntityManager.getMainStore().getMeta().getHref());
        assertEquals(supply.getGroup().getMeta().getHref(), simpleEntityManager.getMainGroup().getMeta().getHref());
    }

    @Test
    public void newByPurchaseOrderTest() throws IOException, LognexApiException {
        PurchaseOrderDocumentEntity purchaseOrder = simpleEntityManager.createSimplePurchaseOrder();

        SupplyDocumentEntity supply = api.entity().supply().newDocument("purchaseOrder", purchaseOrder);
        LocalDateTime time = LocalDateTime.now();

        assertEquals("", supply.getName());
        assertEquals(purchaseOrder.getVatEnabled(), supply.getVatEnabled());
        assertEquals(purchaseOrder.getVatIncluded(), supply.getVatIncluded());
        assertEquals(purchaseOrder.getPayedSum(), supply.getPayedSum());
        assertEquals(purchaseOrder.getSum(), supply.getSum());
        assertFalse(supply.getShared());
        assertTrue(supply.getApplicable());
        assertTrue(ChronoUnit.MILLIS.between(time, supply.getMoment()) < 1000);
        assertEquals(purchaseOrder.getMeta().getHref(), supply.getPurchaseOrder().getMeta().getHref());
        assertEquals(purchaseOrder.getAgent().getMeta().getHref(), supply.getAgent().getMeta().getHref());
        assertEquals(purchaseOrder.getGroup().getMeta().getHref(), supply.getGroup().getMeta().getHref());
        assertEquals(purchaseOrder.getOrganization().getMeta().getHref(), supply.getOrganization().getMeta().getHref());
    }

    @Test
    public void newByInvoicesInTest() throws IOException, LognexApiException {
        InvoiceInDocumentEntity invoiceIn = simpleEntityManager.createSimpleInvoiceIn();

        SupplyDocumentEntity supply = api.entity().supply().newDocument("invoicesIn", Collections.singletonList(invoiceIn));
        LocalDateTime time = LocalDateTime.now();

        assertEquals("", supply.getName());
        assertEquals(invoiceIn.getVatEnabled(), supply.getVatEnabled());
        assertEquals(invoiceIn.getVatIncluded(), supply.getVatIncluded());
        assertEquals(invoiceIn.getPayedSum(), supply.getPayedSum());
        assertEquals(invoiceIn.getSum(), supply.getSum());
        assertFalse(supply.getShared());
        assertTrue(supply.getApplicable());
        assertTrue(ChronoUnit.MILLIS.between(time, supply.getMoment()) < 1000);
        assertEquals(1, supply.getInvoicesIn().size());
        assertEquals(invoiceIn.getMeta().getHref(), supply.getInvoicesIn().get(0).getMeta().getHref());
        assertEquals(invoiceIn.getAgent().getMeta().getHref(), supply.getAgent().getMeta().getHref());
        assertEquals(invoiceIn.getGroup().getMeta().getHref(), supply.getGroup().getMeta().getHref());
        assertEquals(invoiceIn.getOrganization().getMeta().getHref(), supply.getOrganization().getMeta().getHref());
    }

    @Override
    protected void getAsserts(MetaEntity originalEntity, MetaEntity retrievedEntity) {
        SupplyDocumentEntity originalSupply = (SupplyDocumentEntity) originalEntity;
        SupplyDocumentEntity retrievedSupply = (SupplyDocumentEntity) retrievedEntity;

        assertEquals(originalSupply.getName(), retrievedSupply.getName());
        assertEquals(originalSupply.getDescription(), retrievedSupply.getDescription());
        assertEquals(originalSupply.getOrganization().getMeta().getHref(), retrievedSupply.getOrganization().getMeta().getHref());
        assertEquals(originalSupply.getAgent().getMeta().getHref(), retrievedSupply.getAgent().getMeta().getHref());
        assertEquals(originalSupply.getStore().getMeta().getHref(), retrievedSupply.getStore().getMeta().getHref());
    }

    @Override
    protected void putAsserts(MetaEntity originalEntity, MetaEntity updatedEntity, Object changedField) {
        SupplyDocumentEntity originalSupply = (SupplyDocumentEntity) originalEntity;
        SupplyDocumentEntity updatedSupply = (SupplyDocumentEntity) updatedEntity;

        assertNotEquals(originalSupply.getName(), updatedSupply.getName());
        assertEquals(changedField, updatedSupply.getName());
        assertEquals(originalSupply.getDescription(), updatedSupply.getDescription());
        assertEquals(originalSupply.getOrganization().getMeta().getHref(), updatedSupply.getOrganization().getMeta().getHref());
        assertEquals(originalSupply.getAgent().getMeta().getHref(), updatedSupply.getAgent().getMeta().getHref());
        assertEquals(originalSupply.getStore().getMeta().getHref(), updatedSupply.getStore().getMeta().getHref());
    }

    @Override
    protected ApiClient entityClient() {
        return api.entity().supply();
    }

    @Override
    protected Class<? extends MetaEntity> entityClass() {
        return SupplyDocumentEntity.class;
    }
}
