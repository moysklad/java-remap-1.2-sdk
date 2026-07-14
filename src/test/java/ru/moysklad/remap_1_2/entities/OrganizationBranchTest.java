package ru.moysklad.remap_1_2.entities;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Ignore;
import org.junit.Test;
import ru.moysklad.remap_1_2.ApiClient;
import ru.moysklad.remap_1_2.clients.EntityClientBase;
import ru.moysklad.remap_1_2.entities.agents.Organization;
import ru.moysklad.remap_1_2.entities.agents.OrganizationBranch;
import ru.moysklad.remap_1_2.responses.ListEntity;
import ru.moysklad.remap_1_2.responses.metadata.MetadataAttributeSharedResponse;
import ru.moysklad.remap_1_2.utils.ApiClientException;
import ru.moysklad.remap_1_2.utils.TestUtils;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Date;

import static org.junit.Assert.*;
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

        OrganizationBranch organizationBranch = new OrganizationBranch();
        organizationBranch.setName("organizationbranch_" + randomString(3) + "_" + new Date().getTime());
        organizationBranch.setOrganization(organization);
        organizationBranch.setArchived(false);
        organizationBranch.setDescription(randomString());
        organizationBranch.setCode(randomString());
        organizationBranch.setActualAddress("Moscow, Tverskaya 1");

        api.entity().organizationBranch().create(organizationBranch);

        ListEntity<OrganizationBranch> updatedEntitiesList = api.entity().organizationBranch().get(filterEq("name", organizationBranch.getName()));
        assertEquals(1, updatedEntitiesList.getRows().size());

        OrganizationBranch retrievedEntity = updatedEntitiesList.getRows().get(0);
        assertEquals(organizationBranch.getName(), retrievedEntity.getName());
        assertEquals(organizationBranch.getArchived(), retrievedEntity.getArchived());
        assertEquals(organizationBranch.getDescription(), retrievedEntity.getDescription());
        assertEquals(organizationBranch.getCode(), retrievedEntity.getCode());
        assertEquals(organizationBranch.getActualAddress(), retrievedEntity.getActualAddress());
        assertEquals(organization.getMeta(), retrievedEntity.getOrganization().getMeta());
    }

    @Test
    public void metadataTest() throws IOException, ApiClientException {
        MetadataAttributeSharedResponse metadata = api.entity().organizationBranch().metadata();
        assertNotNull(metadata);
    }

    @Test
    public void deserializeTest() throws JsonProcessingException {
        ObjectMapper objectMapper = ApiClient.createObjectMapper();

        OrganizationBranch organizationBranch = objectMapper.readValue(
                TestUtils.getFile("entitiesJson/organizationbranch.json"), OrganizationBranch.class
        );

        assertEquals(Meta.Type.ORGANIZATION_BRANCH, organizationBranch.getMeta().getType());
        assertEquals("branch-id", organizationBranch.getId());
        assertEquals("account-id", organizationBranch.getAccountId());
        assertEquals("Branch name", organizationBranch.getName());
        assertEquals("Branch description", organizationBranch.getDescription());
        assertEquals("branch-code", organizationBranch.getCode());
        assertEquals("branch-external-code", organizationBranch.getExternalCode());
        assertFalse(organizationBranch.getShared());
        assertEquals(LocalDateTime.of(2024, 1, 1, 10, 0), organizationBranch.getUpdated());
        assertEquals("https://api.moysklad.ru/api/remap/1.2/entity/employee/employee-id", organizationBranch.getUpdatedBy().getMeta().getHref());
        assertEquals(LocalDateTime.of(2024, 1, 2, 10, 0), organizationBranch.getDeleted());
        assertEquals(LocalDateTime.of(2023, 12, 1, 10, 0), organizationBranch.getCreated());
        assertEquals(Boolean.TRUE, organizationBranch.getArchived());
        assertEquals("https://api.moysklad.ru/api/remap/1.2/entity/organization/organization-id", organizationBranch.getOrganization().getMeta().getHref());
        assertEquals("Actual address", organizationBranch.getActualAddress());
        assertEquals("+79990000000", organizationBranch.getPhone());
        assertEquals("+74950000000", organizationBranch.getFax());
        assertEquals("branch@example.com", organizationBranch.getEmail());
        assertEquals("123456789", organizationBranch.getRequisitesRuKpp());
    }

    @Ignore
    @Test
    @Override
    public void massUpdateTest() {
    }

    @Ignore
    @Test
    @Override
    public void massCreateDeleteTest() {
    }

    @Override
    protected void getAsserts(MetaEntity originalEntity, MetaEntity retrievedEntity) {
        OrganizationBranch originalOrganizationBranch = (OrganizationBranch) originalEntity;
        OrganizationBranch retrievedOrganizationBranch = (OrganizationBranch) retrievedEntity;

        assertEquals(originalOrganizationBranch.getName(), retrievedOrganizationBranch.getName());
        assertEquals(originalOrganizationBranch.getOrganization().getMeta().getHref(), retrievedOrganizationBranch.getOrganization().getMeta().getHref());
    }

    @Override
    protected void putAsserts(MetaEntity originalEntity, MetaEntity updatedEntity, Object changedField) {
        OrganizationBranch originalOrganizationBranch = (OrganizationBranch) originalEntity;
        OrganizationBranch updatedOrganizationBranch = (OrganizationBranch) updatedEntity;

        assertNotEquals(originalOrganizationBranch.getName(), updatedOrganizationBranch.getName());
        assertEquals(changedField, updatedOrganizationBranch.getName());
        assertEquals(originalOrganizationBranch.getOrganization().getMeta().getHref(), updatedOrganizationBranch.getOrganization().getMeta().getHref());
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
