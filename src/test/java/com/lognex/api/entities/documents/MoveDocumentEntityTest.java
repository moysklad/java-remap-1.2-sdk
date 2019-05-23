package com.lognex.api.entities.documents;

import com.lognex.api.entities.EntityTestBase;
import com.lognex.api.entities.StoreEntity;
import com.lognex.api.entities.agents.OrganizationEntity;
import com.lognex.api.responses.ListEntity;
import com.lognex.api.responses.metadata.MetadataAttributeSharedStatesResponse;
import com.lognex.api.utils.LognexApiException;
import org.junit.Test;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Date;

import static com.lognex.api.utils.params.FilterParam.filterEq;
import static org.junit.Assert.*;

public class MoveDocumentEntityTest extends EntityTestBase {
    @Test
    public void createTest() throws IOException, LognexApiException {
        MoveDocumentEntity e = new MoveDocumentEntity();
        e.setName("move_" + randomString(3) + "_" + new Date().getTime());
        e.setDescription(randomString());
        e.setMoment(LocalDateTime.now());

        ListEntity<OrganizationEntity> orgList = api.entity().organization().get();
        assertNotEquals(0, orgList.getRows().size());
        e.setOrganization(orgList.getRows().get(0));

        ListEntity<StoreEntity> sourceStore = api.entity().store().get(filterEq("name", "Основной склад"));
        assertEquals(1, sourceStore.getRows().size());
        e.setSourceStore(sourceStore.getRows().get(0));

        StoreEntity targetStore = new StoreEntity();
        targetStore.setName(randomString());
        api.entity().store().post(targetStore);
        e.setTargetStore(targetStore);

        api.entity().move().post(e);

        ListEntity<MoveDocumentEntity> updatedEntitiesList = api.entity().move().get(filterEq("name", e.getName()));
        assertEquals(1, updatedEntitiesList.getRows().size());

        MoveDocumentEntity retrievedEntity = updatedEntitiesList.getRows().get(0);
        assertEquals(e.getName(), retrievedEntity.getName());
        assertEquals(e.getDescription(), retrievedEntity.getDescription());
        assertEquals(e.getMoment(), retrievedEntity.getMoment());
        assertEquals(e.getOrganization().getMeta().getHref(), retrievedEntity.getOrganization().getMeta().getHref());
        assertEquals(e.getSourceStore().getMeta().getHref(), retrievedEntity.getSourceStore().getMeta().getHref());
        assertEquals(e.getTargetStore().getMeta().getHref(), retrievedEntity.getTargetStore().getMeta().getHref());
        assertEquals(e.getCreated().withNano(0), retrievedEntity.getCreated().withNano(0));
        assertEquals(e.getUpdated().withNano(0), retrievedEntity.getUpdated().withNano(0));
    }

    @Test
    public void getTest() throws IOException, LognexApiException {
        MoveDocumentEntity e = createSimpleDocumentMove();

        MoveDocumentEntity retrievedEntity = api.entity().move().get(e.getId());
        getAsserts(e, retrievedEntity);

        retrievedEntity = api.entity().move().get(e);
        getAsserts(e, retrievedEntity);
    }

    @Test
    public void putTest() throws IOException, LognexApiException, InterruptedException {
        MoveDocumentEntity e = createSimpleDocumentMove();

        MoveDocumentEntity retrievedOriginalEntity = api.entity().move().get(e.getId());
        String name = "move_" + randomString(3) + "_" + new Date().getTime();
        e.setName(name);
        Thread.sleep(1500);
        api.entity().move().put(e.getId(), e);
        putAsserts(e, retrievedOriginalEntity, name);

        retrievedOriginalEntity.set(e);

        name = "move_" + randomString(3) + "_" + new Date().getTime();
        e.setName(name);
        Thread.sleep(1500);
        api.entity().move().put(e);
        putAsserts(e, retrievedOriginalEntity, name);
    }

    @Test
    public void deleteTest() throws IOException, LognexApiException {
        MoveDocumentEntity e = createSimpleDocumentMove();

        ListEntity<MoveDocumentEntity> entitiesList = api.entity().move().get(filterEq("name", e.getName()));
        assertEquals(1, entitiesList.getRows().size());

        api.entity().move().delete(e.getId());

        entitiesList = api.entity().move().get(filterEq("name", e.getName()));
        assertEquals(0, entitiesList.getRows().size());
    }

