package ru.moysklad.remap_1_2.entities.documents;

import ru.moysklad.remap_1_2.clients.EntityClientBase;
import ru.moysklad.remap_1_2.entities.Attribute;
import ru.moysklad.remap_1_2.entities.DocumentAttribute;
import ru.moysklad.remap_1_2.entities.Meta;
import ru.moysklad.remap_1_2.entities.MetaEntity;
import ru.moysklad.remap_1_2.responses.ListEntity;
import ru.moysklad.remap_1_2.responses.metadata.MetadataAttributeSharedStatesResponse;
import ru.moysklad.remap_1_2.utils.ApiClientException;
import org.junit.Test;

import java.io.IOException;
import java.util.Date;

import static ru.moysklad.remap_1_2.utils.params.FilterParam.filterEq;
import static org.junit.Assert.*;

public class CustomerOrderTest extends DocumentWithPositionsTestBase {
    @Test
    public void createTest() throws IOException, ApiClientException {
        CustomerOrder customerOrder = new CustomerOrder();
        customerOrder.setName("customerorder_" + randomString(3) + "_" + new Date().getTime());
        customerOrder.setDescription(randomString());
        customerOrder.setOrganization(simpleEntityManager.getOwnOrganization());
        customerOrder.setAgent(simpleEntityManager.createSimpleCounterparty());

        api.entity().customerorder().create(customerOrder);

        ListEntity<CustomerOrder> updatedEntitiesList = api.entity().customerorder().get(filterEq("name", customerOrder.getName()));
        assertEquals(1, updatedEntitiesList.getRows().size());

        CustomerOrder retrievedEntity = updatedEntitiesList.getRows().get(0);
        assertEquals(customerOrder.getName(), retrievedEntity.getName());
        assertEquals(customerOrder.getDescription(), retrievedEntity.getDescription());
        assertEquals(customerOrder.getSum(), retrievedEntity.getSum());
        assertEquals(customerOrder.getMoment(), retrievedEntity.getMoment());
        assertEquals(customerOrder.getOrganization().getMeta().getHref(), retrievedEntity.getOrganization().getMeta().getHref());
        assertEquals(customerOrder.getAgent().getMeta().getHref(), retrievedEntity.getAgent().getMeta().getHref());
    }

    @Test
    public void metadataTest() throws IOException, ApiClientException {
        MetadataAttributeSharedStatesResponse<DocumentAttribute> response = api.entity().customerorder().metadata().get();

        assertFalse(response.getCreateShared());
    }


    @Test
    public void attributesTest() throws IOException, ApiClientException{
        ListEntity<Attribute> attributes = api.entity().customerorder().metadataAttributes();
        assertNotNull(attributes);
    }

    @Test
    public void createAttributeTest() throws IOException, ApiClientException {
        DocumentAttribute attribute = new DocumentAttribute();
        attribute.setType(DocumentAttribute.Type.textValue);
        String name = "field" + randomString(3) + "_" + new Date().getTime();
        attribute.setName(name);
        attribute.setRequired(false);
        attribute.setShow(true);
        attribute.setDescription("description");
        DocumentAttribute created = api.entity().customerorder().createMetadataAttribute(attribute);
        assertNotNull(created);
        assertEquals(name, created.getName());
        assertEquals(DocumentAttribute.Type.textValue, created.getType());
        assertFalse(created.getRequired());
        assertTrue(created.getShow());
        assertEquals("description", created.getDescription());
    }

    @Test
    public void updateAttributeTest() throws IOException, ApiClientException {
        DocumentAttribute attribute = new DocumentAttribute();
        attribute.setEntityType(Meta.Type.PRODUCT);
        attribute.setName("field" + randomString(3) + "_" + new Date().getTime());
        attribute.setRequired(true);
        attribute.setShow(true);
        DocumentAttribute created = api.entity().customerorder().createMetadataAttribute(attribute);

        String name = "field" + randomString(3) + "_" + new Date().getTime();
        created.setName(name);
        created.setRequired(false);
        created.setShow(false);
        DocumentAttribute updated = api.entity().customerorder().updateMetadataAttribute(created);
        assertNotNull(created);
        assertEquals(name, updated.getName());
        assertNull(updated.getType());
        assertEquals(Meta.Type.PRODUCT, updated.getEntityType());
        assertFalse(updated.getRequired());
        assertFalse(updated.getShow());
    }

    @Test
    public void deleteAttributeTest() throws IOException, ApiClientException{
        DocumentAttribute attribute = new DocumentAttribute();
        attribute.setEntityType(Meta.Type.PRODUCT);
        attribute.setName("field" + randomString(3) + "_" + new Date().getTime());
        attribute.setRequired(true);
        attribute.setShow(true);
        DocumentAttribute created = api.entity().customerorder().createMetadataAttribute(attribute);

        api.entity().customerorder().deleteMetadataAttribute(created);

        try {
            api.entity().customerorder().metadataAttributes(created.getId());
        } catch (ApiClientException e) {
            assertEquals(404, e.getStatusCode());
        }
    }

    @Override
    protected void getAsserts(MetaEntity originalEntity, MetaEntity retrievedEntity) {
        CustomerOrder originalCustomerOrder = (CustomerOrder) originalEntity;
        CustomerOrder retrievedCustomerOrder = (CustomerOrder) retrievedEntity;

        assertEquals(originalCustomerOrder.getName(), retrievedCustomerOrder.getName());
        assertEquals(originalCustomerOrder.getDescription(), retrievedCustomerOrder.getDescription());
        assertEquals(originalCustomerOrder.getSum(), retrievedCustomerOrder.getSum());
        assertEquals(originalCustomerOrder.getMoment(), retrievedCustomerOrder.getMoment());
        assertEquals(originalCustomerOrder.getOrganization().getMeta().getHref(), retrievedCustomerOrder.getOrganization().getMeta().getHref());
        assertEquals(originalCustomerOrder.getAgent().getMeta().getHref(), retrievedCustomerOrder.getAgent().getMeta().getHref());
    }

    @Override
    protected void putAsserts(MetaEntity originalEntity, MetaEntity updatedEntity, Object changedField) {
        CustomerOrder originalCustomerOrder = (CustomerOrder) originalEntity;
        CustomerOrder updatedCustomerOrder = (CustomerOrder) updatedEntity;

        assertNotEquals(originalCustomerOrder.getName(), updatedCustomerOrder.getName());
        assertEquals(changedField, updatedCustomerOrder.getName());
        assertEquals(originalCustomerOrder.getDescription(), updatedCustomerOrder.getDescription());
        assertEquals(originalCustomerOrder.getSum(), updatedCustomerOrder.getSum());
        assertEquals(originalCustomerOrder.getMoment(), updatedCustomerOrder.getMoment());
        assertEquals(originalCustomerOrder.getOrganization().getMeta().getHref(), updatedCustomerOrder.getOrganization().getMeta().getHref());
        assertEquals(originalCustomerOrder.getAgent().getMeta().getHref(), updatedCustomerOrder.getAgent().getMeta().getHref());
    }

    @Override
    public EntityClientBase entityClient() {
        return api.entity().customerorder();
    }

    @Override
    public Class<? extends MetaEntity> entityClass() {
        return CustomerOrder.class;
    }
}
