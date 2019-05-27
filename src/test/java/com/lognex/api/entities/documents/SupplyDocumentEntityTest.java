package com.lognex.api.entities.documents;

import com.lognex.api.entities.EntityTestBase;
import com.lognex.api.entities.GroupEntity;
import com.lognex.api.entities.StoreEntity;
import com.lognex.api.entities.agents.CounterpartyEntity;
import com.lognex.api.entities.agents.OrganizationEntity;
import com.lognex.api.responses.ListEntity;
import com.lognex.api.responses.metadata.MetadataAttributeSharedStatesResponse;
import com.lognex.api.utils.LognexApiException;
import org.junit.Test;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Optional;

import static com.lognex.api.utils.params.FilterParam.filterEq;
import static com.lognex.api.utils.params.SearchParam.search;
import static org.junit.Assert.*;

public class SupplyDocumentEntityTest extends EntityTestBase {
    @Test
    public void createTest() throws IOException, LognexApiException {
        SupplyDocumentEntity e = new SupplyDocumentEntity();
        e.setName("supply_" + randomString(3) + "_" + new Date().getTime());
        e.setDescription(randomString());
        e.setVatEnabled(true);
        e.setVatIncluded(true);
        e.setMoment(LocalDateTime.now());

        ListEntity<OrganizationEntity> orgList = api.entity().organization().get();
        assertNotEquals(0, orgList.getRows().size());
        e.setOrganization(orgList.getRows().get(0));

        CounterpartyEntity agent = new CounterpartyEntity();
        agent.setName(randomString());
        api.entity().counterparty().post(agent);
        e.setAgent(agent);

        ListEntity<StoreEntity> store = api.entity().store().get(filterEq("name", "Основной склад"));
        assertEquals(1, store.getRows().size());
        e.setStore(store.getRows().get(0));

        api.entity().supply().post(e);

        ListEntity<SupplyDocumentEntity> updatedEntitiesList = api.entity().supply().get(filterEq("name", e.getName()));
        assertEquals(1, updatedEntitiesList.getRows().size());

        SupplyDocumentEntity retrievedEntity = updatedEntitiesList.getRows().get(0);
        assertEquals(e.getName(), retrievedEntity.getName());
        assertEquals(e.getDescription(), retrievedEntity.getDescription());
        assertEquals(e.getVatEnabled(), retrievedEntity.getVatEnabled());
        assertEquals(e.getVatIncluded(), retrievedEntity.getVatIncluded());
        assertEquals(e.getMoment(), retrievedEntity.getMoment());
        assertEquals(e.getOrganization().getMeta().getHref(), retrievedEntity.getOrganization().getMeta().getHref());
        assertEquals(e.getAgent().getMeta().getHref(), retrievedEntity.getAgent().getMeta().getHref());
        assertEquals(e.getStore().getMeta().getHref(), retrievedEntity.getStore().getMeta().getHref());
        assertEquals(e.getCreated().withNano(0), retrievedEntity.getCreated().withNano(0));
        assertEquals(e.getUpdated().withNano(0), retrievedEntity.getUpdated().withNano(0));
    }

    @Test
    public void getTest() throws IOException, LognexApiException {
        SupplyDocumentEntity e = createSimpleDocumentSupply();

        SupplyDocumentEntity retrievedEntity = api.entity().supply().get(e.getId());
        getAsserts(e, retrievedEntity);

        retrievedEntity = api.entity().supply().get(e);
        getAsserts(e, retrievedEntity);
    }

    @Test
    public void putTest() throws IOException, LognexApiException, InterruptedException {
        SupplyDocumentEntity e = createSimpleDocumentSupply();

        SupplyDocumentEntity retrievedOriginalEntity = api.entity().supply().get(e.getId());
        String name = "supply_" + randomString(3) + "_" + new Date().getTime();
        e.setName(name);
        Thread.sleep(1500);
        api.entity().supply().put(e.getId(), e);
        putAsserts(e, retrievedOriginalEntity, name);

        retrievedOriginalEntity.set(e);

        name = "supply_" + randomString(3) + "_" + new Date().getTime();
        e.setName(name);
        Thread.sleep(1500);
        api.entity().supply().put(e);
        putAsserts(e, retrievedOriginalEntity, name);
    }

    @Test
    public void deleteTest() throws IOException, LognexApiException {
        SupplyDocumentEntity e = createSimpleDocumentSupply();

        ListEntity<SupplyDocumentEntity> entitiesList = api.entity().supply().get(filterEq("name", e.getName()));
        assertEquals(1, entitiesList.getRows().size());

        api.entity().supply().delete(e.getId());

        entitiesList = api.entity().supply().get(filterEq("name", e.getName()));
        assertEquals(0, entitiesList.getRows().size());
    }

