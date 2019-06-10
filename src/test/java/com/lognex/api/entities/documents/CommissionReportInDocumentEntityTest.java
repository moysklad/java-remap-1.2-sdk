package com.lognex.api.entities.documents;

import com.lognex.api.clients.ApiClient;
import com.lognex.api.entities.ContractEntity;
import com.lognex.api.entities.MetaEntity;
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

public class CommissionReportInDocumentEntityTest extends DocumentWithPositionsTestBase {
    @Test
    public void createTest() throws IOException, LognexApiException {
        CommissionReportInDocumentEntity commissionReportIn = new CommissionReportInDocumentEntity();
        commissionReportIn.setName("commissionreportin_" + randomString(3) + "_" + new Date().getTime());
        commissionReportIn.setDescription(randomString());
        commissionReportIn.setVatEnabled(true);
        commissionReportIn.setVatIncluded(true);
        commissionReportIn.setMoment(LocalDateTime.now());
        CounterpartyEntity agent = simpleEntityFactory.createSimpleCounterparty();
        commissionReportIn.setAgent(agent);
        OrganizationEntity organization = simpleEntityFactory.getOwnOrganization();
        commissionReportIn.setOrganization(organization);

        ContractEntity contract = new ContractEntity();
        contract.setName(randomString());
        contract.setOwnAgent(organization);
        contract.setAgent(agent);
        contract.setContractType(ContractEntity.Type.commission);
        api.entity().contract().post(contract);
        commissionReportIn.setContract(contract);

        commissionReportIn.setCommissionPeriodStart(LocalDateTime.now());
        commissionReportIn.setCommissionPeriodEnd(LocalDateTime.now().plusNanos(50));

        api.entity().commissionreportin().post(commissionReportIn);

        ListEntity<CommissionReportInDocumentEntity> updatedEntitiesList = api.entity().commissionreportin().get(filterEq("name", commissionReportIn.getName()));
        assertEquals(1, updatedEntitiesList.getRows().size());

        CommissionReportInDocumentEntity retrievedEntity = updatedEntitiesList.getRows().get(0);
        assertEquals(commissionReportIn.getName(), retrievedEntity.getName());
        assertEquals(commissionReportIn.getDescription(), retrievedEntity.getDescription());
        assertEquals(commissionReportIn.getVatEnabled(), retrievedEntity.getVatEnabled());
        assertEquals(commissionReportIn.getVatIncluded(), retrievedEntity.getVatIncluded());
        assertEquals(commissionReportIn.getMoment(), retrievedEntity.getMoment());
        assertEquals(commissionReportIn.getOrganization().getMeta().getHref(), retrievedEntity.getOrganization().getMeta().getHref());
        assertEquals(commissionReportIn.getAgent().getMeta().getHref(), retrievedEntity.getAgent().getMeta().getHref());
        assertEquals(commissionReportIn.getContract().getMeta().getHref(), retrievedEntity.getContract().getMeta().getHref());
    }

    @Test
    public void getTest() throws IOException, LognexApiException {
        CommissionReportInDocumentEntity commissionReportIn = simpleEntityFactory.createSimpleCommissionReportIn();

        CommissionReportInDocumentEntity retrievedEntity = api.entity().commissionreportin().get(commissionReportIn.getId());
        getAsserts(commissionReportIn, retrievedEntity);

        retrievedEntity = api.entity().commissionreportin().get(commissionReportIn);
        getAsserts(commissionReportIn, retrievedEntity);
    }

