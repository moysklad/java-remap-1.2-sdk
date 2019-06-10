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
        invoiceOut.setOrganization(simpleEntityFactory.getOwnOrganization());
        invoiceOut.setAgent(simpleEntityFactory.createSimpleCounterparty());
        invoiceOut.setStore(simpleEntityFactory.getMainStore());

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
    public void getTest() throws IOException, LognexApiException {
        InvoiceOutDocumentEntity invoiceOut = simpleEntityFactory.createSimpleInvoiceOut();

        InvoiceOutDocumentEntity retrievedEntity = api.entity().invoiceout().get(invoiceOut.getId());
        getAsserts(invoiceOut, retrievedEntity);

        retrievedEntity = api.entity().invoiceout().get(invoiceOut);
        getAsserts(invoiceOut, retrievedEntity);
    }

    @Test
    public void putTest() throws IOException, LognexApiException {
        InvoiceOutDocumentEntity invoiceOut = simpleEntityFactory.createSimpleInvoiceOut();

        InvoiceOutDocumentEntity retrievedOriginalEntity = api.entity().invoiceout().get(invoiceOut.getId());
        String name = "invoiceout_" + randomString(3) + "_" + new Date().getTime();
        invoiceOut.setName(name);
        api.entity().invoiceout().put(invoiceOut.getId(), invoiceOut);
        putAsserts(invoiceOut, retrievedOriginalEntity, name);

        retrievedOriginalEntity.set(invoiceOut);

        name = "invoiceout_" + randomString(3) + "_" + new Date().getTime();
        invoiceOut.setName(name);
        api.entity().invoiceout().put(invoiceOut);
        putAsserts(invoiceOut, retrievedOriginalEntity, name);
    }

    @Test
    public void deleteTest() throws IOException, LognexApiException {
        InvoiceOutDocumentEntity invoiceOut = simpleEntityFactory.createSimpleInvoiceOut();

        ListEntity<InvoiceOutDocumentEntity> entitiesList = api.entity().invoiceout().get(filterEq("name", invoiceOut.getName()));
        assertEquals((Integer) 1, entitiesList.getMeta().getSize());

        api.entity().invoiceout().delete(invoiceOut.getId());

        entitiesList = api.entity().invoiceout().get(filterEq("name", invoiceOut.getName()));
        assertEquals((Integer) 0, entitiesList.getMeta().getSize());
    }

    @Test
    public void deleteByIdTest() throws IOException, LognexApiException {
        InvoiceOutDocumentEntity invoiceOut = simpleEntityFactory.createSimpleInvoiceOut();

        ListEntity<InvoiceOutDocumentEntity> entitiesList = api.entity().invoiceout().get(filterEq("name", invoiceOut.getName()));
        assertEquals((Integer) 1, entitiesList.getMeta().getSize());

        api.entity().invoiceout().delete(invoiceOut);

        entitiesList = api.entity().invoiceout().get(filterEq("name", invoiceOut.getName()));
        assertEquals((Integer) 0, entitiesList.getMeta().getSize());
    }

    @Test
    public void metadataTest() throws IOException, LognexApiException {
        MetadataAttributeSharedStatesResponse response = api.entity().invoiceout().metadata().get();

        assertFalse(response.getCreateShared());
    }

    private void getAsserts(InvoiceOutDocumentEntity invoiceOut, InvoiceOutDocumentEntity retrievedEntity) {
        assertEquals(invoiceOut.getName(), retrievedEntity.getName());
        assertEquals(invoiceOut.getOrganization().getMeta().getHref(), retrievedEntity.getOrganization().getMeta().getHref());
        assertEquals(invoiceOut.getAgent().getMeta().getHref(), retrievedEntity.getAgent().getMeta().getHref());
    }

    private void putAsserts(InvoiceOutDocumentEntity invoiceOut, InvoiceOutDocumentEntity retrievedOriginalEntity, String name) throws IOException, LognexApiException {
        InvoiceOutDocumentEntity retrievedUpdatedEntity = api.entity().invoiceout().get(invoiceOut.getId());

        assertNotEquals(retrievedOriginalEntity.getName(), retrievedUpdatedEntity.getName());
        assertEquals(name, retrievedUpdatedEntity.getName());
        assertEquals(retrievedOriginalEntity.getOrganization().getMeta().getHref(), retrievedUpdatedEntity.getOrganization().getMeta().getHref());
        assertEquals(retrievedOriginalEntity.getAgent().getMeta().getHref(), retrievedUpdatedEntity.getAgent().getMeta().getHref());
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
