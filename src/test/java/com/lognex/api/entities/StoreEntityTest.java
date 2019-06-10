package com.lognex.api.entities;

import com.lognex.api.responses.ListEntity;
import com.lognex.api.responses.metadata.MetadataAttributeSharedResponse;
import com.lognex.api.utils.LognexApiException;
import org.junit.Test;

import java.io.IOException;
import java.util.Date;

import static com.lognex.api.utils.params.FilterParam.filterEq;
import static org.junit.Assert.*;

public class StoreEntityTest extends EntityTestBase {
    @Test
    public void createTest() throws IOException, LognexApiException {
        StoreEntity store = new StoreEntity();
        store.setName("store_" + randomString(3) + "_" + new Date().getTime());
        store.setArchived(false);
        store.setDescription(randomString());
        store.setPathName(randomString());

        api.entity().store().post(store);

        ListEntity<StoreEntity> updatedEntitiesList = api.entity().store().get(filterEq("name", store.getName()));
        assertEquals(1, updatedEntitiesList.getRows().size());

        StoreEntity retrievedEntity = updatedEntitiesList.getRows().get(0);
        assertEquals(store.getName(), retrievedEntity.getName());
        assertEquals(store.getArchived(), retrievedEntity.getArchived());
        assertEquals(store.getDescription(), retrievedEntity.getDescription());
        assertEquals(store.getPathName(), retrievedEntity.getPathName());
    }

    @Test
    public void getTest() throws IOException, LognexApiException {
        StoreEntity store = simpleEntityFactory.createSimpleStore();

        StoreEntity retrievedEntity = api.entity().store().get(store.getId());
        getAsserts(store, retrievedEntity);

        retrievedEntity = api.entity().store().get(store);
        getAsserts(store, retrievedEntity);
    }

    @Test
    public void putTest() throws IOException, LognexApiException {
        StoreEntity store = simpleEntityFactory.createSimpleStore();

        StoreEntity retrievedOriginalEntity = api.entity().store().get(store.getId());
        String name = "store_" + randomString(3) + "_" + new Date().getTime();
        store.setName(name);
        api.entity().store().put(store.getId(), store);
        putAsserts(store, retrievedOriginalEntity, name);

        retrievedOriginalEntity.set(store);

        name = "store_" + randomString(3) + "_" + new Date().getTime();
        store.setName(name);
        api.entity().store().put(store);
        putAsserts(store, retrievedOriginalEntity, name);
    }

    @Test
    public void deleteTest() throws IOException, LognexApiException {
        StoreEntity store = simpleEntityFactory.createSimpleStore();

        ListEntity<StoreEntity> entitiesList = api.entity().store().get(filterEq("name", store.getName()));
        assertEquals((Integer) 1, entitiesList.getMeta().getSize());

        api.entity().store().delete(store.getId());

        entitiesList = api.entity().store().get(filterEq("name", store.getName()));
        assertEquals((Integer) 0, entitiesList.getMeta().getSize());
    }

    @Test
    public void deleteByIdTest() throws IOException, LognexApiException {
        StoreEntity store = simpleEntityFactory.createSimpleStore();

        ListEntity<StoreEntity> entitiesList = api.entity().store().get(filterEq("name", store.getName()));
        assertEquals((Integer) 1, entitiesList.getMeta().getSize());

        api.entity().store().delete(store);

        entitiesList = api.entity().store().get(filterEq("name", store.getName()));
        assertEquals((Integer) 0, entitiesList.getMeta().getSize());
    }

    @Test
    public void metadataTest() throws IOException, LognexApiException {
        MetadataAttributeSharedResponse metadata = api.entity().store().metadata();
        assertFalse(metadata.getCreateShared());
    }

    private void getAsserts(StoreEntity store, StoreEntity retrievedEntity) {
        assertEquals(store.getName(), retrievedEntity.getName());
        assertEquals(store.getDescription(), retrievedEntity.getDescription());
    }

    private void putAsserts(StoreEntity store, StoreEntity retrievedOriginalEntity, String name) throws IOException, LognexApiException {
        StoreEntity retrievedUpdatedEntity = api.entity().store().get(store.getId());

        assertNotEquals(retrievedOriginalEntity.getName(), retrievedUpdatedEntity.getName());
        assertEquals(name, retrievedUpdatedEntity.getName());
        assertEquals(retrievedOriginalEntity.getDescription(), retrievedUpdatedEntity.getDescription());
    }
}
