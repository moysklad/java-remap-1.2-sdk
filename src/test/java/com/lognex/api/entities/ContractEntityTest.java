package com.lognex.api.entities;

import com.lognex.api.entities.agents.CounterpartyEntity;
import com.lognex.api.entities.agents.OrganizationEntity;
import com.lognex.api.responses.ListEntity;
import com.lognex.api.responses.metadata.MetadataAttributeSharedStatesResponse;
import com.lognex.api.utils.LognexApiException;
import org.junit.Test;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Date;

import static com.lognex.api.utils.params.FilterParam.filterEq;
import static org.junit.Assert.*;

public class ContractEntityTest extends EntityTestBase {
    @Test
    public void createTest() throws IOException, LognexApiException {
        ContractEntity e = new ContractEntity();
        e.setName("contract_" + randomString(3) + "_" + new Date().getTime());
        e.setArchived(false);
        e.setDescription(randomString());
        e.setSum(randomLong(100, 10000));
        e.setMoment(LocalDateTime.now());
        e.setContractType(ContractEntity.Type.sales);
        e.setRewardType(RewardType.none);

        ListEntity<OrganizationEntity> orgList = api.entity().organization().get();
        assertNotEquals(0, orgList.getRows().size());
        e.setOwnAgent(orgList.getRows().get(0));

        CounterpartyEntity agent = new CounterpartyEntity();
        agent.setName(randomString());
        api.entity().counterparty().post(agent);
        e.setAgent(agent);

        api.entity().contract().post(e);

        ListEntity<ContractEntity> updatedEntitiesList = api.entity().contract().get(filterEq("name", e.getName()));
        assertEquals(1, updatedEntitiesList.getRows().size());

        ContractEntity retrievedEntity = updatedEntitiesList.getRows().get(0);
        assertEquals(e.getName(), retrievedEntity.getName());
        assertEquals(e.getArchived(), retrievedEntity.getArchived());
        assertEquals(e.getDescription(), retrievedEntity.getDescription());
        assertEquals(e.getSum(), retrievedEntity.getSum());
        assertEquals(e.getMoment(), retrievedEntity.getMoment());
        assertEquals(e.getContractType(), retrievedEntity.getContractType());
        assertEquals(e.getRewardType(), retrievedEntity.getRewardType());
        assertEquals(e.getOwnAgent().getMeta().getHref(), retrievedEntity.getOwnAgent().getMeta().getHref());
        assertEquals(e.getAgent().getMeta().getHref(), retrievedEntity.getAgent().getMeta().getHref());
    }

    @Test
    public void getTest() throws IOException, LognexApiException {
        ContractEntity e = createSimpleContract();

        ContractEntity retrievedEntity = api.entity().contract().get(e.getId());
        getAsserts(e, retrievedEntity);

        retrievedEntity = api.entity().contract().get(e);
        getAsserts(e, retrievedEntity);
    }

    @Test
    public void putTest() throws IOException, LognexApiException, InterruptedException {
        ContractEntity e = createSimpleContract();

        ContractEntity retrievedOriginalEntity = api.entity().contract().get(e.getId());
        String name = "contract_" + randomString(3) + "_" + new Date().getTime();
        e.setName(name);
        api.entity().contract().put(e.getId(), e);
        putAsserts(e, retrievedOriginalEntity, name);

        e = createSimpleContract();
        retrievedOriginalEntity = api.entity().contract().get(e.getId());
        name = "contract_" + randomString(3) + "_" + new Date().getTime();
        e.setName(name);
        api.entity().contract().put(e);
        putAsserts(e, retrievedOriginalEntity, name);
    }

    @Test
    public void deleteTest() throws IOException, LognexApiException {
        ContractEntity e = createSimpleContract();

        ListEntity<ContractEntity> entitiesList = api.entity().contract().get(filterEq("name", e.getName()));
        assertEquals((Integer) 1, entitiesList.getMeta().getSize());

        api.entity().contract().delete(e.getId());

        entitiesList = api.entity().contract().get(filterEq("name", e.getName()));
        assertEquals((Integer) 0, entitiesList.getMeta().getSize());
    }

