package ru.moysklad.remap_1_2.entities.documents;

import org.junit.Test;
import ru.moysklad.remap_1_2.clients.EntityClientBase;
import ru.moysklad.remap_1_2.entities.*;
import ru.moysklad.remap_1_2.entities.Meta;
import ru.moysklad.remap_1_2.entities.MetaEntity;
import ru.moysklad.remap_1_2.entities.Store;
import ru.moysklad.remap_1_2.entities.agents.Counterparty;
import ru.moysklad.remap_1_2.entities.agents.Organization;
import ru.moysklad.remap_1_2.responses.ListEntity;
import ru.moysklad.remap_1_2.responses.metadata.MetadataAttributeSharedStatesResponse;
import ru.moysklad.remap_1_2.utils.ApiClientException;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import static ru.moysklad.remap_1_2.utils.params.FilterParam.filterEq;
import static org.junit.Assert.*;

public class PurchaseReturnTest extends DocumentWithPositionsTestBase {
    @Test
    public void createTest() throws IOException, ApiClientException {
        PurchaseReturn purchaseReturn = new PurchaseReturn();
        purchaseReturn.setName("purchasereturn_" + randomString(3) + "_" + new Date().getTime());
        purchaseReturn.setDescription(randomString());
        purchaseReturn.setVatEnabled(true);
        purchaseReturn.setVatIncluded(true);
        purchaseReturn.setMoment(LocalDateTime.now());
        Organization organization = simpleEntityManager.getOwnOrganization();
        purchaseReturn.setOrganization(organization);
        Counterparty agent = simpleEntityManager.createSimple(Counterparty.class);
        purchaseReturn.setAgent(agent);
        Store mainStore = simpleEntityManager.getMainStore();
        purchaseReturn.setStore(mainStore);

        Supply supply = new Supply();
        supply.setName("supply_" + randomString(3) + "_" + new Date().getTime());
        supply.setOrganization(organization);
        supply.setAgent(agent);
        supply.setStore(mainStore);

        api.entity().supply().create(supply);
        purchaseReturn.setSupply(supply);

        api.entity().purchasereturn().create(purchaseReturn);

        ListEntity<PurchaseReturn> updatedEntitiesList = api.entity().purchasereturn().get(filterEq("name", purchaseReturn.getName()));
        assertEquals(1, updatedEntitiesList.getRows().size());

        PurchaseReturn retrievedEntity = updatedEntitiesList.getRows().get(0);
        assertEquals(purchaseReturn.getName(), retrievedEntity.getName());
        assertEquals(purchaseReturn.getDescription(), retrievedEntity.getDescription());
        assertEquals(purchaseReturn.getVatEnabled(), retrievedEntity.getVatEnabled());
        assertEquals(purchaseReturn.getVatIncluded(), retrievedEntity.getVatIncluded());
        assertEquals(purchaseReturn.getMoment(), retrievedEntity.getMoment());
        assertEquals(purchaseReturn.getOrganization().getMeta().getHref(), retrievedEntity.getOrganization().getMeta().getHref());
        assertEquals(purchaseReturn.getAgent().getMeta().getHref(), retrievedEntity.getAgent().getMeta().getHref());
        assertEquals(purchaseReturn.getStore().getMeta().getHref(), retrievedEntity.getStore().getMeta().getHref());
        assertEquals(purchaseReturn.getSupply().getMeta().getHref(), retrievedEntity.getSupply().getMeta().getHref());
    }

    @Test
    public void metadataTest() throws IOException, ApiClientException {
        MetadataAttributeSharedStatesResponse<DocumentAttribute> response = api.entity().purchasereturn().metadata().get();

        assertFalse(response.getCreateShared());
    }

    @Test
    public void attributesTest() throws IOException, ApiClientException{
        ListEntity<Attribute> attributes = api.entity().purchasereturn().metadataAttributes();
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
        DocumentAttribute created = api.entity().purchasereturn().createMetadataAttribute(attribute);
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
        DocumentAttribute created = api.entity().purchasereturn().createMetadataAttribute(attribute);

        String name = "field" + randomString(3) + "_" + new Date().getTime();
        created.setName(name);
        created.setRequired(false);
        created.setShow(false);
        DocumentAttribute updated = api.entity().purchasereturn().updateMetadataAttribute(created);
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
        DocumentAttribute created = api.entity().purchasereturn().createMetadataAttribute(attribute);

        api.entity().purchasereturn().deleteMetadataAttribute(created);

        try {
            api.entity().purchasereturn().metadataAttributes(created.getId());
        } catch (ApiClientException e) {
            assertEquals(404, e.getStatusCode());
        }
    }
    
