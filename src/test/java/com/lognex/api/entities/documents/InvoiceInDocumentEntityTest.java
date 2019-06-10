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
    public void getTest() throws IOException, LognexApiException {
        InvoiceInDocumentEntity invoiceIn = simpleEntityFactory.createSimpleInvoiceIn();

        InvoiceInDocumentEntity retrievedEntity = api.entity().invoicein().get(invoiceIn.getId());
        getAsserts(invoiceIn, retrievedEntity);

        retrievedEntity = api.entity().invoicein().get(invoiceIn);
        getAsserts(invoiceIn, retrievedEntity);
    }

    @Test
    public void putTest() throws IOException, LognexApiException {
        InvoiceInDocumentEntity invoiceIn = simpleEntityFactory.createSimpleInvoiceIn();

        InvoiceInDocumentEntity retrievedOriginalEntity = api.entity().invoicein().get(invoiceIn.getId());
        String name = "invoicein_" + randomString(3) + "_" + new Date().getTime();
        invoiceIn.setName(name);
        api.entity().invoicein().put(invoiceIn.getId(), invoiceIn);
        putAsserts(invoiceIn, retrievedOriginalEntity, name);

        retrievedOriginalEntity.set(invoiceIn);

        name = "invoicein_" + randomString(3) + "_" + new Date().getTime();
        invoiceIn.setName(name);
        api.entity().invoicein().put(invoiceIn);
        putAsserts(invoiceIn, retrievedOriginalEntity, name);
    }

    @Test
    public void deleteTest() throws IOException, LognexApiException {
        InvoiceInDocumentEntity invoiceIn = simpleEntityFactory.createSimpleInvoiceIn();

        ListEntity<InvoiceInDocumentEntity> entitiesList = api.entity().invoicein().get(filterEq("name", invoiceIn.getName()));
        assertEquals((Integer) 1, entitiesList.getMeta().getSize());

        api.entity().invoicein().delete(invoiceIn.getId());

        entitiesList = api.entity().invoicein().get(filterEq("name", invoiceIn.getName()));
        assertEquals((Integer) 0, entitiesList.getMeta().getSize());
    }

    @Test
    public void deleteByIdTest() throws IOException, LognexApiException {
        InvoiceInDocumentEntity invoiceIn = simpleEntityFactory.createSimpleInvoiceIn();

        ListEntity<InvoiceInDocumentEntity> entitiesList = api.entity().invoicein().get(filterEq("name", invoiceIn.getName()));
        assertEquals((Integer) 1, entitiesList.getMeta().getSize());

        api.entity().invoicein().delete(invoiceIn);

        entitiesList = api.entity().invoicein().get(filterEq("name", invoiceIn.getName()));
        assertEquals((Integer) 0, entitiesList.getMeta().getSize());
    }

    @Test
    public void metadataTest() throws IOException, LognexApiException {
        MetadataAttributeSharedStatesResponse response = api.entity().invoicein().metadata().get();

        assertFalse(response.getCreateShared());
    }

    private void getAsserts(InvoiceInDocumentEntity invoiceIn, InvoiceInDocumentEntity retrievedEntity) {
        assertEquals(invoiceIn.getName(), retrievedEntity.getName());
        assertEquals(invoiceIn.getOrganization().getMeta().getHref(), retrievedEntity.getOrganization().getMeta().getHref());
        assertEquals(invoiceIn.getAgent().getMeta().getHref(), retrievedEntity.getAgent().getMeta().getHref());
    }

    private void putAsserts(InvoiceInDocumentEntity invoiceIn, InvoiceInDocumentEntity retrievedOriginalEntity, String name) throws IOException, LognexApiException {
        InvoiceInDocumentEntity retrievedUpdatedEntity = api.entity().invoicein().get(invoiceIn.getId());

        assertNotEquals(retrievedOriginalEntity.getName(), retrievedUpdatedEntity.getName());
        assertEquals(name, retrievedUpdatedEntity.getName());
        assertEquals(retrievedOriginalEntity.getOrganization().getMeta().getHref(), retrievedUpdatedEntity.getOrganization().getMeta().getHref());
        assertEquals(retrievedOriginalEntity.getAgent().getMeta().getHref(), retrievedUpdatedEntity.getAgent().getMeta().getHref());
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
