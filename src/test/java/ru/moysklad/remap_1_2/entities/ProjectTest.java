package ru.moysklad.remap_1_2.entities;

import org.junit.Test;
import ru.moysklad.remap_1_2.clients.EntityClientBase;
import ru.moysklad.remap_1_2.responses.ListEntity;
import ru.moysklad.remap_1_2.responses.metadata.MetadataAttributeSharedResponse;
import ru.moysklad.remap_1_2.utils.ApiClientException;

import java.io.IOException;
import java.util.Date;

import static ru.moysklad.remap_1_2.utils.params.FilterParam.filterEq;
import static org.junit.Assert.*;

public class ProjectTest extends EntityGetUpdateDeleteTest {
    @Test
    public void createTest() throws IOException, ApiClientException {
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
    public void metadataTest() throws IOException, ApiClientException {
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
    protected EntityClientBase entityClient() {
        return api.entity().project();
    }

    @Override
    protected Class<? extends MetaEntity> entityClass() {
        return Project.class;
    }
}

