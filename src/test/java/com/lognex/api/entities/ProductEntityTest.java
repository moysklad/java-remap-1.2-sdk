package com.lognex.api.entities;

import com.lognex.api.clients.EntityClientBase;
import com.lognex.api.entities.products.Product;
import com.lognex.api.responses.ListEntity;
import com.lognex.api.responses.metadata.MetadataAttributeSharedResponse;
import com.lognex.api.utils.ApiClientException;
import org.junit.Test;

import java.io.IOException;
import java.util.Date;

import static com.lognex.api.utils.params.FilterParam.filterEq;
import static org.junit.Assert.*;

public class ProductEntityTest extends EntityGetUpdateDeleteWithImageTest<Product> {
    @Test
    public void createTest() throws IOException, ApiClientException {
        Product product = new Product();
        product.setName("product_" + randomString(3) + "_" + new Date().getTime());
        product.setArchived(false);
        product.setDescription(randomString());
        product.setArticle(randomString());
        product.setWeight(randomDouble(1, 5, 2));

        api.entity().product().create(product);

        ListEntity<Product> updatedEntitiesList = api.entity().product().get(filterEq("name", product.getName()));
        assertEquals(1, updatedEntitiesList.getRows().size());

        Product retrievedEntity = updatedEntitiesList.getRows().get(0);
        assertEquals(product.getName(), retrievedEntity.getName());
        assertEquals(product.getArchived(), retrievedEntity.getArchived());
        assertEquals(product.getDescription(), retrievedEntity.getDescription());
        assertEquals(product.getArticle(), retrievedEntity.getArticle());
        assertEquals(product.getWeight(), retrievedEntity.getWeight());
    }

    @Test
    public void metadataTest() throws IOException, ApiClientException {
        MetadataAttributeSharedResponse metadata = api.entity().product().metadata();
        assertTrue(metadata.getCreateShared());
    }

    @Override
    protected void getAsserts(MetaEntity originalEntity, MetaEntity retrievedEntity) {
        Product originalProduct = (Product) originalEntity;
        Product retrievedProduct = (Product) retrievedEntity;

        assertEquals(originalProduct.getName(), retrievedProduct.getName());
        assertEquals(originalProduct.getDescription(), retrievedProduct.getDescription());
    }

    @Override
    protected void putAsserts(MetaEntity originalEntity, MetaEntity updatedEntity, Object changedField) {
        Product originalProduct = (Product) originalEntity;
        Product updatedProduct = (Product) updatedEntity;

        assertNotEquals(originalProduct.getName(), updatedProduct.getName());
        assertEquals(changedField, updatedProduct.getName());
        assertEquals(originalProduct.getDescription(), updatedProduct.getDescription());
    }

    @Override
    protected EntityClientBase entityClient() {
        return api.entity().product();
    }

    @Override
    protected Class<? extends MetaEntity> entityClass() {
        return Product.class;
    }
}
