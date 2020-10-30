package com.lognex.api.entities.documents;

import com.lognex.api.clients.EntityClientBase;
import com.lognex.api.clients.documents.DocumentMetadataClient;
import com.lognex.api.entities.MetaEntity;
import com.lognex.api.responses.ListEntity;
import com.lognex.api.responses.metadata.MetadataAttributeSharedStatesResponse;
import com.lognex.api.utils.ApiClientException;
import org.junit.Test;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Date;

import static com.lognex.api.utils.params.FilterParam.filterEq;
import static org.junit.Assert.*;

public class InvoiceInTest extends DocumentWithPositionsTestBase {
    @Test
    public void createTest() throws IOException, ApiClientException {
        InvoiceIn invoiceIn = new InvoiceIn();
        invoiceIn.setName("invoicein_" + randomString(3) + "_" + new Date().getTime());
        invoiceIn.setVatEnabled(true);
        invoiceIn.setVatIncluded(true);
        invoiceIn.setMoment(LocalDateTime.now());
        invoiceIn.setSum(randomLong(10, 10000));
        invoiceIn.setOrganization(simpleEntityManager.getOwnOrganization());
        invoiceIn.setAgent(simpleEntityManager.createSimpleCounterparty());
        invoiceIn.setStore(simpleEntityManager.getMainStore());

        api.entity().invoicein().create(invoiceIn);

        ListEntity<InvoiceIn> updatedEntitiesList = api.entity().invoicein().get(filterEq("name", invoiceIn.getName()));
        assertEquals(1, updatedEntitiesList.getRows().size());

        InvoiceIn retrievedEntity = updatedEntitiesList.getRows().get(0);
        assertEquals(invoiceIn.getName(), retrievedEntity.getName());
        assertEquals(invoiceIn.getVatEnabled(), retrievedEntity.getVatEnabled());
        assertEquals(invoiceIn.getVatIncluded(), retrievedEntity.getVatIncluded());
        assertEquals(invoiceIn.getMoment(), retrievedEntity.getMoment());
        assertEquals(invoiceIn.getSum(), retrievedEntity.getSum());
        assertEquals(invoiceIn.getOrganization().getMeta().getHref(), retrievedEntity.getOrganization().getMeta().getHref());
        assertEquals(invoiceIn.getAgent().getMeta().getHref(), retrievedEntity.getAgent().getMeta().getHref());
        assertEquals(invoiceIn.getStore().getMeta().getHref(), retrievedEntity.getStore().getMeta().getHref());
    }

    @Test
    public void metadataTest() throws IOException, ApiClientException {
        DocumentMetadataClient<MetadataAttributeSharedStatesResponse> metadata = api.entity().invoicein().metadata();
        MetadataAttributeSharedStatesResponse response = metadata.get();

        assertFalse(response.getCreateShared());
        assertNotNull(metadata.attributes());
    }

    @Override
    protected void getAsserts(MetaEntity originalEntity, MetaEntity retrievedEntity) {
        InvoiceIn originalInvoiceIn = (InvoiceIn) originalEntity;
        InvoiceIn retrievedInvoiceIn = (InvoiceIn) retrievedEntity;

        assertEquals(originalInvoiceIn.getName(), retrievedInvoiceIn.getName());
        assertEquals(originalInvoiceIn.getOrganization().getMeta().getHref(), retrievedInvoiceIn.getOrganization().getMeta().getHref());
        assertEquals(originalInvoiceIn.getAgent().getMeta().getHref(), retrievedInvoiceIn.getAgent().getMeta().getHref());
    }

    @Override
    protected void putAsserts(MetaEntity originalEntity, MetaEntity updatedEntity, Object changedField) {
        InvoiceIn originalInvoiceIn = (InvoiceIn) originalEntity;
        InvoiceIn updatedInvoiceIn = (InvoiceIn) updatedEntity;

        assertNotEquals(originalInvoiceIn.getName(), updatedInvoiceIn.getName());
        assertEquals(changedField, updatedInvoiceIn.getName());
        assertEquals(originalInvoiceIn.getOrganization().getMeta().getHref(), updatedInvoiceIn.getOrganization().getMeta().getHref());
        assertEquals(originalInvoiceIn.getAgent().getMeta().getHref(), updatedInvoiceIn.getAgent().getMeta().getHref());
    }

    @Override
    protected EntityClientBase entityClient() {
        return api.entity().invoicein();
    }

    @Override
    protected Class<? extends MetaEntity> entityClass() {
        return InvoiceIn.class;
    }
}
