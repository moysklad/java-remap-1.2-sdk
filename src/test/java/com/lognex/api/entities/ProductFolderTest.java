package com.lognex.api.entities;

import com.lognex.api.clients.EntityClientBase;
import com.lognex.api.responses.ListEntity;
import com.lognex.api.utils.ApiClientException;
import org.junit.Test;

import java.io.IOException;
import java.util.Date;

import static com.lognex.api.utils.params.ExpandParam.expand;
import static com.lognex.api.utils.params.FilterParam.filterEq;
import static com.lognex.api.utils.params.LimitParam.limit;
import static org.junit.Assert.*;

public class ProductFolderTest extends EntityGetUpdateDeleteTest {
    @Test
    public void createTest() throws IOException, ApiClientException {
        ProductFolder inner = new ProductFolder();
        inner.setName("innerproductfolder_" + randomString(3) + "_" + new Date().getTime());
        inner.setArchived(false);

        ProductFolder outer = new ProductFolder();
        outer.setName("outerproductfolder_" + randomString(3) + "_" + new Date().getTime());

        api.entity().productfolder().create(outer);
        inner.setProductFolder(outer);
        api.entity().productfolder().create(inner);

        ListEntity<ProductFolder> updatedEntitiesList = api.entity().productfolder().
                get(limit(20), filterEq("name", inner.getName()), expand("productFolder"));
        assertEquals(1, updatedEntitiesList.getRows().size());

        ProductFolder retrievedEntity = updatedEntitiesList.getRows().get(0);
        assertEquals(inner.getName(), retrievedEntity.getName());
        assertEquals(inner.getArchived(), retrievedEntity.getArchived());
        assertEquals(outer.getName(), retrievedEntity.getProductFolder().getName());
    }

    @Override
    protected void getAsserts(MetaEntity originalEntity, MetaEntity retrievedEntity) {
        ProductFolder originalProductFolder = (ProductFolder) originalEntity;
        ProductFolder retrievedProductFolder = (ProductFolder) retrievedEntity;

        assertEquals(originalProductFolder.getName(), retrievedProductFolder.getName());
        assertEquals(originalProductFolder.getDescription(), retrievedProductFolder.getDescription());
    }

    @Override
    protected void putAsserts(MetaEntity originalEntity, MetaEntity updatedEntity, Object changedField) {
        ProductFolder originalProductFolder = (ProductFolder) originalEntity;
        ProductFolder updatedProductFolder = (ProductFolder) updatedEntity;

        assertNotEquals(originalProductFolder.getName(), updatedProductFolder.getName());
        assertEquals(changedField, updatedProductFolder.getName());
        assertEquals(originalProductFolder.getDescription(), updatedProductFolder.getDescription());
    }

    @Override
    protected EntityClientBase entityClient() {
        return api.entity().productfolder();
    }

    @Override
    protected Class<? extends MetaEntity> entityClass() {
        return ProductFolder.class;
    }
}

