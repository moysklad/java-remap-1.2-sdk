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
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static ru.moysklad.remap_1_2.utils.params.FilterParam.filterEq;
import static org.junit.Assert.*;

public class SupplyTest extends DocumentWithPositionsTestBase {
    @Test
    public void createTest() throws IOException, ApiClientException {
        Supply supply = new Supply();
        supply.setName("supply_" + randomString(3) + "_" + new Date().getTime());
        supply.setDescription(randomString());
        supply.setVatEnabled(true);
        supply.setVatIncluded(true);
        supply.setMoment(LocalDateTime.now());
        supply.setOrganization(simpleEntityManager.getOwnOrganization());
        supply.setAgent(simpleEntityManager.createSimpleCounterparty());
        supply.setStore(simpleEntityManager.getMainStore());

        api.entity().supply().create(supply);

        ListEntity<Supply> updatedEntitiesList = api.entity().supply().get(filterEq("name", supply.getName()));
        assertEquals(1, updatedEntitiesList.getRows().size());

        Supply retrievedEntity = updatedEntitiesList.getRows().get(0);
        assertEquals(supply.getName(), retrievedEntity.getName());
        assertEquals(supply.getDescription(), retrievedEntity.getDescription());
        assertEquals(supply.getVatEnabled(), retrievedEntity.getVatEnabled());
        assertEquals(supply.getVatIncluded(), retrievedEntity.getVatIncluded());
        assertEquals(supply.getMoment(), retrievedEntity.getMoment());
        assertEquals(supply.getOrganization().getMeta().getHref(), retrievedEntity.getOrganization().getMeta().getHref());
        assertEquals(supply.getAgent().getMeta().getHref(), retrievedEntity.getAgent().getMeta().getHref());
        assertEquals(supply.getStore().getMeta().getHref(), retrievedEntity.getStore().getMeta().getHref());
    }

    @Test
    public void metadataTest() throws IOException, ApiClientException {
        MetadataAttributeSharedStatesResponse<DocumentAttribute> response = api.entity().supply().metadata().get();

        assertFalse(response.getCreateShared());
    }

    @Test
    public void attributesTest() throws IOException, ApiClientException{
        ListEntity<Attribute> attributes = api.entity().supply().metadataAttributes();
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
        DocumentAttribute created = api.entity().supply().createMetadataAttribute(attribute);
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
        DocumentAttribute created = api.entity().supply().createMetadataAttribute(attribute);

        String name = "field" + randomString(3) + "_" + new Date().getTime();
        created.setName(name);
        created.setRequired(false);
        created.setShow(false);
        DocumentAttribute updated = api.entity().supply().updateMetadataAttribute(created);
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
        DocumentAttribute created = api.entity().supply().createMetadataAttribute(attribute);

        api.entity().supply().deleteMetadataAttribute(created);

        try {
            api.entity().supply().metadataAttributes(created.getId());
        } catch (ApiClientException e) {
            assertEquals(404, e.getStatusCode());
        }
    }

    @Test
    public void newTest() throws IOException, ApiClientException {
        Supply supply = api.entity().supply().templateDocument();
        LocalDateTime time = LocalDateTime.now();

        assertEquals("", supply.getName());
        assertTrue(supply.getVatEnabled());
        assertTrue(supply.getVatIncluded());
        assertEquals(Long.valueOf(0), supply.getPayedSum());
        assertEquals(Long.valueOf(0), supply.getSum());
        assertFalse(supply.getShared());
        assertTrue(supply.getApplicable());
        assertFalse(supply.getPublished());
        assertFalse(supply.getPrinted());
        assertTrue(ChronoUnit.MILLIS.between(time, supply.getMoment()) < 1000);

        assertEquals(supply.getOrganization().getMeta().getHref(), simpleEntityManager.getOwnOrganization().getMeta().getHref());
        assertEquals(supply.getStore().getMeta().getHref(), simpleEntityManager.getMainStore().getMeta().getHref());
        assertEquals(supply.getGroup().getMeta().getHref(), simpleEntityManager.getMainGroup().getMeta().getHref());
    }

