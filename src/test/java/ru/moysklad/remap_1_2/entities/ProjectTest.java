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

    @Test
    public void attributesTest() throws IOException, ApiClientException{
        ListEntity<Attribute> attributes = api.entity().project().metadataAttributes();
        assertNotNull(attributes);
    }

    @Test
    public void createAttributeTest() throws IOException, ApiClientException {
        Attribute attribute = new Attribute();
        attribute.setType(Attribute.Type.textValue);
        String name = "field" + randomString(3) + "_" + new Date().getTime();
        attribute.setName(name);
        attribute.setRequired(false);
        attribute.setShow(true);
        attribute.setDescription("description");
        Attribute created = api.entity().project().createMetadataAttribute(attribute);
        assertNotNull(created);
        assertEquals(name, created.getName());
        assertEquals(Attribute.Type.textValue, created.getType());
        assertFalse(created.getRequired());
        assertEquals("description", created.getDescription());
    }

    @Test
    public void updateAttributeTest() throws IOException, ApiClientException {
        Attribute attribute = new Attribute();
        attribute.setEntityType(Meta.Type.PRODUCT);
        attribute.setName("field" + randomString(3) + "_" + new Date().getTime());
        attribute.setRequired(true);
        attribute.setShow(true);
        Attribute created = api.entity().project().createMetadataAttribute(attribute);

        String name = "field" + randomString(3) + "_" + new Date().getTime();
        created.setName(name);
        created.setRequired(false);
        attribute.setShow(false);
        Attribute updated = api.entity().project().updateMetadataAttribute(created);
        assertNotNull(created);
        assertEquals(name, updated.getName());
        assertNull(updated.getType());
        assertEquals(Meta.Type.PRODUCT, updated.getEntityType());
        assertFalse(updated.getRequired());
    }

    @Test
    public void deleteAttributeTest() throws IOException, ApiClientException{
        Attribute attribute = new Attribute();
        attribute.setEntityType(Meta.Type.PRODUCT);
        attribute.setName("field" + randomString(3) + "_" + new Date().getTime());
        attribute.setRequired(true);
        attribute.setShow(true);
        Attribute created = api.entity().project().createMetadataAttribute(attribute);

        api.entity().project().deleteMetadataAttribute(created);

        try {
            api.entity().project().metadataAttributes(created.getId());
        } catch (ApiClientException e) {
            assertEquals(404, e.getStatusCode());
        }
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
    public EntityClientBase entityClient() {
        return api.entity().project();
    }

    @Override
    public Class<? extends MetaEntity> entityClass() {
        return Project.class;
    }
}