    @Test
    public void newTest() throws IOException, ApiClientException {
        PurchaseReturn purchaseReturn = api.entity().purchasereturn().templateDocument();
        LocalDateTime time = LocalDateTime.now();

        assertEquals("", purchaseReturn.getName());
        assertTrue(purchaseReturn.getVatEnabled());
        assertTrue(purchaseReturn.getVatIncluded());
        assertEquals(Long.valueOf(0), purchaseReturn.getSum());
        assertFalse(purchaseReturn.getShared());
        assertTrue(purchaseReturn.getApplicable());
        assertFalse(purchaseReturn.getPublished());
        assertFalse(purchaseReturn.getPrinted());
        assertTrue(ChronoUnit.MILLIS.between(time, purchaseReturn.getMoment()) < 1000);

        assertEquals(purchaseReturn.getOrganization().getMeta().getHref(), simpleEntityManager.getOwnOrganization().getMeta().getHref());
        assertEquals(purchaseReturn.getStore().getMeta().getHref(), simpleEntityManager.getMainStore().getMeta().getHref());
        assertEquals(purchaseReturn.getGroup().getMeta().getHref(), simpleEntityManager.getMainGroup().getMeta().getHref());
    }

    @Test
    public void newBySupplyTest() throws IOException, ApiClientException {
        Supply supply = simpleEntityManager.createSimple(Supply.class);

        PurchaseReturn purchaseReturn = api.entity().purchasereturn().templateDocument("supply", supply);
        LocalDateTime time = LocalDateTime.now();

        assertEquals("", purchaseReturn.getName());
        assertEquals(supply.getVatEnabled(), purchaseReturn.getVatEnabled());
        assertEquals(supply.getVatIncluded(), purchaseReturn.getVatIncluded());
        assertEquals(supply.getPayedSum(), purchaseReturn.getPayedSum());
        assertEquals(supply.getSum(), purchaseReturn.getSum());
        assertFalse(purchaseReturn.getShared());
        assertTrue(purchaseReturn.getApplicable());
        assertTrue(ChronoUnit.MILLIS.between(time, purchaseReturn.getMoment()) < 1000);
        assertEquals(supply.getMeta().getHref(), purchaseReturn.getSupply().getMeta().getHref());
        assertEquals(supply.getAgent().getMeta().getHref(), purchaseReturn.getAgent().getMeta().getHref());
        assertEquals(supply.getStore().getMeta().getHref(), purchaseReturn.getStore().getMeta().getHref());
        assertEquals(supply.getGroup().getMeta().getHref(), purchaseReturn.getGroup().getMeta().getHref());
        assertEquals(supply.getOrganization().getMeta().getHref(), purchaseReturn.getOrganization().getMeta().getHref());
    }

    @Override
    protected void getAsserts(MetaEntity originalEntity, MetaEntity retrievedEntity) {
        PurchaseReturn originalPurchaseReturn = (PurchaseReturn) originalEntity;
        PurchaseReturn retrievedPurchaseReturn = (PurchaseReturn) retrievedEntity;

        assertEquals(originalPurchaseReturn.getName(), retrievedPurchaseReturn.getName());
        assertEquals(originalPurchaseReturn.getDescription(), retrievedPurchaseReturn.getDescription());
        assertEquals(originalPurchaseReturn.getOrganization().getMeta().getHref(), retrievedPurchaseReturn.getOrganization().getMeta().getHref());
        assertEquals(originalPurchaseReturn.getAgent().getMeta().getHref(), retrievedPurchaseReturn.getAgent().getMeta().getHref());
        assertEquals(originalPurchaseReturn.getStore().getMeta().getHref(), retrievedPurchaseReturn.getStore().getMeta().getHref());
    }

    @Override
    protected void putAsserts(MetaEntity originalEntity, MetaEntity updatedEntity, Object changedField) {
        PurchaseReturn originalPurchaseReturn = (PurchaseReturn) originalEntity;
        PurchaseReturn updatedPurchaseReturn = (PurchaseReturn) updatedEntity;

        assertNotEquals(originalPurchaseReturn.getName(), updatedPurchaseReturn.getName());
        assertEquals(changedField, updatedPurchaseReturn.getName());
        assertEquals(originalPurchaseReturn.getDescription(), updatedPurchaseReturn.getDescription());
        assertEquals(originalPurchaseReturn.getOrganization().getMeta().getHref(), updatedPurchaseReturn.getOrganization().getMeta().getHref());
        assertEquals(originalPurchaseReturn.getAgent().getMeta().getHref(), updatedPurchaseReturn.getAgent().getMeta().getHref());
        assertEquals(originalPurchaseReturn.getStore().getMeta().getHref(), updatedPurchaseReturn.getStore().getMeta().getHref());
    }

    @Override
    public EntityClientBase entityClient() {
        return api.entity().purchasereturn();
    }

    @Override
    public Class<? extends MetaEntity> entityClass() {
        return PurchaseReturn.class;
    }
}