    @Test
    public void putTest() throws IOException, LognexApiException {
        CommissionReportInDocumentEntity commissionReportIn = simpleEntityFactory.createSimpleCommissionReportIn();

        CommissionReportInDocumentEntity retrievedOriginalEntity = api.entity().commissionreportin().get(commissionReportIn.getId());
        String name = "commissionreportin_" + randomString(3) + "_" + new Date().getTime();
        commissionReportIn.setName(name);
        api.entity().commissionreportin().put(commissionReportIn.getId(), commissionReportIn);
        putAsserts(commissionReportIn, retrievedOriginalEntity, name);

        retrievedOriginalEntity.set(commissionReportIn);

        name = "commissionreportin_" + randomString(3) + "_" + new Date().getTime();
        commissionReportIn.setName(name);
        api.entity().commissionreportin().put(commissionReportIn);
        putAsserts(commissionReportIn, retrievedOriginalEntity, name);
    }

    @Test
    public void deleteTest() throws IOException, LognexApiException {
        CommissionReportInDocumentEntity commissionReportIn = simpleEntityFactory.createSimpleCommissionReportIn();

        ListEntity<CommissionReportInDocumentEntity> entitiesList = api.entity().commissionreportin().get(filterEq("name", commissionReportIn.getName()));
        assertEquals((Integer) 1, entitiesList.getMeta().getSize());

        api.entity().commissionreportin().delete(commissionReportIn.getId());

        entitiesList = api.entity().commissionreportin().get(filterEq("name", commissionReportIn.getName()));
        assertEquals((Integer) 0, entitiesList.getMeta().getSize());
    }

    @Test
    public void deleteByIdTest() throws IOException, LognexApiException {
        CommissionReportInDocumentEntity commissionReportIn = simpleEntityFactory.createSimpleCommissionReportIn();

        ListEntity<CommissionReportInDocumentEntity> entitiesList = api.entity().commissionreportin().get(filterEq("name", commissionReportIn.getName()));
        assertEquals((Integer) 1, entitiesList.getMeta().getSize());

        api.entity().commissionreportin().delete(commissionReportIn);

        entitiesList = api.entity().commissionreportin().get(filterEq("name", commissionReportIn.getName()));
        assertEquals((Integer) 0, entitiesList.getMeta().getSize());
    }

    @Test
    public void metadataTest() throws IOException, LognexApiException {
        MetadataAttributeSharedStatesResponse response = api.entity().commissionreportin().metadata().get();

        assertFalse(response.getCreateShared());
    }

    private void getAsserts(CommissionReportInDocumentEntity commissionReportIn, CommissionReportInDocumentEntity retrievedEntity) {
        assertEquals(commissionReportIn.getName(), retrievedEntity.getName());
        assertEquals(commissionReportIn.getOrganization().getMeta().getHref(), retrievedEntity.getOrganization().getMeta().getHref());
        assertEquals(commissionReportIn.getAgent().getMeta().getHref(), retrievedEntity.getAgent().getMeta().getHref());
        assertEquals(commissionReportIn.getContract().getMeta().getHref(), retrievedEntity.getContract().getMeta().getHref());
    }

    private void putAsserts(CommissionReportInDocumentEntity commissionReportIn, CommissionReportInDocumentEntity retrievedOriginalEntity, String name) throws IOException, LognexApiException {
        CommissionReportInDocumentEntity retrievedUpdatedEntity = api.entity().commissionreportin().get(commissionReportIn.getId());

        assertNotEquals(retrievedOriginalEntity.getName(), retrievedUpdatedEntity.getName());
        assertEquals(name, retrievedUpdatedEntity.getName());
        assertEquals(retrievedOriginalEntity.getOrganization().getMeta().getHref(), retrievedUpdatedEntity.getOrganization().getMeta().getHref());
        assertEquals(retrievedOriginalEntity.getAgent().getMeta().getHref(), retrievedUpdatedEntity.getAgent().getMeta().getHref());
        assertEquals(retrievedOriginalEntity.getContract().getMeta().getHref(), retrievedUpdatedEntity.getContract().getMeta().getHref());
    }

    @Override
    protected ApiClient entityClient() {
        return api.entity().commissionreportin();
    }

    @Override
    protected Class<? extends MetaEntity> entityClass() {
        return CommissionReportInDocumentEntity.class;
    }
}
