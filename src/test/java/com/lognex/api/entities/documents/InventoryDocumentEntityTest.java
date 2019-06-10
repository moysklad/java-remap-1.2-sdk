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

public class InventoryDocumentEntityTest extends DocumentWithPositionsTestBase {
    @Test
    public void createTest() throws IOException, LognexApiException {
        InventoryDocumentEntity inventory = new InventoryDocumentEntity();
        inventory.setName("inventory_" + randomString(3) + "_" + new Date().getTime());
        inventory.setMoment(LocalDateTime.now());
        inventory.setOrganization(simpleEntityFactory.getOwnOrganization());
        inventory.setStore(simpleEntityFactory.getMainStore());

        api.entity().inventory().post(inventory);

        ListEntity<InventoryDocumentEntity> updatedEntitiesList = api.entity().inventory().get(filterEq("name", inventory.getName()));
        assertEquals(1, updatedEntitiesList.getRows().size());

        InventoryDocumentEntity retrievedEntity = updatedEntitiesList.getRows().get(0);
        assertEquals(inventory.getName(), retrievedEntity.getName());
        assertEquals(inventory.getMoment(), retrievedEntity.getMoment());
        assertEquals(inventory.getOrganization().getMeta().getHref(), retrievedEntity.getOrganization().getMeta().getHref());
        assertEquals(inventory.getStore().getMeta().getHref(), retrievedEntity.getStore().getMeta().getHref());
    }

    @Test
    public void getTest() throws IOException, LognexApiException {
        InventoryDocumentEntity inventory = simpleEntityFactory.createSimpleInventory();

        InventoryDocumentEntity retrievedEntity = api.entity().inventory().get(inventory.getId());
        getAsserts(inventory, retrievedEntity);

        retrievedEntity = api.entity().inventory().get(inventory);
        getAsserts(inventory, retrievedEntity);
    }

    @Test
    public void putTest() throws IOException, LognexApiException {
        InventoryDocumentEntity inventory = simpleEntityFactory.createSimpleInventory();

        InventoryDocumentEntity retrievedOriginalEntity = api.entity().inventory().get(inventory.getId());
        String name = "inventory_" + randomString(3) + "_" + new Date().getTime();
        inventory.setName(name);
        api.entity().inventory().put(inventory.getId(), inventory);
        putAsserts(inventory, retrievedOriginalEntity, name);

        retrievedOriginalEntity.set(inventory);

        name = "inventory_" + randomString(3) + "_" + new Date().getTime();
        inventory.setName(name);
        api.entity().inventory().put(inventory);
        putAsserts(inventory, retrievedOriginalEntity, name);
    }

    @Test
    public void deleteTest() throws IOException, LognexApiException {
        InventoryDocumentEntity inventory = simpleEntityFactory.createSimpleInventory();

        ListEntity<InventoryDocumentEntity> entitiesList = api.entity().inventory().get(filterEq("name", inventory.getName()));
        assertEquals((Integer) 1, entitiesList.getMeta().getSize());

        api.entity().inventory().delete(inventory.getId());

        entitiesList = api.entity().inventory().get(filterEq("name", inventory.getName()));
        assertEquals((Integer) 0, entitiesList.getMeta().getSize());
    }

    @Test
    public void deleteByIdTest() throws IOException, LognexApiException {
        InventoryDocumentEntity inventory = simpleEntityFactory.createSimpleInventory();

        ListEntity<InventoryDocumentEntity> entitiesList = api.entity().inventory().get(filterEq("name", inventory.getName()));
        assertEquals((Integer) 1, entitiesList.getMeta().getSize());

        api.entity().inventory().delete(inventory);

        entitiesList = api.entity().inventory().get(filterEq("name", inventory.getName()));
        assertEquals((Integer) 0, entitiesList.getMeta().getSize());
    }

    @Test
    public void metadataTest() throws IOException, LognexApiException {
        MetadataAttributeSharedStatesResponse response = api.entity().inventory().metadata().get();

        assertFalse(response.getCreateShared());
    }

    private void getAsserts(InventoryDocumentEntity inventory, InventoryDocumentEntity retrievedEntity) {
        assertEquals(inventory.getName(), retrievedEntity.getName());
        assertEquals(inventory.getOrganization().getMeta().getHref(), retrievedEntity.getOrganization().getMeta().getHref());
        assertEquals(inventory.getStore().getMeta().getHref(), retrievedEntity.getStore().getMeta().getHref());
    }

    private void putAsserts(InventoryDocumentEntity inventory, InventoryDocumentEntity retrievedOriginalEntity, String name) throws IOException, LognexApiException {
        InventoryDocumentEntity retrievedUpdatedEntity = api.entity().inventory().get(inventory.getId());

        assertNotEquals(retrievedOriginalEntity.getName(), retrievedUpdatedEntity.getName());
        assertEquals(name, retrievedUpdatedEntity.getName());
        assertEquals(retrievedOriginalEntity.getOrganization().getMeta().getHref(), retrievedUpdatedEntity.getOrganization().getMeta().getHref());
        assertEquals(retrievedOriginalEntity.getStore().getMeta().getHref(), retrievedUpdatedEntity.getStore().getMeta().getHref());
    }

    @Override
    protected ApiClient entityClient() {
        return api.entity().inventory();
    }

    @Override
    protected Class<? extends MetaEntity> entityClass() {
        return InventoryDocumentEntity.class;
    }
}
