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

public class DemandDocumentEntityTest extends DocumentWithPositionsTestBase {
    @Test
    public void createTest() throws IOException, LognexApiException {
        DemandDocumentEntity demand = new DemandDocumentEntity();
        demand.setName("demand_" + randomString(3) + "_" + new Date().getTime());
        demand.setDescription(randomString());
        demand.setVatEnabled(true);
        demand.setVatIncluded(true);
        demand.setMoment(LocalDateTime.now());
        demand.setOrganization(simpleEntityFactory.getOwnOrganization());
        demand.setAgent(simpleEntityFactory.createSimpleCounterparty());
        demand.setStore(simpleEntityFactory.getMainStore());

        api.entity().demand().post(demand);

        ListEntity<DemandDocumentEntity> updatedEntitiesList = api.entity().demand().get(filterEq("name", demand.getName()));
        assertEquals(1, updatedEntitiesList.getRows().size());

        DemandDocumentEntity retrievedEntity = updatedEntitiesList.getRows().get(0);
        assertEquals(demand.getName(), retrievedEntity.getName());
        assertEquals(demand.getDescription(), retrievedEntity.getDescription());
        assertEquals(demand.getVatEnabled(), retrievedEntity.getVatEnabled());
        assertEquals(demand.getVatIncluded(), retrievedEntity.getVatIncluded());
        assertEquals(demand.getMoment(), retrievedEntity.getMoment());
        assertEquals(demand.getOrganization().getMeta().getHref(), retrievedEntity.getOrganization().getMeta().getHref());
        assertEquals(demand.getAgent().getMeta().getHref(), retrievedEntity.getAgent().getMeta().getHref());
        assertEquals(demand.getStore().getMeta().getHref(), retrievedEntity.getStore().getMeta().getHref());
    }

    @Test
    public void getTest() throws IOException, LognexApiException {
        DemandDocumentEntity demand = simpleEntityFactory.createSimpleDemand();

        DemandDocumentEntity retrievedEntity = api.entity().demand().get(demand.getId());
        getAsserts(demand, retrievedEntity);

        retrievedEntity = api.entity().demand().get(demand);
        getAsserts(demand, retrievedEntity);
    }

    @Test
    public void putTest() throws IOException, LognexApiException {
        DemandDocumentEntity demand = simpleEntityFactory.createSimpleDemand();

        DemandDocumentEntity retrievedOriginalEntity = api.entity().demand().get(demand.getId());
        String name = "demand_" + randomString(3) + "_" + new Date().getTime();
        demand.setName(name);
        api.entity().demand().put(demand.getId(), demand);
        putAsserts(demand, retrievedOriginalEntity, name);

        retrievedOriginalEntity.set(demand);

        name = "demand_" + randomString(3) + "_" + new Date().getTime();
        demand.setName(name);
        api.entity().demand().put(demand);
        putAsserts(demand, retrievedOriginalEntity, name);
    }

    @Test
    public void deleteTest() throws IOException, LognexApiException {
        DemandDocumentEntity demand = simpleEntityFactory.createSimpleDemand();

        ListEntity<DemandDocumentEntity> entitiesList = api.entity().demand().get(filterEq("name", demand.getName()));
        assertEquals((Integer) 1, entitiesList.getMeta().getSize());

        api.entity().demand().delete(demand.getId());

        entitiesList = api.entity().demand().get(filterEq("name", demand.getName()));
        assertEquals((Integer) 0, entitiesList.getMeta().getSize());
    }

    @Test
    public void deleteByIdTest() throws IOException, LognexApiException {
        DemandDocumentEntity demand = simpleEntityFactory.createSimpleDemand();

        ListEntity<DemandDocumentEntity> entitiesList = api.entity().demand().get(filterEq("name", demand.getName()));
        assertEquals((Integer) 1, entitiesList.getMeta().getSize());

        api.entity().demand().delete(demand);

        entitiesList = api.entity().demand().get(filterEq("name", demand.getName()));
        assertEquals((Integer) 0, entitiesList.getMeta().getSize());
    }

    @Test
    public void metadataTest() throws IOException, LognexApiException {
        MetadataAttributeSharedStatesResponse response = api.entity().demand().metadata().get();

        assertFalse(response.getCreateShared());
    }

    @Test
    public void newTest() throws IOException, LognexApiException {
        DemandDocumentEntity demand = api.entity().demand().newDocument();
        LocalDateTime time = LocalDateTime.now();

        assertEquals("", demand.getName());
        assertTrue(demand.getVatEnabled());
        assertTrue(demand.getVatIncluded());
        assertEquals(Long.valueOf(0), demand.getPayedSum());
        assertEquals(Long.valueOf(0), demand.getSum());
        assertFalse(demand.getShared());
        assertTrue(demand.getApplicable());
        assertTrue(ChronoUnit.MILLIS.between(time, demand.getMoment()) < 1000);

        assertEquals(demand.getOrganization().getMeta().getHref(), simpleEntityFactory.getOwnOrganization().getMeta().getHref());
        assertEquals(demand.getStore().getMeta().getHref(), simpleEntityFactory.getMainStore().getMeta().getHref());
        assertEquals(demand.getGroup().getMeta().getHref(), simpleEntityFactory.getMainGroup().getMeta().getHref());
    }

