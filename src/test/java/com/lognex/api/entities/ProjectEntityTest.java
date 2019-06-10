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
        ProjectEntity project = new ProjectEntity();
        project.setName("project_" + randomString(3) + "_" + new Date().getTime());
        project.setDescription(randomString());
        project.setCode(randomString());
        project.setExternalCode(randomString());

        api.entity().project().post(project);

        ListEntity<ProjectEntity> updatedEntitiesList = api.entity().project().get(filterEq("name", project.getName()));
        assertEquals(1, updatedEntitiesList.getRows().size());

        ProjectEntity retrievedEntity = updatedEntitiesList.getRows().get(0);
        assertEquals(project.getName(), retrievedEntity.getName());
        assertEquals(project.getDescription(), retrievedEntity.getDescription());
        assertEquals(project.getCode(), retrievedEntity.getCode());
        assertEquals(project.getExternalCode(), retrievedEntity.getExternalCode());
    }

    @Test
    public void getTest() throws IOException, LognexApiException {
        ProjectEntity project = simpleEntityFactory.createSimpleProject();

        ProjectEntity retrievedEntity = api.entity().project().get(project.getId());
        getAsserts(project, retrievedEntity);

        retrievedEntity = api.entity().project().get(project);
        getAsserts(project, retrievedEntity);
    }

    @Test
    public void putTest() throws IOException, LognexApiException {
        ProjectEntity project = simpleEntityFactory.createSimpleProject();

        ProjectEntity retrievedOriginalEntity = api.entity().project().get(project.getId());
        String name = "project_" + randomString(3) + "_" + new Date().getTime();
        project.setName(name);
        api.entity().project().put(project.getId(), project);
        putAsserts(project, retrievedOriginalEntity, name);

        project = simpleEntityFactory.createSimpleProject();
        retrievedOriginalEntity = api.entity().project().get(project.getId());
        name = "project_" + randomString(3) + "_" + new Date().getTime();
        project.setName(name);
        api.entity().project().put(project);
        putAsserts(project, retrievedOriginalEntity, name);
    }

    @Test
    public void deleteTest() throws IOException, LognexApiException {
        ProjectEntity project = simpleEntityFactory.createSimpleProject();

        ListEntity<ProjectEntity> entitiesList = api.entity().project().get(filterEq("name", project.getName()));
        assertEquals((Integer) 1, entitiesList.getMeta().getSize());

        api.entity().project().delete(project.getId());

        entitiesList = api.entity().project().get(filterEq("name", project.getName()));
        assertEquals((Integer) 0, entitiesList.getMeta().getSize());
    }

    @Test
    public void deleteByIdTest() throws IOException, LognexApiException {
        ProjectEntity project = simpleEntityFactory.createSimpleProject();

        ListEntity<ProjectEntity> entitiesList = api.entity().project().get(filterEq("name", project.getName()));
        assertEquals((Integer) 1, entitiesList.getMeta().getSize());

        api.entity().project().delete(project);

        entitiesList = api.entity().project().get(filterEq("name", project.getName()));
        assertEquals((Integer) 0, entitiesList.getMeta().getSize());
    }

    @Test
    public void metadataTest() throws IOException, LognexApiException {
        MetadataAttributeSharedResponse metadata = api.entity().project().metadata();
        assertTrue(metadata.getCreateShared());
    }

    private void getAsserts(ProjectEntity project, ProjectEntity retrievedEntity) {
        assertEquals(project.getName(), retrievedEntity.getName());
        assertEquals(project.getDescription(), retrievedEntity.getDescription());
    }

    private void putAsserts(ProjectEntity project, ProjectEntity retrievedOriginalEntity, String name) throws IOException, LognexApiException {
        ProjectEntity retrievedUpdatedEntity = api.entity().project().get(project.getId());

        assertNotEquals(retrievedOriginalEntity.getName(), retrievedUpdatedEntity.getName());
        assertEquals(name, retrievedUpdatedEntity.getName());
        assertEquals(retrievedOriginalEntity.getDescription(), retrievedUpdatedEntity.getDescription());
    }
}

