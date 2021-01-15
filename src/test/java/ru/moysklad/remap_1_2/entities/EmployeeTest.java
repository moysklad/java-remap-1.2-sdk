package ru.moysklad.remap_1_2.entities;

import org.junit.Test;
import ru.moysklad.remap_1_2.clients.EntityClientBase;
import ru.moysklad.remap_1_2.entities.agents.Employee;
import ru.moysklad.remap_1_2.responses.ListEntity;
import ru.moysklad.remap_1_2.responses.metadata.MetadataAttributeSharedResponse;
import ru.moysklad.remap_1_2.utils.ApiClientException;

import java.io.IOException;
import java.util.Date;

import static ru.moysklad.remap_1_2.utils.params.FilterParam.filterEq;
import static org.junit.Assert.*;

public class EmployeeTest extends EntityGetUpdateDeleteTest {
    @Test
    public void createTest() throws IOException, ApiClientException {
        Employee employee = new Employee();
        employee.setLastName("employee_" + randomString(3) + "_" + new Date().getTime());
        employee.setFirstName(randomString());
        employee.setMiddleName(randomString());
        employee.setArchived(false);
        employee.setDescription(randomString());

        api.entity().employee().create(employee);

        ListEntity<Employee> updatedEntitiesList = api.entity().employee().get(filterEq("lastName", employee.getLastName()));
        assertEquals(1, updatedEntitiesList.getRows().size());

        Employee retrievedEntity = updatedEntitiesList.getRows().get(0);
        assertEquals(employee.getLastName(), retrievedEntity.getLastName());
        assertEquals(employee.getFirstName(), retrievedEntity.getFirstName());
        assertEquals(employee.getLastName(), retrievedEntity.getLastName());
        assertEquals(employee.getArchived(), retrievedEntity.getArchived());
        assertEquals(employee.getDescription(), retrievedEntity.getDescription());
        assertEquals(employee.getInn(), retrievedEntity.getInn());
    }

    @Override
    protected String getFieldNameToUpdate() {
        return "LastName";
    }

    @Test
    public void metadataTest() throws IOException, ApiClientException {
        MetadataAttributeSharedResponse metadata = api.entity().employee().metadata();
        assertTrue(metadata.getCreateShared());
    }

    @Override
    protected void getAsserts(MetaEntity originalEntity, MetaEntity retrievedEntity) {
        Employee originalEmployee = (Employee) originalEntity;
        Employee retrievedEmployee = (Employee) retrievedEntity;

        assertEquals(originalEmployee.getLastName(), retrievedEmployee.getLastName());
        assertEquals(originalEmployee.getFirstName(), retrievedEmployee.getFirstName());
        assertEquals(originalEmployee.getMiddleName(), retrievedEmployee.getMiddleName());
    }

    @Override
    protected void putAsserts(MetaEntity originalEntity, MetaEntity updatedEntity, Object changedField) {
        Employee originalEmployee = (Employee) originalEntity;
        Employee updatedEmployee = (Employee) updatedEntity;

        assertEquals(changedField, updatedEmployee.getLastName());
        assertNotEquals(originalEmployee.getLastName(), updatedEmployee.getLastName());
        assertEquals(originalEmployee.getFirstName(), updatedEmployee.getFirstName());
        assertEquals(originalEmployee.getMiddleName(), updatedEmployee.getMiddleName());
    }

    @Override
    protected EntityClientBase entityClient() {
        return api.entity().employee();
    }

    @Override
    protected Class<? extends MetaEntity> entityClass() {
        return Employee.class;
    }
}
