package ru.moysklad.remap_1_2.entities.documents;

import org.junit.Test;
import ru.moysklad.remap_1_2.clients.EntityClientBase;
import ru.moysklad.remap_1_2.entities.*;
import ru.moysklad.remap_1_2.entities.Meta;
import ru.moysklad.remap_1_2.entities.MetaEntity;
import ru.moysklad.remap_1_2.responses.ListEntity;
import ru.moysklad.remap_1_2.responses.metadata.MetadataAttributeSharedStatesResponse;
import ru.moysklad.remap_1_2.utils.ApiClientException;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.Date;

import static ru.moysklad.remap_1_2.utils.params.FilterParam.filterEq;
import static org.junit.Assert.*;

public class DemandTest extends DocumentWithPositionsTestBase {
    @Test
    public void createTest() throws IOException, ApiClientException {
        Demand demand = new Demand();
        demand.setName("demand_" + randomString(3) + "_" + new Date().getTime());
        demand.setDescription(randomString());
        demand.setVatEnabled(true);
        demand.setVatIncluded(true);
        demand.setMoment(LocalDateTime.now());
        demand.setOrganization(simpleEntityManager.getOwnOrganization());
        demand.setAgent(simpleEntityManager.createSimpleCounterparty());
        demand.setStore(simpleEntityManager.getMainStore());

        api.entity().demand().create(demand);

        ListEntity<Demand> updatedEntitiesList = api.entity().demand().get(filterEq("name", demand.getName()));
        assertEquals(1, updatedEntitiesList.getRows().size());

        Demand retrievedEntity = updatedEntitiesList.getRows().get(0);
        assertEquals(demand.getName(), retrievedEntity.getName());
        assertEquals(demand.getDescription(), retrievedEntity.getDescription());
        assertEquals(demand.getVatEnabled(), retrievedEntity.getVatEnabled());
        assertEquals(demand.getVatIncluded(), retrievedEntity.getVatIncluded());
        assertEquals(demand.getMoment(), retrievedEntity.getMoment());
        assertEquals(demand.getOrganization().getMeta().getHref(), retrievedEntity.getOrganization().getMeta().getHref());
        assertEquals(demand.getAgent().getMeta().getHref(), retrievedEntity.getAgent().getMeta().getHref());
        assertEquals(demand.getStore().getMeta().getHref(), retrievedEntity.getStore().getMeta().getHref());
    }

    @Test
    public void metadataTest() throws IOException, ApiClientException {
        MetadataAttributeSharedStatesResponse<DocumentAttribute> response = api.entity().demand().metadata().get();

        assertFalse(response.getCreateShared());
    }


    @Test
    public void attributesTest() throws IOException, ApiClientException{
        ListEntity<Attribute> attributes = api.entity().demand().metadataAttributes();
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
        DocumentAttribute created = api.entity().demand().createMetadataAttribute(attribute);
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
        DocumentAttribute created = api.entity().demand().createMetadataAttribute(attribute);

        String name = "field" + randomString(3) + "_" + new Date().getTime();
        created.setName(name);
        created.setRequired(false);
        created.setShow(false);
        DocumentAttribute updated = api.entity().demand().updateMetadataAttribute(created);
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
        DocumentAttribute created = api.entity().demand().createMetadataAttribute(attribute);

        api.entity().demand().deleteMetadataAttribute(created);

        try {
            api.entity().demand().metadataAttributes(created.getId());
        } catch (ApiClientException e) {
            assertEquals(404, e.getStatusCode());
        }
    }

    @Test
    public void newTest() throws IOException, ApiClientException {
        Demand demand = api.entity().demand().templateDocument();
        LocalDateTime time = LocalDateTime.now();

        assertEquals("", demand.getName());
        assertTrue(demand.getVatEnabled());
        assertTrue(demand.getVatIncluded());
        assertEquals(Long.valueOf(0), demand.getPayedSum());
        assertEquals(Long.valueOf(0), demand.getSum());
        assertFalse(demand.getShared());
        assertTrue(demand.getApplicable());
        assertFalse(demand.getPublished());
        assertFalse(demand.getPrinted());
        assertTrue(ChronoUnit.MILLIS.between(time, demand.getMoment()) < 1000);

        assertEquals(demand.getOrganization().getMeta().getHref(), simpleEntityManager.getOwnOrganization().getMeta().getHref());
        assertEquals(demand.getStore().getMeta().getHref(), simpleEntityManager.getMainStore().getMeta().getHref());
        assertEquals(demand.getGroup().getMeta().getHref(), simpleEntityManager.getMainGroup().getMeta().getHref());
    }

