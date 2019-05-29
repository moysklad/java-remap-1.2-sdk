package com.lognex.api.entities;

import com.lognex.api.entities.agents.EmployeeEntity;
import com.lognex.api.responses.ListEntity;
import com.lognex.api.responses.metadata.MetadataAttributeSharedResponse;
import com.lognex.api.utils.LognexApiException;
import org.junit.Test;

import java.io.IOException;
import java.util.Date;

import static com.lognex.api.utils.params.FilterParam.filterEq;
import static org.junit.Assert.*;

public class EmployeeEntityTest extends EntityTestBase {
    @Test
    public void createTest() throws IOException, LognexApiException {
        EmployeeEntity e = new EmployeeEntity();
        e.setLastName("employee_" + randomString(3) + "_" + new Date().getTime());
        e.setFirstName(randomString());
        e.setMiddleName(randomString());
        e.setArchived(false);
        e.setDescription(randomString());

        api.entity().employee().post(e);

        ListEntity<EmployeeEntity> updatedEntitiesList = api.entity().employee().get(filterEq("lastName", e.getLastName()));
        assertEquals(1, updatedEntitiesList.getRows().size());

        EmployeeEntity retrievedEntity = updatedEntitiesList.getRows().get(0);
        assertEquals(e.getLastName(), retrievedEntity.getLastName());
        assertEquals(e.getFirstName(), retrievedEntity.getFirstName());
        assertEquals(e.getLastName(), retrievedEntity.getLastName());
        assertEquals(e.getArchived(), retrievedEntity.getArchived());
        assertEquals(e.getDescription(), retrievedEntity.getDescription());
        assertEquals(e.getInn(), retrievedEntity.getInn());
    }

    @Test
    public void getTest() throws IOException, LognexApiException {
        EmployeeEntity e = createSimpleEmployee();

        EmployeeEntity retrievedEntity = api.entity().employee().get(e.getId());
        getAsserts(e, retrievedEntity);

        retrievedEntity = api.entity().employee().get(e);
        getAsserts(e, retrievedEntity);
    }

    @Test
    public void putTest() throws IOException, LognexApiException, InterruptedException {
        EmployeeEntity e = createSimpleEmployee();

        EmployeeEntity retrievedOriginalEntity = api.entity().employee().get(e.getId());
        String name = "employee_" + randomString(3) + "_" + new Date().getTime();
        e.setLastName(name);
        api.entity().employee().put(e.getId(), e);
        putAsserts(e, retrievedOriginalEntity, name);

        retrievedOriginalEntity.set(e);

        name = "employee_" + randomString(3) + "_" + new Date().getTime();
        e.setLastName(name);
        api.entity().employee().put(e);
        putAsserts(e, retrievedOriginalEntity, name);
    }

    @Test
    public void deleteTest() throws IOException, LognexApiException {
        EmployeeEntity e = createSimpleEmployee();

        ListEntity<EmployeeEntity> entitiesList = api.entity().employee().get(filterEq("lastName", e.getLastName()));
        assertEquals(1, entitiesList.getRows().size());

        api.entity().employee().delete(e.getId());

        entitiesList = api.entity().employee().get(filterEq("lastName", e.getLastName()));
        assertEquals(0, entitiesList.getRows().size());
    }

    @Test
    public void deleteByIdTest() throws IOException, LognexApiException {
        EmployeeEntity e = createSimpleEmployee();

        ListEntity<EmployeeEntity> entitiesList = api.entity().employee().get(filterEq("lastName", e.getLastName()));
        assertEquals(1, entitiesList.getRows().size());

        api.entity().employee().delete(e);

        entitiesList = api.entity().employee().get(filterEq("lastName", e.getLastName()));
        assertEquals(0, entitiesList.getRows().size());
    }

    @Test
    public void metadataTest() throws IOException, LognexApiException {
        MetadataAttributeSharedResponse metadata = api.entity().employee().metadata();
        assertTrue(metadata.getCreateShared());
    }

    private EmployeeEntity createSimpleEmployee() throws IOException, LognexApiException {
        EmployeeEntity e = new EmployeeEntity();
        e.setLastName("employee_" + randomString(3) + "_" + new Date().getTime());
        e.setFirstName(randomString());
        e.setMiddleName(randomString());
        e.setArchived(false);
        e.setDescription(randomString());

        api.entity().employee().post(e);

        return e;
    }

    private void getAsserts(EmployeeEntity e, EmployeeEntity retrievedEntity) {
        assertEquals(e.getLastName(), retrievedEntity.getLastName());
        assertEquals(e.getFirstName(), retrievedEntity.getFirstName());
        assertEquals(e.getMiddleName(), retrievedEntity.getMiddleName());
        assertEquals(e.getArchived(), retrievedEntity.getArchived());
        assertEquals(e.getDescription(), retrievedEntity.getDescription());
    }

    private void putAsserts(EmployeeEntity e, EmployeeEntity retrievedOriginalEntity, String name) throws IOException, LognexApiException {
        EmployeeEntity retrievedUpdatedEntity = api.entity().employee().get(e.getId());

        assertEquals(name, retrievedUpdatedEntity.getLastName());
        assertNotEquals(retrievedUpdatedEntity.getLastName(), retrievedOriginalEntity.getLastName());
        assertEquals(retrievedUpdatedEntity.getFirstName(), retrievedOriginalEntity.getFirstName());
        assertEquals(retrievedUpdatedEntity.getMiddleName(), retrievedOriginalEntity.getMiddleName());
        assertEquals(retrievedUpdatedEntity.getArchived(), retrievedOriginalEntity.getArchived());
        assertEquals(retrievedUpdatedEntity.getDescription(), retrievedOriginalEntity.getDescription());
    }
}
