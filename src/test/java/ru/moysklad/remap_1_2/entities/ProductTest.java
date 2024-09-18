package ru.moysklad.remap_1_2.entities;

import org.junit.Test;
import ru.moysklad.remap_1_2.clients.EntityClientBase;
import ru.moysklad.remap_1_2.entities.agents.Counterparty;
import ru.moysklad.remap_1_2.entities.products.*;
import ru.moysklad.remap_1_2.responses.ListEntity;
import ru.moysklad.remap_1_2.responses.metadata.MetadataAttributeSharedResponse;
import ru.moysklad.remap_1_2.utils.ApiClientException;

import java.io.IOException;
import java.util.Arrays;
import java.util.Date;

import static ru.moysklad.remap_1_2.utils.params.FilterParam.filterEq;
import static org.junit.Assert.*;

public class ProductTest extends EntityGetUpdateDeleteWithImageTest<Product> implements FilesTest<Product> {
    @Test
    public void createTest() throws IOException, ApiClientException {
        Product product = new Product();
        product.setName("product_" + randomString(3) + "_" + new Date().getTime());
        product.setArchived(false);
        product.setDescription(randomString());
        product.setArticle(randomString());
        product.setWeight(randomDouble(1, 5, 2));
        product.setTrackingType(Product.TrackingType.OTP);
        product.setPaymentItemType(GoodPaymentItemType.GOOD);
        product.setTaxSystem(GoodTaxSystem.SIMPLIFIED_TAX_SYSTEM_INCOME);
        product.setSupplier(simpleEntityManager.createSimple(Counterparty.class));
        product.setPartialDisposal(true);

        api.entity().product().create(product);

        ListEntity<Product> updatedEntitiesList = api.entity().product().get(filterEq("name", product.getName()));
        assertEquals(1, updatedEntitiesList.getRows().size());

        Product retrievedEntity = updatedEntitiesList.getRows().get(0);
        assertEquals(product.getName(), retrievedEntity.getName());
        assertEquals(product.getArchived(), retrievedEntity.getArchived());
        assertEquals(product.getDescription(), retrievedEntity.getDescription());
        assertEquals(product.getArticle(), retrievedEntity.getArticle());
        assertEquals(product.getWeight(), retrievedEntity.getWeight());
        assertEquals(product.getTrackingType(), retrievedEntity.getTrackingType());
        assertEquals(product.getPaymentItemType(), retrievedEntity.getPaymentItemType());
        assertEquals(product.getTaxSystem(), retrievedEntity.getTaxSystem());
        assertEquals(product.getSupplier(), retrievedEntity.getSupplier());
        assertFalse(product.getAttribute(null).isPresent());
        assertEquals(product.getPpeType(), retrievedEntity.getPpeType());
        assertEquals(product.getPartialDisposal(), retrievedEntity.getPartialDisposal());
    }

    @Test
    public void createOnTapProduct() throws ApiClientException, IOException {
        Product product = new Product();
        product.setName("product_" + randomString(3) + "_" + new Date().getTime());
        product.setArchived(false);
        product.setDescription(randomString());
        product.setArticle(randomString());
        product.setOnTap(true);
        api.entity().product().create(product);
        ListEntity<Product> updatedEntitiesList = api.entity().product().get(filterEq("name", product.getName()));
        assertEquals(1, updatedEntitiesList.getRows().size());
        Product retrievedEntity = updatedEntitiesList.getRows().get(0);
        assertEquals(product.getOnTap(), retrievedEntity.getOnTap());
    }