    @Test
    public void newByPurchaseOrderTest() throws IOException, ApiClientException {
        PurchaseOrder purchaseOrder = simpleEntityManager.createSimple(PurchaseOrder.class);

        Supply supply = api.entity().supply().templateDocument("purchaseOrder", purchaseOrder);
        LocalDateTime time = LocalDateTime.now();

        assertEquals("", supply.getName());
        assertEquals(purchaseOrder.getVatEnabled(), supply.getVatEnabled());
        assertEquals(purchaseOrder.getVatIncluded(), supply.getVatIncluded());
        assertEquals(purchaseOrder.getPayedSum(), supply.getPayedSum());
        assertEquals(purchaseOrder.getSum(), supply.getSum());
        assertFalse(supply.getShared());
        assertTrue(supply.getApplicable());
        assertTrue(ChronoUnit.MILLIS.between(time, supply.getMoment()) < 1000);
        assertEquals(purchaseOrder.getMeta().getHref(), supply.getPurchaseOrder().getMeta().getHref());
        assertEquals(purchaseOrder.getAgent().getMeta().getHref(), supply.getAgent().getMeta().getHref());
        assertEquals(purchaseOrder.getGroup().getMeta().getHref(), supply.getGroup().getMeta().getHref());
        assertEquals(purchaseOrder.getOrganization().getMeta().getHref(), supply.getOrganization().getMeta().getHref());
    }

    @Test
    public void newByInvoicesInTest() throws IOException, ApiClientException {
        InvoiceIn invoiceIn = simpleEntityManager.createSimple(InvoiceIn.class);

        Supply supply = api.entity().supply().templateDocument("invoicesIn", Collections.singletonList(invoiceIn));
        LocalDateTime time = LocalDateTime.now();

        assertEquals("", supply.getName());
        assertEquals(invoiceIn.getVatEnabled(), supply.getVatEnabled());
        assertEquals(invoiceIn.getVatIncluded(), supply.getVatIncluded());
        assertEquals(invoiceIn.getPayedSum(), supply.getPayedSum());
        assertEquals(invoiceIn.getSum(), supply.getSum());
        assertFalse(supply.getShared());
        assertTrue(supply.getApplicable());
        assertTrue(ChronoUnit.MILLIS.between(time, supply.getMoment()) < 1000);
        assertEquals(1, supply.getInvoicesIn().size());
        assertEquals(invoiceIn.getMeta().getHref(), supply.getInvoicesIn().get(0).getMeta().getHref());
        assertEquals(invoiceIn.getAgent().getMeta().getHref(), supply.getAgent().getMeta().getHref());
        assertEquals(invoiceIn.getGroup().getMeta().getHref(), supply.getGroup().getMeta().getHref());
        assertEquals(invoiceIn.getOrganization().getMeta().getHref(), supply.getOrganization().getMeta().getHref());
    }

    @Test
    public void createStateTest() throws IOException, ApiClientException {
        State state = new State();
        state.setName("state_" + randomStringTail());
        state.setStateType(State.StateType.regular);
        state.setColor(randomColor());

        api.entity().supply().states().create(state);

        List<State> retrievedStates = api.entity().supply().metadata().get().getStates();

        State retrievedState = retrievedStates.stream().filter(s -> s.getId().equals(state.getId())).findFirst().orElse(null);
        assertNotNull(retrievedState);
        assertEquals(state.getName(), retrievedState.getName());
        assertEquals(state.getStateType(), retrievedState.getStateType());
        assertEquals(state.getColor(), retrievedState.getColor());
        assertEquals(state.getEntityType(), retrievedState.getEntityType());
    }

    @Override
    protected void getAsserts(MetaEntity originalEntity, MetaEntity retrievedEntity) {
        Supply originalSupply = (Supply) originalEntity;
        Supply retrievedSupply = (Supply) retrievedEntity;

        assertEquals(originalSupply.getName(), retrievedSupply.getName());
        assertEquals(originalSupply.getDescription(), retrievedSupply.getDescription());
        assertEquals(originalSupply.getOrganization().getMeta().getHref(), retrievedSupply.getOrganization().getMeta().getHref());
        assertEquals(originalSupply.getAgent().getMeta().getHref(), retrievedSupply.getAgent().getMeta().getHref());
        assertEquals(originalSupply.getStore().getMeta().getHref(), retrievedSupply.getStore().getMeta().getHref());
    }

    @Override
    protected void putAsserts(MetaEntity originalEntity, MetaEntity updatedEntity, Object changedField) {
        Supply originalSupply = (Supply) originalEntity;
        Supply updatedSupply = (Supply) updatedEntity;

        assertNotEquals(originalSupply.getName(), updatedSupply.getName());
        assertEquals(changedField, updatedSupply.getName());
        assertEquals(originalSupply.getDescription(), updatedSupply.getDescription());
        assertEquals(originalSupply.getOrganization().getMeta().getHref(), updatedSupply.getOrganization().getMeta().getHref());
        assertEquals(originalSupply.getAgent().getMeta().getHref(), updatedSupply.getAgent().getMeta().getHref());
        assertEquals(originalSupply.getStore().getMeta().getHref(), updatedSupply.getStore().getMeta().getHref());
    }

    @Override
    public EntityClientBase entityClient() {
        return api.entity().supply();
    }

    @Override
    public Class<? extends MetaEntity> entityClass() {
        return Supply.class;
    }
}
