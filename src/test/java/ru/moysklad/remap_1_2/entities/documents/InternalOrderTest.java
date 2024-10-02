package ru.moysklad.remap_1_2.entities.documents;

import org.junit.Test;
import ru.moysklad.remap_1_2.clients.EntityClientBase;
import ru.moysklad.remap_1_2.entities.Attribute;
import ru.moysklad.remap_1_2.entities.DocumentAttribute;
import ru.moysklad.remap_1_2.entities.Meta;
import ru.moysklad.remap_1_2.entities.MetaEntity;
import ru.moysklad.remap_1_2.responses.ListEntity;
import ru.moysklad.remap_1_2.responses.metadata.MetadataAttributeSharedStatesResponse;
import ru.moysklad.remap_1_2.utils.ApiClientException;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import static ru.moysklad.remap_1_2.utils.params.FilterParam.filterEq;
import static org.junit.Assert.*;

public class InternalOrderTest extends DocumentWithPositionsTestBase {
    @Test
    public void createTest() throws IOException, ApiClientException {
        InternalOrder internalOrder = new InternalOrder();
        internalOrder.setName("internalorder_" + randomString(3) + "_" + new Date().getTime());
        internalOrder.setDescription(randomString());
        internalOrder.setVatEnabled(true);
        internalOrder.setVatIncluded(true);
        internalOrder.setMoment(LocalDateTime.now());
        internalOrder.setOrganization(simpleEntityManager.getOwnOrganization());
        internalOrder.setStore(simpleEntityManager.getMainStore());

        api.entity().internalorder().create(internalOrder);

        ListEntity<InternalOrder> updatedEntitiesList = api.entity().internalorder().get(filterEq("name", internalOrder.getName()));
        assertEquals(1, updatedEntitiesList.getRows().size());

        InternalOrder retrievedEntity = updatedEntitiesList.getRows().get(0);
        assertEquals(internalOrder.getName(), retrievedEntity.getName());
        assertEquals(internalOrder.getDescription(), retrievedEntity.getDescription());
        assertEquals(internalOrder.getVatEnabled(), retrievedEntity.getVatEnabled());
        assertEquals(internalOrder.getVatIncluded(), retrievedEntity.getVatIncluded());
        assertEquals(internalOrder.getMoment(), retrievedEntity.getMoment());
        assertEquals(internalOrder.getOrganization().getMeta().getHref(), retrievedEntity.getOrganization().getMeta().getHref());
        assertEquals(internalOrder.getStore().getMeta().getHref(), retrievedEntity.getStore().getMeta().getHref());
    }

    @Test
    public void metadataTest() throws IOException, ApiClientException {
        MetadataAttributeSharedStatesResponse<DocumentAttribute> response = api.entity().internalorder().metadata().get();

        assertFalse(response.getCreateShared());
    }

    @Test
    public void attributesTest() throws IOException, ApiClientException{
        ListEntity<Attribute> attributes = api.entity().internalorder().metadataAttributes();
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
        DocumentAttribute created = api.entity().internalorder().createMetadataAttribute(attribute);
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
        DocumentAttribute created = api.entity().internalorder().createMetadataAttribute(attribute);

        String name = "field" + randomString(3) + "_" + new Date().getTime();
        created.setName(name);
        created.setRequired(false);
        created.setShow(false);
        DocumentAttribute updated = api.entity().internalorder().updateMetadataAttribute(created);
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
        DocumentAttribute created = api.entity().internalorder().createMetadataAttribute(attribute);

        api.entity().internalorder().deleteMetadataAttribute(created);

        try {
            api.entity().internalorder().metadataAttributes(created.getId());
        } catch (ApiClientException e) {
            assertEquals(404, e.getStatusCode());
        }
    }

    @Test
    public void newTest() throws IOException, ApiClientException {
        InternalOrder internalOrder = api.entity().internalorder().templateDocument();
        LocalDateTime time = LocalDateTime.now();

        assertEquals("", internalOrder.getName());
        assertTrue(internalOrder.getVatEnabled());
        assertTrue(internalOrder.getVatIncluded());
        assertEquals(Long.valueOf(0), internalOrder.getSum());
        assertFalse(internalOrder.getShared());
        assertTrue(internalOrder.getApplicable());
        assertFalse(internalOrder.getPublished());
        assertFalse(internalOrder.getPrinted());
        assertTrue(ChronoUnit.MILLIS.between(time, internalOrder.getMoment()) < 1000);

        assertEquals(internalOrder.getOrganization().getMeta().getHref(), simpleEntityManager.getOwnOrganization().getMeta().getHref());
        assertEquals(internalOrder.getStore().getMeta().getHref(), simpleEntityManager.getMainStore().getMeta().getHref());
        assertEquals(internalOrder.getGroup().getMeta().getHref(), simpleEntityManager.getMainGroup().getMeta().getHref());
    }

    @Override
    protected void getAsserts(MetaEntity originalEntity, MetaEntity retrievedEntity) {
        InternalOrder originalInternalOrder = (InternalOrder) originalEntity;
        InternalOrder retrievedInternalOrder = (InternalOrder) retrievedEntity;

        assertEquals(originalInternalOrder.getName(), retrievedInternalOrder.getName());
        assertEquals(originalInternalOrder.getDescription(), retrievedInternalOrder.getDescription());
        assertEquals(originalInternalOrder.getOrganization().getMeta().getHref(), retrievedInternalOrder.getOrganization().getMeta().getHref());
        assertEquals(originalInternalOrder.getStore().getMeta().getHref(), retrievedInternalOrder.getStore().getMeta().getHref());
    }

    @Override
    protected void putAsserts(MetaEntity originalEntity, MetaEntity updatedEntity, Object changedField) {
        InternalOrder originalInternalOrder = (InternalOrder) originalEntity;
        InternalOrder updatedInternalOrder = (InternalOrder) updatedEntity;

        assertNotEquals(originalInternalOrder.getName(), updatedInternalOrder.getName());
        assertEquals(changedField, updatedInternalOrder.getName());
        assertEquals(originalInternalOrder.getDescription(), updatedInternalOrder.getDescription());
        assertEquals(originalInternalOrder.getOrganization().getMeta().getHref(), updatedInternalOrder.getOrganization().getMeta().getHref());
        assertEquals(originalInternalOrder.getStore().getMeta().getHref(), updatedInternalOrder.getStore().getMeta().getHref());
    }

    @Override
    public EntityClientBase entityClient() {
        return api.entity().internalorder();
    }

    @Override
    public Class<? extends MetaEntity> entityClass() {
        return InternalOrder.class;
    }
}


