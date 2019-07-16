package com.lognex.api.entities;

import com.lognex.api.clients.ApiClient;
import com.lognex.api.responses.ListEntity;
import com.lognex.api.responses.metadata.MetadataAttributeSharedResponse;
import com.lognex.api.utils.LognexApiException;
import org.junit.Test;

import java.io.IOException;
import java.util.Date;

import static com.lognex.api.utils.params.FilterParam.filterEq;
import static org.junit.Assert.*;

public class ProjectTest extends EntityGetUpdateDeleteTest {
    @Test
    public void createTest() throws IOException, LognexApiException {
        Project project = new Project();
        project.setName("project_" + randomString(3) + "_" + new Date().getTime());
        project.setDescription(randomString());
        project.setCode(randomString());
        project.setExternalCode(randomString());

        api.entity().project().create(project);

        ListEntity<Project> updatedEntitiesList = api.entity().project().get(filterEq("name", project.getName()));
        assertEquals(1, updatedEntitiesList.getRows().size());

        Project retrievedEntity = updatedEntitiesList.getRows().get(0);
        assertEquals(project.getName(), retrievedEntity.getName());
        assertEquals(project.getDescription(), retrievedEntity.getDescription());
        assertEquals(project.getCode(), retrievedEntity.getCode());
        assertEquals(project.getExternalCode(), retrievedEntity.getExternalCode());
    }

    @Test
    public void metadataTest() throws IOException, LognexApiException {
        MetadataAttributeSharedResponse metadata = api.entity().project().metadata();
        assertTrue(metadata.getCreateShared());
    }

    @Override
    protected void getAsserts(MetaEntity originalEntity, MetaEntity retrievedEntity) {
        Project originalProject = (Project) originalEntity;
        Project retrievedProject = (Project) retrievedEntity;

        assertEquals(originalProject.getName(), retrievedProject.getName());
        assertEquals(originalProject.getDescription(), retrievedProject.getDescription());
    }

    @Override
    protected void putAsserts(MetaEntity originalEntity, MetaEntity updatedEntity, Object changedField) {
        Project originalProject = (Project) originalEntity;
        Project updatedProject = (Project) updatedEntity;

        assertNotEquals(originalProject.getName(), updatedProject.getName());
        assertEquals(changedField, updatedProject.getName());
        assertEquals(originalProject.getDescription(), updatedProject.getDescription());
    }

    @Override
    protected ApiClient entityClient() {
        return api.entity().project();
    }

    @Override
    protected Class<? extends MetaEntity> entityClass() {
        return Project.class;
    }
}