    @Test
    public void newByCustomerOrderTest() throws IOException, LognexApiException {
        CustomerOrderDocumentEntity customerOrder = simpleEntityFactory.createSimpleCustomerOrder();

        DemandDocumentEntity demand = api.entity().demand().newDocument("customerOrder", customerOrder);
        LocalDateTime time = LocalDateTime.now();

        assertEquals("", demand.getName());
        assertEquals(customerOrder.getVatEnabled(), demand.getVatEnabled());
        assertEquals(customerOrder.getVatIncluded(), demand.getVatIncluded());
        assertEquals(customerOrder.getPayedSum(), demand.getPayedSum());
        assertEquals(customerOrder.getSum(), demand.getSum());
        assertFalse(demand.getShared());
        assertTrue(demand.getApplicable());
        assertTrue(ChronoUnit.MILLIS.between(time, demand.getMoment()) < 1000);
        assertEquals(customerOrder.getMeta().getHref(), demand.getCustomerOrder().getMeta().getHref());
        assertEquals(customerOrder.getAgent().getMeta().getHref(), demand.getAgent().getMeta().getHref());
        assertEquals(customerOrder.getGroup().getMeta().getHref(), demand.getGroup().getMeta().getHref());
        assertEquals(customerOrder.getOrganization().getMeta().getHref(), demand.getOrganization().getMeta().getHref());
    }

    @Test
    public void newByInvoicesOutTest() throws IOException, LognexApiException {
        InvoiceOutDocumentEntity invoiceOut = simpleEntityFactory.createSimpleInvoiceOut();

        DemandDocumentEntity demand = api.entity().demand().newDocument("invoicesOut", Collections.singletonList(invoiceOut));
        LocalDateTime time = LocalDateTime.now();

        assertEquals("", demand.getName());
        assertEquals(invoiceOut.getVatEnabled(), demand.getVatEnabled());
        assertEquals(invoiceOut.getVatIncluded(), demand.getVatIncluded());
        assertEquals(invoiceOut.getPayedSum(), demand.getPayedSum());
        assertEquals(invoiceOut.getSum(), demand.getSum());
        assertFalse(demand.getShared());
        assertTrue(demand.getApplicable());
        assertTrue(ChronoUnit.MILLIS.between(time, demand.getMoment()) < 1000);
        assertEquals(1, demand.getInvoicesOut().size());
        assertEquals(invoiceOut.getMeta().getHref(), demand.getInvoicesOut().get(0).getMeta().getHref());
        assertEquals(invoiceOut.getAgent().getMeta().getHref(), demand.getAgent().getMeta().getHref());
        assertEquals(invoiceOut.getGroup().getMeta().getHref(), demand.getGroup().getMeta().getHref());
        assertEquals(invoiceOut.getOrganization().getMeta().getHref(), demand.getOrganization().getMeta().getHref());
    }

    private void getAsserts(DemandDocumentEntity demand, DemandDocumentEntity retrievedEntity) {
        assertEquals(demand.getName(), retrievedEntity.getName());
        assertEquals(demand.getDescription(), retrievedEntity.getDescription());
        assertEquals(demand.getOrganization().getMeta().getHref(), retrievedEntity.getOrganization().getMeta().getHref());
        assertEquals(demand.getAgent().getMeta().getHref(), retrievedEntity.getAgent().getMeta().getHref());
        assertEquals(demand.getStore().getMeta().getHref(), retrievedEntity.getStore().getMeta().getHref());
    }

    private void putAsserts(DemandDocumentEntity demand, DemandDocumentEntity retrievedOriginalEntity, String name) throws IOException, LognexApiException {
        DemandDocumentEntity retrievedUpdatedEntity = api.entity().demand().get(demand.getId());

        assertNotEquals(retrievedOriginalEntity.getName(), retrievedUpdatedEntity.getName());
        assertEquals(name, retrievedUpdatedEntity.getName());
        assertEquals(retrievedOriginalEntity.getDescription(), retrievedUpdatedEntity.getDescription());
        assertEquals(retrievedOriginalEntity.getOrganization().getMeta().getHref(), retrievedUpdatedEntity.getOrganization().getMeta().getHref());
        assertEquals(retrievedOriginalEntity.getAgent().getMeta().getHref(), retrievedUpdatedEntity.getAgent().getMeta().getHref());
        assertEquals(retrievedOriginalEntity.getStore().getMeta().getHref(), retrievedUpdatedEntity.getStore().getMeta().getHref());
    }

    @Override
    protected ApiClient entityClient() {
        return api.entity().demand();
    }

    @Override
    protected Class<? extends MetaEntity> entityClass() {
        return DemandDocumentEntity.class;
    }
}