    @Test
    public void deleteByIdTest() throws IOException, LognexApiException {
        MoveDocumentEntity e = createSimpleDocumentMove();

        ListEntity<MoveDocumentEntity> entitiesList = api.entity().move().get(filterEq("name", e.getName()));
        assertEquals(1, entitiesList.getRows().size());

        api.entity().move().delete(e);

        entitiesList = api.entity().move().get(filterEq("name", e.getName()));
        assertEquals(0, entitiesList.getRows().size());
    }

    @Test
    public void metadataTest() throws IOException, LognexApiException {
        MetadataAttributeSharedStatesResponse response = api.entity().move().metadata().get();

        assertFalse(response.getCreateShared());
    }

    // не работает, так как post не указывает заголовок Content-type при пустом теле
    @Test
    public void newTest() throws IOException, LognexApiException {
        LocalDateTime time = LocalDateTime.now().withNano(0);
        MoveDocumentEntity e = api.entity().move().newDocument();

        assertEquals("", e.getName());
        assertEquals(Long.valueOf(0), e.getSum());
        assertFalse(e.getShared());
        assertTrue(e.getApplicable());
        assertEquals(time, e.getCreated().withNano(0));

        ListEntity<OrganizationEntity> org = api.entity().organization().get(filterEq("name", "Администратор"));
        assertEquals(1, org.getRows().size());
        assertEquals(e.getOrganization().getMeta().getHref(), org.getRows().get(0).getMeta().getHref());
    }

    private MoveDocumentEntity createSimpleDocumentMove() throws IOException, LognexApiException {
        MoveDocumentEntity e = new MoveDocumentEntity();
        e.setName("move_" + randomString(3) + "_" + new Date().getTime());
        e.setDescription(randomString());

        ListEntity<OrganizationEntity> orgList = api.entity().organization().get();
        assertNotEquals(0, orgList.getRows().size());
        e.setOrganization(orgList.getRows().get(0));

        ListEntity<StoreEntity> sourceStore = api.entity().store().get(filterEq("name", "Основной склад"));
        assertEquals(1, sourceStore.getRows().size());
        e.setSourceStore(sourceStore.getRows().get(0));

        StoreEntity targetStore = new StoreEntity();
        targetStore.setName(randomString());
        api.entity().store().post(targetStore);
        e.setTargetStore(targetStore);

        api.entity().move().post(e);

        return e;
    }

    private void getAsserts(MoveDocumentEntity e, MoveDocumentEntity retrievedEntity) {
        assertEquals(e.getName(), retrievedEntity.getName());
        assertEquals(e.getDescription(), retrievedEntity.getDescription());
        assertEquals(e.getOrganization().getMeta().getHref(), retrievedEntity.getOrganization().getMeta().getHref());
        assertEquals(e.getSourceStore().getMeta().getHref(), retrievedEntity.getSourceStore().getMeta().getHref());
        assertEquals(e.getTargetStore().getMeta().getHref(), retrievedEntity.getTargetStore().getMeta().getHref());
        assertEquals(e.getCreated().withNano(0), retrievedEntity.getCreated().withNano(0));
        assertEquals(e.getUpdated().withNano(0), retrievedEntity.getUpdated().withNano(0));
    }

    private void putAsserts(MoveDocumentEntity e, MoveDocumentEntity retrievedOriginalEntity, String name) throws IOException, LognexApiException {
        MoveDocumentEntity retrievedUpdatedEntity = api.entity().move().get(e.getId());

        assertNotEquals(retrievedOriginalEntity.getName(), retrievedUpdatedEntity.getName());
        assertEquals(name, retrievedUpdatedEntity.getName());
        assertEquals(retrievedOriginalEntity.getDescription(), retrievedUpdatedEntity.getDescription());
        assertEquals(retrievedOriginalEntity.getOrganization().getMeta().getHref(), retrievedUpdatedEntity.getOrganization().getMeta().getHref());
        assertEquals(retrievedOriginalEntity.getSourceStore().getMeta().getHref(), retrievedUpdatedEntity.getSourceStore().getMeta().getHref());
        assertEquals(retrievedOriginalEntity.getTargetStore().getMeta().getHref(), retrievedUpdatedEntity.getTargetStore().getMeta().getHref());
        assertEquals(retrievedOriginalEntity.getCreated().withNano(0), retrievedUpdatedEntity.getCreated().withNano(0));
        assertNotEquals(retrievedOriginalEntity.getUpdated().withNano(0), retrievedUpdatedEntity.getUpdated().withNano(0));
    }
}
