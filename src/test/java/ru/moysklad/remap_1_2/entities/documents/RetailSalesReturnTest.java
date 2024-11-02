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

public class RetailSalesReturnTest extends EntityGetUpdateDeleteTest implements FilesTest<RetailSalesReturn> {
    @Ignore
    @Test
    public void createTest() throws IOException, ApiClientException {
        RetailSalesReturn retailSalesReturn = new RetailSalesReturn();
        retailSalesReturn.setName("retailsalesreturn_" + randomString(3) + "_" + new Date().getTime());
        retailSalesReturn.setDescription(randomString());
        retailSalesReturn.setVatEnabled(true);
        retailSalesReturn.setVatIncluded(true);
        retailSalesReturn.setMoment(LocalDateTime.now());

        retailSalesReturn.setOrganization(simpleEntityManager.getOwnOrganization());
        retailSalesReturn.setAgent(simpleEntityManager.createSimpleCounterparty());
        retailSalesReturn.setStore(simpleEntityManager.getMainStore());

        api.entity().retailsalesreturn().create(retailSalesReturn);

        ListEntity<RetailSalesReturn> updatedEntitiesList = api.entity().retailsalesreturn().get(filterEq("name", retailSalesReturn.getName()));
        assertEquals(1, updatedEntitiesList.getRows().size());

        RetailSalesReturn retrievedEntity = updatedEntitiesList.getRows().get(0);
        assertEquals(retailSalesReturn.getName(), retrievedEntity.getName());
        assertEquals(retailSalesReturn.getDescription(), retrievedEntity.getDescription());
        assertEquals(retailSalesReturn.getVatEnabled(), retrievedEntity.getVatEnabled());
        assertEquals(retailSalesReturn.getVatIncluded(), retrievedEntity.getVatIncluded());
        assertEquals(retailSalesReturn.getMoment(), retrievedEntity.getMoment());
        assertEquals(retailSalesReturn.getOrganization().getMeta().getHref(), retrievedEntity.getOrganization().getMeta().getHref());
        assertEquals(retailSalesReturn.getAgent().getMeta().getHref(), retrievedEntity.getAgent().getMeta().getHref());
        assertEquals(retailSalesReturn.getStore().getMeta().getHref(), retrievedEntity.getStore().getMeta().getHref());
    }

    @Test
    public void createStateTest() throws IOException, ApiClientException {
        State state = new State();
        state.setName("state_" + randomStringTail());
        state.setStateType(State.StateType.regular);
        state.setColor(randomColor());

        api.entity().retailsalesreturn().states().create(state);

        List<State> retrievedStates = api.entity().retailsalesreturn().metadata().get().getStates();

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

    @Test
    public void metadataTest() throws IOException, ApiClientException {
        MetadataAttributeSharedStatesResponse<DocumentAttribute> response = api.entity().retailsalesreturn().metadata().get();

        assertFalse(response.getCreateShared());
    }

    @Test
    public void attributesTest() throws IOException, ApiClientException{
        ListEntity<Attribute> attributes = api.entity().retailsalesreturn().metadataAttributes();
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
        DocumentAttribute created = api.entity().retailsalesreturn().createMetadataAttribute(attribute);
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
        DocumentAttribute created = api.entity().retailsalesreturn().createMetadataAttribute(attribute);

        String name = "field" + randomString(3) + "_" + new Date().getTime();
        created.setName(name);
        created.setRequired(false);
        created.setShow(false);
        DocumentAttribute updated = api.entity().retailsalesreturn().updateMetadataAttribute(created);
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
        DocumentAttribute created = api.entity().retailsalesreturn().createMetadataAttribute(attribute);

        api.entity().retailsalesreturn().deleteMetadataAttribute(created);

        try {
            api.entity().retailsalesreturn().metadataAttributes(created.getId());
        } catch (ApiClientException e) {
            assertEquals(404, e.getStatusCode());
        }
    }

    @Override
    protected void getAsserts(MetaEntity originalEntity, MetaEntity retrievedEntity) {
        SalesReturn originalSalesReturn = (SalesReturn) originalEntity;
        SalesReturn retrievedSalesReturn = (SalesReturn) retrievedEntity;

        assertEquals(originalSalesReturn.getName(), retrievedSalesReturn.getName());
        assertEquals(originalSalesReturn.getDescription(), retrievedSalesReturn.getDescription());
        assertEquals(originalSalesReturn.getOrganization().getMeta().getHref(), retrievedSalesReturn.getOrganization().getMeta().getHref());
        assertEquals(originalSalesReturn.getAgent().getMeta().getHref(), retrievedSalesReturn.getAgent().getMeta().getHref());
        assertEquals(originalSalesReturn.getStore().getMeta().getHref(), retrievedSalesReturn.getStore().getMeta().getHref());
    }

    @Override
    protected void putAsserts(MetaEntity originalEntity, MetaEntity updatedEntity, Object changedField) {
        SalesReturn originalSalesReturn = (SalesReturn) originalEntity;
        SalesReturn updatedSalesReturn = (SalesReturn) updatedEntity;

        assertNotEquals(originalSalesReturn.getName(), updatedSalesReturn.getName());
        assertEquals(changedField, updatedSalesReturn.getName());
        assertEquals(originalSalesReturn.getDescription(), updatedSalesReturn.getDescription());
        assertEquals(originalSalesReturn.getOrganization().getMeta().getHref(), updatedSalesReturn.getOrganization().getMeta().getHref());
        assertEquals(originalSalesReturn.getAgent().getMeta().getHref(), updatedSalesReturn.getAgent().getMeta().getHref());
        assertEquals(originalSalesReturn.getStore().getMeta().getHref(), updatedSalesReturn.getStore().getMeta().getHref());
    }

    @Test
    public void testFiles() throws IOException, ApiClientException {
        doTestFiles();
    }

    @Override
    public EntityClientBase entityClient() {
        return api.entity().salesreturn();
    }

    @Override
    public Class<? extends MetaEntity> entityClass() {
        return SalesReturn.class;
    }
}
