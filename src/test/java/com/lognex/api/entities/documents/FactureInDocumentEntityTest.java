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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.lognex.api.utils.params.FilterParam.filterEq;
import static org.junit.Assert.*;

public class FactureInDocumentEntityTest extends EntityTestBase {
    @Test
    public void createTest() throws IOException, LognexApiException {
        FactureInDocumentEntity e = new FactureInDocumentEntity();
        e.setName("facturein_" + randomString(3) + "_" + new Date().getTime());
        e.setDescription(randomString());
        e.setMoment(LocalDateTime.now());

        e.setIncomingNumber(randomString());
        e.setIncomingDate(LocalDateTime.now());

        List<SupplyDocumentEntity> supplies = new ArrayList<>();
        SupplyDocumentEntity supply = new SupplyDocumentEntity();
        supply.setName("supply_" + randomString(3) + "_" + new Date().getTime());
        supply.setDescription(randomString());

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
        supplies.add(supply);
        e.setSupplies(supplies);

        api.entity().facturein().post(e);

        ListEntity<FactureInDocumentEntity> updatedEntitiesList = api.entity().facturein().get(filterEq("name", e.getName()));
        assertEquals(1, updatedEntitiesList.getRows().size());

        FactureInDocumentEntity retrievedEntity = updatedEntitiesList.getRows().get(0);
        assertEquals(e.getName(), retrievedEntity.getName());
        assertEquals(e.getDescription(), retrievedEntity.getDescription());
        assertEquals(e.getMoment(), retrievedEntity.getMoment());
        assertEquals(e.getIncomingNumber(), retrievedEntity.getIncomingNumber());
        assertEquals(e.getIncomingDate(), retrievedEntity.getIncomingDate());
        assertEquals(e.getOrganization().getMeta().getHref(), retrievedEntity.getOrganization().getMeta().getHref());
        assertEquals(e.getSupplies().get(0).getMeta().getHref(), retrievedEntity.getSupplies().get(0).getMeta().getHref());
        assertEquals(e.getCreated().withNano(0), retrievedEntity.getCreated().withNano(0));
        assertEquals(e.getUpdated().withNano(0), retrievedEntity.getUpdated().withNano(0));
    }

    @Test
    public void getTest() throws IOException, LognexApiException {
        FactureInDocumentEntity e = createSimpleDocumentFactureIn();

        FactureInDocumentEntity retrievedEntity = api.entity().facturein().get(e.getId());
        getAsserts(e, retrievedEntity);

        retrievedEntity = api.entity().facturein().get(e);
        getAsserts(e, retrievedEntity);
    }

    @Test
    public void putTest() throws IOException, LognexApiException, InterruptedException {
        FactureInDocumentEntity e = createSimpleDocumentFactureIn();

        FactureInDocumentEntity retrievedOriginalEntity = api.entity().facturein().get(e.getId());
        String name = "facturein_" + randomString(3) + "_" + new Date().getTime();
        e.setName(name);
        Thread.sleep(1500);
        api.entity().facturein().put(e.getId(), e);
        putAsserts(e, retrievedOriginalEntity, name);

        retrievedOriginalEntity.set(e);

        name = "facturein_" + randomString(3) + "_" + new Date().getTime();
        e.setName(name);
        Thread.sleep(1500);
        api.entity().facturein().put(e);
        putAsserts(e, retrievedOriginalEntity, name);
    }

    @Test
    public void deleteTest() throws IOException, LognexApiException {
        FactureInDocumentEntity e = createSimpleDocumentFactureIn();

        ListEntity<FactureInDocumentEntity> entitiesList = api.entity().facturein().get(filterEq("name", e.getName()));
        assertEquals(1, entitiesList.getRows().size());

        api.entity().facturein().delete(e.getId());

        entitiesList = api.entity().facturein().get(filterEq("name", e.getName()));
        assertEquals(0, entitiesList.getRows().size());
    }

    @Test
    public void deleteByIdTest() throws IOException, LognexApiException {
        FactureInDocumentEntity e = createSimpleDocumentFactureIn();

        ListEntity<FactureInDocumentEntity> entitiesList = api.entity().facturein().get(filterEq("name", e.getName()));
        assertEquals(1, entitiesList.getRows().size());

        api.entity().facturein().delete(e);

        entitiesList = api.entity().facturein().get(filterEq("name", e.getName()));
        assertEquals(0, entitiesList.getRows().size());
    }

    @Test
    public void metadataTest() throws IOException, LognexApiException {
        MetadataAttributeSharedStatesResponse response = api.entity().facturein().metadata().get();

        assertFalse(response.getCreateShared());
    }

    private FactureInDocumentEntity createSimpleDocumentFactureIn() throws IOException, LognexApiException {
        FactureInDocumentEntity e = new FactureInDocumentEntity();
        e.setName("facturein_" + randomString(3) + "_" + new Date().getTime());

        e.setIncomingNumber(randomString());
        e.setIncomingDate(LocalDateTime.now());

        List<SupplyDocumentEntity> supplies = new ArrayList<>();
        SupplyDocumentEntity supply = new SupplyDocumentEntity();
        supply.setName("supply_" + randomString(3) + "_" + new Date().getTime());
        supply.setDescription(randomString());

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
        supplies.add(supply);
        e.setSupplies(supplies);

        api.entity().facturein().post(e);

        return e;
    }

    private void getAsserts(FactureInDocumentEntity e, FactureInDocumentEntity retrievedEntity) {
        assertEquals(e.getName(), retrievedEntity.getName());
        assertEquals(e.getIncomingNumber(), retrievedEntity.getIncomingNumber());
        assertEquals(e.getIncomingDate(), retrievedEntity.getIncomingDate());
        assertEquals(e.getOrganization().getMeta().getHref(), retrievedEntity.getOrganization().getMeta().getHref());
        assertEquals(e.getSupplies().get(0).getMeta().getHref(), retrievedEntity.getSupplies().get(0).getMeta().getHref());
        assertEquals(e.getCreated().withNano(0), retrievedEntity.getCreated().withNano(0));
        assertEquals(e.getUpdated().withNano(0), retrievedEntity.getUpdated().withNano(0));
    }

    private void putAsserts(FactureInDocumentEntity e, FactureInDocumentEntity retrievedOriginalEntity, String name) throws IOException, LognexApiException {
        FactureInDocumentEntity retrievedUpdatedEntity = api.entity().facturein().get(e.getId());

        assertNotEquals(retrievedOriginalEntity.getName(), retrievedUpdatedEntity.getName());
        assertEquals(name, retrievedUpdatedEntity.getName());
        assertEquals(retrievedOriginalEntity.getIncomingNumber(), retrievedUpdatedEntity.getIncomingNumber());
        assertEquals(retrievedOriginalEntity.getIncomingDate(), retrievedUpdatedEntity.getIncomingDate());
        assertEquals(retrievedOriginalEntity.getOrganization().getMeta().getHref(), retrievedUpdatedEntity.getOrganization().getMeta().getHref());
        assertEquals(retrievedOriginalEntity.getSupplies().get(0).getMeta().getHref(), retrievedUpdatedEntity.getSupplies().get(0).getMeta().getHref());
        assertEquals(retrievedOriginalEntity.getCreated().withNano(0), retrievedUpdatedEntity.getCreated().withNano(0));
        assertNotEquals(retrievedOriginalEntity.getUpdated().withNano(0), retrievedUpdatedEntity.getUpdated().withNano(0));
    }
}
