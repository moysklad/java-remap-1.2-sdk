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

public class InvoiceInTest extends DocumentWithPositionsTestBase {
    @Test
    public void createTest() throws IOException, ApiClientException {
        InvoiceIn invoiceIn = new InvoiceIn();
        invoiceIn.setName("invoicein_" + randomString(3) + "_" + new Date().getTime());
        invoiceIn.setVatEnabled(true);
        invoiceIn.setVatIncluded(true);
        invoiceIn.setMoment(LocalDateTime.now());
        invoiceIn.setSum(randomLong(10, 10000));
        invoiceIn.setOrganization(simpleEntityManager.getOwnOrganization());
        invoiceIn.setAgent(simpleEntityManager.createSimpleCounterparty());
        invoiceIn.setStore(simpleEntityManager.getMainStore());
        api.entity().invoicein().create(invoiceIn);

        ListEntity<InvoiceIn> updatedEntitiesList = api.entity().invoicein().get(filterEq("name", invoiceIn.getName()));
        assertEquals(1, updatedEntitiesList.getRows().size());

        InvoiceIn retrievedEntity = updatedEntitiesList.getRows().get(0);
        assertEquals(invoiceIn.getName(), retrievedEntity.getName());
        assertEquals(invoiceIn.getVatEnabled(), retrievedEntity.getVatEnabled());
        assertEquals(invoiceIn.getVatIncluded(), retrievedEntity.getVatIncluded());
        assertEquals(invoiceIn.getMoment(), retrievedEntity.getMoment());
        assertEquals(invoiceIn.getSum(), retrievedEntity.getSum());
        assertEquals(invoiceIn.getOrganization().getMeta().getHref(), retrievedEntity.getOrganization().getMeta().getHref());
        assertEquals(invoiceIn.getAgent().getMeta().getHref(), retrievedEntity.getAgent().getMeta().getHref());
        assertEquals(invoiceIn.getStore().getMeta().getHref(), retrievedEntity.getStore().getMeta().getHref());
    }

    @Test
    public void metadataTest() throws IOException, ApiClientException {
        MetadataAttributeSharedStatesResponse<DocumentAttribute> response = api.entity().invoicein().metadata().get();

        assertFalse(response.getCreateShared());
    }

    @Test
    public void attributesTest() throws IOException, ApiClientException{
        ListEntity<Attribute> attributes = api.entity().commissionreportin().metadataAttributes();
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
        DocumentAttribute created = api.entity().commissionreportin().createMetadataAttribute(attribute);
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
        DocumentAttribute created = api.entity().commissionreportin().createMetadataAttribute(attribute);

        String name = "field" + randomString(3) + "_" + new Date().getTime();
        created.setName(name);
        created.setRequired(false);
        created.setShow(false);
        DocumentAttribute updated = api.entity().commissionreportin().updateMetadataAttribute(created);
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
        DocumentAttribute created = api.entity().commissionreportin().createMetadataAttribute(attribute);

        api.entity().commissionreportin().deleteMetadataAttribute(created);

        try {
            api.entity().commissionreportin().metadataAttributes(created.getId());
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

        api.entity().invoicein().states().create(state);

        List<State> retrievedStates = api.entity().invoicein().metadata().get().getStates();

        State retrievedState = retrievedStates.stream().filter(s -> s.getId().equals(state.getId())).findFirst().orElse(null);
        assertNotNull(retrievedState);
        assertEquals(state.getName(), retrievedState.getName());
        assertEquals(state.getStateType(), retrievedState.getStateType());
        assertEquals(state.getColor(), retrievedState.getColor());
        assertEquals(state.getEntityType(), retrievedState.getEntityType());
    }

    @Override
    protected void getAsserts(MetaEntity originalEntity, MetaEntity retrievedEntity) {
        InvoiceIn originalInvoiceIn = (InvoiceIn) originalEntity;
        InvoiceIn retrievedInvoiceIn = (InvoiceIn) retrievedEntity;

        assertEquals(originalInvoiceIn.getName(), retrievedInvoiceIn.getName());
        assertEquals(originalInvoiceIn.getOrganization().getMeta().getHref(), retrievedInvoiceIn.getOrganization().getMeta().getHref());
        assertEquals(originalInvoiceIn.getAgent().getMeta().getHref(), retrievedInvoiceIn.getAgent().getMeta().getHref());
    }

    @Override
    protected void putAsserts(MetaEntity originalEntity, MetaEntity updatedEntity, Object changedField) {
        InvoiceIn originalInvoiceIn = (InvoiceIn) originalEntity;
        InvoiceIn updatedInvoiceIn = (InvoiceIn) updatedEntity;

        assertNotEquals(originalInvoiceIn.getName(), updatedInvoiceIn.getName());
        assertEquals(changedField, updatedInvoiceIn.getName());
        assertEquals(originalInvoiceIn.getOrganization().getMeta().getHref(), updatedInvoiceIn.getOrganization().getMeta().getHref());
        assertEquals(originalInvoiceIn.getAgent().getMeta().getHref(), updatedInvoiceIn.getAgent().getMeta().getHref());
    }


    @Override
    public EntityClientBase entityClient() {
        return api.entity().invoicein();
    }

    @Override
    public Class<? extends MetaEntity> entityClass() {
        return InvoiceIn.class;
    }
}