    @Test
    public void newByCustomerOrderTest() throws IOException, ApiClientException {
        CustomerOrder customerOrder = simpleEntityManager.createSimple(CustomerOrder.class);

        Demand demand = api.entity().demand().templateDocument("customerOrder", customerOrder);
        LocalDateTime time = LocalDateTime.now();

        assertEquals("", demand.getName());
        assertEquals(customerOrder.getVatEnabled(), demand.getVatEnabled());
        assertEquals(customerOrder.getVatIncluded(), demand.getVatIncluded());
        assertEquals(customerOrder.getPayedSum(), demand.getPayedSum());
        assertEquals(customerOrder.getSum(), demand.getSum());
        assertFalse(demand.getShared());
        assertTrue(demand.getApplicable());
        assertTrue(ChronoUnit.MILLIS.between(time, demand.getMoment()) < 1000);
        assertEquals(customerOrder.getMeta().getHref(), demand.getCustomerOrder().getMeta().getHref());
        assertEquals(customerOrder.getAgent().getMeta().getHref(), demand.getAgent().getMeta().getHref());
        assertEquals(customerOrder.getGroup().getMeta().getHref(), demand.getGroup().getMeta().getHref());
        assertEquals(customerOrder.getOrganization().getMeta().getHref(), demand.getOrganization().getMeta().getHref());
    }

    @Test
    public void newByInvoicesOutTest() throws IOException, ApiClientException {
        InvoiceOut invoiceOut = simpleEntityManager.createSimple(InvoiceOut.class);

        Demand demand = api.entity().demand().templateDocument("invoicesOut", Collections.singletonList(invoiceOut));
        LocalDateTime time = LocalDateTime.now();

        assertEquals("", demand.getName());
        assertEquals(invoiceOut.getVatEnabled(), demand.getVatEnabled());
        assertEquals(invoiceOut.getVatIncluded(), demand.getVatIncluded());
        assertEquals(invoiceOut.getPayedSum(), demand.getPayedSum());
        assertEquals(invoiceOut.getSum(), demand.getSum());
        assertFalse(demand.getShared());
        assertTrue(demand.getApplicable());
        assertTrue(ChronoUnit.MILLIS.between(time, demand.getMoment()) < 1000);
        assertEquals(1, demand.getInvoicesOut().size());
        assertEquals(invoiceOut.getMeta().getHref(), demand.getInvoicesOut().get(0).getMeta().getHref());
        assertEquals(invoiceOut.getAgent().getMeta().getHref(), demand.getAgent().getMeta().getHref());
        assertEquals(invoiceOut.getGroup().getMeta().getHref(), demand.getGroup().getMeta().getHref());
        assertEquals(invoiceOut.getOrganization().getMeta().getHref(), demand.getOrganization().getMeta().getHref());
    }

    @Override
    protected void getAsserts(MetaEntity originalEntity, MetaEntity retrievedEntity) {
        Demand originalDemand = (Demand) originalEntity;
        Demand retrievedDemand = (Demand) retrievedEntity;

        assertEquals(originalDemand.getName(), retrievedDemand.getName());
        assertEquals(originalDemand.getDescription(), retrievedDemand.getDescription());
        assertEquals(originalDemand.getOrganization().getMeta().getHref(), retrievedDemand.getOrganization().getMeta().getHref());
        assertEquals(originalDemand.getAgent().getMeta().getHref(), retrievedDemand.getAgent().getMeta().getHref());
        assertEquals(originalDemand.getStore().getMeta().getHref(), retrievedDemand.getStore().getMeta().getHref());
    }

    @Override
    protected void putAsserts(MetaEntity originalEntity, MetaEntity updatedEntity, Object changedField) {
        Demand originalDemand = (Demand) originalEntity;
        Demand updatedDemand = (Demand) updatedEntity;

        assertNotEquals(originalDemand.getName(), updatedDemand.getName());
        assertEquals(changedField, updatedDemand.getName());
        assertEquals(originalDemand.getDescription(), updatedDemand.getDescription());
        assertEquals(originalDemand.getOrganization().getMeta().getHref(), updatedDemand.getOrganization().getMeta().getHref());
        assertEquals(originalDemand.getAgent().getMeta().getHref(), updatedDemand.getAgent().getMeta().getHref());
        assertEquals(originalDemand.getStore().getMeta().getHref(), updatedDemand.getStore().getMeta().getHref());
    }

    @Override
    public EntityClientBase entityClient() {
        return api.entity().demand();
    }

    @Override
    public Class<? extends MetaEntity> entityClass() {
        return Demand.class;
    }
}
