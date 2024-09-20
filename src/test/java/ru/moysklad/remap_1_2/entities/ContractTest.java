package ru.moysklad.remap_1_2.entities;

import org.junit.Test;
import ru.moysklad.remap_1_2.clients.EntityClientBase;
import ru.moysklad.remap_1_2.entities.agents.Counterparty;
import ru.moysklad.remap_1_2.responses.ListEntity;
import ru.moysklad.remap_1_2.responses.metadata.MetadataAttributeSharedStatesResponse;
import ru.moysklad.remap_1_2.utils.ApiClientException;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Date;

import static ru.moysklad.remap_1_2.utils.params.FilterParam.filterEq;
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
        MetadataAttributeSharedStatesResponse response = api.entity().contract().metadata().get();

        assertFalse(response.getCreateShared());
    }

    @Test
    public void attributesTest() throws IOException, ApiClientException{
        ListEntity<Attribute> attributes = api.entity().contract().metadataAttributes();
        assertNotNull(attributes);
    }

    @Test
    public void createAttributeTest() throws IOException, ApiClientException {
        Attribute attribute = new AttributeEntity();
        attribute.setType(Attribute.Type.textValue);
        String name = "field" + randomString(3) + "_" + new Date().getTime();
        attribute.setName(name);
        attribute.setRequired(false);
        attribute.setDescription("description");
        AttributeEntity created = (AttributeEntity) api.entity().contract().createMetadataAttribute(attribute);
        assertNotNull(created);
        assertEquals(name, created.getName());
        assertEquals(Attribute.Type.textValue, created.getType());
        assertFalse(created.getRequired());
        assertEquals("description", created.getDescription());
    }

    @Test
    public void updateAttributeTest() throws IOException, ApiClientException {
        Attribute attribute = new AttributeEntity();
        attribute.setEntityType(Meta.Type.PRODUCT);
        attribute.setName("field" + randomString(3) + "_" + new Date().getTime());
        attribute.setRequired(true);
        AttributeEntity created = (AttributeEntity) api.entity().contract().createMetadataAttribute(attribute);

        String name = "field" + randomString(3) + "_" + new Date().getTime();
        created.setName(name);
        created.setRequired(false);
        AttributeEntity updated = (AttributeEntity) api.entity().contract().updateMetadataAttribute(created);
        assertNotNull(created);
        assertEquals(name, updated.getName());
        assertNull(updated.getType());
        assertEquals(Meta.Type.PRODUCT, updated.getEntityType());
        assertFalse(updated.getRequired());
    }

    @Test
    public void deleteAttributeTest() throws IOException, ApiClientException{
        Attribute attribute = new AttributeEntity();
        attribute.setEntityType(Meta.Type.PRODUCT);
        attribute.setName("field" + randomString(3) + "_" + new Date().getTime());
        attribute.setRequired(true);
        AttributeEntity created = (AttributeEntity) api.entity().contract().createMetadataAttribute(attribute);

        api.entity().contract().deleteMetadataAttribute(created);

        try {
            api.entity().contract().metadataAttributes(created.getId());
        } catch (ApiClientException e) {
            assertEquals(404, e.getStatusCode());
        }
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
    public EntityClientBase entityClient() {
        return api.entity().contract();
    }

    @Override
    public Class<? extends MetaEntity> entityClass() {
        return Contract.class;
    }
}

