package ru.moysklad.remap_1_2.entities.documents;

import org.junit.Test;
import ru.moysklad.remap_1_2.clients.EntityClientBase;
import ru.moysklad.remap_1_2.entities.Attribute;
import ru.moysklad.remap_1_2.entities.MetaEntity;
import ru.moysklad.remap_1_2.entities.State;
import ru.moysklad.remap_1_2.entities.documents.positions.InventoryDocumentPosition;
import ru.moysklad.remap_1_2.responses.ListEntity;
import ru.moysklad.remap_1_2.responses.metadata.MetadataAttributeSharedStatesResponse;
import ru.moysklad.remap_1_2.utils.ApiClientException;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import static ru.moysklad.remap_1_2.utils.params.ExpandParam.expand;
import static ru.moysklad.remap_1_2.utils.params.FilterParam.filterEq;
import static ru.moysklad.remap_1_2.utils.params.LimitParam.limit;
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
        MetadataAttributeSharedStatesResponse response = api.entity().inventory().metadata().get();

        assertFalse(response.getCreateShared());
    }

    @Test
    public void attributesTest() throws IOException, ApiClientException {
        ListEntity<Attribute> attributes = api.entity().inventory().metadataAttributes();
        assertNotNull(attributes);
    }

    @Test
    public void createStateTest() throws IOException, ApiClientException {
        State state = new State();
        state.setName("state_" + randomStringTail());
        state.setStateType(State.StateType.regular);
        state.setColor(randomColor());

        api.entity().inventory().states().create(state);

        List<State> retrievedStates = api.entity().inventory().metadata().get().getStates();

        State retrievedState = retrievedStates.stream().filter(s -> s.getId().equals(state.getId())).findFirst().orElse(null);
        assertNotNull(retrievedState);
        assertEquals(state.getName(), retrievedState.getName());
        assertEquals(state.getStateType(), retrievedState.getStateType());
        assertEquals(state.getColor(), retrievedState.getColor());
        assertEquals(state.getEntityType(), retrievedState.getEntityType());
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
