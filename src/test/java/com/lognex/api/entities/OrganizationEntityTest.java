package com.lognex.api.entities;

import com.lognex.api.clients.ApiClient;
import com.lognex.api.entities.agents.OrganizationEntity;
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
        OrganizationEntity organization = new OrganizationEntity();
        organization.setName("organization_" + randomString(3) + "_" + new Date().getTime());
        organization.setArchived(false);
        organization.setCompanyType(CompanyType.legal);
        organization.setInn(randomString());
        organization.setOgrn(randomString());

        api.entity().organization().post(organization);

        ListEntity<OrganizationEntity> updatedEntitiesList = api.entity().organization().get(filterEq("name", organization.getName()));
        assertEquals(1, updatedEntitiesList.getRows().size());

        OrganizationEntity retrievedEntity = updatedEntitiesList.getRows().get(0);
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
        OrganizationEntity organization = new OrganizationEntity();
        organization.setName("organization_" + randomString(3) + "_" + new Date().getTime());

        ListEntity<AccountEntity> accounts = new ListEntity<>();

        accounts.setRows(new ArrayList<>());
        AccountEntity account = new AccountEntity();
        account.setIsDefault(true);
        account.setAccountNumber(randomString());
        accounts.getRows().add(account);
        account.setIsDefault(false);
        accounts.getRows().add(account);
        organization.setAccounts(accounts);

        api.entity().organization().post(organization);

        ListEntity<AccountEntity> accountList = api.entity().organization().getAccounts(organization.getId());
        assertEquals(2, accountList.getRows().size());
        assertEquals(account.getAccountNumber(), accountList.getRows().get(0).getAccountNumber());
        assertTrue(accountList.getRows().get(0).getIsDefault());
        assertEquals(account.getAccountNumber(), accountList.getRows().get(1).getAccountNumber());
        assertFalse(accountList.getRows().get(1).getIsDefault());
    }

    @Test
    public void postAccountsTest() throws IOException, LognexApiException {
        OrganizationEntity organization = new OrganizationEntity();
        organization.setName("organization_" + randomString(3) + "_" + new Date().getTime());

        ListEntity<AccountEntity> accounts = new ListEntity<>();

        accounts.setRows(new ArrayList<>());
        AccountEntity account = new AccountEntity();
        account.setIsDefault(true);
        account.setAccountNumber(randomString());
        accounts.getRows().add(account);
        account.setIsDefault(false);
        accounts.getRows().add(account);

        api.entity().organization().post(organization);

        api.entity().organization().postAccounts(organization.getId(), accounts.getRows());

        ListEntity<AccountEntity> accountList = api.entity().organization().getAccounts(organization.getId());
        assertEquals(2, accountList.getRows().size());
        assertEquals(account.getAccountNumber(), accountList.getRows().get(0).getAccountNumber());
        assertTrue(accountList.getRows().get(0).getIsDefault());
        assertEquals(account.getAccountNumber(), accountList.getRows().get(1).getAccountNumber());
        assertFalse(accountList.getRows().get(1).getIsDefault());
    }

    @Override
    protected void getAsserts(MetaEntity originalEntity, MetaEntity retrievedEntity) {
        OrganizationEntity originalOrganization = (OrganizationEntity) originalEntity;
        OrganizationEntity retrievedOrganization = (OrganizationEntity) retrievedEntity;

        assertEquals(originalOrganization.getName(), retrievedOrganization.getName());
        assertEquals(originalOrganization.getCompanyType(), retrievedOrganization.getCompanyType());
        assertEquals(originalOrganization.getAccounts(), retrievedOrganization.getAccounts());
        assertEquals(originalOrganization.getInn(), retrievedOrganization.getInn());
        assertEquals(originalOrganization.getOgrn(), retrievedOrganization.getOgrn());
    }

    @Override
    protected void putAsserts(MetaEntity originalEntity, MetaEntity updatedEntity, Object changedField) {
        OrganizationEntity originalOrganization = (OrganizationEntity) originalEntity;
        OrganizationEntity updatedOrganization = (OrganizationEntity) updatedEntity;

        assertNotEquals(originalOrganization.getName(), updatedOrganization.getName());
        assertEquals(changedField, updatedOrganization.getName());
        assertEquals(originalOrganization.getCompanyType(), updatedOrganization.getCompanyType());
        assertEquals(originalOrganization.getAccounts(), updatedOrganization.getAccounts());
        assertEquals(originalOrganization.getInn(), updatedOrganization.getInn());
        assertEquals(originalOrganization.getOgrn(), updatedOrganization.getOgrn());
    }

    @Override
    protected ApiClient entityClient() {
        return api.entity().organization();
    }

    @Override
    protected Class<? extends MetaEntity> entityClass() {
        return OrganizationEntity.class;
    }
}
