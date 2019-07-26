package com.lognex.api.entities.documents;

import com.lognex.api.clients.EntityClientBase;
import com.lognex.api.entities.Contract;
import com.lognex.api.entities.MetaEntity;
import com.lognex.api.entities.agents.Counterparty;
import com.lognex.api.entities.agents.Organization;
import com.lognex.api.responses.ListEntity;
import com.lognex.api.responses.metadata.MetadataAttributeSharedStatesResponse;
import com.lognex.api.utils.ApiClientException;
import org.junit.Test;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Date;

import static com.lognex.api.utils.params.FilterParam.filterEq;
import static org.junit.Assert.*;

public class CommissionReportOutTest extends DocumentWithPositionsTestBase {
    @Test
    public void createTest() throws IOException, ApiClientException {
        CommissionReportOut commissionReportOut = new CommissionReportOut();
        commissionReportOut.setName("commissionreportout_" + randomString(3) + "_" + new Date().getTime());
        commissionReportOut.setDescription(randomString());
        commissionReportOut.setVatEnabled(true);
        commissionReportOut.setVatIncluded(true);
        commissionReportOut.setMoment(LocalDateTime.now());
        Counterparty agent = simpleEntityManager.createSimple(Counterparty.class);
        commissionReportOut.setAgent(agent);
        Organization organization = simpleEntityManager.getOwnOrganization();
        commissionReportOut.setOrganization(organization);

        Contract contract = new Contract();
        contract.setName(randomString());
        contract.setOwnAgent(organization);
        contract.setAgent(agent);
        contract.setContractType(Contract.Type.commission);
        api.entity().contract().create(contract);
        commissionReportOut.setContract(contract);

        commissionReportOut.setCommissionPeriodStart(LocalDateTime.now());
        commissionReportOut.setCommissionPeriodEnd(LocalDateTime.now().plusNanos(50));

        api.entity().commissionreportout().create(commissionReportOut);

        ListEntity<CommissionReportOut> updatedEntitiesList = api.entity().commissionreportout().get(filterEq("name", commissionReportOut.getName()));
        assertEquals(1, updatedEntitiesList.getRows().size());

        CommissionReportOut retrievedEntity = updatedEntitiesList.getRows().get(0);
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
    public void metadataTest() throws IOException, ApiClientException {
        MetadataAttributeSharedStatesResponse response = api.entity().commissionreportout().metadata().get();

        assertFalse(response.getCreateShared());
    }

    @Override
    protected void getAsserts(MetaEntity originalEntity, MetaEntity retrievedEntity) {
        CommissionReportOut originalCommissionReportOut = (CommissionReportOut) originalEntity;
        CommissionReportOut retrievedCommissionReportOut = (CommissionReportOut) retrievedEntity;

        assertEquals(originalCommissionReportOut.getName(), retrievedCommissionReportOut.getName());
        assertEquals(originalCommissionReportOut.getOrganization().getMeta().getHref(), retrievedCommissionReportOut.getOrganization().getMeta().getHref());
        assertEquals(originalCommissionReportOut.getAgent().getMeta().getHref(), retrievedCommissionReportOut.getAgent().getMeta().getHref());
        assertEquals(originalCommissionReportOut.getContract().getMeta().getHref(), retrievedCommissionReportOut.getContract().getMeta().getHref());
    }

    @Override
    protected void putAsserts(MetaEntity originalEntity, MetaEntity updatedEntity, Object changedField) {
        CommissionReportOut originalCommissionReportOut = (CommissionReportOut) originalEntity;
        CommissionReportOut updatedCommissionReportOut = (CommissionReportOut) updatedEntity;

        assertNotEquals(originalCommissionReportOut.getName(), updatedCommissionReportOut.getName());
        assertEquals(changedField, updatedCommissionReportOut.getName());
        assertEquals(originalCommissionReportOut.getOrganization().getMeta().getHref(), updatedCommissionReportOut.getOrganization().getMeta().getHref());
        assertEquals(originalCommissionReportOut.getAgent().getMeta().getHref(), updatedCommissionReportOut.getAgent().getMeta().getHref());
        assertEquals(originalCommissionReportOut.getContract().getMeta().getHref(), updatedCommissionReportOut.getContract().getMeta().getHref());
    }

    @Override
    protected EntityClientBase entityClient() {
        return api.entity().commissionreportout();
    }

    @Override
    protected Class<? extends MetaEntity> entityClass() {
        return CommissionReportOut.class;
    }
}
