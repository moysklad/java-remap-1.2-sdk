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

    @Test
    public void attributesTest() throws IOException, ApiClientException{
        ListEntity<Attribute> attributes = api.entity().productfolder().metadataAttributes();
        assertNotNull(attributes);
    }

    @Test
    public void createAttributeTest() throws IOException, ApiClientException {
        Attribute attribute = new Attribute();
        attribute.setType(Attribute.Type.textValue);
        String name = "field" + randomString(3) + "_" + new Date().getTime();
        attribute.setName(name);
        attribute.setRequired(false);
        attribute.setDescription("description");
        Attribute created = api.entity().productfolder().createMetadataAttribute(attribute);
        assertNotNull(created);
        assertEquals(name, created.getName());
        assertEquals(Attribute.Type.textValue, created.getType());
        assertFalse(created.getRequired());
        assertEquals("description", created.getDescription());
    }

    @Test
    public void updateAttributeTest2() throws IOException, ApiClientException {
        Attribute attribute = new Attribute();
        attribute.setEntityType(Meta.Type.PRODUCT);
        attribute.setName("field" + randomString(3) + "_" + new Date().getTime());
        attribute.setRequired(true);
        Attribute created = api.entity().productfolder().createMetadataAttribute(attribute);

        String name = "field" + randomString(3) + "_" + new Date().getTime();
        created.setName(name);
        created.setRequired(false);
        Attribute updated = api.entity().productfolder().updateMetadataAttribute(created);
        assertNotNull(created);
        assertEquals(name, updated.getName());
        assertNull(updated.getType());
        assertEquals(Meta.Type.PRODUCT, updated.getEntityType());
        assertFalse(updated.getRequired());
    }

    @Test
    public void deleteAttributeTest() throws IOException, ApiClientException{
        Attribute attribute = new Attribute();
        attribute.setEntityType(Meta.Type.PRODUCT);
        attribute.setName("field" + randomString(3) + "_" + new Date().getTime());
        attribute.setRequired(true);
        Attribute created = api.entity().productfolder().createMetadataAttribute(attribute);

        api.entity().productfolder().deleteMetadataAttribute(created);

        try {
            api.entity().productfolder().metadataAttributes(created.getId());
        } catch (ApiClientException e) {
            assertEquals(404, e.getStatusCode());
        }
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

