package com.lognex.api.entities.documents;

import com.lognex.api.entities.EntityTestBase;
import com.lognex.api.entities.StoreEntity;
import com.lognex.api.entities.agents.CounterpartyEntity;
import com.lognex.api.entities.agents.OrganizationEntity;
import com.lognex.api.responses.ListEntity;
import com.lognex.api.responses.metadata.MetadataAttributeSharedStatesResponse;
import com.lognex.api.utils.LognexApiException;
import org.junit.Test;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Date;

import static com.lognex.api.utils.params.FilterParam.filterEq;
import static org.junit.Assert.*;

public class PurchaseReturnDocumentEntityTest extends EntityTestBase {
    @Test
    public void createTest() throws IOException, LognexApiException {
        PurchaseReturnDocumentEntity e = new PurchaseReturnDocumentEntity();
        e.setName("purchasereturn_" + randomString(3) + "_" + new Date().getTime());
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

        SupplyDocumentEntity supply = new SupplyDocumentEntity();
        supply.setName("supply_" + randomString(3) + "_" + new Date().getTime());
        supply.setOrganization(orgList.getRows().get(0));
        supply.setAgent(agent);
        supply.setStore(store.getRows().get(0));

        api.entity().supply().post(supply);
        e.setSupply(supply);

        api.entity().purchasereturn().post(e);

        ListEntity<PurchaseReturnDocumentEntity> updatedEntitiesList = api.entity().purchasereturn().get(filterEq("name", e.getName()));
        assertEquals(1, updatedEntitiesList.getRows().size());

        PurchaseReturnDocumentEntity retrievedEntity = updatedEntitiesList.getRows().get(0);
        assertEquals(e.getName(), retrievedEntity.getName());
        assertEquals(e.getDescription(), retrievedEntity.getDescription());
        assertEquals(e.getVatEnabled(), retrievedEntity.getVatEnabled());
        assertEquals(e.getVatIncluded(), retrievedEntity.getVatIncluded());
        assertEquals(e.getMoment(), retrievedEntity.getMoment());
        assertEquals(e.getOrganization().getMeta().getHref(), retrievedEntity.getOrganization().getMeta().getHref());
        assertEquals(e.getAgent().getMeta().getHref(), retrievedEntity.getAgent().getMeta().getHref());
        assertEquals(e.getStore().getMeta().getHref(), retrievedEntity.getStore().getMeta().getHref());
        assertEquals(e.getSupply().getMeta().getHref(), retrievedEntity.getSupply().getMeta().getHref());
        assertEquals(e.getCreated().withNano(0), retrievedEntity.getCreated().withNano(0));
        assertEquals(e.getUpdated().withNano(0), retrievedEntity.getUpdated().withNano(0));
    }

    @Test
    public void getTest() throws IOException, LognexApiException {
        PurchaseReturnDocumentEntity e = createSimpleDocumentPurchaseReturn();

        PurchaseReturnDocumentEntity retrievedEntity = api.entity().purchasereturn().get(e.getId());
        getAsserts(e, retrievedEntity);

        retrievedEntity = api.entity().purchasereturn().get(e);
        getAsserts(e, retrievedEntity);
    }

    @Test
    public void putTest() throws IOException, LognexApiException, InterruptedException {
        PurchaseReturnDocumentEntity e = createSimpleDocumentPurchaseReturn();

        PurchaseReturnDocumentEntity retrievedOriginalEntity = api.entity().purchasereturn().get(e.getId());
        String name = "purchasereturn_" + randomString(3) + "_" + new Date().getTime();
        e.setName(name);
        Thread.sleep(1500);
        api.entity().purchasereturn().put(e.getId(), e);
        putAsserts(e, retrievedOriginalEntity, name);

        retrievedOriginalEntity.set(e);

        name = "purchasereturn_" + randomString(3) + "_" + new Date().getTime();
        e.setName(name);
        Thread.sleep(1500);
        api.entity().purchasereturn().put(e);
        putAsserts(e, retrievedOriginalEntity, name);
    }

