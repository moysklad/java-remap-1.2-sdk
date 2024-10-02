package ru.moysklad.remap_1_2.entities.documents;

import org.junit.Test;
import ru.moysklad.remap_1_2.clients.EntityClientBase;
import ru.moysklad.remap_1_2.entities.*;
import ru.moysklad.remap_1_2.entities.Meta;
import ru.moysklad.remap_1_2.entities.MetaEntity;
import ru.moysklad.remap_1_2.entities.State;
import ru.moysklad.remap_1_2.responses.ListEntity;
import ru.moysklad.remap_1_2.responses.metadata.MetadataAttributeSharedStatesResponse;
import ru.moysklad.remap_1_2.utils.ApiClientException;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import static ru.moysklad.remap_1_2.utils.params.FilterParam.filterEq;
import static org.junit.Assert.*;

public class InvoiceOutTest extends DocumentWithPositionsTestBase {
    @Test
    public void createTest() throws IOException, ApiClientException {
        InvoiceOut invoiceOut = new InvoiceOut();
        invoiceOut.setName("invoiceout_" + randomString(3) + "_" + new Date().getTime());
        invoiceOut.setVatEnabled(true);
        invoiceOut.setVatIncluded(true);
        invoiceOut.setMoment(LocalDateTime.now());
        invoiceOut.setSum(randomLong(10, 10000));
        invoiceOut.setOrganization(simpleEntityManager.getOwnOrganization());
        invoiceOut.setAgent(simpleEntityManager.createSimpleCounterparty());
        invoiceOut.setStore(simpleEntityManager.getMainStore());
        api.entity().invoiceout().create(invoiceOut);

        ListEntity<InvoiceOut> updatedEntitiesList = api.entity().invoiceout().get(filterEq("name", invoiceOut.getName()));
        assertEquals(1, updatedEntitiesList.getRows().size());

        InvoiceOut retrievedEntity = updatedEntitiesList.getRows().get(0);
        assertEquals(invoiceOut.getName(), retrievedEntity.getName());
        assertEquals(invoiceOut.getVatEnabled(), retrievedEntity.getVatEnabled());
        assertEquals(invoiceOut.getVatIncluded(), retrievedEntity.getVatIncluded());
        assertEquals(invoiceOut.getMoment(), retrievedEntity.getMoment());
        assertEquals(invoiceOut.getSum(), retrievedEntity.getSum());
        assertEquals(invoiceOut.getOrganization().getMeta().getHref(), retrievedEntity.getOrganization().getMeta().getHref());
        assertEquals(invoiceOut.getAgent().getMeta().getHref(), retrievedEntity.getAgent().getMeta().getHref());
        assertEquals(invoiceOut.getStore().getMeta().getHref(), retrievedEntity.getStore().getMeta().getHref());
    }

    @Test
    public void metadataTest() throws IOException, ApiClientException {
        MetadataAttributeSharedStatesResponse<DocumentAttribute> response = api.entity().invoiceout().metadata().get();

        assertFalse(response.getCreateShared());
    }
    
    @Test
    public void attributesTest() throws IOException, ApiClientException{
        ListEntity<Attribute> attributes = api.entity().invoiceout().metadataAttributes();
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
        DocumentAttribute created = api.entity().invoiceout().createMetadataAttribute(attribute);
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
        DocumentAttribute created = api.entity().invoiceout().createMetadataAttribute(attribute);

        String name = "field" + randomString(3) + "_" + new Date().getTime();
        created.setName(name);
        created.setRequired(false);
        created.setShow(false);
        DocumentAttribute updated = api.entity().invoiceout().updateMetadataAttribute(created);
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
        DocumentAttribute created = api.entity().invoiceout().createMetadataAttribute(attribute);

        api.entity().invoiceout().deleteMetadataAttribute(created);

        try {
            api.entity().invoiceout().metadataAttributes(created.getId());
        } catch (ApiClientException e) {
            assertEquals(404, e.getStatusCode());
        }
    }

    @Test
    public void createStateTest() throws IOException, ApiClientException {
        State state = new State();
        state.setName("state_" + randomStringTail());
        state.setStateType(State.StateType.regular);
        state.setColor(randomColor());

        api.entity().invoiceout().states().create(state);

        List<State> retrievedStates = api.entity().invoiceout().metadata().get().getStates();

        State retrievedState = retrievedStates.stream().filter(s -> s.getId().equals(state.getId())).findFirst().orElse(null);
        assertNotNull(retrievedState);
        assertEquals(state.getName(), retrievedState.getName());
        assertEquals(state.getStateType(), retrievedState.getStateType());
        assertEquals(state.getColor(), retrievedState.getColor());
        assertEquals(state.getEntityType(), retrievedState.getEntityType());
    }

    @Override
    protected void getAsserts(MetaEntity originalEntity, MetaEntity retrievedEntity) {
        InvoiceOut originalInvoiceOut = (InvoiceOut) originalEntity;
        InvoiceOut retrievedInvoiceOut = (InvoiceOut) retrievedEntity;

        assertEquals(originalInvoiceOut.getName(), retrievedInvoiceOut.getName());
        assertEquals(originalInvoiceOut.getOrganization().getMeta().getHref(), retrievedInvoiceOut.getOrganization().getMeta().getHref());
        assertEquals(originalInvoiceOut.getAgent().getMeta().getHref(), retrievedInvoiceOut.getAgent().getMeta().getHref());
    }

    @Override
    protected void putAsserts(MetaEntity originalEntity, MetaEntity updatedEntity, Object changedField) {
        InvoiceOut originalInvoiceOut = (InvoiceOut) originalEntity;
        InvoiceOut updatedInvoiceOut = (InvoiceOut) updatedEntity;

        assertNotEquals(originalInvoiceOut.getName(), updatedInvoiceOut.getName());
        assertEquals(changedField, updatedInvoiceOut.getName());
        assertEquals(originalInvoiceOut.getOrganization().getMeta().getHref(), updatedInvoiceOut.getOrganization().getMeta().getHref());
        assertEquals(originalInvoiceOut.getAgent().getMeta().getHref(), updatedInvoiceOut.getAgent().getMeta().getHref());
    }

    @Override
    public EntityClientBase entityClient() {
        return api.entity().invoiceout();
    }

    @Override
    public Class<? extends MetaEntity> entityClass() {
        return InvoiceOut.class;
    }
}
