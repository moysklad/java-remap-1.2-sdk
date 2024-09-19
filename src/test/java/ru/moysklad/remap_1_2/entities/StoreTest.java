package ru.moysklad.remap_1_2.entities;

import org.junit.Test;
import ru.moysklad.remap_1_2.clients.EntityClientBase;
import ru.moysklad.remap_1_2.responses.ListEntity;
import ru.moysklad.remap_1_2.responses.metadata.MetadataAttributeSharedResponse;
import ru.moysklad.remap_1_2.utils.ApiClientException;

import java.io.IOException;
import java.util.Date;

import static ru.moysklad.remap_1_2.utils.params.FilterParam.filterEq;
import static org.junit.Assert.*;

public class StoreTest extends EntityGetUpdateDeleteTest {
    @Test
    public void createTest() throws IOException, ApiClientException {
        Store store = new Store();
        store.setName("store_" + randomString(3) + "_" + new Date().getTime());
        store.setArchived(false);
        store.setDescription(randomString());
        store.setPathName(randomString());
        Address addressFull = randomAddress(api);
        store.setAddressFull(addressFull);

        api.entity().store().create(store);

        ListEntity<Store> updatedEntitiesList = api.entity().store().get(filterEq("name", store.getName()));
        assertEquals(1, updatedEntitiesList.getRows().size());

        Store retrievedEntity = updatedEntitiesList.getRows().get(0);
        assertEquals(store.getName(), retrievedEntity.getName());
        assertEquals(store.getArchived(), retrievedEntity.getArchived());
        assertEquals(store.getDescription(), retrievedEntity.getDescription());
        assertEquals(store.getPathName(), retrievedEntity.getPathName());
        assertAddressFull(addressFull, store.getAddressFull());
    }

    @Test
    public void metadataTest() throws IOException, ApiClientException {
        MetadataAttributeSharedResponse metadata = api.entity().store().metadata();
        assertFalse(metadata.getCreateShared());
    }

    @Test
    public void attributesTest() throws IOException, ApiClientException{
        ListEntity<Attribute> attributes = api.entity().store().metadataAttributes();
        assertNotNull(attributes);
    }

    @Test
    public void createAttributeTest() throws IOException, ApiClientException {
        Attribute attribute = new Attribute();
        attribute.setType(Attribute.Type.textValue);
        String name = "field" + randomString(3) + "_" + new Date().getTime();
        attribute.setName(name);
        attribute.setRequired(false);
        attribute.setShow(false);
        attribute.setDescription("description");
        Attribute created = api.entity().store().createMetadataAttribute(attribute);
        assertNotNull(created);
        assertEquals(name, created.getName());
        assertEquals(Attribute.Type.textValue, created.getType());
        assertFalse(created.getRequired());
        assertFalse(created.getShow());
        assertEquals("description", created.getDescription());
    }

    @Test
    public void updateAttributeTest() throws IOException, ApiClientException {
        Attribute attribute = new Attribute();
        attribute.setEntityType(Meta.Type.PRODUCT);
        attribute.setName("field" + randomString(3) + "_" + new Date().getTime());
        attribute.setRequired(true);
        attribute.setShow(true);
        Attribute created = api.entity().store().createMetadataAttribute(attribute);

        String name = "field" + randomString(3) + "_" + new Date().getTime();
        created.setName(name);
        created.setRequired(false);
        created.setShow(false);
        Attribute updated = api.entity().store().updateMetadataAttribute(created);
        assertNotNull(created);
        assertEquals(name, updated.getName());
        assertNull(updated.getType());
        assertEquals(Meta.Type.PRODUCT, updated.getEntityType());
        assertFalse(updated.getRequired());
        assertFalse(updated.getShow());
    }

    @Test
    public void deleteAttributeTest() throws IOException, ApiClientException{
        Attribute attribute = new Attribute();
        attribute.setEntityType(Meta.Type.PRODUCT);
        attribute.setName("field" + randomString(3) + "_" + new Date().getTime());
        attribute.setRequired(true);
        attribute.setShow(true);
        Attribute created = api.entity().store().createMetadataAttribute(attribute);

        api.entity().store().deleteMetadataAttribute(created);

        try {
            api.entity().store().metadataAttributes(created.getId());
        } catch (ApiClientException e) {
            assertEquals(404, e.getStatusCode());
        }
    }
    
    @Override
    protected void getAsserts(MetaEntity originalEntity, MetaEntity retrievedEntity) {
        Store originalStore = (Store) originalEntity;
        Store retrievedStore = (Store) retrievedEntity;

        assertEquals(originalStore.getName(), retrievedStore.getName());
        assertEquals(originalStore.getDescription(), retrievedStore.getDescription());
    }

    @Override
    protected void putAsserts(MetaEntity originalEntity, MetaEntity updatedEntity, Object changedField) {
        Store originalStore = (Store) originalEntity;
        Store updatedStore = (Store) updatedEntity;

        assertNotEquals(originalStore.getName(), updatedStore.getName());
        assertEquals(changedField, updatedStore.getName());
        assertEquals(originalStore.getDescription(), updatedStore.getDescription());
    }

    @Override
    public EntityClientBase entityClient() {
        return api.entity().store();
    }

    @Override
    public Class<? extends MetaEntity> entityClass() {
        return Store.class;
    }
}
