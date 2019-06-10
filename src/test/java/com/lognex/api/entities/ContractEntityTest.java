package com.lognex.api.entities;

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

public class ContractEntityTest extends EntityTestBase {
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
    public void getTest() throws IOException, LognexApiException {
        ContractEntity contract = simpleEntityFactory.createSimpleContract();

        ContractEntity retrievedEntity = api.entity().contract().get(contract.getId());
        getAsserts(contract, retrievedEntity);

        retrievedEntity = api.entity().contract().get(contract);
        getAsserts(contract, retrievedEntity);
    }

    @Test
    public void putTest() throws IOException, LognexApiException {
        ContractEntity contract = simpleEntityFactory.createSimpleContract();

        ContractEntity retrievedOriginalEntity = api.entity().contract().get(contract.getId());
        String name = "contract_" + randomString(3) + "_" + new Date().getTime();
        contract.setName(name);
        api.entity().contract().put(contract.getId(), contract);
        putAsserts(contract, retrievedOriginalEntity, name);

        contract = simpleEntityFactory.createSimpleContract();
        retrievedOriginalEntity = api.entity().contract().get(contract.getId());
        name = "contract_" + randomString(3) + "_" + new Date().getTime();
        contract.setName(name);
        api.entity().contract().put(contract);
        putAsserts(contract, retrievedOriginalEntity, name);
    }

    @Test
    public void deleteTest() throws IOException, LognexApiException {
        ContractEntity contract = simpleEntityFactory.createSimpleContract();

        ListEntity<ContractEntity> entitiesList = api.entity().contract().get(filterEq("name", contract.getName()));
        assertEquals((Integer) 1, entitiesList.getMeta().getSize());

        api.entity().contract().delete(contract.getId());

        entitiesList = api.entity().contract().get(filterEq("name", contract.getName()));
        assertEquals((Integer) 0, entitiesList.getMeta().getSize());
    }

    @Test
    public void deleteByIdTest() throws IOException, LognexApiException {
        ContractEntity contract = simpleEntityFactory.createSimpleContract();

        ListEntity<ContractEntity> entitiesList = api.entity().contract().get(filterEq("name", contract.getName()));
        assertEquals((Integer) 1, entitiesList.getMeta().getSize());

        api.entity().contract().delete(contract);

        entitiesList = api.entity().contract().get(filterEq("name", contract.getName()));
        assertEquals((Integer) 0, entitiesList.getMeta().getSize());
    }

    @Test
    public void metadataTest() throws IOException, LognexApiException {
        MetadataAttributeSharedStatesResponse metadata = api.entity().contract().metadata();

        assertFalse(metadata.getCreateShared());
    }

    private void getAsserts(ContractEntity contract, ContractEntity retrievedEntity) {
        assertEquals(contract.getName(), retrievedEntity.getName());
        assertEquals(contract.getOwnAgent().getMeta().getHref(), retrievedEntity.getOwnAgent().getMeta().getHref());
        assertEquals(contract.getAgent().getMeta().getHref(), retrievedEntity.getAgent().getMeta().getHref());
    }

    private void putAsserts(ContractEntity contract, ContractEntity retrievedOriginalEntity, String name) throws IOException, LognexApiException {
        ContractEntity retrievedUpdatedEntity = api.entity().contract().get(contract.getId());

        assertNotEquals(retrievedOriginalEntity.getName(), retrievedUpdatedEntity.getName());
        assertEquals(name, retrievedUpdatedEntity.getName());
        assertEquals(retrievedOriginalEntity.getOwnAgent().getMeta().getHref(), retrievedUpdatedEntity.getOwnAgent().getMeta().getHref());
        assertEquals(retrievedOriginalEntity.getAgent().getMeta().getHref(), retrievedUpdatedEntity.getAgent().getMeta().getHref());
    }
}

