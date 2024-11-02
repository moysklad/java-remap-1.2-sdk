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

public class RetailDrawerCashInTest extends EntityGetUpdateDeleteTest implements FilesTest<RetailDrawerCashIn> {
    @Ignore
    @Test
    public void createTest() throws IOException, ApiClientException {
        RetailDrawerCashIn retailDrawerCashIn = new RetailDrawerCashIn();
        retailDrawerCashIn.setName("retaildrawercashin_" + randomString(3) + "_" + new Date().getTime());
        retailDrawerCashIn.setDescription(randomString());
        retailDrawerCashIn.setMoment(LocalDateTime.now());

        retailDrawerCashIn.setOrganization(simpleEntityManager.getOwnOrganization());
        retailDrawerCashIn.setAgent(simpleEntityManager.createSimpleCounterparty());

        api.entity().retaildrawercashin().create(retailDrawerCashIn);

        ListEntity<RetailDrawerCashIn> updatedEntitiesList = api.entity().retaildrawercashin().get(filterEq("name", retailDrawerCashIn.getName()));
        assertEquals(1, updatedEntitiesList.getRows().size());

        RetailDrawerCashIn retrievedEntity = updatedEntitiesList.getRows().get(0);
        assertEquals(retailDrawerCashIn.getName(), retrievedEntity.getName());
        assertEquals(retailDrawerCashIn.getDescription(), retrievedEntity.getDescription());
        assertEquals(retailDrawerCashIn.getMoment(), retrievedEntity.getMoment());
        assertEquals(retailDrawerCashIn.getOrganization().getMeta().getHref(), retrievedEntity.getOrganization().getMeta().getHref());
        assertEquals(retailDrawerCashIn.getAgent().getMeta().getHref(), retrievedEntity.getAgent().getMeta().getHref());
    }

    @Ignore
    @Test
    public void metadataTest() throws IOException, ApiClientException {
        MetadataAttributeSharedStatesResponse<DocumentAttribute> response = api.entity().retaildrawercashin().metadata().get();

        assertFalse(response.getCreateShared());
    }

    @Test
    public void attributesTest() throws IOException, ApiClientException{
        ListEntity<Attribute> attributes = api.entity().retaildrawercashin().metadataAttributes();
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
        DocumentAttribute created = api.entity().retaildrawercashin().createMetadataAttribute(attribute);
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
        DocumentAttribute created = api.entity().retaildrawercashin().createMetadataAttribute(attribute);

        String name = "field" + randomString(3) + "_" + new Date().getTime();
        created.setName(name);
        created.setRequired(false);
        created.setShow(false);
        DocumentAttribute updated = api.entity().retaildrawercashin().updateMetadataAttribute(created);
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
        DocumentAttribute created = api.entity().retaildrawercashin().createMetadataAttribute(attribute);

        api.entity().retaildrawercashin().deleteMetadataAttribute(created);

        try {
            api.entity().retaildrawercashin().metadataAttributes(created.getId());
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
    public void massUpdateTest() throws IOException, ApiClientException {
    }

    @Ignore
    @Test
    @Override
    public void massCreateDeleteTest() {
    }

    @Override
    protected void getAsserts(MetaEntity originalEntity, MetaEntity retrievedEntity) {
        RetailDrawerCashIn originalRetailDrawerCashIn = (RetailDrawerCashIn) originalEntity;
        RetailDrawerCashIn retrievedRetailDrawerCashIn = (RetailDrawerCashIn) retrievedEntity;

        assertEquals(originalRetailDrawerCashIn.getName(), retrievedRetailDrawerCashIn.getName());
        assertEquals(originalRetailDrawerCashIn.getDescription(), retrievedRetailDrawerCashIn.getDescription());
        assertEquals(originalRetailDrawerCashIn.getOrganization().getMeta().getHref(), retrievedRetailDrawerCashIn.getOrganization().getMeta().getHref());
        assertEquals(originalRetailDrawerCashIn.getAgent().getMeta().getHref(), retrievedRetailDrawerCashIn.getAgent().getMeta().getHref());
    }

    @Override
    protected void putAsserts(MetaEntity originalEntity, MetaEntity updatedEntity, Object changedField) {
        RetailDrawerCashIn originalRetailDrawerCashIn = (RetailDrawerCashIn) originalEntity;
        RetailDrawerCashIn updatedRetailDrawerCashIn = (RetailDrawerCashIn) updatedEntity;

        assertNotEquals(originalRetailDrawerCashIn.getName(), updatedRetailDrawerCashIn.getName());
        assertEquals(changedField, updatedRetailDrawerCashIn.getName());
        assertEquals(originalRetailDrawerCashIn.getDescription(), updatedRetailDrawerCashIn.getDescription());
        assertEquals(originalRetailDrawerCashIn.getOrganization().getMeta().getHref(), updatedRetailDrawerCashIn.getOrganization().getMeta().getHref());
        assertEquals(originalRetailDrawerCashIn.getAgent().getMeta().getHref(), updatedRetailDrawerCashIn.getAgent().getMeta().getHref());
    }

    @Ignore // cant create cashin without retailShift
    @Test
    public void testFiles() throws IOException, ApiClientException {
        doTestFiles();
    }

    @Override
    public EntityClientBase entityClient() {
        return api.entity().retaildrawercashin();
    }

    @Override
    public Class<? extends MetaEntity> entityClass() {
        return RetailDrawerCashIn.class;
    }
}
