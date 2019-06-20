package com.lognex.api.entities;

import com.lognex.api.clients.ApiClient;
import com.lognex.api.responses.ListEntity;
import com.lognex.api.utils.LognexApiException;
import org.junit.Test;

import java.io.IOException;
import java.util.Date;

import static com.lognex.api.utils.params.ExpandParam.expand;
import static com.lognex.api.utils.params.FilterParam.filterEq;
import static com.lognex.api.utils.params.LimitParam.limit;
import static org.junit.Assert.*;

public class ProductFolderEntityTest extends EntityGetUpdateDeleteTest {
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

    @Override
    protected void getAsserts(MetaEntity originalEntity, MetaEntity retrievedEntity) {
        ProductFolderEntity originalProductFolder = (ProductFolderEntity) originalEntity;
        ProductFolderEntity retrievedProductFolder = (ProductFolderEntity) retrievedEntity;

        assertEquals(originalProductFolder.getName(), retrievedProductFolder.getName());
        assertEquals(originalProductFolder.getDescription(), retrievedProductFolder.getDescription());
    }

    @Override
    protected void putAsserts(MetaEntity originalEntity, MetaEntity updatedEntity, Object changedField) {
        ProductFolderEntity originalProductFolder = (ProductFolderEntity) originalEntity;
        ProductFolderEntity updatedProductFolder = (ProductFolderEntity) updatedEntity;

        assertNotEquals(originalProductFolder.getName(), updatedProductFolder.getName());
        assertEquals(changedField, updatedProductFolder.getName());
        assertEquals(originalProductFolder.getDescription(), updatedProductFolder.getDescription());
    }

    @Override
    protected ApiClient entityClient() {
        return api.entity().productfolder();
    }

    @Override
    protected Class<? extends MetaEntity> entityClass() {
        return ProductFolderEntity.class;
    }
}

