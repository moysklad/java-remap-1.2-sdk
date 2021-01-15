package ru.moysklad.remap_1_2.entities;

import ru.moysklad.remap_1_2.clients.EntityClientBase;
import ru.moysklad.remap_1_2.entities.products.GoodTaxSystem;
import ru.moysklad.remap_1_2.responses.ListEntity;
import ru.moysklad.remap_1_2.utils.ApiClientException;
import org.junit.Test;

import java.io.IOException;
import java.util.Date;

import static ru.moysklad.remap_1_2.utils.params.ExpandParam.expand;
import static ru.moysklad.remap_1_2.utils.params.FilterParam.filterEq;
import static ru.moysklad.remap_1_2.utils.params.LimitParam.limit;
import static org.junit.Assert.*;

public class ProductFolderTest extends EntityGetUpdateDeleteTest {
    @Test
    public void createTest() throws IOException, ApiClientException {
        ProductFolder inner = new ProductFolder();
        inner.setName("innerproductfolder_" + randomString(3) + "_" + new Date().getTime());
        inner.setArchived(false);
        inner.setVat(randomInteger(10, 20));
        inner.setTaxSystem(GoodTaxSystem.TAX_SYSTEM_SAME_AS_GROUP);

        ProductFolder outer = new ProductFolder();
        outer.setName("outerproductfolder_" + randomString(3) + "_" + new Date().getTime());
        outer.setTaxSystem(GoodTaxSystem.PATENT_BASED);

        api.entity().productfolder().create(outer);
        inner.setProductFolder(outer);
        api.entity().productfolder().create(inner);

        ListEntity<ProductFolder> updatedEntitiesList = api.entity().productfolder().
                get(limit(20), filterEq("name", inner.getName()), expand("productFolder"));
        assertEquals(1, updatedEntitiesList.getRows().size());

        ProductFolder retrievedEntity = updatedEntitiesList.getRows().get(0);
        assertEquals(inner.getName(), retrievedEntity.getName());
        assertEquals(inner.getArchived(), retrievedEntity.getArchived());
        assertEquals(inner.getVat(), retrievedEntity.getVat());
        assertEquals(inner.getEffectiveVat(), retrievedEntity.getEffectiveVat());
        assertEquals(inner.getTaxSystem(), retrievedEntity.getProductFolder().getTaxSystem());
        assertEquals(outer.getName(), retrievedEntity.getProductFolder().getName());
        assertEquals(outer.getTaxSystem(), retrievedEntity.getProductFolder().getTaxSystem());
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

