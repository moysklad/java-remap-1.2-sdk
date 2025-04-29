package ru.moysklad.remap_1_2.entities.documents;

import org.junit.Ignore;
import org.junit.Test;
import ru.moysklad.remap_1_2.entities.Attribute;
import ru.moysklad.remap_1_2.entities.EntityTestBase;
import ru.moysklad.remap_1_2.entities.Meta;
import ru.moysklad.remap_1_2.entities.DocumentAttribute;
import ru.moysklad.remap_1_2.responses.ListEntity;
import ru.moysklad.remap_1_2.utils.ApiClientException;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Date;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;
import static ru.moysklad.remap_1_2.utils.params.FilterParam.filterEq;

public class RetailShiftTest extends EntityTestBase {
    
    @Test
    public void attributesTest() throws IOException, ApiClientException{
        ListEntity<Attribute> attributes = api.entity().retailshift().metadataAttributes();
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
        DocumentAttribute created = api.entity().retailshift().createMetadataAttribute(attribute);
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
        DocumentAttribute created = api.entity().retailshift().createMetadataAttribute(attribute);

        String name = "field" + randomString(3) + "_" + new Date().getTime();
        created.setName(name);
        created.setRequired(false);
        created.setShow(false);
        DocumentAttribute updated = api.entity().retailshift().updateMetadataAttribute(created);
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
        DocumentAttribute created = api.entity().retailshift().createMetadataAttribute(attribute);

        api.entity().retailshift().deleteMetadataAttribute(created);

        try {
            api.entity().retailshift().metadataAttributes(created.getId());
        } catch (ApiClientException e) {
            assertEquals(404, e.getStatusCode());
        }
    }
    
    @Test
    public void createTest() throws IOException, ApiClientException {
        RetailShift retailShift = new RetailShift();
        retailShift.setName("retailshfit_" + randomString(3) + "_" + new Date().getTime());
        retailShift.setCreated(LocalDateTime.now());
        retailShift.setCloseDate(LocalDateTime.now().plusHours(1));
        retailShift.setReceivedCash(100_000.0);
        retailShift.setReceivedNoCash(200_000.0);
        retailShift.setRetailStore(simpleEntityManager.getRetailStore());
        retailShift.setOrganization(simpleEntityManager.getOwnOrganization());

        api.entity().retailshift().create(retailShift);

        ListEntity<RetailShift> updatedEntitiesList = api.entity().retailshift().get(filterEq("name", retailShift.getName()));
        assertEquals(1, updatedEntitiesList.getRows().size());

        RetailShift retrievedEntity = updatedEntitiesList.getRows().get(0);
        assertEquals(retailShift.getName(), retrievedEntity.getName());
        assertEquals(retailShift.getCreated(), retrievedEntity.getCreated());
        assertEquals(retailShift.getCloseDate(), retrievedEntity.getCloseDate());
        assertEquals(retailShift.getReceivedCash(), retrievedEntity.getReceivedCash());
        assertEquals(retailShift.getReceivedNoCash(), retrievedEntity.getReceivedNoCash());
        assertEquals(retailShift.getRetailStore().getId(), retrievedEntity.getRetailStore().getId());
        assertEquals(retailShift.getOrganization().getId(), retrievedEntity.getOrganization().getId());
    }

    @Ignore
    @Test
    public void getTest() throws IOException, ApiClientException {
    }

    @Ignore
    @Test
    public void deleteTest() throws IOException, ApiClientException {
    }

    @Ignore
    @Test
    public void deleteByIdTest() throws IOException, ApiClientException {
    }

    @Ignore
    @Test
    public void metadataTest() throws IOException, ApiClientException {
    }
}