    @Test
    public void deleteByIdTest() throws IOException, LognexApiException {
        SupplyDocumentEntity e = createSimpleDocumentSupply();

        ListEntity<SupplyDocumentEntity> entitiesList = api.entity().supply().get(filterEq("name", e.getName()));
        assertEquals(1, entitiesList.getRows().size());

        api.entity().supply().delete(e);

        entitiesList = api.entity().supply().get(filterEq("name", e.getName()));
        assertEquals(0, entitiesList.getRows().size());
    }

    @Test
    public void metadataTest() throws IOException, LognexApiException {
        MetadataAttributeSharedStatesResponse response = api.entity().supply().metadata().get();

        assertFalse(response.getCreateShared());
    }

    @Test
    public void newTest() throws IOException, LognexApiException {
        LocalDateTime time = LocalDateTime.now().withNano(0);
        SupplyDocumentEntity e = api.entity().supply().newDocument();

        assertEquals("", e.getName());
        assertTrue(e.getVatEnabled());
        assertTrue(e.getVatIncluded());
        assertEquals(Long.valueOf(0), e.getPayedSum());
        assertEquals(Long.valueOf(0), e.getSum());
        assertFalse(e.getShared());
        assertTrue(e.getApplicable());
        assertEquals(time, e.getMoment().withNano(0));

        ListEntity<OrganizationEntity> orgList = api.entity().organization().get();
        Optional<OrganizationEntity> orgOptional = orgList.getRows().stream().
                min(Comparator.comparing(OrganizationEntity::getCreated));

        OrganizationEntity org = null;
        if (orgOptional.isPresent()) {
            org = orgOptional.get();
        } else {
            // Должно быть первое созданное юрлицо
            fail();
        }

        assertEquals(e.getOrganization().getMeta().getHref(), org.getMeta().getHref());

        ListEntity<StoreEntity> store = api.entity().store().get(filterEq("name", "Основной склад"));
        assertEquals(1, store.getRows().size());
        assertEquals(e.getStore().getMeta().getHref(), store.getRows().get(0).getMeta().getHref());

        ListEntity<GroupEntity> group = api.entity().group().get(search("Основной"));
        assertEquals(1, group.getRows().size());
        assertEquals(e.getGroup().getMeta().getHref(), group.getRows().get(0).getMeta().getHref());
    }

    @Test
    public void newByPurchaseOrderTest() throws IOException, LognexApiException {
        PurchaseOrderDocumentEntity purchaseOrder = new PurchaseOrderDocumentEntity();
        purchaseOrder.setName("purchaseorder_" + randomString(3) + "_" + new Date().getTime());
        purchaseOrder.setDescription(randomString());
        purchaseOrder.setVatEnabled(true);
        purchaseOrder.setVatIncluded(true);

        ListEntity<OrganizationEntity> orgList = api.entity().organization().get();
        assertNotEquals(0, orgList.getRows().size());
        purchaseOrder.setOrganization(orgList.getRows().get(0));

        CounterpartyEntity agent = new CounterpartyEntity();
        agent.setName(randomString());
        api.entity().counterparty().post(agent);
        purchaseOrder.setAgent(agent);
        purchaseOrder.setMoment(LocalDateTime.now().withNano(0));

        api.entity().purchaseorder().post(purchaseOrder);

        LocalDateTime time = LocalDateTime.now().withNano(0);
        SupplyDocumentEntity e = api.entity().supply().newDocument("purchaseOrder", purchaseOrder);

        assertEquals("", e.getName());
        assertEquals(purchaseOrder.getVatEnabled(), e.getVatEnabled());
        assertEquals(purchaseOrder.getVatIncluded(), e.getVatIncluded());
        assertEquals(purchaseOrder.getPayedSum(), e.getPayedSum());
        assertEquals(purchaseOrder.getSum(), e.getSum());
        assertFalse(e.getShared());
        assertTrue(e.getApplicable());
        assertEquals(time, e.getMoment().withNano(0));
        assertEquals(purchaseOrder.getMeta().getHref(), e.getPurchaseOrder().getMeta().getHref());
        assertEquals(purchaseOrder.getAgent().getMeta().getHref(), e.getAgent().getMeta().getHref());
        assertEquals(purchaseOrder.getGroup().getMeta().getHref(), e.getGroup().getMeta().getHref());
        assertEquals(purchaseOrder.getOrganization().getMeta().getHref(), e.getOrganization().getMeta().getHref());
        assertEquals(purchaseOrder.getOrganizationAccount().getMeta().getHref(), e.getOrganizationAccount().getMeta().getHref());
    }

