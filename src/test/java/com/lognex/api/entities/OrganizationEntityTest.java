package com.lognex.api.entities;

import com.lognex.api.clients.EntityApiClient;
import com.lognex.api.entities.agents.Organization;
import com.lognex.api.responses.ListEntity;
import com.lognex.api.responses.metadata.MetadataAttributeSharedResponse;
import com.lognex.api.utils.LognexApiException;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import static com.lognex.api.utils.params.FilterParam.filterEq;
import static org.junit.Assert.*;

public class OrganizationEntityTest extends EntityGetUpdateDeleteTest {
    @Test
    public void createTest() throws IOException, LognexApiException {
        Organization organization = new Organization();
        organization.setName("organization_" + randomString(3) + "_" + new Date().getTime());
        organization.setArchived(false);
        organization.setCompanyType(CompanyType.legal);
        organization.setInn(randomString());
        organization.setOgrn(randomString());

        api.entity().organization().create(organization);

        ListEntity<Organization> updatedEntitiesList = api.entity().organization().get(filterEq("name", organization.getName()));
        assertEquals(1, updatedEntitiesList.getRows().size());

        Organization retrievedEntity = updatedEntitiesList.getRows().get(0);
        assertEquals(organization.getName(), retrievedEntity.getName());
        assertEquals(organization.getArchived(), retrievedEntity.getArchived());
        assertEquals(organization.getAccounts(), retrievedEntity.getAccounts());
        assertEquals(organization.getCompanyType(), retrievedEntity.getCompanyType());
        assertEquals(organization.getInn(), retrievedEntity.getInn());
        assertEquals(organization.getOgrn(), retrievedEntity.getOgrn());
    }

    @Test
    public void metadataTest() throws IOException, LognexApiException {
        MetadataAttributeSharedResponse metadata = api.entity().organization().metadata();
        assertTrue(metadata.getCreateShared());
    }

    @Test
    public void getAccountsTest() throws IOException, LognexApiException {
        Organization organization = new Organization();
        organization.setName("organization_" + randomString(3) + "_" + new Date().getTime());

        ListEntity<AgentAccount> accounts = new ListEntity<>();

        accounts.setRows(new ArrayList<>());
        AgentAccount account = new AgentAccount();
        account.setIsDefault(true);
        account.setAccountNumber(randomString());
        accounts.getRows().add(account);
        account.setIsDefault(false);
        accounts.getRows().add(account);
        organization.setAccounts(accounts);

        api.entity().organization().create(organization);

        ListEntity<AgentAccount> accountList = api.entity().organization().getAccounts(organization.getId());
        assertEquals(2, accountList.getRows().size());
        assertEquals(account.getAccountNumber(), accountList.getRows().get(0).getAccountNumber());
        assertTrue(accountList.getRows().get(0).getIsDefault());
        assertEquals(account.getAccountNumber(), accountList.getRows().get(1).getAccountNumber());
        assertFalse(accountList.getRows().get(1).getIsDefault());
    }

    @Test
    public void postAccountsTest() throws IOException, LognexApiException {
        Organization organization = new Organization();
        organization.setName("organization_" + randomString(3) + "_" + new Date().getTime());

        ListEntity<AgentAccount> accounts = new ListEntity<>();

        accounts.setRows(new ArrayList<>());
        AgentAccount account = new AgentAccount();
        account.setIsDefault(true);
        account.setAccountNumber(randomString());
        accounts.getRows().add(account);
        account.setIsDefault(false);
        accounts.getRows().add(account);

        api.entity().organization().create(organization);

        api.entity().organization().createAccounts(organization.getId(), accounts.getRows());

        ListEntity<AgentAccount> accountList = api.entity().organization().getAccounts(organization.getId());
        assertEquals(2, accountList.getRows().size());
        assertEquals(account.getAccountNumber(), accountList.getRows().get(0).getAccountNumber());
        assertTrue(accountList.getRows().get(0).getIsDefault());
        assertEquals(account.getAccountNumber(), accountList.getRows().get(1).getAccountNumber());
        assertFalse(accountList.getRows().get(1).getIsDefault());
    }

    @Override
    protected void getAsserts(MetaEntity originalEntity, MetaEntity retrievedEntity) {
        Organization originalOrganization = (Organization) originalEntity;
        Organization retrievedOrganization = (Organization) retrievedEntity;

        assertEquals(originalOrganization.getName(), retrievedOrganization.getName());
        assertEquals(originalOrganization.getCompanyType(), retrievedOrganization.getCompanyType());
        assertEquals(originalOrganization.getAccounts(), retrievedOrganization.getAccounts());
        assertEquals(originalOrganization.getInn(), retrievedOrganization.getInn());
        assertEquals(originalOrganization.getOgrn(), retrievedOrganization.getOgrn());
    }

    @Override
    protected void putAsserts(MetaEntity originalEntity, MetaEntity updatedEntity, Object changedField) {
        Organization originalOrganization = (Organization) originalEntity;
        Organization updatedOrganization = (Organization) updatedEntity;

        assertNotEquals(originalOrganization.getName(), updatedOrganization.getName());
        assertEquals(changedField, updatedOrganization.getName());
        assertEquals(originalOrganization.getCompanyType(), updatedOrganization.getCompanyType());
        assertEquals(originalOrganization.getAccounts(), updatedOrganization.getAccounts());
        assertEquals(originalOrganization.getInn(), updatedOrganization.getInn());
        assertEquals(originalOrganization.getOgrn(), updatedOrganization.getOgrn());
    }

    @Override
    protected EntityApiClient entityClient() {
        return api.entity().organization();
    }

    @Override
    protected Class<? extends MetaEntity> entityClass() {
        return Organization.class;
    }
}
