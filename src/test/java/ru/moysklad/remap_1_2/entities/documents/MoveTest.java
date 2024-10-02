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
import java.util.Date;

import static ru.moysklad.remap_1_2.utils.params.FilterParam.filterEq;
import static org.junit.Assert.*;

public class MoveTest extends DocumentWithPositionsTestBase {
    @Test
    public void createTest() throws IOException, ApiClientException {
        Move move = new Move();
        move.setName("move_" + randomString(3) + "_" + new Date().getTime());
        move.setDescription(randomString());
        move.setMoment(LocalDateTime.now());
        move.setOrganization(simpleEntityManager.getOwnOrganization());
        move.setSourceStore(simpleEntityManager.getMainStore());
        move.setTargetStore(simpleEntityManager.createSimpleStore());

        api.entity().move().create(move);

        ListEntity<Move> updatedEntitiesList = api.entity().move().get(filterEq("name", move.getName()));
        assertEquals(1, updatedEntitiesList.getRows().size());

        Move retrievedEntity = updatedEntitiesList.getRows().get(0);
        assertEquals(move.getName(), retrievedEntity.getName());
        assertEquals(move.getDescription(), retrievedEntity.getDescription());
        assertEquals(move.getMoment(), retrievedEntity.getMoment());
        assertEquals(move.getOrganization().getMeta().getHref(), retrievedEntity.getOrganization().getMeta().getHref());
        assertEquals(move.getSourceStore().getMeta().getHref(), retrievedEntity.getSourceStore().getMeta().getHref());
        assertEquals(move.getTargetStore().getMeta().getHref(), retrievedEntity.getTargetStore().getMeta().getHref());
    }

    @Test
    public void metadataTest() throws IOException, ApiClientException {
        MetadataAttributeSharedStatesResponse<DocumentAttribute> response = api.entity().move().metadata().get();

        assertFalse(response.getCreateShared());
    }
    
    @Test
    public void attributesTest() throws IOException, ApiClientException{
        ListEntity<Attribute> attributes = api.entity().move().metadataAttributes();
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
        DocumentAttribute created = api.entity().move().createMetadataAttribute(attribute);
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
        DocumentAttribute created = api.entity().move().createMetadataAttribute(attribute);

        String name = "field" + randomString(3) + "_" + new Date().getTime();
        created.setName(name);
        created.setRequired(false);
        attribute.setShow(false);
        DocumentAttribute updated = api.entity().move().updateMetadataAttribute(created);
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
        DocumentAttribute created = api.entity().move().createMetadataAttribute(attribute);

        api.entity().move().deleteMetadataAttribute(created);

        try {
            api.entity().move().metadataAttributes(created.getId());
        } catch (ApiClientException e) {
            assertEquals(404, e.getStatusCode());
        }
    }

    @Test
    public void newTest() throws IOException, ApiClientException {
        Move move = api.entity().move().templateDocument();
        LocalDateTime time = LocalDateTime.now();

        assertEquals("", move.getName());
        assertEquals(Long.valueOf(0), move.getSum());
        assertFalse(move.getShared());
        assertTrue(move.getApplicable());
        assertFalse(move.getPublished());
        assertFalse(move.getPrinted());
        assertTrue(ChronoUnit.MILLIS.between(time, move.getMoment()) < 1000);

        assertEquals(move.getOrganization().getMeta().getHref(), simpleEntityManager.getOwnOrganization().getMeta().getHref());
        assertEquals(move.getGroup().getMeta().getHref(), simpleEntityManager.getMainGroup().getMeta().getHref());
    }

    @Test
    public void newByInternalOrderTest() throws IOException, ApiClientException {
        InternalOrder internalOrder = simpleEntityManager.createSimple(InternalOrder.class);

        Move move = api.entity().move().templateDocument("internalOrder", internalOrder);
        LocalDateTime time = LocalDateTime.now();

        assertEquals("", move.getName());
        assertEquals(internalOrder.getSum(), move.getSum());
        assertFalse(move.getShared());
        assertTrue(move.getApplicable());
        assertTrue(ChronoUnit.MILLIS.between(time, move.getMoment()) < 1000);
        assertEquals(internalOrder.getMeta().getHref(), move.getInternalOrder().getMeta().getHref());
        assertEquals(internalOrder.getGroup().getMeta().getHref(), move.getGroup().getMeta().getHref());
        assertEquals(internalOrder.getStore().getMeta().getHref(), move.getTargetStore().getMeta().getHref());
        assertEquals(internalOrder.getOrganization().getMeta().getHref(), move.getOrganization().getMeta().getHref());
    }

    @Override
    protected void getAsserts(MetaEntity originalEntity, MetaEntity retrievedEntity) {
        Move originalMove = (Move) originalEntity;
        Move retrievedMove = (Move) retrievedEntity;

        assertEquals(originalMove.getName(), retrievedMove.getName());
        assertEquals(originalMove.getDescription(), retrievedMove.getDescription());
        assertEquals(originalMove.getOrganization().getMeta().getHref(), retrievedMove.getOrganization().getMeta().getHref());
        assertEquals(originalMove.getSourceStore().getMeta().getHref(), retrievedMove.getSourceStore().getMeta().getHref());
        assertEquals(originalMove.getTargetStore().getMeta().getHref(), retrievedMove.getTargetStore().getMeta().getHref());
    }

    @Override
    protected void putAsserts(MetaEntity originalEntity, MetaEntity updatedEntity, Object changedField) {
        Move originalMove = (Move) originalEntity;
        Move updatedMove = (Move) updatedEntity;

        assertNotEquals(originalMove.getName(), updatedMove.getName());
        assertEquals(changedField, updatedMove.getName());
        assertEquals(originalMove.getDescription(), updatedMove.getDescription());
        assertEquals(originalMove.getOrganization().getMeta().getHref(), updatedMove.getOrganization().getMeta().getHref());
        assertEquals(originalMove.getSourceStore().getMeta().getHref(), updatedMove.getSourceStore().getMeta().getHref());
        assertEquals(originalMove.getTargetStore().getMeta().getHref(), updatedMove.getTargetStore().getMeta().getHref());
    }

    @Override
    public EntityClientBase entityClient() {
        return api.entity().move();
    }

    @Override
    public Class<? extends MetaEntity> entityClass() {
        return Move.class;
    }
}
