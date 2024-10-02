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
import java.util.List;

import static ru.moysklad.remap_1_2.utils.params.FilterParam.filterEq;
import static org.junit.Assert.*;

public class RetailDemandTest extends EntityGetUpdateDeleteTest implements FilesTest<RetailDemand> {
    @Ignore
    @Test
    public void createTest() throws IOException, ApiClientException {
        RetailDemand retailDemand = new RetailDemand();
        retailDemand.setName("retaildemand_" + randomString(3) + "_" + new Date().getTime());
        retailDemand.setDescription(randomString());
        retailDemand.setVatEnabled(true);
        retailDemand.setVatIncluded(true);
        retailDemand.setMoment(LocalDateTime.now());

        retailDemand.setOrganization(simpleEntityManager.getOwnOrganization());
        retailDemand.setAgent(simpleEntityManager.createSimpleCounterparty());
        retailDemand.setStore(simpleEntityManager.getMainStore());

        api.entity().retaildemand().create(retailDemand);

        ListEntity<RetailDemand> updatedEntitiesList = api.entity().retaildemand().get(filterEq("name", retailDemand.getName()));
        assertEquals(1, updatedEntitiesList.getRows().size());

        RetailDemand retrievedEntity = updatedEntitiesList.getRows().get(0);
        assertEquals(retailDemand.getName(), retrievedEntity.getName());
        assertEquals(retailDemand.getDescription(), retrievedEntity.getDescription());
        assertEquals(retailDemand.getVatEnabled(), retrievedEntity.getVatEnabled());
        assertEquals(retailDemand.getVatIncluded(), retrievedEntity.getVatIncluded());
        assertEquals(retailDemand.getMoment(), retrievedEntity.getMoment());
        assertEquals(retailDemand.getOrganization().getMeta().getHref(), retrievedEntity.getOrganization().getMeta().getHref());
        assertEquals(retailDemand.getAgent().getMeta().getHref(), retrievedEntity.getAgent().getMeta().getHref());
        assertEquals(retailDemand.getStore().getMeta().getHref(), retrievedEntity.getStore().getMeta().getHref());
    }

    @Test
    public void createStateTest() throws IOException, ApiClientException {
        State state = new State();
        state.setName("state_" + randomStringTail());
        state.setStateType(State.StateType.regular);
        state.setColor(randomColor());

        api.entity().retaildemand().states().create(state);

        List<State> retrievedStates = api.entity().retaildemand().metadata().get().getStates();

        State retrievedState = retrievedStates.stream().filter(s -> s.getId().equals(state.getId())).findFirst().orElse(null);
        assertNotNull(retrievedState);
        assertEquals(state.getName(), retrievedState.getName());
        assertEquals(state.getStateType(), retrievedState.getStateType());
        assertEquals(state.getColor(), retrievedState.getColor());
        assertEquals(state.getEntityType(), retrievedState.getEntityType());
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
    public void metadataTest() throws IOException, ApiClientException {
        MetadataAttributeSharedStatesResponse<DocumentAttribute> response = api.entity().retaildemand().metadata().get();

        assertFalse(response.getCreateShared());
    }

    @Test
    public void attributesTest() throws IOException, ApiClientException{
        ListEntity<Attribute> attributes = api.entity().retaildemand().metadataAttributes();
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
        DocumentAttribute created = api.entity().retaildemand().createMetadataAttribute(attribute);
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
        DocumentAttribute created = api.entity().retaildemand().createMetadataAttribute(attribute);

        String name = "field" + randomString(3) + "_" + new Date().getTime();
        created.setName(name);
        created.setRequired(false);
        created.setShow(false);
        DocumentAttribute updated = api.entity().retaildemand().updateMetadataAttribute(created);
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
        DocumentAttribute created = api.entity().retaildemand().createMetadataAttribute(attribute);

        api.entity().retaildemand().deleteMetadataAttribute(created);

        try {
            api.entity().retaildemand().metadataAttributes(created.getId());
        } catch (ApiClientException e) {
            assertEquals(404, e.getStatusCode());
        }
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
        RetailDemand originalDemand = (RetailDemand) originalEntity;
        RetailDemand retrievedDemand = (RetailDemand) retrievedEntity;

        assertEquals(originalDemand.getName(), retrievedDemand.getName());
        assertEquals(originalDemand.getDescription(), retrievedDemand.getDescription());
        assertEquals(originalDemand.getOrganization().getMeta().getHref(), retrievedDemand.getOrganization().getMeta().getHref());
        assertEquals(originalDemand.getAgent().getMeta().getHref(), retrievedDemand.getAgent().getMeta().getHref());
        assertEquals(originalDemand.getStore().getMeta().getHref(), retrievedDemand.getStore().getMeta().getHref());
    }

    @Override
    protected void putAsserts(MetaEntity originalEntity, MetaEntity updatedEntity, Object changedField) {
        RetailDemand originalDemand = (RetailDemand) originalEntity;
        RetailDemand updatedDemand = (RetailDemand) updatedEntity;

        assertNotEquals(originalDemand.getName(), updatedDemand.getName());
        assertEquals(changedField, updatedDemand.getName());
        assertEquals(originalDemand.getDescription(), updatedDemand.getDescription());
        assertEquals(originalDemand.getOrganization().getMeta().getHref(), updatedDemand.getOrganization().getMeta().getHref());
        assertEquals(originalDemand.getAgent().getMeta().getHref(), updatedDemand.getAgent().getMeta().getHref());
        assertEquals(originalDemand.getStore().getMeta().getHref(), updatedDemand.getStore().getMeta().getHref());
    }

    @Ignore // cant create demand without retailShift
    @Test
    public void testFiles() throws IOException, ApiClientException {
        doTestFiles();
    }

    @Override
    public EntityClientBase entityClient() {
        return api.entity().retaildemand();
    }

    @Override
    public Class<? extends MetaEntity> entityClass() {
        return RetailDemand.class;
    }
}
