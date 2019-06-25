package com.lognex.api.entities.documents;

import com.lognex.api.clients.ApiClient;
import com.lognex.api.entities.MetaEntity;
import com.lognex.api.responses.ListEntity;
import com.lognex.api.responses.metadata.MetadataAttributeSharedStatesResponse;
import com.lognex.api.utils.LognexApiException;
import org.junit.Test;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Date;

import static com.lognex.api.utils.params.FilterParam.filterEq;
import static org.junit.Assert.*;

public class InvoiceInDocumentEntityTest extends DocumentWithPositionsTestBase {
    @Test
    public void createTest() throws IOException, LognexApiException {
        InvoiceInDocumentEntity invoiceIn = new InvoiceInDocumentEntity();
        invoiceIn.setName("invoicein_" + randomString(3) + "_" + new Date().getTime());
        invoiceIn.setVatEnabled(true);
        invoiceIn.setVatIncluded(true);
        invoiceIn.setMoment(LocalDateTime.now());
        invoiceIn.setSum(randomLong(10, 10000));
        invoiceIn.setOrganization(simpleEntityFactory.getOwnOrganization());
        invoiceIn.setAgent(simpleEntityFactory.createSimpleCounterparty());
        invoiceIn.setStore(simpleEntityFactory.getMainStore());

        api.entity().invoicein().post(invoiceIn);

        ListEntity<InvoiceInDocumentEntity> updatedEntitiesList = api.entity().invoicein().get(filterEq("name", invoiceIn.getName()));
        assertEquals(1, updatedEntitiesList.getRows().size());

        InvoiceInDocumentEntity retrievedEntity = updatedEntitiesList.getRows().get(0);
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
    public void metadataTest() throws IOException, LognexApiException {
        MetadataAttributeSharedStatesResponse response = api.entity().invoicein().metadata().get();

        assertFalse(response.getCreateShared());
    }

    @Override
    protected void getAsserts(MetaEntity originalEntity, MetaEntity retrievedEntity) {
        InvoiceInDocumentEntity originalInvoiceIn = (InvoiceInDocumentEntity) originalEntity;
        InvoiceInDocumentEntity retrievedInvoiceIn = (InvoiceInDocumentEntity) retrievedEntity;

        assertEquals(originalInvoiceIn.getName(), retrievedInvoiceIn.getName());
        assertEquals(originalInvoiceIn.getOrganization().getMeta().getHref(), retrievedInvoiceIn.getOrganization().getMeta().getHref());
        assertEquals(originalInvoiceIn.getAgent().getMeta().getHref(), retrievedInvoiceIn.getAgent().getMeta().getHref());
    }

    @Override
    protected void putAsserts(MetaEntity originalEntity, MetaEntity updatedEntity, Object changedField) {
        InvoiceInDocumentEntity originalInvoiceIn = (InvoiceInDocumentEntity) originalEntity;
        InvoiceInDocumentEntity updatedInvoiceIn = (InvoiceInDocumentEntity) updatedEntity;

        assertNotEquals(originalInvoiceIn.getName(), updatedInvoiceIn.getName());
        assertEquals(changedField, updatedInvoiceIn.getName());
        assertEquals(originalInvoiceIn.getOrganization().getMeta().getHref(), updatedInvoiceIn.getOrganization().getMeta().getHref());
        assertEquals(originalInvoiceIn.getAgent().getMeta().getHref(), updatedInvoiceIn.getAgent().getMeta().getHref());
    }

    @Override
    protected ApiClient entityClient() {
        return api.entity().invoicein();
    }

    @Override
    protected Class<? extends MetaEntity> entityClass() {
        return InvoiceInDocumentEntity.class;
    }
}
