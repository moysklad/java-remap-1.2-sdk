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
import java.util.Comparator;
import java.util.Date;
import java.util.Optional;

import static com.lognex.api.utils.params.FilterParam.filterEq;
import static com.lognex.api.utils.params.SearchParam.search;
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

    @Test
    public void newTest() throws IOException, LognexApiException {
        LocalDateTime time = LocalDateTime.now().withNano(0);
        PurchaseReturnDocumentEntity e = api.entity().purchasereturn().newDocument();

        assertEquals("", e.getName());
        assertTrue(e.getVatEnabled());
        assertTrue(e.getVatIncluded());
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
    public void newBySupplyTest() throws IOException, LognexApiException {
        SupplyDocumentEntity supply = new SupplyDocumentEntity();
        supply.setName("supply_" + randomString(3) + "_" + new Date().getTime());

        ListEntity<OrganizationEntity> orgList = api.entity().organization().get();
        assertNotEquals(0, orgList.getRows().size());
        supply.setOrganization(orgList.getRows().get(0));

        CounterpartyEntity agent = new CounterpartyEntity();
        agent.setName(randomString());
        api.entity().counterparty().post(agent);
        supply.setAgent(agent);

        ListEntity<StoreEntity> store = api.entity().store().get(filterEq("name", "Основной склад"));
        assertEquals(1, store.getRows().size());
        supply.setStore(store.getRows().get(0));

        api.entity().supply().post(supply);

        LocalDateTime time = LocalDateTime.now().withNano(0);
        PurchaseReturnDocumentEntity e = api.entity().purchasereturn().newDocument("supply", supply);

        assertEquals("", e.getName());
        assertEquals(supply.getVatEnabled(), e.getVatEnabled());
        assertEquals(supply.getVatIncluded(), e.getVatIncluded());
        assertEquals(supply.getPayedSum(), e.getPayedSum());
        assertEquals(supply.getSum(), e.getSum());
        assertFalse(e.getShared());
        assertTrue(e.getApplicable());
        assertEquals(time, e.getMoment().withNano(0));
        assertEquals(supply.getMeta().getHref(), e.getSupply().getMeta().getHref());
        assertEquals(supply.getAgent().getMeta().getHref(), e.getAgent().getMeta().getHref());
        assertEquals(supply.getStore().getMeta().getHref(), e.getStore().getMeta().getHref());
        assertEquals(supply.getGroup().getMeta().getHref(), e.getGroup().getMeta().getHref());
        assertEquals(supply.getOrganization().getMeta().getHref(), e.getOrganization().getMeta().getHref());
        assertEquals(supply.getOrganizationAccount().getMeta().getHref(), e.getOrganizationAccount().getMeta().getHref());
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
