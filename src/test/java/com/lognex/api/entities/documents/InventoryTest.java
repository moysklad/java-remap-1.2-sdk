package com.lognex.api.entities.documents;

import com.lognex.api.clients.EntityClientBase;
import com.lognex.api.clients.documents.DocumentMetadataClient;
import com.lognex.api.entities.MetaEntity;
import com.lognex.api.entities.documents.positions.InventoryDocumentPosition;
import com.lognex.api.responses.ListEntity;
import com.lognex.api.responses.metadata.MetadataAttributeSharedStatesResponse;
import com.lognex.api.utils.ApiClientException;
import org.junit.Test;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Date;

import static com.lognex.api.utils.params.ExpandParam.expand;
import static com.lognex.api.utils.params.FilterParam.filterEq;
import static com.lognex.api.utils.params.LimitParam.limit;
import static org.junit.Assert.*;

public class InventoryTest extends DocumentWithPositionsTestBase {
    @Test
    public void createTest() throws IOException, ApiClientException {
        Inventory inventory = new Inventory();
        inventory.setName("inventory_" + randomString(3) + "_" + new Date().getTime());
        inventory.setMoment(LocalDateTime.now());
        inventory.setOrganization(simpleEntityManager.getOwnOrganization());
        inventory.setStore(simpleEntityManager.getMainStore());
        inventory.setDescription("inventory_description");

        InventoryDocumentPosition position = new InventoryDocumentPosition();
        position.setAssortment(simpleEntityManager.createSimpleProduct());
        position.setQuantity(1.);
        position.setCalculatedQuantity(2.);
        position.setCorrectionAmount(-1.);
        inventory.setPositions(new ListEntity<>(position));

        api.entity().inventory().create(inventory);

        ListEntity<Inventory> updatedEntitiesList = api.entity().inventory().get(
                filterEq("name", inventory.getName()),
                expand("positions"),
                limit(5)
        );
        assertEquals(1, updatedEntitiesList.getRows().size());

        Inventory retrievedEntity = updatedEntitiesList.getRows().get(0);
        assertEquals(inventory.getName(), retrievedEntity.getName());
        assertEquals(inventory.getMoment(), retrievedEntity.getMoment());
        assertEquals(inventory.getOrganization().getMeta().getHref(), retrievedEntity.getOrganization().getMeta().getHref());
        assertEquals(inventory.getStore().getMeta().getHref(), retrievedEntity.getStore().getMeta().getHref());
        assertEquals(inventory.getDescription(), retrievedEntity.getDescription());

        assertEquals(1, retrievedEntity.getPositions().getRows().size());
        InventoryDocumentPosition retrievedPosition = retrievedEntity.getPositions().getRows().get(0);
        assertEquals(position.getCalculatedQuantity(), retrievedPosition.getCalculatedQuantity());
        assertEquals(position.getCorrectionAmount(), retrievedPosition.getCorrectionAmount());
    }

    @Test
    public void metadataTest() throws IOException, ApiClientException {
        DocumentMetadataClient<MetadataAttributeSharedStatesResponse> metadata = api.entity().inventory().metadata();
        MetadataAttributeSharedStatesResponse response = metadata.get();

        assertFalse(response.getCreateShared());
        assertNotNull(metadata.attributes());
    }

    @Override
    protected void getAsserts(MetaEntity originalEntity, MetaEntity retrievedEntity) {
        Inventory originalInventory = (Inventory) originalEntity;
        Inventory retrievedInventory = (Inventory) retrievedEntity;

        assertEquals(originalInventory.getName(), retrievedInventory.getName());
        assertEquals(originalInventory.getOrganization().getMeta().getHref(), retrievedInventory.getOrganization().getMeta().getHref());
        assertEquals(originalInventory.getStore().getMeta().getHref(), retrievedInventory.getStore().getMeta().getHref());
    }

    @Override
    protected void putAsserts(MetaEntity originalEntity, MetaEntity updatedEntity, Object changedField) {
        Inventory originalInventory = (Inventory) originalEntity;
        Inventory updatedInventory = (Inventory) updatedEntity;

        assertNotEquals(originalInventory.getName(), updatedInventory.getName());
        assertEquals(changedField, updatedInventory.getName());
        assertEquals(originalInventory.getOrganization().getMeta().getHref(), updatedInventory.getOrganization().getMeta().getHref());
        assertEquals(originalInventory.getStore().getMeta().getHref(), updatedInventory.getStore().getMeta().getHref());
    }

    @Override
    protected EntityClientBase entityClient() {
        return api.entity().inventory();
    }

    @Override
    protected Class<? extends MetaEntity> entityClass() {
        return Inventory.class;
    }
}
