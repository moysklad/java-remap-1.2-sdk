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
        ProductEntity product = new ProductEntity();
        product.setName("product_" + randomString(3) + "_" + new Date().getTime());
        product.setArchived(false);
        product.setDescription(randomString());
        product.setArticle(randomString());
        product.setWeight(randomDouble(1, 5, 2));

        api.entity().product().post(product);

        ListEntity<ProductEntity> updatedEntitiesList = api.entity().product().get(filterEq("name", product.getName()));
        assertEquals(1, updatedEntitiesList.getRows().size());

        ProductEntity retrievedEntity = updatedEntitiesList.getRows().get(0);
        assertEquals(product.getName(), retrievedEntity.getName());
        assertEquals(product.getArchived(), retrievedEntity.getArchived());
        assertEquals(product.getDescription(), retrievedEntity.getDescription());
        assertEquals(product.getArticle(), retrievedEntity.getArticle());
        assertEquals(product.getWeight(), retrievedEntity.getWeight());
    }

    @Test
    public void getTest() throws IOException, LognexApiException {
        ProductEntity product = simpleEntityFactory.createSimpleProduct();

        ProductEntity retrievedEntity = api.entity().product().get(product.getId());
        getAsserts(product, retrievedEntity);

        retrievedEntity = api.entity().product().get(product);
        getAsserts(product, retrievedEntity);
    }

    @Test
    public void putTest() throws IOException, LognexApiException {
        ProductEntity product = simpleEntityFactory.createSimpleProduct();

        ProductEntity retrievedOriginalEntity = api.entity().product().get(product.getId());
        String name = "product_" + randomString(3) + "_" + new Date().getTime();
        product.setName(name);
        api.entity().product().put(product.getId(), product);
        putAsserts(product, retrievedOriginalEntity, name);

        retrievedOriginalEntity.set(product);

        name = "product_" + randomString(3) + "_" + new Date().getTime();
        product.setName(name);
        api.entity().product().put(product);
        putAsserts(product, retrievedOriginalEntity, name);
    }

    @Test
    public void deleteTest() throws IOException, LognexApiException {
        ProductEntity product = simpleEntityFactory.createSimpleProduct();

        ListEntity<ProductEntity> entitiesList = api.entity().product().get(filterEq("name", product.getName()));
        assertEquals((Integer) 1, entitiesList.getMeta().getSize());

        api.entity().product().delete(product.getId());

        entitiesList = api.entity().product().get(filterEq("name", product.getName()));
        assertEquals((Integer) 0, entitiesList.getMeta().getSize());
    }

    @Test
    public void deleteByIdTest() throws IOException, LognexApiException {
        ProductEntity product = simpleEntityFactory.createSimpleProduct();

        ListEntity<ProductEntity> entitiesList = api.entity().product().get(filterEq("name", product.getName()));
        assertEquals((Integer) 1, entitiesList.getMeta().getSize());

        api.entity().product().delete(product);

        entitiesList = api.entity().product().get(filterEq("name", product.getName()));
        assertEquals((Integer) 0, entitiesList.getMeta().getSize());
    }

    @Test
    public void metadataTest() throws IOException, LognexApiException {
        MetadataAttributeSharedResponse metadata = api.entity().product().metadata();
        assertTrue(metadata.getCreateShared());
    }

    private void getAsserts(ProductEntity product, ProductEntity retrievedEntity) {
        assertEquals(product.getName(), retrievedEntity.getName());
        assertEquals(product.getDescription(), retrievedEntity.getDescription());
    }

    private void putAsserts(ProductEntity product, ProductEntity retrievedOriginalEntity, String name) throws IOException, LognexApiException {
        ProductEntity retrievedUpdatedEntity = api.entity().product().get(product.getId());

        assertNotEquals(retrievedOriginalEntity.getName(), retrievedUpdatedEntity.getName());
        assertEquals(name, retrievedUpdatedEntity.getName());
        assertEquals(retrievedOriginalEntity.getDescription(), retrievedUpdatedEntity.getDescription());
    }
}
