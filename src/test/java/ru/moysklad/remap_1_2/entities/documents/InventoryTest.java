package ru.moysklad.remap_1_2.entities.documents;

import org.junit.Test;
import ru.moysklad.remap_1_2.clients.EntityClientBase;
import ru.moysklad.remap_1_2.entities.*;
import ru.moysklad.remap_1_2.entities.Meta;
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
        MetadataAttributeSharedStatesResponse<DocumentAttribute> response = api.entity().inventory().metadata().get();

        assertFalse(response.getCreateShared());
    }

    @Test
    public void attributesTest() throws IOException, ApiClientException{
        ListEntity<Attribute> attributes = api.entity().inventory().metadataAttributes();
        assertNotNull(attributes);
    }

    @Test
    public void createAttributeTest() throws IOException, ApiClientException {
        DocumentAttribute attribute = new DocumentAttribute();
        attribute.setType(DocumentAttribute.Type.textValue);
        String name = "field" + randomString(3) + "_" + new Date().getTime();
        attribute.setName(name);
        attribute.setRequired(false);
        attribute.setShow(true);
        attribute.setDescription("description");
        DocumentAttribute created = api.entity().inventory().createMetadataAttribute(attribute);
        assertNotNull(created);
        assertEquals(name, created.getName());
        assertEquals(DocumentAttribute.Type.textValue, created.getType());
        assertFalse(created.getRequired());
        assertTrue(created.getShow());
        assertEquals("description", created.getDescription());
    }

    @Test
    public void updateAttributeTest() throws IOException, ApiClientException {
        DocumentAttribute attribute = new DocumentAttribute();
        attribute.setEntityType(Meta.Type.PRODUCT);
        attribute.setName("field" + randomString(3) + "_" + new Date().getTime());
        attribute.setRequired(true);
        attribute.setShow(true);
        DocumentAttribute created = api.entity().inventory().createMetadataAttribute(attribute);

        String name = "field" + randomString(3) + "_" + new Date().getTime();
        created.setName(name);
        created.setRequired(false);
        created.setShow(false);
        DocumentAttribute updated = api.entity().inventory().updateMetadataAttribute(created);
        assertNotNull(created);
        assertEquals(name, updated.getName());
        assertNull(updated.getType());
        assertEquals(Meta.Type.PRODUCT, updated.getEntityType());
        assertFalse(updated.getRequired());
        assertFalse(updated.getShow());
    }

    @Test
    public void deleteAttributeTest() throws IOException, ApiClientException{
        DocumentAttribute attribute = new DocumentAttribute();
        attribute.setEntityType(Meta.Type.PRODUCT);
        attribute.setName("field" + randomString(3) + "_" + new Date().getTime());
        attribute.setRequired(true);
        attribute.setShow(true);
        DocumentAttribute created = api.entity().inventory().createMetadataAttribute(attribute);

        api.entity().inventory().deleteMetadataAttribute(created);

        try {
            api.entity().inventory().metadataAttributes(created.getId());
        } catch (ApiClientException e) {
            assertEquals(404, e.getStatusCode());
        }
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
    public EntityClientBase entityClient() {
        return api.entity().inventory();
    }

    @Override
    public Class<? extends MetaEntity> entityClass() {
        return Inventory.class;
    }
}
