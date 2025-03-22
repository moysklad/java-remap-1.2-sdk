package ru.moysklad.remap_1_2.entities;

import org.junit.Ignore;
import org.junit.Test;
import ru.moysklad.remap_1_2.clients.EntityClientBase;
import ru.moysklad.remap_1_2.entities.permissions.EmployeePermissions;
import ru.moysklad.remap_1_2.entities.permissions.EmployeeRole;
import ru.moysklad.remap_1_2.responses.ListEntity;
import ru.moysklad.remap_1_2.utils.ApiClientException;

import java.io.IOException;
import java.util.Objects;

import static org.junit.Assert.*;

public class RoleTest extends EntityGetUpdateDeleteTest {

    @Test
    public void getAdminRole() throws ApiClientException, IOException {
        EmployeeRole adminRole = api.entity().role().adminRole();
        assertNotNull(adminRole);

        EmployeePermissions permissions = adminRole.getPermissions();
        assertNull(permissions);

        Meta meta = adminRole.getMeta();
        assertNotNull(meta);

        String href = meta.getHref();
        Meta.Type type = meta.getType();

        assertNotNull(href);
        assertNotNull(type);

        assertEquals(Meta.Type.SYSTEM_ROLE, type);
        assertTrue(href.endsWith("/entity/role/admin"));
    }

    @Test
    public void getCashierRole() throws ApiClientException, IOException {
        EmployeeRole cashierRole = api.entity().role().cashierRole();
        assertNotNull(cashierRole);

        EmployeePermissions permissions = cashierRole.getPermissions();
        assertNull(permissions);

        Meta meta = cashierRole.getMeta();
        assertNotNull(meta);

        String href = meta.getHref();
        Meta.Type type = meta.getType();

        assertNotNull(href);
        assertNotNull(type);

        assertEquals(Meta.Type.SYSTEM_ROLE, type);
        assertTrue(href.endsWith("/entity/role/cashier"));
    }

    @Test
    public void getIndividualRole() throws ApiClientException, IOException {
        EmployeeRole individualRole = api.entity().role().individualRole();
        assertNotNull(individualRole);

        EmployeePermissions permissions = individualRole.getPermissions();
        assertNull(permissions);

        Meta meta = individualRole.getMeta();
        assertNotNull(meta);

        String href = meta.getHref();
        Meta.Type type = meta.getType();

        assertNotNull(href);
        assertNotNull(type);

        assertEquals(Meta.Type.INDIVIDUAL_ROLE, type);
        assertTrue(href.endsWith("/entity/role/individual"));
    }

    @Test
    public void createAndUpdateCustomRole() throws ApiClientException, IOException {
        EmployeeRole employeeRole = new EmployeeRole();
        employeeRole.setName("RoleTest");
        EmployeeRole createdRole = api.entity().role().createCustomRole(employeeRole);

        assertNotNull(createdRole);
        assertEquals("RoleTest", createdRole.getName());

        try {
            api.entity().role().createCustomRole(employeeRole);
            fail();
        } catch (Exception e){
            assertTrue(true);
        }

        EmployeeRole receivedRole = api.entity().role().customRole(createdRole.getId());

        assertNotNull(receivedRole);
        assertCustomRoleMeta(receivedRole);
        assertEquals(receivedRole, createdRole);

        ListEntity<EmployeeRole> employeeRoleList = api.entity().role().get();
        assertCollectionContainsEmployeeRole(employeeRoleList, receivedRole);

        EmployeeRole firstRoleFromList = employeeRoleList.getRows().get(0);

        assertNotNull(firstRoleFromList);
        assertCustomRoleMeta(firstRoleFromList);

        assertEquals(firstRoleFromList, createdRole);

        createdRole.getPermissions().setOnlineShops(false);
        createdRole.setName("newRoleForTest");

        EmployeeRole updatedRole = api.entity().role().updateCustomRole(createdRole.getId(), createdRole);

        EmployeeRole receivedUpdatedRole = api.entity().role().customRole(updatedRole.getId());

        assertNotNull(receivedUpdatedRole);
        assertCustomRoleMeta(receivedUpdatedRole);
        assertEquals("newRoleForTest", receivedUpdatedRole.getName());
        assertNotNull(receivedUpdatedRole.getPermissions());
        assertFalse(receivedUpdatedRole.getPermissions().getOnlineShops());

        api.entity().role().deleteCustomRole(createdRole.getId());

        try {
            api.entity().role().customRole(createdRole.getId());
            fail();
        } catch (Exception e) {
            assertTrue(true);
        }

        ListEntity<EmployeeRole> list = api.entity().role().get();
        assertNotCollectionContainsEmployeeRole(list, receivedUpdatedRole);
    }

    private void assertCollectionContainsEmployeeRole(ListEntity<EmployeeRole> employeeRoleList, EmployeeRole employeeRole) {
        assertCollectionEmployeeRole(employeeRoleList, employeeRole, true);
    }

    private void assertNotCollectionContainsEmployeeRole(ListEntity<EmployeeRole> employeeRoleList, EmployeeRole employeeRole) {
        assertCollectionEmployeeRole(employeeRoleList, employeeRole, false);
    }

    private void assertCollectionEmployeeRole(ListEntity<EmployeeRole> employeeRoleList, EmployeeRole employeeRole, boolean contains) {
        assertNotNull(employeeRoleList);
        assertNotNull(employeeRoleList.getRows());
        boolean nonMatch = employeeRoleList.getRows().stream().noneMatch(t -> Objects.equals(t, employeeRole));
        if(contains){
            assertFalse(nonMatch);
        } else {
            assertTrue(nonMatch);
        }
    }

    private void assertCustomRoleMeta(EmployeeRole employeeRole) {
        String id = employeeRole.getId();
        Meta meta = employeeRole.getMeta();
        String href = meta.getHref();

        assertEquals(Meta.Type.CUSTOM_ROLE, meta.getType());

        assertTrue(href.endsWith("/entity/role/" + id));
    }

    @Ignore
    @Test
    @Override
    public void massCreateDeleteTest() {
    }

    @Ignore
    @Test
    @Override
    public void massUpdateTest() {
    }

    @Ignore
    @Test
    @Override
    public void putTest() throws IOException, ApiClientException {
    }

    @Ignore
    @Test
    @Override
    public void deleteTest() throws IOException, ApiClientException {
    }

    @Ignore
    @Test
    @Override
    public void getTest() throws IOException, ApiClientException {
    }

    @Override
    protected void getAsserts(MetaEntity originalEntity, MetaEntity retrievedEntity) {
    }

    @Override
    protected void putAsserts(MetaEntity originalEntity, MetaEntity updatedEntity, Object changedField) {
    }

    @Override
    protected EntityClientBase entityClient() {
        return api.entity().role();
    }

    @Override
    protected Class<? extends MetaEntity> entityClass() {
        return EmployeeRole.class;
    }
}
