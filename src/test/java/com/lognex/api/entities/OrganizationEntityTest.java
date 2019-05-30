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
        OrganizationEntity e = new OrganizationEntity();
        e.setName("organization_" + randomString(3) + "_" + new Date().getTime());
        e.setArchived(false);
        e.setCompanyType(CompanyType.legal);
        e.setInn(randomString());
        e.setOgrn(randomString());

        api.entity().organization().post(e);

        ListEntity<OrganizationEntity> updatedEntitiesList = api.entity().organization().get(filterEq("name", e.getName()));
        assertEquals(1, updatedEntitiesList.getRows().size());

        OrganizationEntity retrievedEntity = updatedEntitiesList.getRows().get(0);
        assertEquals(e.getName(), retrievedEntity.getName());
        assertEquals(e.getArchived(), retrievedEntity.getArchived());
        assertEquals(e.getAccounts(), retrievedEntity.getAccounts());
        assertEquals(e.getCompanyType(), retrievedEntity.getCompanyType());
        assertEquals(e.getInn(), retrievedEntity.getInn());
        assertEquals(e.getOgrn(), retrievedEntity.getOgrn());
    }

    @Test
    public void getTest() throws IOException, LognexApiException {
        OrganizationEntity e = createSimpleOrganization();

        OrganizationEntity retrievedEntity = api.entity().organization().get(e.getId());
        getAsserts(e, retrievedEntity);

        retrievedEntity = api.entity().organization().get(e);
        getAsserts(e, retrievedEntity);
    }

    @Test
    public void putTest() throws IOException, LognexApiException, InterruptedException {
        OrganizationEntity e = createSimpleOrganization();

        OrganizationEntity retrievedOriginalEntity = api.entity().organization().get(e.getId());
        String name = "organization_" + randomString(3) + "_" + new Date().getTime();
        e.setName(name);
        api.entity().organization().put(e.getId(), e);
        putAsserts(e, retrievedOriginalEntity, name);

        retrievedOriginalEntity.set(e);

        name = "organization_" + randomString(3) + "_" + new Date().getTime();
        e.setName(name);
        api.entity().organization().put(e);
        putAsserts(e, retrievedOriginalEntity, name);
    }

    @Test
    public void deleteTest() throws IOException, LognexApiException {
        OrganizationEntity e = createSimpleOrganization();

        ListEntity<OrganizationEntity> entitiesList = api.entity().organization().get(filterEq("name", e.getName()));
        assertEquals((Integer) 1, entitiesList.getMeta().getSize());

        api.entity().organization().delete(e.getId());

        entitiesList = api.entity().organization().get(filterEq("name", e.getName()));
        assertEquals((Integer) 0, entitiesList.getMeta().getSize());
    }

    @Test
    public void deleteByIdTest() throws IOException, LognexApiException {
        OrganizationEntity e = createSimpleOrganization();

        ListEntity<OrganizationEntity> entitiesList = api.entity().organization().get(filterEq("name", e.getName()));
        assertEquals((Integer) 1, entitiesList.getMeta().getSize());

        api.entity().organization().delete(e);

        entitiesList = api.entity().organization().get(filterEq("name", e.getName()));
        assertEquals((Integer) 0, entitiesList.getMeta().getSize());
    }

    @Test
    public void metadataTest() throws IOException, LognexApiException {
        MetadataAttributeSharedResponse metadata = api.entity().organization().metadata();
        assertTrue(metadata.getCreateShared());
    }

    @Test
    public void getAccountsTest() throws IOException, LognexApiException {
        OrganizationEntity e = new OrganizationEntity();
        e.setName("organization_" + randomString(3) + "_" + new Date().getTime());

        ListEntity<AccountEntity> accounts = new ListEntity<>();

        accounts.setRows(new ArrayList<>());
        AccountEntity ae = new AccountEntity();
        ae.setIsDefault(true);
        ae.setAccountNumber(randomString());
        accounts.getRows().add(ae);
        ae.setIsDefault(false);
        accounts.getRows().add(ae);
        e.setAccounts(accounts);

        api.entity().organization().post(e);

        ListEntity<AccountEntity> accountList = api.entity().organization().getAccounts(e.getId());
        assertEquals(2, accountList.getRows().size());
        assertEquals(ae.getAccountNumber(), accountList.getRows().get(0).getAccountNumber());
        assertTrue(accountList.getRows().get(0).getIsDefault());
        assertEquals(ae.getAccountNumber(), accountList.getRows().get(1).getAccountNumber());
        assertFalse(accountList.getRows().get(1).getIsDefault());
    }

    @Test
    public void postAccountsTest() throws IOException, LognexApiException {
        OrganizationEntity e = new OrganizationEntity();
        e.setName("organization_" + randomString(3) + "_" + new Date().getTime());

        ListEntity<AccountEntity> accounts = new ListEntity<>();

        accounts.setRows(new ArrayList<>());
        AccountEntity ae = new AccountEntity();
        ae.setIsDefault(true);
        ae.setAccountNumber(randomString());
        accounts.getRows().add(ae);
        ae.setIsDefault(false);
        accounts.getRows().add(ae);

        api.entity().organization().post(e);

        api.entity().organization().postAccounts(e.getId(), accounts.getRows());

        ListEntity<AccountEntity> accountList = api.entity().organization().getAccounts(e.getId());
        assertEquals(2, accountList.getRows().size());
        assertEquals(ae.getAccountNumber(), accountList.getRows().get(0).getAccountNumber());
        assertTrue(accountList.getRows().get(0).getIsDefault());
        assertEquals(ae.getAccountNumber(), accountList.getRows().get(1).getAccountNumber());
        assertFalse(accountList.getRows().get(1).getIsDefault());
    }

    private OrganizationEntity createSimpleOrganization() throws IOException, LognexApiException {
        OrganizationEntity e = new OrganizationEntity();
        e.setName("organization_" + randomString(3) + "_" + new Date().getTime());
        e.setArchived(false);
        e.setCompanyType(CompanyType.legal);
        e.setInn(randomString());
        e.setOgrn(randomString());
        e.setLegalAddress(randomString());
        e.setLegalTitle(randomString());

        api.entity().organization().post(e);

        return e;
    }

    private void getAsserts(OrganizationEntity e, OrganizationEntity retrievedEntity) {
        assertEquals(e.getName(), retrievedEntity.getName());
        assertEquals(e.getArchived(), retrievedEntity.getArchived());
        assertEquals(e.getCompanyType(), retrievedEntity.getCompanyType());
        assertEquals(e.getAccounts(), retrievedEntity.getAccounts());
        assertEquals(e.getInn(), retrievedEntity.getInn());
        assertEquals(e.getOgrn(), retrievedEntity.getOgrn());
        assertEquals(e.getLegalAddress(), retrievedEntity.getLegalAddress());
        assertEquals(e.getLegalTitle(), retrievedEntity.getLegalTitle());
    }

    private void putAsserts(OrganizationEntity e, OrganizationEntity retrievedOriginalEntity, String name) throws IOException, LognexApiException {
        OrganizationEntity retrievedUpdatedEntity = api.entity().organization().get(e.getId());

        assertNotEquals(retrievedUpdatedEntity.getName(), retrievedOriginalEntity.getName());
        assertEquals(name, retrievedUpdatedEntity.getName());
        assertEquals(retrievedUpdatedEntity.getArchived(), retrievedOriginalEntity.getArchived());
        assertEquals(retrievedUpdatedEntity.getCompanyType(), retrievedOriginalEntity.getCompanyType());
        assertEquals(retrievedUpdatedEntity.getAccounts(), retrievedOriginalEntity.getAccounts());
        assertEquals(retrievedUpdatedEntity.getInn(), retrievedOriginalEntity.getInn());
        assertEquals(retrievedUpdatedEntity.getOgrn(), retrievedOriginalEntity.getOgrn());
        assertEquals(retrievedUpdatedEntity.getLegalAddress(), retrievedOriginalEntity.getLegalAddress());
        assertEquals(retrievedUpdatedEntity.getLegalTitle(), retrievedOriginalEntity.getLegalTitle());
    }
}
