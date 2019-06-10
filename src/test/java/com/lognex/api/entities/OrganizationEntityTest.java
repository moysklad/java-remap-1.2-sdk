package com.lognex.api.entities;

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

public class OrganizationEntityTest extends EntityTestBase {
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
    public void getTest() throws IOException, LognexApiException {
        OrganizationEntity organization = simpleEntityFactory.createSimpleOrganization();

        OrganizationEntity retrievedEntity = api.entity().organization().get(organization.getId());
        getAsserts(organization, retrievedEntity);

        retrievedEntity = api.entity().organization().get(organization);
        getAsserts(organization, retrievedEntity);
    }

    @Test
    public void putTest() throws IOException, LognexApiException {
        OrganizationEntity organization = simpleEntityFactory.createSimpleOrganization();

        OrganizationEntity retrievedOriginalEntity = api.entity().organization().get(organization.getId());
        String name = "organization_" + randomString(3) + "_" + new Date().getTime();
        organization.setName(name);
        api.entity().organization().put(organization.getId(), organization);
        putAsserts(organization, retrievedOriginalEntity, name);

        retrievedOriginalEntity.set(organization);

        name = "organization_" + randomString(3) + "_" + new Date().getTime();
        organization.setName(name);
        api.entity().organization().put(organization);
        putAsserts(organization, retrievedOriginalEntity, name);
    }

    @Test
    public void deleteTest() throws IOException, LognexApiException {
        OrganizationEntity organization = simpleEntityFactory.createSimpleOrganization();

        ListEntity<OrganizationEntity> entitiesList = api.entity().organization().get(filterEq("name", organization.getName()));
        assertEquals((Integer) 1, entitiesList.getMeta().getSize());

        api.entity().organization().delete(organization.getId());

        entitiesList = api.entity().organization().get(filterEq("name", organization.getName()));
        assertEquals((Integer) 0, entitiesList.getMeta().getSize());
    }

    @Test
    public void deleteByIdTest() throws IOException, LognexApiException {
        OrganizationEntity organization = simpleEntityFactory.createSimpleOrganization();

        ListEntity<OrganizationEntity> entitiesList = api.entity().organization().get(filterEq("name", organization.getName()));
        assertEquals((Integer) 1, entitiesList.getMeta().getSize());

        api.entity().organization().delete(organization);

        entitiesList = api.entity().organization().get(filterEq("name", organization.getName()));
        assertEquals((Integer) 0, entitiesList.getMeta().getSize());
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

    private void getAsserts(OrganizationEntity organization, OrganizationEntity retrievedEntity) {
        assertEquals(organization.getName(), retrievedEntity.getName());
        assertEquals(organization.getCompanyType(), retrievedEntity.getCompanyType());
        assertEquals(organization.getAccounts(), retrievedEntity.getAccounts());
        assertEquals(organization.getInn(), retrievedEntity.getInn());
        assertEquals(organization.getOgrn(), retrievedEntity.getOgrn());
    }

    private void putAsserts(OrganizationEntity organization, OrganizationEntity retrievedOriginalEntity, String name) throws IOException, LognexApiException {
        OrganizationEntity retrievedUpdatedEntity = api.entity().organization().get(organization.getId());

        assertNotEquals(retrievedUpdatedEntity.getName(), retrievedOriginalEntity.getName());
        assertEquals(name, retrievedUpdatedEntity.getName());
        assertEquals(retrievedUpdatedEntity.getCompanyType(), retrievedOriginalEntity.getCompanyType());
        assertEquals(retrievedUpdatedEntity.getAccounts(), retrievedOriginalEntity.getAccounts());
        assertEquals(retrievedUpdatedEntity.getInn(), retrievedOriginalEntity.getInn());
        assertEquals(retrievedUpdatedEntity.getOgrn(), retrievedOriginalEntity.getOgrn());
    }
}
