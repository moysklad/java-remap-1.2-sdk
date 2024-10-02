package ru.moysklad.remap_1_2.entities.documents;

import org.junit.Test;
import ru.moysklad.remap_1_2.clients.EntityClientBase;
import ru.moysklad.remap_1_2.entities.*;
import ru.moysklad.remap_1_2.entities.Meta;
import ru.moysklad.remap_1_2.entities.MetaEntity;
import ru.moysklad.remap_1_2.entities.documents.positions.LossDocumentPosition;
import ru.moysklad.remap_1_2.responses.ListEntity;
import ru.moysklad.remap_1_2.responses.metadata.MetadataAttributeSharedStatesResponse;
import ru.moysklad.remap_1_2.utils.ApiClientException;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import static ru.moysklad.remap_1_2.utils.params.FilterParam.filterEq;
import static org.junit.Assert.*;

public class LossTest extends DocumentWithPositionsTestBase {
    @Test
    public void createTest() throws IOException, ApiClientException {
        Loss loss = new Loss();
        loss.setName("loss_" + randomString(3) + "_" + new Date().getTime());
        loss.setDescription(randomString());
        loss.setMoment(LocalDateTime.now());
        loss.setOrganization(simpleEntityManager.getOwnOrganization());
        loss.setStore(simpleEntityManager.getMainStore());

        LossDocumentPosition position = new LossDocumentPosition();
        position.setAssortment(simpleEntityManager.createSimpleProduct());
        position.setReason(randomString());
        position.setQuantity(1.);
        loss.setPositions(new ListEntity<>(position));

        api.entity().loss().create(loss);

        ListEntity<Loss> updatedEntitiesList = api.entity().loss().get(filterEq("name", loss.getName()));
        assertEquals(1, updatedEntitiesList.getRows().size());

        Loss retrievedEntity = updatedEntitiesList.getRows().get(0);
        assertEquals(loss.getName(), retrievedEntity.getName());
        assertEquals(loss.getDescription(), retrievedEntity.getDescription());
        assertEquals(loss.getMoment(), retrievedEntity.getMoment());
        assertEquals(loss.getOrganization().getMeta().getHref(), retrievedEntity.getOrganization().getMeta().getHref());
        assertEquals(loss.getStore().getMeta().getHref(), retrievedEntity.getStore().getMeta().getHref());

        ListEntity<LossDocumentPosition> retrievedPositions = api.entity().loss().getPositions(retrievedEntity.getId());
        assertEquals(1, retrievedPositions.getRows().size());
        assertEquals(position.getReason(), retrievedPositions.getRows().get(0).getReason());
    }

    @Test
    public void metadataTest() throws IOException, ApiClientException {
        MetadataAttributeSharedStatesResponse<DocumentAttribute> response = api.entity().loss().metadata().get();

        assertFalse(response.getCreateShared());
    }

    @Test
    public void attributesTest() throws IOException, ApiClientException{
        ListEntity<Attribute> attributes = api.entity().loss().metadataAttributes();
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
        DocumentAttribute created = api.entity().loss().createMetadataAttribute(attribute);
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
        DocumentAttribute created = api.entity().loss().createMetadataAttribute(attribute);

        String name = "field" + randomString(3) + "_" + new Date().getTime();
        created.setName(name);
        created.setRequired(false);
        created.setShow(false);
        DocumentAttribute updated = api.entity().loss().updateMetadataAttribute(created);
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
        DocumentAttribute created = api.entity().loss().createMetadataAttribute(attribute);

        api.entity().loss().deleteMetadataAttribute(created);

        try {
            api.entity().loss().metadataAttributes(created.getId());
        } catch (ApiClientException e) {
            assertEquals(404, e.getStatusCode());
        }
    }
    @Test
    public void newTest() throws IOException, ApiClientException {
        Loss loss = api.entity().loss().templateDocument();
        LocalDateTime time = LocalDateTime.now();

        assertEquals("", loss.getName());
        assertEquals(Long.valueOf(0), loss.getSum());
        assertFalse(loss.getShared());
        assertTrue(loss.getApplicable());
        assertFalse(loss.getPublished());
        assertFalse(loss.getPrinted());
        assertTrue(ChronoUnit.MILLIS.between(time, loss.getMoment()) < 1000);

        assertEquals(loss.getOrganization().getMeta().getHref(), simpleEntityManager.getOwnOrganization().getMeta().getHref());
        assertEquals(loss.getStore().getMeta().getHref(), simpleEntityManager.getMainStore().getMeta().getHref());
        assertEquals(loss.getGroup().getMeta().getHref(), simpleEntityManager.getMainGroup().getMeta().getHref());
    }

    @Test
    public void newBySalesReturnTest() throws IOException, ApiClientException {
        SalesReturn salesReturn = simpleEntityManager.createSimple(SalesReturn.class);

        Loss loss = api.entity().loss().templateDocument("salesReturn", salesReturn);
        LocalDateTime time = LocalDateTime.now();

        assertEquals("", loss.getName());
        assertEquals(salesReturn.getSum(), loss.getSum());
        assertFalse(loss.getShared());
        assertTrue(loss.getApplicable());
        assertTrue(ChronoUnit.MILLIS.between(time, loss.getMoment()) < 1000);
        assertEquals(salesReturn.getMeta().getHref(), loss.getSalesReturn().getMeta().getHref());
        assertEquals(salesReturn.getStore().getMeta().getHref(), loss.getStore().getMeta().getHref());
        assertEquals(salesReturn.getGroup().getMeta().getHref(), loss.getGroup().getMeta().getHref());
        assertEquals(salesReturn.getOrganization().getMeta().getHref(), loss.getOrganization().getMeta().getHref());
    }

    @Override
    protected void getAsserts(MetaEntity originalEntity, MetaEntity retrievedEntity) {
        Loss originalLoss = (Loss) originalEntity;
        Loss retrievedLoss = (Loss) retrievedEntity;

        assertEquals(originalLoss.getName(), retrievedLoss.getName());
        assertEquals(originalLoss.getDescription(), retrievedLoss.getDescription());
        assertEquals(originalLoss.getOrganization().getMeta().getHref(), retrievedLoss.getOrganization().getMeta().getHref());
        assertEquals(originalLoss.getStore().getMeta().getHref(), retrievedLoss.getStore().getMeta().getHref());
    }

    @Override
    protected void putAsserts(MetaEntity originalEntity, MetaEntity updatedEntity, Object changedField) {
        Loss originalLoss = (Loss) originalEntity;
        Loss updatedLoss = (Loss) updatedEntity;

        assertNotEquals(originalLoss.getName(), updatedLoss.getName());
        assertEquals(changedField, updatedLoss.getName());
        assertEquals(originalLoss.getDescription(), updatedLoss.getDescription());
        assertEquals(originalLoss.getOrganization().getMeta().getHref(), updatedLoss.getOrganization().getMeta().getHref());
        assertEquals(originalLoss.getStore().getMeta().getHref(), updatedLoss.getStore().getMeta().getHref());
    }

    @Override
    public EntityClientBase entityClient() {
        return api.entity().loss();
    }

    @Override
    public Class<? extends MetaEntity> entityClass() {
        return Loss.class;
    }
}
