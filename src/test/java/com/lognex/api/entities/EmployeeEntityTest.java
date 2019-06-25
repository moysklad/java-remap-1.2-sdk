package com.lognex.api.entities;

import com.lognex.api.clients.ApiClient;
import com.lognex.api.entities.agents.EmployeeEntity;
import com.lognex.api.responses.ListEntity;
import com.lognex.api.responses.metadata.MetadataAttributeSharedResponse;
import com.lognex.api.utils.LognexApiException;
import org.junit.Test;

import java.io.IOException;
import java.util.Date;

import static com.lognex.api.utils.params.FilterParam.filterEq;
import static org.junit.Assert.*;

public class EmployeeEntityTest extends EntityGetUpdateDeleteTest {
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

    @Override
    @Test
    public void putTest() throws IOException, LognexApiException {
        doPutTest("LastName");
    }

    @Test
    public void metadataTest() throws IOException, LognexApiException {
        MetadataAttributeSharedResponse metadata = api.entity().employee().metadata();
        assertTrue(metadata.getCreateShared());
    }

    @Override
    protected void getAsserts(MetaEntity originalEntity, MetaEntity retrievedEntity) {
        EmployeeEntity originalEmployee = (EmployeeEntity) originalEntity;
        EmployeeEntity retrievedEmployee = (EmployeeEntity) retrievedEntity;

        assertEquals(originalEmployee.getLastName(), retrievedEmployee.getLastName());
        assertEquals(originalEmployee.getFirstName(), retrievedEmployee.getFirstName());
        assertEquals(originalEmployee.getMiddleName(), retrievedEmployee.getMiddleName());
    }

    @Override
    protected void putAsserts(MetaEntity originalEntity, MetaEntity updatedEntity, Object changedField) {
        EmployeeEntity originalEmployee = (EmployeeEntity) originalEntity;
        EmployeeEntity updatedEmployee = (EmployeeEntity) updatedEntity;

        assertEquals(changedField, updatedEmployee.getLastName());
        assertNotEquals(originalEmployee.getLastName(), updatedEmployee.getLastName());
        assertEquals(originalEmployee.getFirstName(), updatedEmployee.getFirstName());
        assertEquals(originalEmployee.getMiddleName(), updatedEmployee.getMiddleName());
    }

    @Override
    protected ApiClient entityClient() {
        return api.entity().employee();
    }

    @Override
    protected Class<? extends MetaEntity> entityClass() {
        return EmployeeEntity.class;
    }
}
