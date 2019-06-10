package com.lognex.api.entities.documents;

import com.lognex.api.clients.ApiClient;
import com.lognex.api.entities.MetaEntity;
import com.lognex.api.responses.ListEntity;
import com.lognex.api.responses.metadata.MetadataAttributeSharedStatesResponse;
import com.lognex.api.utils.LognexApiException;
import org.junit.Test;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Date;

import static com.lognex.api.utils.params.FilterParam.filterEq;
import static org.junit.Assert.*;

public class EnterDocumentEntityTest extends DocumentWithPositionsTestBase {
    @Test
    public void createTest() throws IOException, LognexApiException {
        EnterDocumentEntity enter = new EnterDocumentEntity();
        enter.setName("enter_" + randomString(3) + "_" + new Date().getTime());
        enter.setDescription(randomString());
        enter.setMoment(LocalDateTime.now());
        enter.setOrganization(simpleEntityFactory.getOwnOrganization());
        enter.setStore(simpleEntityFactory.getMainStore());

        api.entity().enter().post(enter);

        ListEntity<EnterDocumentEntity> updatedEntitiesList = api.entity().enter().get(filterEq("name", enter.getName()));
        assertEquals(1, updatedEntitiesList.getRows().size());

        EnterDocumentEntity retrievedEntity = updatedEntitiesList.getRows().get(0);
        assertEquals(enter.getName(), retrievedEntity.getName());
        assertEquals(enter.getDescription(), retrievedEntity.getDescription());
        assertEquals(enter.getMoment(), retrievedEntity.getMoment());
        assertEquals(enter.getOrganization().getMeta().getHref(), retrievedEntity.getOrganization().getMeta().getHref());
        assertEquals(enter.getStore().getMeta().getHref(), retrievedEntity.getStore().getMeta().getHref());
    }

    @Test
    public void getTest() throws IOException, LognexApiException {
        EnterDocumentEntity enter = simpleEntityFactory.createSimpleEnter();

        EnterDocumentEntity retrievedEntity = api.entity().enter().get(enter.getId());
        getAsserts(enter, retrievedEntity);

        retrievedEntity = api.entity().enter().get(enter);
        getAsserts(enter, retrievedEntity);
    }

    @Test
    public void putTest() throws IOException, LognexApiException {
        EnterDocumentEntity enter = simpleEntityFactory.createSimpleEnter();

        EnterDocumentEntity retrievedOriginalEntity = api.entity().enter().get(enter.getId());
        String name = "enter_" + randomString(3) + "_" + new Date().getTime();
        enter.setName(name);
        api.entity().enter().put(enter.getId(), enter);
        putAsserts(enter, retrievedOriginalEntity, name);

        retrievedOriginalEntity.set(enter);

        name = "enter_" + randomString(3) + "_" + new Date().getTime();
        enter.setName(name);
        api.entity().enter().put(enter);
        putAsserts(enter, retrievedOriginalEntity, name);
    }

    @Test
    public void deleteTest() throws IOException, LognexApiException {
        EnterDocumentEntity enter = simpleEntityFactory.createSimpleEnter();

        ListEntity<EnterDocumentEntity> entitiesList = api.entity().enter().get(filterEq("name", enter.getName()));
        assertEquals((Integer) 1, entitiesList.getMeta().getSize());

        api.entity().enter().delete(enter.getId());

        entitiesList = api.entity().enter().get(filterEq("name", enter.getName()));
        assertEquals((Integer) 0, entitiesList.getMeta().getSize());
    }

    @Test
    public void deleteByIdTest() throws IOException, LognexApiException {
        EnterDocumentEntity enter = simpleEntityFactory.createSimpleEnter();

        ListEntity<EnterDocumentEntity> entitiesList = api.entity().enter().get(filterEq("name", enter.getName()));
        assertEquals((Integer) 1, entitiesList.getMeta().getSize());

        api.entity().enter().delete(enter);

        entitiesList = api.entity().enter().get(filterEq("name", enter.getName()));
        assertEquals((Integer) 0, entitiesList.getMeta().getSize());
    }

    @Test
    public void metadataTest() throws IOException, LognexApiException {
        MetadataAttributeSharedStatesResponse response = api.entity().enter().metadata().get();

        assertFalse(response.getCreateShared());
    }

    private void getAsserts(EnterDocumentEntity enter, EnterDocumentEntity retrievedEntity) {
        assertEquals(enter.getName(), retrievedEntity.getName());
        assertEquals(enter.getDescription(), retrievedEntity.getDescription());
        assertEquals(enter.getOrganization().getMeta().getHref(), retrievedEntity.getOrganization().getMeta().getHref());
        assertEquals(enter.getStore().getMeta().getHref(), retrievedEntity.getStore().getMeta().getHref());
    }

    private void putAsserts(EnterDocumentEntity enter, EnterDocumentEntity retrievedOriginalEntity, String name) throws IOException, LognexApiException {
        EnterDocumentEntity retrievedUpdatedEntity = api.entity().enter().get(enter.getId());

        assertNotEquals(retrievedOriginalEntity.getName(), retrievedUpdatedEntity.getName());
        assertEquals(name, retrievedUpdatedEntity.getName());
        assertEquals(retrievedOriginalEntity.getDescription(), retrievedUpdatedEntity.getDescription());
        assertEquals(retrievedOriginalEntity.getOrganization().getMeta().getHref(), retrievedUpdatedEntity.getOrganization().getMeta().getHref());
        assertEquals(retrievedOriginalEntity.getStore().getMeta().getHref(), retrievedUpdatedEntity.getStore().getMeta().getHref());
    }

    @Override
    protected ApiClient entityClient() {
        return api.entity().enter();
    }

    @Override
    protected Class<? extends MetaEntity> entityClass() {
        return EnterDocumentEntity.class;
    }
}
