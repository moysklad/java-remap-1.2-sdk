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

public class InvoiceOutDocumentEntityTest extends DocumentWithPositionsTestBase {
    @Test
    public void createTest() throws IOException, LognexApiException {
        InvoiceOutDocumentEntity invoiceOut = new InvoiceOutDocumentEntity();
        invoiceOut.setName("invoiceout_" + randomString(3) + "_" + new Date().getTime());
        invoiceOut.setVatEnabled(true);
        invoiceOut.setVatIncluded(true);
        invoiceOut.setMoment(LocalDateTime.now());
        invoiceOut.setSum(randomLong(10, 10000));
        invoiceOut.setOrganization(simpleEntityManager.getOwnOrganization());
        invoiceOut.setAgent(simpleEntityManager.createSimpleCounterparty());
        invoiceOut.setStore(simpleEntityManager.getMainStore());

        api.entity().invoiceout().post(invoiceOut);

        ListEntity<InvoiceOutDocumentEntity> updatedEntitiesList = api.entity().invoiceout().get(filterEq("name", invoiceOut.getName()));
        assertEquals(1, updatedEntitiesList.getRows().size());

        InvoiceOutDocumentEntity retrievedEntity = updatedEntitiesList.getRows().get(0);
        assertEquals(invoiceOut.getName(), retrievedEntity.getName());
        assertEquals(invoiceOut.getVatEnabled(), retrievedEntity.getVatEnabled());
        assertEquals(invoiceOut.getVatIncluded(), retrievedEntity.getVatIncluded());
        assertEquals(invoiceOut.getMoment(), retrievedEntity.getMoment());
        assertEquals(invoiceOut.getSum(), retrievedEntity.getSum());
        assertEquals(invoiceOut.getOrganization().getMeta().getHref(), retrievedEntity.getOrganization().getMeta().getHref());
        assertEquals(invoiceOut.getAgent().getMeta().getHref(), retrievedEntity.getAgent().getMeta().getHref());
        assertEquals(invoiceOut.getStore().getMeta().getHref(), retrievedEntity.getStore().getMeta().getHref());
    }

    @Test
    public void metadataTest() throws IOException, LognexApiException {
        MetadataAttributeSharedStatesResponse response = api.entity().invoiceout().metadata().get();

        assertFalse(response.getCreateShared());
    }

    @Override
    protected void getAsserts(MetaEntity originalEntity, MetaEntity retrievedEntity) {
        InvoiceOutDocumentEntity originalInvoiceOut = (InvoiceOutDocumentEntity) originalEntity;
        InvoiceOutDocumentEntity retrievedInvoiceOut = (InvoiceOutDocumentEntity) retrievedEntity;

        assertEquals(originalInvoiceOut.getName(), retrievedInvoiceOut.getName());
        assertEquals(originalInvoiceOut.getOrganization().getMeta().getHref(), retrievedInvoiceOut.getOrganization().getMeta().getHref());
        assertEquals(originalInvoiceOut.getAgent().getMeta().getHref(), retrievedInvoiceOut.getAgent().getMeta().getHref());
    }

    @Override
    protected void putAsserts(MetaEntity originalEntity, MetaEntity updatedEntity, Object changedField) {
        InvoiceOutDocumentEntity originalInvoiceOut = (InvoiceOutDocumentEntity) originalEntity;
        InvoiceOutDocumentEntity updatedInvoiceOut = (InvoiceOutDocumentEntity) updatedEntity;

        assertNotEquals(originalInvoiceOut.getName(), updatedInvoiceOut.getName());
        assertEquals(changedField, updatedInvoiceOut.getName());
        assertEquals(originalInvoiceOut.getOrganization().getMeta().getHref(), updatedInvoiceOut.getOrganization().getMeta().getHref());
        assertEquals(originalInvoiceOut.getAgent().getMeta().getHref(), updatedInvoiceOut.getAgent().getMeta().getHref());
    }

    @Override
    protected ApiClient entityClient() {
        return api.entity().invoiceout();
    }

    @Override
    protected Class<? extends MetaEntity> entityClass() {
        return InvoiceOutDocumentEntity.class;
    }
}
