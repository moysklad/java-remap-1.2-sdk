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

    @Override
    protected void getAsserts(MetaEntity originalEntity, MetaEntity retrievedEntity) {
        MoveDocumentEntity originalMove = (MoveDocumentEntity) originalEntity;
        MoveDocumentEntity retrievedMove = (MoveDocumentEntity) retrievedEntity;

        assertEquals(originalMove.getName(), retrievedMove.getName());
        assertEquals(originalMove.getDescription(), retrievedMove.getDescription());
        assertEquals(originalMove.getOrganization().getMeta().getHref(), retrievedMove.getOrganization().getMeta().getHref());
        assertEquals(originalMove.getSourceStore().getMeta().getHref(), retrievedMove.getSourceStore().getMeta().getHref());
        assertEquals(originalMove.getTargetStore().getMeta().getHref(), retrievedMove.getTargetStore().getMeta().getHref());
    }

    @Override
    protected void putAsserts(MetaEntity originalEntity, MetaEntity updatedEntity, Object changedField) {
        MoveDocumentEntity originalMove = (MoveDocumentEntity) originalEntity;
        MoveDocumentEntity updatedMove = (MoveDocumentEntity) updatedEntity;

        assertNotEquals(originalMove.getName(), updatedMove.getName());
        assertEquals(changedField, updatedMove.getName());
        assertEquals(originalMove.getDescription(), updatedMove.getDescription());
        assertEquals(originalMove.getOrganization().getMeta().getHref(), updatedMove.getOrganization().getMeta().getHref());
        assertEquals(originalMove.getSourceStore().getMeta().getHref(), updatedMove.getSourceStore().getMeta().getHref());
        assertEquals(originalMove.getTargetStore().getMeta().getHref(), updatedMove.getTargetStore().getMeta().getHref());
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