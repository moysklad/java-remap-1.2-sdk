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
    public void metadataTest() throws IOException, LognexApiException {
        MetadataAttributeSharedStatesResponse response = api.entity().inventory().metadata().get();

        assertFalse(response.getCreateShared());
    }

    @Override
    protected void getAsserts(MetaEntity originalEntity, MetaEntity retrievedEntity) {
        InventoryDocumentEntity originalInventory = (InventoryDocumentEntity) originalEntity;
        InventoryDocumentEntity retrievedInventory = (InventoryDocumentEntity) retrievedEntity;

        assertEquals(originalInventory.getName(), retrievedInventory.getName());
        assertEquals(originalInventory.getOrganization().getMeta().getHref(), retrievedInventory.getOrganization().getMeta().getHref());
        assertEquals(originalInventory.getStore().getMeta().getHref(), retrievedInventory.getStore().getMeta().getHref());
    }

    @Override
    protected void putAsserts(MetaEntity originalEntity, MetaEntity updatedEntity, Object changedField) {
        InventoryDocumentEntity originalInventory = (InventoryDocumentEntity) originalEntity;
        InventoryDocumentEntity updatedInventory = (InventoryDocumentEntity) updatedEntity;

        assertNotEquals(originalInventory.getName(), updatedInventory.getName());
        assertEquals(changedField, updatedInventory.getName());
        assertEquals(originalInventory.getOrganization().getMeta().getHref(), updatedInventory.getOrganization().getMeta().getHref());
        assertEquals(originalInventory.getStore().getMeta().getHref(), updatedInventory.getStore().getMeta().getHref());
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
