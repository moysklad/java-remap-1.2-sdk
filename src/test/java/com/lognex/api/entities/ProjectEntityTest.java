package com.lognex.api.entities;

import com.lognex.api.responses.ListEntity;
import com.lognex.api.responses.metadata.MetadataAttributeSharedResponse;
import com.lognex.api.utils.LognexApiException;
import org.junit.Test;

import java.io.IOException;
import java.util.Date;

import static com.lognex.api.utils.params.FilterParam.filterEq;
import static org.junit.Assert.*;

public class ProjectEntityTest extends EntityTestBase {
    @Test
    public void createTest() throws IOException, LognexApiException {
        ProjectEntity e = new ProjectEntity();
        e.setName("project_" + randomString(3) + "_" + new Date().getTime());
        e.setDescription(randomString());
        e.setCode(randomString());
        e.setExternalCode(randomString());

        api.entity().project().post(e);

        ListEntity<ProjectEntity> updatedEntitiesList = api.entity().project().get(filterEq("name", e.getName()));
        assertEquals(1, updatedEntitiesList.getRows().size());

        ProjectEntity retrievedEntity = updatedEntitiesList.getRows().get(0);
        assertEquals(e.getName(), retrievedEntity.getName());
        assertEquals(e.getDescription(), retrievedEntity.getDescription());
        assertEquals(e.getCode(), retrievedEntity.getCode());
        assertEquals(e.getExternalCode(), retrievedEntity.getExternalCode());
        assertEquals(e.getUpdated().withNano(0), retrievedEntity.getUpdated().withNano(0));
    }

    @Test
    public void getTest() throws IOException, LognexApiException {
        ProjectEntity e = createSimpleProject();

        ProjectEntity retrievedEntity = api.entity().project().get(e.getId());
        getAsserts(e, retrievedEntity);

        retrievedEntity = api.entity().project().get(e);
        getAsserts(e, retrievedEntity);
    }

    @Test
    public void putTest() throws IOException, LognexApiException, InterruptedException {
        ProjectEntity e = createSimpleProject();

        ProjectEntity retrievedOriginalEntity = api.entity().project().get(e.getId());
        String name = "project_" + randomString(3) + "_" + new Date().getTime();
        e.setName(name);
        Thread.sleep(1500);
        api.entity().project().put(e.getId(), e);
        putAsserts(e, retrievedOriginalEntity, name);

        e = createSimpleProject();
        retrievedOriginalEntity = api.entity().project().get(e.getId());
        name = "project_" + randomString(3) + "_" + new Date().getTime();
        e.setName(name);
        Thread.sleep(1500);
        api.entity().project().put(e);
        putAsserts(e, retrievedOriginalEntity, name);
    }

    @Test
    public void deleteTest() throws IOException, LognexApiException {
        ProjectEntity e = createSimpleProject();

        ListEntity<ProjectEntity> entitiesList = api.entity().project().get(filterEq("name", e.getName()));
        assertEquals(1, entitiesList.getRows().size());

        api.entity().project().delete(e.getId());

        entitiesList = api.entity().project().get(filterEq("name", e.getName()));
        assertEquals(0, entitiesList.getRows().size());
    }

    @Test
    public void deleteByIdTest() throws IOException, LognexApiException {
        ProjectEntity e = createSimpleProject();

        ListEntity<ProjectEntity> entitiesList = api.entity().project().get(filterEq("name", e.getName()));
        assertEquals(1, entitiesList.getRows().size());

        api.entity().project().delete(e);

        entitiesList = api.entity().project().get(filterEq("name", e.getName()));
        assertEquals(0, entitiesList.getRows().size());
    }

    @Test
    public void metadataTest() throws IOException, LognexApiException {
        MetadataAttributeSharedResponse metadata = api.entity().project().metadata();
        assertTrue(metadata.getCreateShared());
    }

    private ProjectEntity createSimpleProject() throws IOException, LognexApiException {
        ProjectEntity e = new ProjectEntity();
        e.setName("project_" + randomString(3) + "_" + new Date().getTime());
        e.setDescription(randomString());
        e.setCode(randomString());
        e.setExternalCode(randomString());

        api.entity().project().post(e);

        return e;
    }

    private void getAsserts(ProjectEntity e, ProjectEntity retrievedEntity) {
        assertEquals(e.getName(), retrievedEntity.getName());
        assertEquals(e.getDescription(), retrievedEntity.getDescription());
        assertEquals(e.getCode(), retrievedEntity.getCode());
        assertEquals(e.getExternalCode(), retrievedEntity.getExternalCode());
        assertEquals(e.getUpdated().withNano(0), retrievedEntity.getUpdated().withNano(0));
    }

    private void putAsserts(ProjectEntity e, ProjectEntity retrievedOriginalEntity, String name) throws IOException, LognexApiException {
        ProjectEntity retrievedUpdatedEntity = api.entity().project().get(e.getId());

        assertNotEquals(retrievedOriginalEntity.getName(), retrievedUpdatedEntity.getName());
        assertEquals(name, retrievedUpdatedEntity.getName());
        assertEquals(retrievedOriginalEntity.getDescription(), retrievedUpdatedEntity.getDescription());
        assertEquals(retrievedOriginalEntity.getCode(), retrievedUpdatedEntity.getCode());
        assertEquals(retrievedOriginalEntity.getExternalCode(), retrievedUpdatedEntity.getExternalCode());
        assertNotEquals(retrievedOriginalEntity.getUpdated(), retrievedUpdatedEntity.getUpdated());
    }
}

