package ru.moysklad.remap_1_2.entities;

import org.junit.Test;
import ru.moysklad.remap_1_2.clients.EntityClientBase;
import ru.moysklad.remap_1_2.entities.agents.Organization;
import ru.moysklad.remap_1_2.entities.agents.OrganizationBranch;
import ru.moysklad.remap_1_2.responses.ListEntity;
import ru.moysklad.remap_1_2.utils.ApiClientException;

import java.io.IOException;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static ru.moysklad.remap_1_2.utils.params.FilterParam.filterEq;

public class OrganizationBranchTest extends EntityGetUpdateDeleteTest {

    private Organization createTestOrganization() throws IOException, ApiClientException {
        Organization organization = new Organization();
        organization.setName("organization_for_branch_" + randomString(3) + "_" + new Date().getTime());
        api.entity().organization().create(organization);
        return organization;
    }

    @Test
    public void createTest() throws IOException, ApiClientException {
        Organization organization = createTestOrganization();

        OrganizationBranch branch = new OrganizationBranch();
        branch.setName("organizationbranch_" + randomString(3) + "_" + new Date().getTime());
        branch.setOrganization(organization);
        branch.setArchived(false);
        branch.setDescription(randomString());
        branch.setCode(randomString());
        branch.setKpp("123456789");
        branch.setActualAddress("Moscow, Tverskaya 1");

        api.entity().organizationBranch().create(branch);

        ListEntity<OrganizationBranch> updatedEntitiesList = api.entity().organizationBranch().get(filterEq("name", branch.getName()));
        assertEquals(1, updatedEntitiesList.getRows().size());

        OrganizationBranch retrievedEntity = updatedEntitiesList.getRows().get(0);
        assertEquals(branch.getName(), retrievedEntity.getName());
        assertEquals(branch.getArchived(), retrievedEntity.getArchived());
        assertEquals(branch.getDescription(), retrievedEntity.getDescription());
        assertEquals(branch.getCode(), retrievedEntity.getCode());
        assertEquals(branch.getKpp(), retrievedEntity.getKpp());
        assertEquals(branch.getActualAddress(), retrievedEntity.getActualAddress());
        assertEquals(organization.getMeta(), retrievedEntity.getOrganization().getMeta());
    }

    @Override
    protected void getAsserts(MetaEntity originalEntity, MetaEntity retrievedEntity) {
        OrganizationBranch original = (OrganizationBranch) originalEntity;
        OrganizationBranch retrieved = (OrganizationBranch) retrievedEntity;

        assertEquals(original.getName(), retrieved.getName());
        assertEquals(original.getOrganization().getId(), retrieved.getOrganization().getId());
    }

    @Override
    protected void putAsserts(MetaEntity originalEntity, MetaEntity updatedEntity, Object changedField) {
        OrganizationBranch original = (OrganizationBranch) originalEntity;
        OrganizationBranch updated = (OrganizationBranch) updatedEntity;

        assertNotEquals(original.getName(), updated.getName());
        assertEquals(changedField, updated.getName());
        assertEquals(original.getOrganization().getId(), updated.getOrganization().getId());
    }

    @Override
    public EntityClientBase entityClient() {
        return api.entity().organizationBranch();
    }

    @Override
    public Class<? extends MetaEntity> entityClass() {
        return OrganizationBranch.class;
    }
}
