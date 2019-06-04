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
        StoreEntity e = new StoreEntity();
        e.setName("store_" + randomString(3) + "_" + new Date().getTime());
        e.setArchived(false);
        e.setDescription(randomString());
        e.setPathName(randomString());

        api.entity().store().post(e);

        ListEntity<StoreEntity> updatedEntitiesList = api.entity().store().get(filterEq("name", e.getName()));
        assertEquals(1, updatedEntitiesList.getRows().size());

        StoreEntity retrievedEntity = updatedEntitiesList.getRows().get(0);
        assertEquals(e.getName(), retrievedEntity.getName());
        assertEquals(e.getArchived(), retrievedEntity.getArchived());
        assertEquals(e.getDescription(), retrievedEntity.getDescription());
        assertEquals(e.getPathName(), retrievedEntity.getPathName());
    }

    @Test
    public void getTest() throws IOException, LognexApiException {
        StoreEntity e = createSimpleStore();

        StoreEntity retrievedEntity = api.entity().store().get(e.getId());
        getAsserts(e, retrievedEntity);

        retrievedEntity = api.entity().store().get(e);
        getAsserts(e, retrievedEntity);
    }

    @Test
    public void putTest() throws IOException, LognexApiException, InterruptedException {
        StoreEntity e = createSimpleStore();

        StoreEntity retrievedOriginalEntity = api.entity().store().get(e.getId());
        String name = "store_" + randomString(3) + "_" + new Date().getTime();
        e.setName(name);
        api.entity().store().put(e.getId(), e);
        putAsserts(e, retrievedOriginalEntity, name);

        retrievedOriginalEntity.set(e);

        name = "store_" + randomString(3) + "_" + new Date().getTime();
        e.setName(name);
        api.entity().store().put(e);
        putAsserts(e, retrievedOriginalEntity, name);
    }

    @Test
    public void deleteTest() throws IOException, LognexApiException {
        StoreEntity e = createSimpleStore();

        ListEntity<StoreEntity> entitiesList = api.entity().store().get(filterEq("name", e.getName()));
        assertEquals((Integer) 1, entitiesList.getMeta().getSize());

        api.entity().store().delete(e.getId());

        entitiesList = api.entity().store().get(filterEq("name", e.getName()));
        assertEquals((Integer) 0, entitiesList.getMeta().getSize());
    }

    @Test
    public void deleteByIdTest() throws IOException, LognexApiException {
        StoreEntity e = createSimpleStore();

        ListEntity<StoreEntity> entitiesList = api.entity().store().get(filterEq("name", e.getName()));
        assertEquals((Integer) 1, entitiesList.getMeta().getSize());

        api.entity().store().delete(e);

        entitiesList = api.entity().store().get(filterEq("name", e.getName()));
        assertEquals((Integer) 0, entitiesList.getMeta().getSize());
    }

    @Test
    public void metadataTest() throws IOException, LognexApiException {
        MetadataAttributeSharedResponse metadata = api.entity().store().metadata();
        assertFalse(metadata.getCreateShared());
    }

    private void getAsserts(StoreEntity e, StoreEntity retrievedEntity) {
        assertEquals(e.getName(), retrievedEntity.getName());
        assertEquals(e.getDescription(), retrievedEntity.getDescription());
    }

    private void putAsserts(StoreEntity e, StoreEntity retrievedOriginalEntity, String name) throws IOException, LognexApiException {
        StoreEntity retrievedUpdatedEntity = api.entity().store().get(e.getId());

        assertNotEquals(retrievedOriginalEntity.getName(), retrievedUpdatedEntity.getName());
        assertEquals(name, retrievedUpdatedEntity.getName());
        assertEquals(retrievedOriginalEntity.getDescription(), retrievedUpdatedEntity.getDescription());
    }
}
