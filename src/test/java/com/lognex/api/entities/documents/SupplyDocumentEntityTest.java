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