    @Test
    public void createDiscountProhibitedProduct() throws ApiClientException, IOException {
        Product productDiscountProhibited = new Product();
        productDiscountProhibited.setName("product_" + randomString(3) + "_" + new Date().getTime());
        productDiscountProhibited.setArchived(false);
        productDiscountProhibited.setDescription(randomString());
        productDiscountProhibited.setArticle(randomString());
        productDiscountProhibited.setDiscountProhibited(true);

        api.entity().product().create(productDiscountProhibited);
        ListEntity<Product> updatedEntitiesList = api.entity().product().get(filterEq("name", productDiscountProhibited.getName()));
        assertEquals(1, updatedEntitiesList.getRows().size());
        Product retrievedEntity = updatedEntitiesList.getRows().get(0);
        assertEquals(productDiscountProhibited.getDiscountProhibited(), retrievedEntity.getDiscountProhibited());

        Product productDiscountNotProhibited = new Product();
        productDiscountNotProhibited.setName("product_" + randomString(3) + "_" + new Date().getTime());
        productDiscountNotProhibited.setArchived(false);
        productDiscountNotProhibited.setDescription(randomString());
        productDiscountNotProhibited.setArticle(randomString());
        productDiscountNotProhibited.setDiscountProhibited(false);

        api.entity().product().create(productDiscountNotProhibited);
        ListEntity<Product> updatedEntitiesList2 = api.entity().product().get(filterEq("name", productDiscountNotProhibited.getName()));
        assertEquals(1, updatedEntitiesList2.getRows().size());
        Product retrievedEntity2 = updatedEntitiesList2.getRows().get(0);
        assertEquals(productDiscountNotProhibited.getDiscountProhibited(), retrievedEntity2.getDiscountProhibited());
    }

    @Test
    public void paymentItemTypeTest() {
        Product product = simpleEntityManager.createSimple(Product.class);

        Arrays.stream(GoodPaymentItemType.values()).forEach(goodPaymentItemType -> {
            product.setPaymentItemType(goodPaymentItemType);
            try {
                api.entity().product().update(product);
                assertEquals(goodPaymentItemType, product.getPaymentItemType());
            } catch (IOException | ApiClientException e) {
                fail();
            }
        });
    }

    @Test
    public void metadataTest() throws IOException, ApiClientException {
        MetadataAttributeSharedResponse metadata = api.entity().product().metadata();
        assertTrue(metadata.getCreateShared());
    }

    @Test
    public void attributesTest() throws IOException, ApiClientException{
        ListEntity<Attribute> attributes = api.entity().product().metadataAttributes();
        assertNotNull(attributes);
    }

    @Test
    public void createAttributeTest() throws IOException, ApiClientException {
        Attribute attribute = new Attribute();
        attribute.setType(Attribute.Type.textValue);
        String name = "field" + randomString(3) + "_" + new Date().getTime();
        attribute.setName(name);
        attribute.setRequired(false);
        attribute.setShow(true);
        attribute.setDescription("description");
        Attribute created = api.entity().product().createMetadataAttribute(attribute);
        assertNotNull(created);
        assertEquals(name, created.getName());
        assertEquals(Attribute.Type.textValue, created.getType());
        assertFalse(created.getRequired());
        assertTrue(created.getShow());
        assertEquals("description", created.getDescription());
    }

    @Test
    public void updateAttributeTest() throws IOException, ApiClientException {
        Attribute attribute = new Attribute();
        attribute.setEntityType(Meta.Type.PRODUCT);
        attribute.setName("field" + randomString(3) + "_" + new Date().getTime());
        attribute.setRequired(true);
        attribute.setShow(true);
        Attribute created = api.entity().product().createMetadataAttribute(attribute);

        String name = "field" + randomString(3) + "_" + new Date().getTime();
        created.setName(name);
        created.setRequired(false);
        attribute.setShow(false);
        Attribute updated = api.entity().product().updateMetadataAttribute(created);
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
        attribute.setShow(true);
        Attribute created = api.entity().product().createMetadataAttribute(attribute);

        api.entity().product().deleteMetadataAttribute(created);

        try {
            api.entity().product().metadataAttributes(created.getId());
        } catch (ApiClientException e) {
            assertEquals(404, e.getStatusCode());
        }
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

    @Test
    public void testFiles() throws IOException, ApiClientException {
        doTestFiles();
    }

    @Override
    public EntityClientBase entityClient() {
        return api.entity().product();
    }

    @Override
    public Class<? extends MetaEntity> entityClass() {
        return Product.class;
    }
}
