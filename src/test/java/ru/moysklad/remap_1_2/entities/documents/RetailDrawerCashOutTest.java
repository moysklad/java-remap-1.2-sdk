package ru.moysklad.remap_1_2.entities.documents;

import org.junit.Ignore;
import org.junit.Test;
import ru.moysklad.remap_1_2.clients.EntityClientBase;
import ru.moysklad.remap_1_2.entities.*;
import ru.moysklad.remap_1_2.responses.ListEntity;
import ru.moysklad.remap_1_2.responses.metadata.MetadataAttributeSharedStatesResponse;
import ru.moysklad.remap_1_2.utils.ApiClientException;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Date;

import static ru.moysklad.remap_1_2.utils.params.FilterParam.filterEq;
import static org.junit.Assert.*;

public class RetailDrawerCashOutTest extends EntityGetUpdateDeleteTest implements FilesTest<RetailDrawerCashIn> {
    @Ignore
    @Test
    public void createTest() throws IOException, ApiClientException {
        RetailDrawerCashOut retailDrawerCashOut = new RetailDrawerCashOut();
        retailDrawerCashOut.setName("retaildrawercashout_" + randomString(3) + "_" + new Date().getTime());
        retailDrawerCashOut.setDescription(randomString());
        retailDrawerCashOut.setMoment(LocalDateTime.now());

        retailDrawerCashOut.setOrganization(simpleEntityManager.getOwnOrganization());
        retailDrawerCashOut.setAgent(simpleEntityManager.createSimpleCounterparty());

        api.entity().retaildrawercashout().create(retailDrawerCashOut);

        ListEntity<RetailDrawerCashOut> updatedEntitiesList = api.entity().retaildrawercashout().get(filterEq("name", retailDrawerCashOut.getName()));
        assertEquals(1, updatedEntitiesList.getRows().size());

        RetailDrawerCashOut retrievedEntity = updatedEntitiesList.getRows().get(0);
        assertEquals(retailDrawerCashOut.getName(), retrievedEntity.getName());
        assertEquals(retailDrawerCashOut.getDescription(), retrievedEntity.getDescription());
        assertEquals(retailDrawerCashOut.getMoment(), retrievedEntity.getMoment());
        assertEquals(retailDrawerCashOut.getOrganization().getMeta().getHref(), retrievedEntity.getOrganization().getMeta().getHref());
        assertEquals(retailDrawerCashOut.getAgent().getMeta().getHref(), retrievedEntity.getAgent().getMeta().getHref());
    }

    @Ignore
    @Test
    public void metadataTest() throws IOException, ApiClientException {
        MetadataAttributeSharedStatesResponse<DocumentAttribute> response = api.entity().retaildrawercashout().metadata().get();

        assertFalse(response.getCreateShared());
    }

    @Test
    public void attributesTest() throws IOException, ApiClientException{
        ListEntity<Attribute> attributes = api.entity().retaildrawercashout().metadataAttributes();
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
        DocumentAttribute created = api.entity().retaildrawercashout().createMetadataAttribute(attribute);
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
        DocumentAttribute created = api.entity().retaildrawercashout().createMetadataAttribute(attribute);

        String name = "field" + randomString(3) + "_" + new Date().getTime();
        created.setName(name);
        created.setRequired(false);
        created.setShow(false);
        DocumentAttribute updated = api.entity().retaildrawercashout().updateMetadataAttribute(created);
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
        DocumentAttribute created = api.entity().retaildrawercashout().createMetadataAttribute(attribute);

        api.entity().retaildrawercashout().deleteMetadataAttribute(created);

        try {
            api.entity().retaildrawercashout().metadataAttributes(created.getId());
        } catch (ApiClientException e) {
            assertEquals(404, e.getStatusCode());
        }
    }

    @Ignore
    @Test
    @Override
    public void putTest() throws IOException, ApiClientException {
    }

    @Ignore
    @Test
    @Override
    public void deleteTest() throws IOException, ApiClientException {
    }

    @Ignore
    @Test
    @Override
    public void getTest() throws IOException, ApiClientException {
    }

    @Ignore
    @Test
    @Override
    public void massUpdateTest() {
    }

    @Ignore
    @Test
    @Override
    public void massCreateDeleteTest() {
    }

    @Override
    protected void getAsserts(MetaEntity originalEntity, MetaEntity retrievedEntity) {
        RetailDrawerCashOut originalRetailDrawerCashOut = (RetailDrawerCashOut) originalEntity;
        RetailDrawerCashOut retrievedRetailDrawerCashOut = (RetailDrawerCashOut) retrievedEntity;

        assertEquals(originalRetailDrawerCashOut.getName(), retrievedRetailDrawerCashOut.getName());
        assertEquals(originalRetailDrawerCashOut.getDescription(), retrievedRetailDrawerCashOut.getDescription());
        assertEquals(originalRetailDrawerCashOut.getOrganization().getMeta().getHref(), retrievedRetailDrawerCashOut.getOrganization().getMeta().getHref());
        assertEquals(originalRetailDrawerCashOut.getAgent().getMeta().getHref(), retrievedRetailDrawerCashOut.getAgent().getMeta().getHref());
    }

    @Override
    protected void putAsserts(MetaEntity originalEntity, MetaEntity updatedEntity, Object changedField) {
        RetailDrawerCashOut originalRetailDrawerCashOut = (RetailDrawerCashOut) originalEntity;
        RetailDrawerCashOut updatedRetailDrawerCashOut = (RetailDrawerCashOut) updatedEntity;

        assertNotEquals(originalRetailDrawerCashOut.getName(), updatedRetailDrawerCashOut.getName());
        assertEquals(changedField, updatedRetailDrawerCashOut.getName());
        assertEquals(originalRetailDrawerCashOut.getDescription(), updatedRetailDrawerCashOut.getDescription());
        assertEquals(originalRetailDrawerCashOut.getOrganization().getMeta().getHref(), updatedRetailDrawerCashOut.getOrganization().getMeta().getHref());
        assertEquals(originalRetailDrawerCashOut.getAgent().getMeta().getHref(), updatedRetailDrawerCashOut.getAgent().getMeta().getHref());
    }

    @Ignore // cant create cashout without retailShift
    @Test
    public void testFiles() throws IOException, ApiClientException {
        doTestFiles();
    }

    @Override
    public EntityClientBase entityClient() {
        return api.entity().retaildrawercashout();
    }

    @Override
    public Class<? extends MetaEntity> entityClass() {
        return RetailDrawerCashOut.class;
    }
}
