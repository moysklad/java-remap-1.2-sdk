package com.lognex.api.entities;

import com.lognex.api.entities.products.*;
import com.lognex.api.responses.ListEntity;
import com.lognex.api.responses.metadata.MetadataAttributeSharedResponse;
import com.lognex.api.utils.LognexApiException;
import org.junit.Test;

import java.io.IOException;
import java.util.Date;

import static com.lognex.api.utils.params.FilterParam.filterEq;
import static org.junit.Assert.*;

public class ProductEntityTest extends EntityTestBase {
    @Test
    public void createTest() throws IOException, LognexApiException {
        ProductEntity e = new ProductEntity();
        e.setName("product_" + randomString(3) + "_" + new Date().getTime());
        e.setArchived(false);
        e.setDescription(randomString());
        e.setArticle(randomString());
        e.setWeight(randomDouble(1, 5, 2));

        api.entity().product().post(e);

        ListEntity<ProductEntity> updatedEntitiesList = api.entity().product().get(filterEq("name", e.getName()));
        assertEquals(1, updatedEntitiesList.getRows().size());

        ProductEntity retrievedEntity = updatedEntitiesList.getRows().get(0);
        assertEquals(e.getName(), retrievedEntity.getName());
        assertEquals(e.getArchived(), retrievedEntity.getArchived());
        assertEquals(e.getDescription(), retrievedEntity.getDescription());
        assertEquals(e.getArticle(), retrievedEntity.getArticle());
        assertEquals(e.getWeight(), retrievedEntity.getWeight());
    }

    @Test
    public void getTest() throws IOException, LognexApiException {
        ProductEntity e = createSimpleProduct();

        ProductEntity retrievedEntity = api.entity().product().get(e.getId());
        getAsserts(e, retrievedEntity);

        retrievedEntity = api.entity().product().get(e);
        getAsserts(e, retrievedEntity);
    }

    @Test
    public void putTest() throws IOException, LognexApiException, InterruptedException {
        ProductEntity e = createSimpleProduct();

        ProductEntity retrievedOriginalEntity = api.entity().product().get(e.getId());
        String name = "product_" + randomString(3) + "_" + new Date().getTime();
        e.setName(name);
        Thread.sleep(500);
        api.entity().product().put(e.getId(), e);
        putAsserts(e, retrievedOriginalEntity, name);

        retrievedOriginalEntity.set(e);

        name = "product_" + randomString(3) + "_" + new Date().getTime();
        e.setName(name);
        Thread.sleep(500);
        api.entity().product().put(e);
        putAsserts(e, retrievedOriginalEntity, name);
    }

    @Test
    public void deleteTest() throws IOException, LognexApiException {
        ProductEntity e = createSimpleProduct();

        ListEntity<ProductEntity> entitiesList = api.entity().product().get(filterEq("name", e.getName()));
        assertEquals(1, entitiesList.getRows().size());

        api.entity().product().delete(e.getId());

        entitiesList = api.entity().product().get(filterEq("name", e.getName()));
        assertEquals(0, entitiesList.getRows().size());
    }

    @Test
    public void deleteByIdTest() throws IOException, LognexApiException {
        ProductEntity e = createSimpleProduct();

        ListEntity<ProductEntity> entitiesList = api.entity().product().get(filterEq("name", e.getName()));
        assertEquals(1, entitiesList.getRows().size());

        api.entity().product().delete(e);

        entitiesList = api.entity().product().get(filterEq("name", e.getName()));
        assertEquals(0, entitiesList.getRows().size());
    }

    @Test
    public void metadataTest() throws IOException, LognexApiException {
        MetadataAttributeSharedResponse metadata = api.entity().product().metadata();
        assertTrue(metadata.getCreateShared());
    }

    private ProductEntity createSimpleProduct() throws IOException, LognexApiException {
        ProductEntity e = new ProductEntity();
        e.setName("product_" + randomString(3) + "_" + new Date().getTime());
        e.setArchived(false);
        e.setDescription(randomString());
        e.setPathName(randomString());

        api.entity().product().post(e);

        return e;
    }

    private void getAsserts(ProductEntity e, ProductEntity retrievedEntity) {
        assertEquals(e.getName(), retrievedEntity.getName());
        assertEquals(e.getArchived(), retrievedEntity.getArchived());
        assertEquals(e.getDescription(), retrievedEntity.getDescription());
        assertEquals(e.getArticle(), retrievedEntity.getArticle());
        assertEquals(e.getWeight(), retrievedEntity.getWeight());
    }

    private void putAsserts(ProductEntity e, ProductEntity retrievedOriginalEntity, String name) throws IOException, LognexApiException {
        ProductEntity retrievedUpdatedEntity = api.entity().product().get(e.getId());

        assertNotEquals(retrievedOriginalEntity.getName(), retrievedUpdatedEntity.getName());
        assertEquals(name, retrievedUpdatedEntity.getName());
        assertEquals(retrievedOriginalEntity.getArchived(), retrievedUpdatedEntity.getArchived());
        assertEquals(retrievedOriginalEntity.getDescription(), retrievedUpdatedEntity.getDescription());
        assertEquals(retrievedOriginalEntity.getArticle(), retrievedUpdatedEntity.getArticle());
        assertEquals(retrievedOriginalEntity.getWeight(), retrievedUpdatedEntity.getWeight());
    }
}
