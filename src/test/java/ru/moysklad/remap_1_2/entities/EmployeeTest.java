package ru.moysklad.remap_1_2.entities;

import ru.moysklad.remap_1_2.clients.EntityClientBase;
import ru.moysklad.remap_1_2.entities.agents.Employee;
import ru.moysklad.remap_1_2.entities.permissions.EmployeePermission;
import ru.moysklad.remap_1_2.entities.permissions.EmployeeRole;
import ru.moysklad.remap_1_2.responses.ListEntity;
import ru.moysklad.remap_1_2.responses.metadata.MetadataAttributeSharedResponse;
import ru.moysklad.remap_1_2.utils.ApiClientException;
import org.junit.Test;

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

    @Test
    public void getEmployeePermissionsTest() throws IOException, ApiClientException {
        Employee employee = new Employee();
        employee.setLastName("employee_" + randomString(3) + "_" + new Date().getTime());
        employee.setEmail("test@test.ru");
        api.entity().employee().create(employee);

        ListEntity<Employee> updatedEntitiesList = api.entity().employee().get(filterEq("lastName", employee.getLastName()));
        assertEquals(1, updatedEntitiesList.getRows().size());

        Employee retrievedEntity = updatedEntitiesList.getRows().get(0);

        EmployeePermission employeePermission = api.entity().employee().getPermissions(retrievedEntity.getId());
        assertEquals(employeePermission.getIsActive(), false);
    }

    @Test
    public void updateEmployeePermissionsTest() throws IOException, ApiClientException {
        Employee employee = new Employee();
        employee.setLastName("employee_" + randomString(3) + "_" + new Date().getTime());
        employee.setEmail("test@test.ru");
        api.entity().employee().create(employee);

        ListEntity<Employee> updatedEntitiesList = api.entity().employee().get(filterEq("lastName", employee.getLastName()));
        assertEquals(1, updatedEntitiesList.getRows().size());

        Employee retrievedEntity = updatedEntitiesList.getRows().get(0);
        EmployeePermission employeePermission = new EmployeePermission();
        employeePermission.setLogin(employee.getLastName() + "@" + api.getLogin().split("@")[1]);
        api.entity().employee().activate(retrievedEntity.getId(), employeePermission);
        employeePermission = api.entity().employee().getPermissions(retrievedEntity.getId());
        assertTrue(employeePermission.getIsActive());

        employeePermission = new EmployeePermission();
        employeePermission.setRole(EmployeeRole.cashierRole());
        employeePermission = api.entity().employee().updatePermissions(retrievedEntity.getId(), employeePermission);
        assertTrue(employeePermission.getRole().getMeta().getHref().endsWith("entity/role/cashier"));
    }

    @Override
    protected String getFieldNameToUpdate() {
        return "LastName";
    }

    @Test
    public void metadataTest() throws IOException, ApiClientException {
        MetadataAttributeSharedResponse<Attribute> metadata = api.entity().employee().metadata();
        assertTrue(metadata.getCreateShared());
    }
    
    @Test
    public void attributesTest() throws IOException, ApiClientException{
        ListEntity<Attribute> attributes = api.entity().employee().metadataAttributes();
        assertNotNull(attributes);
    }

    @Test
    public void createAttributeTest() throws IOException, ApiClientException {
        Attribute attribute = new Attribute();
        attribute.setType(Attribute.Type.textValue);
        String name = "field" + randomString(3) + "_" + new Date().getTime();
        attribute.setName(name);
        attribute.setRequired(false);
        attribute.setDescription("description");
        Attribute created = api.entity().employee().createMetadataAttribute(attribute);
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
        Attribute created = api.entity().employee().createMetadataAttribute(attribute);

        String name = "field" + randomString(3) + "_" + new Date().getTime();
        created.setName(name);
        created.setRequired(false);
        Attribute updated = api.entity().employee().updateMetadataAttribute(created);
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
        Attribute created = api.entity().employee().createMetadataAttribute(attribute);

        api.entity().employee().deleteMetadataAttribute(created);

        try {
            api.entity().employee().metadataAttributes(created.getId());
        } catch (ApiClientException e) {
            assertEquals(404, e.getStatusCode());
        }
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
    public EntityClientBase entityClient() {
        return api.entity().employee();
    }

    @Override
    public Class<? extends MetaEntity> entityClass() {
        return Employee.class;
    }
}
