package com.lognex.api.entities;

import com.lognex.api.clients.EntityClientBase;
import com.lognex.api.entities.agents.Counterparty;
import com.lognex.api.responses.ListEntity;
import com.lognex.api.responses.metadata.MetadataAttributeSharedStatesResponse;
import com.lognex.api.utils.ApiClientException;
import org.junit.Test;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Date;

import static com.lognex.api.utils.params.FilterParam.filterEq;
import static org.junit.Assert.*;

public class ContractTest extends EntityGetUpdateDeleteTest {
    @Test
    public void createTest() throws IOException, ApiClientException {
        Contract contract = new Contract();
        contract.setName("contract_" + randomString(3) + "_" + new Date().getTime());
        contract.setArchived(false);
        contract.setDescription(randomString());
        contract.setSum(randomLong(100, 10000));
        contract.setMoment(LocalDateTime.now());
        contract.setContractType(Contract.Type.sales);
        contract.setRewardType(RewardType.none);

        contract.setOwnAgent(simpleEntityManager.getOwnOrganization());

        Counterparty agent = simpleEntityManager.createSimple(Counterparty.class);
        contract.setAgent(agent);

        api.entity().contract().create(contract);

        ListEntity<Contract> updatedEntitiesList = api.entity().contract().get(filterEq("name", contract.getName()));
        assertEquals(1, updatedEntitiesList.getRows().size());

        Contract retrievedEntity = updatedEntitiesList.getRows().get(0);
        assertEquals(contract.getName(), retrievedEntity.getName());
        assertEquals(contract.getArchived(), retrievedEntity.getArchived());
        assertEquals(contract.getDescription(), retrievedEntity.getDescription());
        assertEquals(contract.getSum(), retrievedEntity.getSum());
        assertEquals(contract.getMoment(), retrievedEntity.getMoment());
        assertEquals(contract.getContractType(), retrievedEntity.getContractType());
        assertEquals(contract.getRewardType(), retrievedEntity.getRewardType());
        assertEquals(contract.getOwnAgent().getMeta().getHref(), retrievedEntity.getOwnAgent().getMeta().getHref());
        assertEquals(contract.getAgent().getMeta().getHref(), retrievedEntity.getAgent().getMeta().getHref());
    }

    @Test
    public void metadataTest() throws IOException, ApiClientException {
        MetadataAttributeSharedStatesResponse metadata = api.entity().contract().metadata();

        assertFalse(metadata.getCreateShared());
    }

    @Override
    protected void getAsserts(MetaEntity originalEntity, MetaEntity retrievedEntity) {
        Contract originalContract = (Contract) originalEntity;
        Contract retrievedContract = (Contract) retrievedEntity;
        assertEquals(originalContract.getName(), retrievedEntity.getName());
        assertEquals(originalContract.getOwnAgent().getMeta().getHref(), retrievedContract.getOwnAgent().getMeta().getHref());
        assertEquals(originalContract.getAgent().getMeta().getHref(), retrievedContract.getAgent().getMeta().getHref());
    }

    @Override
    protected void putAsserts(MetaEntity originalEntity, MetaEntity updatedEntity, Object changedField) {
        Contract updatedContract = (Contract) updatedEntity;
        Contract originalContract = (Contract) originalEntity;

        assertNotEquals(originalContract.getName(), updatedContract.getName());
        assertEquals(changedField, updatedContract.getName());
        assertEquals(originalContract.getOwnAgent().getMeta().getHref(), updatedContract.getOwnAgent().getMeta().getHref());
        assertEquals(originalContract.getAgent().getMeta().getHref(), updatedContract.getAgent().getMeta().getHref());
    }

    @Override
    protected EntityClientBase entityClient() {
        return api.entity().contract();
    }

    @Override
    protected Class<? extends MetaEntity> entityClass() {
        return Contract.class;
    }
}

