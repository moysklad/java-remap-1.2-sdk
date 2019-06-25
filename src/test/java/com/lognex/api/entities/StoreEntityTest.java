package com.lognex.api.entities;

import com.lognex.api.clients.ApiClient;
import com.lognex.api.responses.ListEntity;
import com.lognex.api.responses.metadata.MetadataAttributeSharedResponse;
import com.lognex.api.utils.LognexApiException;
import org.junit.Test;

import java.io.IOException;
import java.util.Date;

import static com.lognex.api.utils.params.FilterParam.filterEq;
import static org.junit.Assert.*;

public class StoreEntityTest extends EntityGetUpdateDeleteTest {
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
    public void metadataTest() throws IOException, LognexApiException {
        MetadataAttributeSharedResponse metadata = api.entity().store().metadata();
        assertFalse(metadata.getCreateShared());
    }

    @Override
    protected void getAsserts(MetaEntity originalEntity, MetaEntity retrievedEntity) {
        StoreEntity originalStore = (StoreEntity) originalEntity;
        StoreEntity retrievedStore = (StoreEntity) retrievedEntity;

        assertEquals(originalStore.getName(), retrievedStore.getName());
        assertEquals(originalStore.getDescription(), retrievedStore.getDescription());
    }

    @Override
    protected void putAsserts(MetaEntity originalEntity, MetaEntity updatedEntity, Object changedField) {
        StoreEntity originalStore = (StoreEntity) originalEntity;
        StoreEntity updatedStore = (StoreEntity) updatedEntity;

        assertNotEquals(originalStore.getName(), updatedStore.getName());
        assertEquals(changedField, updatedStore.getName());
        assertEquals(originalStore.getDescription(), updatedStore.getDescription());
    }

    @Override
    protected ApiClient entityClient() {
        return api.entity().store();
    }

    @Override
    protected Class<? extends MetaEntity> entityClass() {
        return StoreEntity.class;
    }
}
