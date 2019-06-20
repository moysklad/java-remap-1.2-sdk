package com.lognex.api.entities;

import com.lognex.api.clients.ApiClient;
import com.lognex.api.entities.agents.CounterpartyEntity;
import com.lognex.api.responses.ListEntity;
import com.lognex.api.responses.metadata.MetadataAttributeSharedStatesResponse;
import com.lognex.api.utils.LognexApiException;
import org.junit.Test;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Date;

import static com.lognex.api.utils.params.FilterParam.filterEq;
import static org.junit.Assert.*;

public class ContractEntityTest extends EntityGetUpdateDeleteTest {
    @Test
    public void createTest() throws IOException, LognexApiException {
        ContractEntity contract = new ContractEntity();
        contract.setName("contract_" + randomString(3) + "_" + new Date().getTime());
        contract.setArchived(false);
        contract.setDescription(randomString());
        contract.setSum(randomLong(100, 10000));
        contract.setMoment(LocalDateTime.now());
        contract.setContractType(ContractEntity.Type.sales);
        contract.setRewardType(RewardType.none);

        contract.setOwnAgent(simpleEntityFactory.getOwnOrganization());

        CounterpartyEntity agent = simpleEntityFactory.createSimpleCounterparty();
        contract.setAgent(agent);

        api.entity().contract().post(contract);

        ListEntity<ContractEntity> updatedEntitiesList = api.entity().contract().get(filterEq("name", contract.getName()));
        assertEquals(1, updatedEntitiesList.getRows().size());

        ContractEntity retrievedEntity = updatedEntitiesList.getRows().get(0);
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
    public void metadataTest() throws IOException, LognexApiException {
        MetadataAttributeSharedStatesResponse metadata = api.entity().contract().metadata();

        assertFalse(metadata.getCreateShared());
    }

    @Override
    protected void getAsserts(MetaEntity originalEntity, MetaEntity retrievedEntity) {
        ContractEntity originalContract = (ContractEntity) originalEntity;
        ContractEntity retrievedContract = (ContractEntity) retrievedEntity;
        assertEquals(originalContract.getName(), retrievedEntity.getName());
        assertEquals(originalContract.getOwnAgent().getMeta().getHref(), retrievedContract.getOwnAgent().getMeta().getHref());
        assertEquals(originalContract.getAgent().getMeta().getHref(), retrievedContract.getAgent().getMeta().getHref());
    }

    @Override
    protected void putAsserts(MetaEntity originalEntity, MetaEntity updatedEntity, Object changedField) {
        ContractEntity updatedContract = (ContractEntity) updatedEntity;
        ContractEntity originalContract = (ContractEntity) originalEntity;

        assertNotEquals(originalContract.getName(), updatedContract.getName());
        assertEquals(changedField, updatedContract.getName());
        assertEquals(originalContract.getOwnAgent().getMeta().getHref(), updatedContract.getOwnAgent().getMeta().getHref());
        assertEquals(originalContract.getAgent().getMeta().getHref(), updatedContract.getAgent().getMeta().getHref());
    }

    @Override
    protected ApiClient entityClient() {
        return api.entity().contract();
    }

    @Override
    protected Class<? extends MetaEntity> entityClass() {
        return ContractEntity.class;
    }
}

