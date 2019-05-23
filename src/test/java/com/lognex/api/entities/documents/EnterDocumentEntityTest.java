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

public class EnterDocumentEntityTest extends EntityTestBase {
    @Test
    public void createTest() throws IOException, LognexApiException {
        EnterDocumentEntity e = new EnterDocumentEntity();
        e.setName("enter_" + randomString(3) + "_" + new Date().getTime());
        e.setDescription(randomString());
        e.setMoment(LocalDateTime.now());

        ListEntity<OrganizationEntity> orgList = api.entity().organization().get();
        assertNotEquals(0, orgList.getRows().size());
        e.setOrganization(orgList.getRows().get(0));

        ListEntity<StoreEntity> store = api.entity().store().get(filterEq("name", "Основной склад"));
        assertEquals(1, store.getRows().size());
        e.setStore(store.getRows().get(0));

        api.entity().enter().post(e);

        ListEntity<EnterDocumentEntity> updatedEntitiesList = api.entity().enter().get(filterEq("name", e.getName()));
        assertEquals(1, updatedEntitiesList.getRows().size());

        EnterDocumentEntity retrievedEntity = updatedEntitiesList.getRows().get(0);
        assertEquals(e.getName(), retrievedEntity.getName());
        assertEquals(e.getDescription(), retrievedEntity.getDescription());
        assertEquals(e.getMoment(), retrievedEntity.getMoment());
        assertEquals(e.getOrganization().getMeta().getHref(), retrievedEntity.getOrganization().getMeta().getHref());
        assertEquals(e.getStore().getMeta().getHref(), retrievedEntity.getStore().getMeta().getHref());
        assertEquals(e.getCreated().withNano(0), retrievedEntity.getCreated().withNano(0));
        assertEquals(e.getUpdated().withNano(0), retrievedEntity.getUpdated().withNano(0));
    }

    @Test
    public void getTest() throws IOException, LognexApiException {
        EnterDocumentEntity e = createSimpleDocumentEnter();

        EnterDocumentEntity retrievedEntity = api.entity().enter().get(e.getId());
        getAsserts(e, retrievedEntity);

        retrievedEntity = api.entity().enter().get(e);
        getAsserts(e, retrievedEntity);
    }

    @Test
    public void putTest() throws IOException, LognexApiException, InterruptedException {
        EnterDocumentEntity e = createSimpleDocumentEnter();

        EnterDocumentEntity retrievedOriginalEntity = api.entity().enter().get(e.getId());
        String name = "enter_" + randomString(3) + "_" + new Date().getTime();
        e.setName(name);
        Thread.sleep(1500);
        api.entity().enter().put(e.getId(), e);
        putAsserts(e, retrievedOriginalEntity, name);

        retrievedOriginalEntity.set(e);

        name = "enter_" + randomString(3) + "_" + new Date().getTime();
        e.setName(name);
        Thread.sleep(1500);
        api.entity().enter().put(e);
        putAsserts(e, retrievedOriginalEntity, name);
    }

    @Test
    public void deleteTest() throws IOException, LognexApiException {
        EnterDocumentEntity e = createSimpleDocumentEnter();

        ListEntity<EnterDocumentEntity> entitiesList = api.entity().enter().get(filterEq("name", e.getName()));
        assertEquals(1, entitiesList.getRows().size());

        api.entity().enter().delete(e.getId());

        entitiesList = api.entity().enter().get(filterEq("name", e.getName()));
        assertEquals(0, entitiesList.getRows().size());
    }

    @Test
    public void deleteByIdTest() throws IOException, LognexApiException {
        EnterDocumentEntity e = createSimpleDocumentEnter();

        ListEntity<EnterDocumentEntity> entitiesList = api.entity().enter().get(filterEq("name", e.getName()));
        assertEquals(1, entitiesList.getRows().size());

        api.entity().enter().delete(e);

        entitiesList = api.entity().enter().get(filterEq("name", e.getName()));
        assertEquals(0, entitiesList.getRows().size());
    }

    @Test
    public void metadataTest() throws IOException, LognexApiException {
        MetadataAttributeSharedStatesResponse response = api.entity().enter().metadata().get();

        assertFalse(response.getCreateShared());
    }

    private EnterDocumentEntity createSimpleDocumentEnter() throws IOException, LognexApiException {
        EnterDocumentEntity e = new EnterDocumentEntity();
        e.setName("enter_" + randomString(3) + "_" + new Date().getTime());
        e.setDescription(randomString());

        ListEntity<OrganizationEntity> orgList = api.entity().organization().get();
        assertNotEquals(0, orgList.getRows().size());
        e.setOrganization(orgList.getRows().get(0));

        ListEntity<StoreEntity> store = api.entity().store().get(filterEq("name", "Основной склад"));
        assertEquals(1, store.getRows().size());
        e.setStore(store.getRows().get(0));

        api.entity().enter().post(e);

        return e;
    }

    private void getAsserts(EnterDocumentEntity e, EnterDocumentEntity retrievedEntity) {
        assertEquals(e.getName(), retrievedEntity.getName());
        assertEquals(e.getDescription(), retrievedEntity.getDescription());
        assertEquals(e.getOrganization().getMeta().getHref(), retrievedEntity.getOrganization().getMeta().getHref());
        assertEquals(e.getStore().getMeta().getHref(), retrievedEntity.getStore().getMeta().getHref());
        assertEquals(e.getUpdated().withNano(0), retrievedEntity.getUpdated().withNano(0));
        assertEquals(e.getCreated().withNano(0), retrievedEntity.getCreated().withNano(0));
    }

    private void putAsserts(EnterDocumentEntity e, EnterDocumentEntity retrievedOriginalEntity, String name) throws IOException, LognexApiException {
        EnterDocumentEntity retrievedUpdatedEntity = api.entity().enter().get(e.getId());

        assertNotEquals(retrievedOriginalEntity.getName(), retrievedUpdatedEntity.getName());
        assertEquals(name, retrievedUpdatedEntity.getName());
        assertEquals(retrievedOriginalEntity.getDescription(), retrievedUpdatedEntity.getDescription());
        assertEquals(retrievedOriginalEntity.getOrganization().getMeta().getHref(), retrievedUpdatedEntity.getOrganization().getMeta().getHref());
        assertEquals(retrievedOriginalEntity.getStore().getMeta().getHref(), retrievedUpdatedEntity.getStore().getMeta().getHref());
        assertNotEquals(retrievedOriginalEntity.getUpdated(), retrievedUpdatedEntity.getUpdated());
        assertEquals(retrievedOriginalEntity.getCreated(), retrievedUpdatedEntity.getCreated());
    }
}
