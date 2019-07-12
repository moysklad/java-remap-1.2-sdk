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
        CounterpartyEntity agent = simpleEntityManager.createSimpleCounterparty();
        commissionReportIn.setAgent(agent);
        OrganizationEntity organization = simpleEntityManager.getOwnOrganization();
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
    public void metadataTest() throws IOException, LognexApiException {
        MetadataAttributeSharedStatesResponse response = api.entity().commissionreportin().metadata().get();

        assertFalse(response.getCreateShared());
    }

    @Override
    protected void getAsserts(MetaEntity originalEntity, MetaEntity retrievedEntity) {
        CommissionReportInDocumentEntity originalCommissionReportIn = (CommissionReportInDocumentEntity) originalEntity;
        CommissionReportInDocumentEntity retrievedCommissionReportIn = (CommissionReportInDocumentEntity) retrievedEntity;

        assertEquals(originalCommissionReportIn.getName(), retrievedCommissionReportIn.getName());
        assertEquals(originalCommissionReportIn.getOrganization().getMeta().getHref(), retrievedCommissionReportIn.getOrganization().getMeta().getHref());
        assertEquals(originalCommissionReportIn.getAgent().getMeta().getHref(), retrievedCommissionReportIn.getAgent().getMeta().getHref());
        assertEquals(originalCommissionReportIn.getContract().getMeta().getHref(), retrievedCommissionReportIn.getContract().getMeta().getHref());
    }

    @Override
    protected void putAsserts(MetaEntity originalEntity, MetaEntity updatedEntity, Object changedField) {
        CommissionReportInDocumentEntity originalCommissionReportIn = (CommissionReportInDocumentEntity) originalEntity;
        CommissionReportInDocumentEntity updatedCommissionReportIn = (CommissionReportInDocumentEntity) updatedEntity;

        assertNotEquals(originalCommissionReportIn.getName(), updatedCommissionReportIn.getName());
        assertEquals(changedField, updatedCommissionReportIn.getName());
        assertEquals(originalCommissionReportIn.getOrganization().getMeta().getHref(), updatedCommissionReportIn.getOrganization().getMeta().getHref());
        assertEquals(originalCommissionReportIn.getAgent().getMeta().getHref(), updatedCommissionReportIn.getAgent().getMeta().getHref());
        assertEquals(originalCommissionReportIn.getContract().getMeta().getHref(), updatedCommissionReportIn.getContract().getMeta().getHref());
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
