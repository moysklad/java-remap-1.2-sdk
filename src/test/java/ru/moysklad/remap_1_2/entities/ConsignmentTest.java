package ru.moysklad.remap_1_2.entities;

import org.junit.Test;
import ru.moysklad.remap_1_2.clients.EntityClientBase;
import ru.moysklad.remap_1_2.entities.products.Product;
import ru.moysklad.remap_1_2.responses.ListEntity;
import ru.moysklad.remap_1_2.utils.ApiClientException;

import java.io.IOException;
import java.util.Date;

import static ru.moysklad.remap_1_2.utils.params.FilterParam.filterEq;
import static org.junit.Assert.*;

public class ConsignmentTest extends EntityGetUpdateDeleteTest {
    @Test
    public void createTest() throws IOException, ApiClientException {
        Consignment consignment = new Consignment();
        consignment.setLabel("consignment_" + randomString(3) + "_" + new Date().getTime());
        consignment.setDescription(randomString());
        consignment.setCode(randomString());

        Product product = simpleEntityManager.createSimple(Product.class);
        consignment.setAssortment(product);

        api.entity().consignment().create(consignment);

        ListEntity<Consignment> updatedEntitiesList = api.entity().consignment().get(filterEq("name", consignment.getName()));
        assertEquals(1, updatedEntitiesList.getRows().size());

        Consignment retrievedEntity = updatedEntitiesList.getRows().get(0);
        assertEquals(consignment.getName(), retrievedEntity.getName());
        assertEquals(consignment.getLabel(), retrievedEntity.getLabel());
        assertEquals(consignment.getDescription(), retrievedEntity.getDescription());
        assertEquals(consignment.getCode(), retrievedEntity.getCode());
        assertEquals(consignment.getAssortment(), retrievedEntity.getAssortment());
    }

    @Override
    protected String getFieldNameToUpdate() {
        return "Label";
    }

    @Override
    protected void putAsserts(MetaEntity originalEntity, MetaEntity updatedEntity, Object changedField) {
        Consignment originalConsignment = (Consignment) originalEntity;
        Consignment updatedConsignment = (Consignment) updatedEntity;

        assertNotEquals(originalConsignment.getName(), updatedConsignment.getName());
        assertNotEquals(originalConsignment.getLabel(), updatedConsignment.getLabel());
        assertEquals(changedField, updatedConsignment.getLabel());
        assertEquals(originalConsignment.getAssortment(), updatedConsignment.getAssortment());
    }

    @Override
    protected void getAsserts(MetaEntity originalEntity, MetaEntity retrievedEntity) {
        Consignment originalConsignment = (Consignment) originalEntity;
        Consignment retrievedConsignment = (Consignment) retrievedEntity;

        assertEquals(originalConsignment.getName(), retrievedConsignment.getName());
        assertEquals(originalConsignment.getLabel(), retrievedConsignment.getLabel());
        assertEquals(originalConsignment.getAssortment(), retrievedConsignment.getAssortment());
    }
    
    @Test
    public void attributesTest() throws IOException, ApiClientException{
        ListEntity<Attribute> attributes = api.entity().consignment().metadataAttributes();
        assertNotNull(attributes);
    }

    @Test
    public void createAttributeTest() throws IOException, ApiClientException {
        Attribute attribute = new AttributeCustomEntity();
        attribute.setType(Attribute.Type.textValue);
        String name = "field" + randomString(3) + "_" + new Date().getTime();
        attribute.setName(name);
        attribute.setRequired(false);
        attribute.setDescription("description");
        Attribute created = api.entity().consignment().createMetadataAttribute(attribute);
        assertNotNull(created);
        assertEquals(name, created.getName());
        assertEquals(Attribute.Type.textValue, created.getType());
        assertFalse(created.getRequired());
        assertEquals("description", created.getDescription());
    }

    @Test
    public void updateAttributeTest() throws IOException, ApiClientException {
        Attribute attribute = new AttributeCustomEntity();
        attribute.setEntityType(Meta.Type.PRODUCT);
        attribute.setName("field" + randomString(3) + "_" + new Date().getTime());
        attribute.setRequired(true);
        AttributeCustomEntity created = (AttributeCustomEntity) api.entity().consignment().createMetadataAttribute(attribute);

        String name = "field" + randomString(3) + "_" + new Date().getTime();
        created.setName(name);
        created.setRequired(false);
        AttributeCustomEntity updated = (AttributeCustomEntity) api.entity().consignment().updateMetadataAttribute(created);
        assertNotNull(created);
        assertEquals(name, updated.getName());
        assertNull(updated.getType());
        assertEquals(Meta.Type.PRODUCT, updated.getEntityType());
        assertFalse(updated.getRequired());
    }

    @Test
    public void deleteAttributeTest() throws IOException, ApiClientException{
        Attribute attribute = new AttributeCustomEntity();
        attribute.setEntityType(Meta.Type.PRODUCT);
        attribute.setName("field" + randomString(3) + "_" + new Date().getTime());
        attribute.setRequired(true);
        AttributeCustomEntity created = (AttributeCustomEntity) api.entity().consignment().createMetadataAttribute(attribute);

        api.entity().consignment().deleteMetadataAttribute(created);

        try {
            api.entity().consignment().metadataAttributes(created.getId());
        } catch (ApiClientException e) {
            assertEquals(404, e.getStatusCode());
        }
    }
    
    @Override
    public EntityClientBase entityClient() {
        return api.entity().consignment();
    }

    @Override
    public Class<? extends MetaEntity> entityClass() {
        return Consignment.class;
    }
}