    @Test
    public void newByInvoicesInTest() throws IOException, LognexApiException {
        InvoiceInDocumentEntity invoiceIn = new InvoiceInDocumentEntity();
        invoiceIn.setName("invoiceout_" + randomString(3) + "_" + new Date().getTime());
        invoiceIn.setVatEnabled(true);
        invoiceIn.setVatIncluded(true);

        ListEntity<OrganizationEntity> orgList = api.entity().organization().get();
        assertNotEquals(0, orgList.getRows().size());
        invoiceIn.setOrganization(orgList.getRows().get(0));

        CounterpartyEntity agent = new CounterpartyEntity();
        agent.setName(randomString());
        api.entity().counterparty().post(agent);
        invoiceIn.setAgent(agent);

        api.entity().invoicein().post(invoiceIn);

        LocalDateTime time = LocalDateTime.now().withNano(0);
        SupplyDocumentEntity e = api.entity().supply().newDocument("invoicesIn", Collections.singletonList(invoiceIn));

        assertEquals("", e.getName());
        assertEquals(invoiceIn.getVatEnabled(), e.getVatEnabled());
        assertEquals(invoiceIn.getVatIncluded(), e.getVatIncluded());
        assertEquals(invoiceIn.getPayedSum(), e.getPayedSum());
        assertEquals(invoiceIn.getSum(), e.getSum());
        assertFalse(e.getShared());
        assertTrue(e.getApplicable());
        assertEquals(time, e.getMoment().withNano(0));
        assertEquals(1, e.getInvoicesIn().size());
        assertEquals(invoiceIn.getMeta().getHref(), e.getInvoicesIn().get(0).getMeta().getHref());
        assertEquals(invoiceIn.getAgent().getMeta().getHref(), e.getAgent().getMeta().getHref());
        assertEquals(invoiceIn.getGroup().getMeta().getHref(), e.getGroup().getMeta().getHref());
        assertEquals(invoiceIn.getOrganization().getMeta().getHref(), e.getOrganization().getMeta().getHref());
    }

    private SupplyDocumentEntity createSimpleDocumentSupply() throws IOException, LognexApiException {
        SupplyDocumentEntity e = new SupplyDocumentEntity();
        e.setName("supply_" + randomString(3) + "_" + new Date().getTime());
        e.setDescription(randomString());

        ListEntity<OrganizationEntity> orgList = api.entity().organization().get();
        assertNotEquals(0, orgList.getRows().size());
        e.setOrganization(orgList.getRows().get(0));

        CounterpartyEntity agent = new CounterpartyEntity();
        agent.setName(randomString());
        api.entity().counterparty().post(agent);
        e.setAgent(agent);

        ListEntity<StoreEntity> store = api.entity().store().get(filterEq("name", "Основной склад"));
        assertEquals(1, store.getRows().size());
        e.setStore(store.getRows().get(0));

        api.entity().supply().post(e);

        return e;
    }

    private void getAsserts(SupplyDocumentEntity e, SupplyDocumentEntity retrievedEntity) {
        assertEquals(e.getName(), retrievedEntity.getName());
        assertEquals(e.getDescription(), retrievedEntity.getDescription());
        assertEquals(e.getOrganization().getMeta().getHref(), retrievedEntity.getOrganization().getMeta().getHref());
        assertEquals(e.getAgent().getMeta().getHref(), retrievedEntity.getAgent().getMeta().getHref());
        assertEquals(e.getStore().getMeta().getHref(), retrievedEntity.getStore().getMeta().getHref());
        assertEquals(e.getCreated().withNano(0), retrievedEntity.getCreated().withNano(0));
        assertEquals(e.getUpdated().withNano(0), retrievedEntity.getUpdated().withNano(0));
    }

    private void putAsserts(SupplyDocumentEntity e, SupplyDocumentEntity retrievedOriginalEntity, String name) throws IOException, LognexApiException {
        SupplyDocumentEntity retrievedUpdatedEntity = api.entity().supply().get(e.getId());

        assertNotEquals(retrievedOriginalEntity.getName(), retrievedUpdatedEntity.getName());
        assertEquals(name, retrievedUpdatedEntity.getName());
        assertEquals(retrievedOriginalEntity.getDescription(), retrievedUpdatedEntity.getDescription());
        assertEquals(retrievedOriginalEntity.getOrganization().getMeta().getHref(), retrievedUpdatedEntity.getOrganization().getMeta().getHref());
        assertEquals(retrievedOriginalEntity.getAgent().getMeta().getHref(), retrievedUpdatedEntity.getAgent().getMeta().getHref());
        assertEquals(retrievedOriginalEntity.getStore().getMeta().getHref(), retrievedUpdatedEntity.getStore().getMeta().getHref());
        assertEquals(retrievedOriginalEntity.getCreated().withNano(0), retrievedUpdatedEntity.getCreated().withNano(0));
        assertNotEquals(retrievedOriginalEntity.getUpdated().withNano(0), retrievedUpdatedEntity.getUpdated().withNano(0));
    }
}
