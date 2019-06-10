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
        EmployeeEntity employee = new EmployeeEntity();
        employee.setLastName("employee_" + randomString(3) + "_" + new Date().getTime());
        employee.setFirstName(randomString());
        employee.setMiddleName(randomString());
        employee.setArchived(false);
        employee.setDescription(randomString());

        api.entity().employee().post(employee);

        ListEntity<EmployeeEntity> updatedEntitiesList = api.entity().employee().get(filterEq("lastName", employee.getLastName()));
        assertEquals(1, updatedEntitiesList.getRows().size());

        EmployeeEntity retrievedEntity = updatedEntitiesList.getRows().get(0);
        assertEquals(employee.getLastName(), retrievedEntity.getLastName());
        assertEquals(employee.getFirstName(), retrievedEntity.getFirstName());
        assertEquals(employee.getLastName(), retrievedEntity.getLastName());
        assertEquals(employee.getArchived(), retrievedEntity.getArchived());
        assertEquals(employee.getDescription(), retrievedEntity.getDescription());
        assertEquals(employee.getInn(), retrievedEntity.getInn());
    }

    @Test
    public void getTest() throws IOException, LognexApiException {
        EmployeeEntity employee = simpleEntityFactory.createSimpleEmployee();

        EmployeeEntity retrievedEntity = api.entity().employee().get(employee.getId());
        getAsserts(employee, retrievedEntity);

        retrievedEntity = api.entity().employee().get(employee);
        getAsserts(employee, retrievedEntity);
    }

    @Test
    public void putTest() throws IOException, LognexApiException {
        EmployeeEntity employee = simpleEntityFactory.createSimpleEmployee();

        EmployeeEntity retrievedOriginalEntity = api.entity().employee().get(employee.getId());
        String name = "employee_" + randomString(3) + "_" + new Date().getTime();
        employee.setLastName(name);
        api.entity().employee().put(employee.getId(), employee);
        putAsserts(employee, retrievedOriginalEntity, name);

        retrievedOriginalEntity.set(employee);

        name = "employee_" + randomString(3) + "_" + new Date().getTime();
        employee.setLastName(name);
        api.entity().employee().put(employee);
        putAsserts(employee, retrievedOriginalEntity, name);
    }

    @Test
    public void deleteTest() throws IOException, LognexApiException {
        EmployeeEntity employee = simpleEntityFactory.createSimpleEmployee();

        ListEntity<EmployeeEntity> entitiesList = api.entity().employee().get(filterEq("lastName", employee.getLastName()));
        assertEquals((Integer) 1, entitiesList.getMeta().getSize());

        api.entity().employee().delete(employee.getId());

        entitiesList = api.entity().employee().get(filterEq("lastName", employee.getLastName()));
        assertEquals((Integer) 0, entitiesList.getMeta().getSize());
    }

    @Test
    public void deleteByIdTest() throws IOException, LognexApiException {
        EmployeeEntity employee = simpleEntityFactory.createSimpleEmployee();

        ListEntity<EmployeeEntity> entitiesList = api.entity().employee().get(filterEq("lastName", employee.getLastName()));
        assertEquals((Integer) 1, entitiesList.getMeta().getSize());

        api.entity().employee().delete(employee);

        entitiesList = api.entity().employee().get(filterEq("lastName", employee.getLastName()));
        assertEquals((Integer) 0, entitiesList.getMeta().getSize());
    }

    @Test
    public void metadataTest() throws IOException, LognexApiException {
        MetadataAttributeSharedResponse metadata = api.entity().employee().metadata();
        assertTrue(metadata.getCreateShared());
    }


    private void getAsserts(EmployeeEntity employee, EmployeeEntity retrievedEntity) {
        assertEquals(employee.getLastName(), retrievedEntity.getLastName());
        assertEquals(employee.getFirstName(), retrievedEntity.getFirstName());
        assertEquals(employee.getMiddleName(), retrievedEntity.getMiddleName());
    }

    private void putAsserts(EmployeeEntity employee, EmployeeEntity retrievedOriginalEntity, String name) throws IOException, LognexApiException {
        EmployeeEntity retrievedUpdatedEntity = api.entity().employee().get(employee.getId());

        assertEquals(name, retrievedUpdatedEntity.getLastName());
        assertNotEquals(retrievedUpdatedEntity.getLastName(), retrievedOriginalEntity.getLastName());
        assertEquals(retrievedUpdatedEntity.getFirstName(), retrievedOriginalEntity.getFirstName());
        assertEquals(retrievedUpdatedEntity.getMiddleName(), retrievedOriginalEntity.getMiddleName());
    }
}