    @Test
    public void deleteTest() throws IOException, LognexApiException {
        PurchaseReturnDocumentEntity e = createSimpleDocumentPurchaseReturn();

        ListEntity<PurchaseReturnDocumentEntity> entitiesList = api.entity().purchasereturn().get(filterEq("name", e.getName()));
        assertEquals(1, entitiesList.getRows().size());

        api.entity().purchasereturn().delete(e.getId());

        entitiesList = api.entity().purchasereturn().get(filterEq("name", e.getName()));
        assertEquals(0, entitiesList.getRows().size());
    }

    @Test
    public void deleteByIdTest() throws IOException, LognexApiException {
        PurchaseReturnDocumentEntity e = createSimpleDocumentPurchaseReturn();

        ListEntity<PurchaseReturnDocumentEntity> entitiesList = api.entity().purchasereturn().get(filterEq("name", e.getName()));
        assertEquals(1, entitiesList.getRows().size());

        api.entity().purchasereturn().delete(e);

        entitiesList = api.entity().purchasereturn().get(filterEq("name", e.getName()));
        assertEquals(0, entitiesList.getRows().size());
    }

    @Test
    public void metadataTest() throws IOException, LognexApiException {
        MetadataAttributeSharedStatesResponse response = api.entity().purchasereturn().metadata().get();

        assertFalse(response.getCreateShared());
    }

    private PurchaseReturnDocumentEntity createSimpleDocumentPurchaseReturn() throws IOException, LognexApiException {
        PurchaseReturnDocumentEntity e = new PurchaseReturnDocumentEntity();
        e.setName("purchasereturn_" + randomString(3) + "_" + new Date().getTime());

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

        api.entity().purchasereturn().post(e);

        return e;
    }

    private void getAsserts(PurchaseReturnDocumentEntity e, PurchaseReturnDocumentEntity retrievedEntity) {
        assertEquals(e.getName(), retrievedEntity.getName());
        assertEquals(e.getOrganization().getMeta().getHref(), retrievedEntity.getOrganization().getMeta().getHref());
        assertEquals(e.getAgent().getMeta().getHref(), retrievedEntity.getAgent().getMeta().getHref());
        assertEquals(e.getStore().getMeta().getHref(), retrievedEntity.getStore().getMeta().getHref());
        assertEquals(e.getCreated().withNano(0), retrievedEntity.getCreated().withNano(0));
        assertEquals(e.getUpdated().withNano(0), retrievedEntity.getUpdated().withNano(0));
    }

    private void putAsserts(PurchaseReturnDocumentEntity e, PurchaseReturnDocumentEntity retrievedOriginalEntity, String name) throws IOException, LognexApiException {
        PurchaseReturnDocumentEntity retrievedUpdatedEntity = api.entity().purchasereturn().get(e.getId());

        assertNotEquals(retrievedOriginalEntity.getName(), retrievedUpdatedEntity.getName());
        assertEquals(name, retrievedUpdatedEntity.getName());
        assertEquals(retrievedOriginalEntity.getOrganization().getMeta().getHref(), retrievedUpdatedEntity.getOrganization().getMeta().getHref());
        assertEquals(retrievedOriginalEntity.getAgent().getMeta().getHref(), retrievedUpdatedEntity.getAgent().getMeta().getHref());
        assertEquals(retrievedOriginalEntity.getStore().getMeta().getHref(), retrievedUpdatedEntity.getStore().getMeta().getHref());
        assertEquals(retrievedOriginalEntity.getCreated().withNano(0), retrievedUpdatedEntity.getCreated().withNano(0));
        assertNotEquals(retrievedOriginalEntity.getUpdated().withNano(0), retrievedUpdatedEntity.getUpdated().withNano(0));
    }
}