    @Test
    public void deleteByIdTest() throws IOException, LognexApiException {
        ContractEntity e = createSimpleContract();

        ListEntity<ContractEntity> entitiesList = api.entity().contract().get(filterEq("name", e.getName()));
        assertEquals((Integer) 1, entitiesList.getMeta().getSize());

        api.entity().contract().delete(e);

        entitiesList = api.entity().contract().get(filterEq("name", e.getName()));
        assertEquals((Integer) 0, entitiesList.getMeta().getSize());
    }

    @Test
    public void metadataTest() throws IOException, LognexApiException {
        MetadataAttributeSharedStatesResponse metadata = api.entity().contract().metadata();

        assertFalse(metadata.getCreateShared());
    }


    private ContractEntity createSimpleContract() throws IOException, LognexApiException {
        ContractEntity e = new ContractEntity();
        e.setName("contract_" + randomString(3) + "_" + new Date().getTime());
        e.setArchived(false);
        e.setDescription(randomString());
        e.setSum(randomLong(100, 10000));
        e.setMoment(LocalDateTime.now());

        ListEntity<OrganizationEntity> orgList = api.entity().organization().get();
        assertNotEquals(0, orgList.getRows().size());
        e.setOwnAgent(orgList.getRows().get(0));

        CounterpartyEntity agent = new CounterpartyEntity();
        agent.setName(randomString());
        api.entity().counterparty().post(agent);
        e.setAgent(agent);

        api.entity().contract().post(e);

        return e;
    }

    private void getAsserts(ContractEntity e, ContractEntity retrievedEntity) {
        assertEquals(e.getName(), retrievedEntity.getName());
        assertEquals(e.getArchived(), retrievedEntity.getArchived());
        assertEquals(e.getDescription(), retrievedEntity.getDescription());
        assertEquals(e.getSum(), retrievedEntity.getSum());
        assertEquals(e.getMoment(), retrievedEntity.getMoment());
        assertEquals(e.getContractType(), retrievedEntity.getContractType());
        assertEquals(e.getRewardType(), retrievedEntity.getRewardType());
        assertEquals(e.getOwnAgent().getMeta().getHref(), retrievedEntity.getOwnAgent().getMeta().getHref());
        assertEquals(e.getAgent().getMeta().getHref(), retrievedEntity.getAgent().getMeta().getHref());
    }

    private void putAsserts(ContractEntity e, ContractEntity retrievedOriginalEntity, String name) throws IOException, LognexApiException {
        ContractEntity retrievedUpdatedEntity = api.entity().contract().get(e.getId());

        assertNotEquals(retrievedOriginalEntity.getName(), retrievedUpdatedEntity.getName());
        assertEquals(name, retrievedUpdatedEntity.getName());
        assertEquals(retrievedOriginalEntity.getArchived(), retrievedUpdatedEntity.getArchived());
        assertEquals(retrievedOriginalEntity.getDescription(), retrievedUpdatedEntity.getDescription());
        assertEquals(retrievedOriginalEntity.getSum(), retrievedUpdatedEntity.getSum());
        assertEquals(retrievedOriginalEntity.getMoment(), retrievedUpdatedEntity.getMoment());
        assertEquals(retrievedOriginalEntity.getContractType(), retrievedUpdatedEntity.getContractType());
        assertEquals(retrievedOriginalEntity.getRewardType(), retrievedUpdatedEntity.getRewardType());
        assertEquals(retrievedOriginalEntity.getOwnAgent().getMeta().getHref(), retrievedUpdatedEntity.getOwnAgent().getMeta().getHref());
        assertEquals(retrievedOriginalEntity.getAgent().getMeta().getHref(), retrievedUpdatedEntity.getAgent().getMeta().getHref());
    }
}

