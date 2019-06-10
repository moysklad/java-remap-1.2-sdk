package com.lognex.api.entities.documents;

import com.lognex.api.clients.ApiClient;
import com.lognex.api.entities.MetaEntity;
import com.lognex.api.responses.ListEntity;
import com.lognex.api.responses.metadata.MetadataAttributeSharedStatesResponse;
import com.lognex.api.utils.LognexApiException;
import org.junit.Test;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

import static com.lognex.api.utils.params.FilterParam.filterEq;
import static org.junit.Assert.*;

public class MoveDocumentEntityTest extends DocumentWithPositionsTestBase {
    @Test
    public void createTest() throws IOException, LognexApiException {
        MoveDocumentEntity move = new MoveDocumentEntity();
        move.setName("move_" + randomString(3) + "_" + new Date().getTime());
        move.setDescription(randomString());
        move.setMoment(LocalDateTime.now());
        move.setOrganization(simpleEntityFactory.getOwnOrganization());
        move.setSourceStore(simpleEntityFactory.getMainStore());
        move.setTargetStore(simpleEntityFactory.createSimpleStore());

        api.entity().move().post(move);

        ListEntity<MoveDocumentEntity> updatedEntitiesList = api.entity().move().get(filterEq("name", move.getName()));
        assertEquals(1, updatedEntitiesList.getRows().size());

        MoveDocumentEntity retrievedEntity = updatedEntitiesList.getRows().get(0);
        assertEquals(move.getName(), retrievedEntity.getName());
        assertEquals(move.getDescription(), retrievedEntity.getDescription());
        assertEquals(move.getMoment(), retrievedEntity.getMoment());
        assertEquals(move.getOrganization().getMeta().getHref(), retrievedEntity.getOrganization().getMeta().getHref());
        assertEquals(move.getSourceStore().getMeta().getHref(), retrievedEntity.getSourceStore().getMeta().getHref());
        assertEquals(move.getTargetStore().getMeta().getHref(), retrievedEntity.getTargetStore().getMeta().getHref());
    }

    @Test
    public void getTest() throws IOException, LognexApiException {
        MoveDocumentEntity move = simpleEntityFactory.createSimpleMove();

        MoveDocumentEntity retrievedEntity = api.entity().move().get(move.getId());
        getAsserts(move, retrievedEntity);

        retrievedEntity = api.entity().move().get(move);
        getAsserts(move, retrievedEntity);
    }

    @Test
    public void putTest() throws IOException, LognexApiException {
        MoveDocumentEntity move = simpleEntityFactory.createSimpleMove();

        MoveDocumentEntity retrievedOriginalEntity = api.entity().move().get(move.getId());
        String name = "move_" + randomString(3) + "_" + new Date().getTime();
        move.setName(name);
        api.entity().move().put(move.getId(), move);
        putAsserts(move, retrievedOriginalEntity, name);

        retrievedOriginalEntity.set(move);

        name = "move_" + randomString(3) + "_" + new Date().getTime();
        move.setName(name);
        api.entity().move().put(move);
        putAsserts(move, retrievedOriginalEntity, name);
    }

    @Test
    public void deleteTest() throws IOException, LognexApiException {
        MoveDocumentEntity move = simpleEntityFactory.createSimpleMove();

        ListEntity<MoveDocumentEntity> entitiesList = api.entity().move().get(filterEq("name", move.getName()));
        assertEquals((Integer) 1, entitiesList.getMeta().getSize());

        api.entity().move().delete(move.getId());

        entitiesList = api.entity().move().get(filterEq("name", move.getName()));
        assertEquals((Integer) 0, entitiesList.getMeta().getSize());
    }

    @Test
    public void deleteByIdTest() throws IOException, LognexApiException {
        MoveDocumentEntity move = simpleEntityFactory.createSimpleMove();

        ListEntity<MoveDocumentEntity> entitiesList = api.entity().move().get(filterEq("name", move.getName()));
        assertEquals((Integer) 1, entitiesList.getMeta().getSize());

        api.entity().move().delete(move);

        entitiesList = api.entity().move().get(filterEq("name", move.getName()));
        assertEquals((Integer) 0, entitiesList.getMeta().getSize());
    }

    @Test
    public void metadataTest() throws IOException, LognexApiException {
        MetadataAttributeSharedStatesResponse response = api.entity().move().metadata().get();

        assertFalse(response.getCreateShared());
    }

    @Test
    public void newTest() throws IOException, LognexApiException {
        MoveDocumentEntity move = api.entity().move().newDocument();
        LocalDateTime time = LocalDateTime.now();

        assertEquals("", move.getName());
        assertEquals(Long.valueOf(0), move.getSum());
        assertFalse(move.getShared());
        assertTrue(move.getApplicable());
        assertTrue(ChronoUnit.MILLIS.between(time, move.getMoment()) < 1000);

        assertEquals(move.getOrganization().getMeta().getHref(), simpleEntityFactory.getOwnOrganization().getMeta().getHref());
        assertEquals(move.getGroup().getMeta().getHref(), simpleEntityFactory.getMainGroup().getMeta().getHref());
    }

    @Test
    public void newByInternalOrderTest() throws IOException, LognexApiException {
        InternalOrderDocumentEntity internalOrder = simpleEntityFactory.createSimpleInternalOrder();

        MoveDocumentEntity move = api.entity().move().newDocument("internalOrder", internalOrder);
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

    private void getAsserts(MoveDocumentEntity move, MoveDocumentEntity retrievedEntity) {
        assertEquals(move.getName(), retrievedEntity.getName());
        assertEquals(move.getDescription(), retrievedEntity.getDescription());
        assertEquals(move.getOrganization().getMeta().getHref(), retrievedEntity.getOrganization().getMeta().getHref());
        assertEquals(move.getSourceStore().getMeta().getHref(), retrievedEntity.getSourceStore().getMeta().getHref());
        assertEquals(move.getTargetStore().getMeta().getHref(), retrievedEntity.getTargetStore().getMeta().getHref());
    }

    private void putAsserts(MoveDocumentEntity move, MoveDocumentEntity retrievedOriginalEntity, String name) throws IOException, LognexApiException {
        MoveDocumentEntity retrievedUpdatedEntity = api.entity().move().get(move.getId());

        assertNotEquals(retrievedOriginalEntity.getName(), retrievedUpdatedEntity.getName());
        assertEquals(name, retrievedUpdatedEntity.getName());
        assertEquals(retrievedOriginalEntity.getDescription(), retrievedUpdatedEntity.getDescription());
        assertEquals(retrievedOriginalEntity.getOrganization().getMeta().getHref(), retrievedUpdatedEntity.getOrganization().getMeta().getHref());
        assertEquals(retrievedOriginalEntity.getSourceStore().getMeta().getHref(), retrievedUpdatedEntity.getSourceStore().getMeta().getHref());
        assertEquals(retrievedOriginalEntity.getTargetStore().getMeta().getHref(), retrievedUpdatedEntity.getTargetStore().getMeta().getHref());
    }

    @Override
    protected ApiClient entityClient() {
        return api.entity().move();
    }

    @Override
    protected Class<? extends MetaEntity> entityClass() {
        return MoveDocumentEntity.class;
    }
}
