package com.lognex.api.entities;

import com.lognex.api.responses.ListEntity;
import com.lognex.api.utils.LognexApiException;
import org.junit.Test;

import java.io.IOException;
import java.util.Date;

import static com.lognex.api.utils.params.ExpandParam.expand;
import static com.lognex.api.utils.params.FilterParam.filterEq;
import static com.lognex.api.utils.params.LimitParam.limit;
import static org.junit.Assert.*;

public class ProductFolderEntityTest extends EntityTestBase {
    @Test
    public void createTest() throws IOException, LognexApiException {
        ProductFolderEntity inner = new ProductFolderEntity();
        inner.setName("innerproductfolder_" + randomString(3) + "_" + new Date().getTime());
        inner.setArchived(false);

        ProductFolderEntity outer = new ProductFolderEntity();
        outer.setName("outerproductfolder_" + randomString(3) + "_" + new Date().getTime());

        api.entity().productfolder().post(outer);
        inner.setProductFolder(outer);
        api.entity().productfolder().post(inner);

        ListEntity<ProductFolderEntity> updatedEntitiesList = api.entity().productfolder().
                get(limit(20), filterEq("name", inner.getName()), expand("productFolder"));
        assertEquals(1, updatedEntitiesList.getRows().size());

        ProductFolderEntity retrievedEntity = updatedEntitiesList.getRows().get(0);
        assertEquals(inner.getName(), retrievedEntity.getName());
        assertEquals(inner.getArchived(), retrievedEntity.getArchived());
        assertEquals(outer.getName(), retrievedEntity.getProductFolder().getName());
    }

    @Test
    public void getTest() throws IOException, LognexApiException {
        ProductFolderEntity e = createSimpleProductFolder();

        ProductFolderEntity retrievedEntity = api.entity().productfolder().get(e.getId());
        getAsserts(e, retrievedEntity);

        retrievedEntity = api.entity().productfolder().get(e);
        getAsserts(e, retrievedEntity);
    }

    @Test
    public void putTest() throws IOException, LognexApiException, InterruptedException {
        ProductFolderEntity e = createSimpleProductFolder();

        ProductFolderEntity retrievedOriginalEntity = api.entity().productfolder().get(e.getId());
        String name = "productfolder_" + randomString(3) + "_" + new Date().getTime();
        e.setName(name);
        api.entity().productfolder().put(e.getId(), e);
        putAsserts(e, retrievedOriginalEntity, name);

        e = createSimpleProductFolder();
        retrievedOriginalEntity = api.entity().productfolder().get(e.getId());
        name = "productfolder_" + randomString(3) + "_" + new Date().getTime();
        e.setName(name);
        api.entity().productfolder().put(e);
        putAsserts(e, retrievedOriginalEntity, name);
    }

    @Test
    public void deleteTest() throws IOException, LognexApiException {
        ProductFolderEntity e = createSimpleProductFolder();

        ListEntity<ProductFolderEntity> entitiesList = api.entity().productfolder().get(filterEq("name", e.getName()));
        assertEquals((Integer) 1, entitiesList.getMeta().getSize());

        api.entity().productfolder().delete(e.getId());

        entitiesList = api.entity().productfolder().get(filterEq("name", e.getName()));
        assertEquals((Integer) 0, entitiesList.getMeta().getSize());
    }

    @Test
    public void deleteByIdTest() throws IOException, LognexApiException {
        ProductFolderEntity e = createSimpleProductFolder();

        ListEntity<ProductFolderEntity> entitiesList = api.entity().productfolder().get(filterEq("name", e.getName()));
        assertEquals((Integer) 1, entitiesList.getMeta().getSize());

        api.entity().productfolder().delete(e);

        entitiesList = api.entity().productfolder().get(filterEq("name", e.getName()));
        assertEquals((Integer) 0, entitiesList.getMeta().getSize());
    }

    private void getAsserts(ProductFolderEntity e, ProductFolderEntity retrievedEntity) {
        assertEquals(e.getName(), retrievedEntity.getName());
        assertEquals(e.getDescription(), retrievedEntity.getDescription());
    }

    private void putAsserts(ProductFolderEntity e, ProductFolderEntity retrievedOriginalEntity, String name) throws IOException, LognexApiException {
        ProductFolderEntity retrievedUpdatedEntity = api.entity().productfolder().get(e.getId());

        assertNotEquals(retrievedOriginalEntity.getName(), retrievedUpdatedEntity.getName());
        assertEquals(name, retrievedUpdatedEntity.getName());
        assertEquals(retrievedOriginalEntity.getDescription(), retrievedUpdatedEntity.getDescription());
    }
}

