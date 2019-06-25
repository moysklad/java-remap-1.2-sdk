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
    public void metadataTest() throws IOException, LognexApiException {
        MetadataAttributeSharedStatesResponse response = api.entity().commissionreportout().metadata().get();

        assertFalse(response.getCreateShared());
    }

    @Override
    protected void getAsserts(MetaEntity originalEntity, MetaEntity retrievedEntity) {
        CommissionReportOutDocumentEntity originalCommissionReportOut = (CommissionReportOutDocumentEntity) originalEntity;
        CommissionReportOutDocumentEntity retrievedCommissionReportOut = (CommissionReportOutDocumentEntity) retrievedEntity;

        assertEquals(originalCommissionReportOut.getName(), retrievedCommissionReportOut.getName());
        assertEquals(originalCommissionReportOut.getOrganization().getMeta().getHref(), retrievedCommissionReportOut.getOrganization().getMeta().getHref());
        assertEquals(originalCommissionReportOut.getAgent().getMeta().getHref(), retrievedCommissionReportOut.getAgent().getMeta().getHref());
        assertEquals(originalCommissionReportOut.getContract().getMeta().getHref(), retrievedCommissionReportOut.getContract().getMeta().getHref());
    }

    @Override
    protected void putAsserts(MetaEntity originalEntity, MetaEntity updatedEntity, Object changedField) {
        CommissionReportOutDocumentEntity originalCommissionReportOut = (CommissionReportOutDocumentEntity) originalEntity;
        CommissionReportOutDocumentEntity updatedCommissionReportOut = (CommissionReportOutDocumentEntity) updatedEntity;

        assertNotEquals(originalCommissionReportOut.getName(), updatedCommissionReportOut.getName());
        assertEquals(changedField, updatedCommissionReportOut.getName());
        assertEquals(originalCommissionReportOut.getOrganization().getMeta().getHref(), updatedCommissionReportOut.getOrganization().getMeta().getHref());
        assertEquals(originalCommissionReportOut.getAgent().getMeta().getHref(), updatedCommissionReportOut.getAgent().getMeta().getHref());
        assertEquals(originalCommissionReportOut.getContract().getMeta().getHref(), updatedCommissionReportOut.getContract().getMeta().getHref());
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
