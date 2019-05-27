package com.lognex.api.entities.documents;

import com.lognex.api.entities.EntityTestBase;
import com.lognex.api.entities.GroupEntity;
import com.lognex.api.entities.StoreEntity;
import com.lognex.api.entities.agents.OrganizationEntity;
import com.lognex.api.responses.ListEntity;
import com.lognex.api.responses.metadata.MetadataAttributeSharedStatesResponse;
import com.lognex.api.utils.LognexApiException;
import org.junit.Test;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.Date;
import java.util.Optional;

import static com.lognex.api.utils.params.FilterParam.filterEq;
import static com.lognex.api.utils.params.SearchParam.search;
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

    @Test
    public void newTest() throws IOException, LognexApiException {
        LocalDateTime time = LocalDateTime.now().withNano(0);
        MoveDocumentEntity e = api.entity().move().newDocument();

        assertEquals("", e.getName());
        assertEquals(Long.valueOf(0), e.getSum());
        assertFalse(e.getShared());
        assertTrue(e.getApplicable());
        assertEquals(time, e.getMoment().withNano(0));

        ListEntity<OrganizationEntity> orgList = api.entity().organization().get();
        Optional<OrganizationEntity> orgOptional = orgList.getRows().stream().
                min(Comparator.comparing(OrganizationEntity::getCreated));

        OrganizationEntity org = null;
        if (orgOptional.isPresent()) {
            org = orgOptional.get();
        } else {
            // Должно быть первое созданное юрлицо
            fail();
        }

        assertEquals(e.getOrganization().getMeta().getHref(), org.getMeta().getHref());

        ListEntity<GroupEntity> group = api.entity().group().get(search("Основной"));
        assertEquals(1, group.getRows().size());
        assertEquals(e.getGroup().getMeta().getHref(), group.getRows().get(0).getMeta().getHref());
    }

    @Test
    public void newByInternalOrderTest() throws IOException, LognexApiException {
        InternalOrderDocumentEntity internalOrder = new InternalOrderDocumentEntity();
        internalOrder.setName("internalorder_" + randomString(3) + "_" + new Date().getTime());
        internalOrder.setVatEnabled(true);
        internalOrder.setVatIncluded(true);

        ListEntity<OrganizationEntity> orgList = api.entity().organization().get();
        assertNotEquals(0, orgList.getRows().size());
        internalOrder.setOrganization(orgList.getRows().get(0));

        ListEntity<StoreEntity> store = api.entity().store().get(filterEq("name", "Основной склад"));
        assertEquals(1, store.getRows().size());
        internalOrder.setStore(store.getRows().get(0));

        api.entity().internalorder().post(internalOrder);

        LocalDateTime time = LocalDateTime.now().withNano(0);
        MoveDocumentEntity e = api.entity().move().newDocument("internalOrder", internalOrder);

        assertEquals("", e.getName());
        assertEquals(internalOrder.getSum(), e.getSum());
        assertFalse(e.getShared());
        assertTrue(e.getApplicable());
        assertEquals(time, e.getMoment().withNano(0));
        assertEquals(internalOrder.getMeta().getHref(), e.getInternalOrder().getMeta().getHref());
        assertEquals(internalOrder.getGroup().getMeta().getHref(), e.getGroup().getMeta().getHref());
        assertEquals(internalOrder.getStore().getMeta().getHref(), e.getTargetStore().getMeta().getHref());
        assertEquals(internalOrder.getOrganization().getMeta().getHref(), e.getOrganization().getMeta().getHref());
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
