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

public class CommissionReportOutDocumentEntityTest extends DocumentWithPositionsTestBase {
    @Test
    public void createTest() throws IOException, LognexApiException {
        CommissionReportOutDocumentEntity commissionReportOut = new CommissionReportOutDocumentEntity();
        commissionReportOut.setName("commissionreportout_" + randomString(3) + "_" + new Date().getTime());
        commissionReportOut.setDescription(randomString());
        commissionReportOut.setVatEnabled(true);
        commissionReportOut.setVatIncluded(true);
        commissionReportOut.setMoment(LocalDateTime.now());
        CounterpartyEntity agent = simpleEntityFactory.createSimpleCounterparty();
        commissionReportOut.setAgent(agent);
        OrganizationEntity organization = simpleEntityFactory.getOwnOrganization();
        commissionReportOut.setOrganization(organization);

        ContractEntity contract = new ContractEntity();
        contract.setName(randomString());
        contract.setOwnAgent(organization);
        contract.setAgent(agent);
        contract.setContractType(ContractEntity.Type.commission);
        api.entity().contract().post(contract);
        commissionReportOut.setContract(contract);

        commissionReportOut.setCommissionPeriodStart(LocalDateTime.now());
        commissionReportOut.setCommissionPeriodEnd(LocalDateTime.now().plusNanos(50));

        api.entity().commissionreportout().post(commissionReportOut);

        ListEntity<CommissionReportOutDocumentEntity> updatedEntitiesList = api.entity().commissionreportout().get(filterEq("name", commissionReportOut.getName()));
        assertEquals(1, updatedEntitiesList.getRows().size());

        CommissionReportOutDocumentEntity retrievedEntity = updatedEntitiesList.getRows().get(0);
        assertEquals(commissionReportOut.getName(), retrievedEntity.getName());
        assertEquals(commissionReportOut.getDescription(), retrievedEntity.getDescription());
        assertEquals(commissionReportOut.getVatEnabled(), retrievedEntity.getVatEnabled());
        assertEquals(commissionReportOut.getVatIncluded(), retrievedEntity.getVatIncluded());
        assertEquals(commissionReportOut.getMoment(), retrievedEntity.getMoment());
        assertEquals(commissionReportOut.getOrganization().getMeta().getHref(), retrievedEntity.getOrganization().getMeta().getHref());
        assertEquals(commissionReportOut.getAgent().getMeta().getHref(), retrievedEntity.getAgent().getMeta().getHref());
        assertEquals(commissionReportOut.getContract().getMeta().getHref(), retrievedEntity.getContract().getMeta().getHref());
    }

    @Test
    public void getTest() throws IOException, LognexApiException {
        CommissionReportOutDocumentEntity commissionReportOut = simpleEntityFactory.createSimpleCommissionReportOut();

        CommissionReportOutDocumentEntity retrievedEntity = api.entity().commissionreportout().get(commissionReportOut.getId());
        getAsserts(commissionReportOut, retrievedEntity);

        retrievedEntity = api.entity().commissionreportout().get(commissionReportOut);
        getAsserts(commissionReportOut, retrievedEntity);
    }

    @Test
    public void putTest() throws IOException, LognexApiException {
        CommissionReportOutDocumentEntity commissionReportOut = simpleEntityFactory.createSimpleCommissionReportOut();

        CommissionReportOutDocumentEntity retrievedOriginalEntity = api.entity().commissionreportout().get(commissionReportOut.getId());
        String name = "commissionreportout_" + randomString(3) + "_" + new Date().getTime();
        commissionReportOut.setName(name);
        api.entity().commissionreportout().put(commissionReportOut.getId(), commissionReportOut);
        putAsserts(commissionReportOut, retrievedOriginalEntity, name);

        retrievedOriginalEntity.set(commissionReportOut);

        name = "commissionreportout_" + randomString(3) + "_" + new Date().getTime();
        commissionReportOut.setName(name);
        api.entity().commissionreportout().put(commissionReportOut);
        putAsserts(commissionReportOut, retrievedOriginalEntity, name);
    }

    @Test
    public void deleteTest() throws IOException, LognexApiException {
        CommissionReportOutDocumentEntity commissionReportOut = simpleEntityFactory.createSimpleCommissionReportOut();

        ListEntity<CommissionReportOutDocumentEntity> entitiesList = api.entity().commissionreportout().get(filterEq("name", commissionReportOut.getName()));
        assertEquals((Integer) 1, entitiesList.getMeta().getSize());

        api.entity().commissionreportout().delete(commissionReportOut.getId());

        entitiesList = api.entity().commissionreportout().get(filterEq("name", commissionReportOut.getName()));
        assertEquals((Integer) 0, entitiesList.getMeta().getSize());
    }

    @Test
    public void deleteByIdTest() throws IOException, LognexApiException {
        CommissionReportOutDocumentEntity commissionReportOut = simpleEntityFactory.createSimpleCommissionReportOut();

        ListEntity<CommissionReportOutDocumentEntity> entitiesList = api.entity().commissionreportout().get(filterEq("name", commissionReportOut.getName()));
        assertEquals((Integer) 1, entitiesList.getMeta().getSize());

        api.entity().commissionreportout().delete(commissionReportOut);

        entitiesList = api.entity().commissionreportout().get(filterEq("name", commissionReportOut.getName()));
        assertEquals((Integer) 0, entitiesList.getMeta().getSize());
    }

    @Test
    public void metadataTest() throws IOException, LognexApiException {
        MetadataAttributeSharedStatesResponse response = api.entity().commissionreportout().metadata().get();

        assertFalse(response.getCreateShared());
    }

    private void getAsserts(CommissionReportOutDocumentEntity commissionReportOut, CommissionReportOutDocumentEntity retrievedEntity) {
        assertEquals(commissionReportOut.getName(), retrievedEntity.getName());
        assertEquals(commissionReportOut.getOrganization().getMeta().getHref(), retrievedEntity.getOrganization().getMeta().getHref());
        assertEquals(commissionReportOut.getAgent().getMeta().getHref(), retrievedEntity.getAgent().getMeta().getHref());
        assertEquals(commissionReportOut.getContract().getMeta().getHref(), retrievedEntity.getContract().getMeta().getHref());
    }

    private void putAsserts(CommissionReportOutDocumentEntity commissionReportOut, CommissionReportOutDocumentEntity retrievedOriginalEntity, String name) throws IOException, LognexApiException {
        CommissionReportOutDocumentEntity retrievedUpdatedEntity = api.entity().commissionreportout().get(commissionReportOut.getId());

        assertNotEquals(retrievedOriginalEntity.getName(), retrievedUpdatedEntity.getName());
        assertEquals(name, retrievedUpdatedEntity.getName());
        assertEquals(retrievedOriginalEntity.getOrganization().getMeta().getHref(), retrievedUpdatedEntity.getOrganization().getMeta().getHref());
        assertEquals(retrievedOriginalEntity.getAgent().getMeta().getHref(), retrievedUpdatedEntity.getAgent().getMeta().getHref());
        assertEquals(retrievedOriginalEntity.getContract().getMeta().getHref(), retrievedUpdatedEntity.getContract().getMeta().getHref());
    }

    @Override
    protected ApiClient entityClient() {
        return api.entity().commissionreportout();
    }

    @Override
    protected Class<? extends MetaEntity> entityClass() {
        return CommissionReportOutDocumentEntity.class;
    }
}
