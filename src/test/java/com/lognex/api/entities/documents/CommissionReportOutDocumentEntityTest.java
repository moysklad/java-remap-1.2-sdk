package com.lognex.api.entities.documents;

import com.lognex.api.entities.ContractEntity;
import com.lognex.api.entities.EntityTestBase;
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

public class CommissionReportOutDocumentEntityTest extends EntityTestBase {
    @Test
    public void createTest() throws IOException, LognexApiException {
        CommissionReportOutDocumentEntity e = new CommissionReportOutDocumentEntity();
        e.setName("commissionreportout_" + randomString(3) + "_" + new Date().getTime());
        e.setDescription(randomString());
        e.setVatEnabled(true);
        e.setVatIncluded(true);
        e.setMoment(LocalDateTime.now());

        ListEntity<OrganizationEntity> orgList = api.entity().organization().get();
        assertNotEquals(0, orgList.getRows().size());
        e.setOrganization(orgList.getRows().get(0));

        CounterpartyEntity agent = new CounterpartyEntity();
        agent.setName(randomString());
        api.entity().counterparty().post(agent);
        e.setAgent(agent);

        ContractEntity contract = new ContractEntity();
        contract.setName(randomString());
        contract.setOwnAgent(orgList.getRows().get(0));
        contract.setAgent(agent);
        contract.setContractType(ContractEntity.Type.commission);
        api.entity().contract().post(contract);
        e.setContract(contract);

        e.setCommissionPeriodStart(LocalDateTime.now());
        e.setCommissionPeriodEnd(LocalDateTime.now().plusNanos(50));

        api.entity().commissionreportout().post(e);

        ListEntity<CommissionReportOutDocumentEntity> updatedEntitiesList = api.entity().commissionreportout().get(filterEq("name", e.getName()));
        assertEquals(1, updatedEntitiesList.getRows().size());

        CommissionReportOutDocumentEntity retrievedEntity = updatedEntitiesList.getRows().get(0);
        assertEquals(e.getName(), retrievedEntity.getName());
        assertEquals(e.getDescription(), retrievedEntity.getDescription());
        assertEquals(e.getVatEnabled(), retrievedEntity.getVatEnabled());
        assertEquals(e.getVatIncluded(), retrievedEntity.getVatIncluded());
        assertEquals(e.getMoment(), retrievedEntity.getMoment());
        assertEquals(e.getOrganization().getMeta().getHref(), retrievedEntity.getOrganization().getMeta().getHref());
        assertEquals(e.getAgent().getMeta().getHref(), retrievedEntity.getAgent().getMeta().getHref());
        assertEquals(e.getContract().getMeta().getHref(), retrievedEntity.getContract().getMeta().getHref());
        assertEquals(e.getCreated().withNano(0), retrievedEntity.getCreated().withNano(0));
        assertEquals(e.getUpdated().withNano(0), retrievedEntity.getUpdated().withNano(0));
    }

    @Test
    public void getTest() throws IOException, LognexApiException {
        CommissionReportOutDocumentEntity e = createSimpleDocumentCommissionReportOut();

        CommissionReportOutDocumentEntity retrievedEntity = api.entity().commissionreportout().get(e.getId());
        getAsserts(e, retrievedEntity);

        retrievedEntity = api.entity().commissionreportout().get(e);
        getAsserts(e, retrievedEntity);
    }

    @Test
    public void putTest() throws IOException, LognexApiException, InterruptedException {
        CommissionReportOutDocumentEntity e = createSimpleDocumentCommissionReportOut();

        CommissionReportOutDocumentEntity retrievedOriginalEntity = api.entity().commissionreportout().get(e.getId());
        String name = "commissionreportout_" + randomString(3) + "_" + new Date().getTime();
        e.setName(name);
        Thread.sleep(1500);
        api.entity().commissionreportout().put(e.getId(), e);
        putAsserts(e, retrievedOriginalEntity, name);

        retrievedOriginalEntity.set(e);

        name = "commissionreportout_" + randomString(3) + "_" + new Date().getTime();
        e.setName(name);
        Thread.sleep(1500);
        api.entity().commissionreportout().put(e);
        putAsserts(e, retrievedOriginalEntity, name);
    }

    @Test
    public void deleteTest() throws IOException, LognexApiException {
        CommissionReportOutDocumentEntity e = createSimpleDocumentCommissionReportOut();

        ListEntity<CommissionReportOutDocumentEntity> entitiesList = api.entity().commissionreportout().get(filterEq("name", e.getName()));
        assertEquals(1, entitiesList.getRows().size());

        api.entity().commissionreportout().delete(e.getId());

        entitiesList = api.entity().commissionreportout().get(filterEq("name", e.getName()));
        assertEquals(0, entitiesList.getRows().size());
    }

    @Test
    public void deleteByIdTest() throws IOException, LognexApiException {
        CommissionReportOutDocumentEntity e = createSimpleDocumentCommissionReportOut();

        ListEntity<CommissionReportOutDocumentEntity> entitiesList = api.entity().commissionreportout().get(filterEq("name", e.getName()));
        assertEquals(1, entitiesList.getRows().size());

        api.entity().commissionreportout().delete(e);

        entitiesList = api.entity().commissionreportout().get(filterEq("name", e.getName()));
        assertEquals(0, entitiesList.getRows().size());
    }

    @Test
    public void metadataTest() throws IOException, LognexApiException {
        MetadataAttributeSharedStatesResponse response = api.entity().commissionreportout().metadata().get();

        assertFalse(response.getCreateShared());
    }

    private CommissionReportOutDocumentEntity createSimpleDocumentCommissionReportOut() throws IOException, LognexApiException {
        CommissionReportOutDocumentEntity e = new CommissionReportOutDocumentEntity();
        e.setName("commissionreportout_" + randomString(3) + "_" + new Date().getTime());

        ListEntity<OrganizationEntity> orgList = api.entity().organization().get();
        assertNotEquals(0, orgList.getRows().size());
        e.setOrganization(orgList.getRows().get(0));

        CounterpartyEntity agent = new CounterpartyEntity();
        agent.setName(randomString());
        api.entity().counterparty().post(agent);
        e.setAgent(agent);

        ContractEntity contract = new ContractEntity();
        contract.setName(randomString());
        contract.setOwnAgent(orgList.getRows().get(0));
        contract.setAgent(agent);
        contract.setContractType(ContractEntity.Type.commission);
        api.entity().contract().post(contract);
        e.setContract(contract);

        e.setCommissionPeriodStart(LocalDateTime.now());
        e.setCommissionPeriodEnd(LocalDateTime.now().plusNanos(50));

        api.entity().commissionreportout().post(e);

        return e;
    }

    private void getAsserts(CommissionReportOutDocumentEntity e, CommissionReportOutDocumentEntity retrievedEntity) {
        assertEquals(e.getName(), retrievedEntity.getName());
        assertEquals(e.getOrganization().getMeta().getHref(), retrievedEntity.getOrganization().getMeta().getHref());
        assertEquals(e.getAgent().getMeta().getHref(), retrievedEntity.getAgent().getMeta().getHref());
        assertEquals(e.getContract().getMeta().getHref(), retrievedEntity.getContract().getMeta().getHref());
        assertEquals(e.getCreated().withNano(0), retrievedEntity.getCreated().withNano(0));
        assertEquals(e.getUpdated().withNano(0), retrievedEntity.getUpdated().withNano(0));
    }

    private void putAsserts(CommissionReportOutDocumentEntity e, CommissionReportOutDocumentEntity retrievedOriginalEntity, String name) throws IOException, LognexApiException {
        CommissionReportOutDocumentEntity retrievedUpdatedEntity = api.entity().commissionreportout().get(e.getId());

        assertNotEquals(retrievedOriginalEntity.getName(), retrievedUpdatedEntity.getName());
        assertEquals(name, retrievedUpdatedEntity.getName());
        assertEquals(retrievedOriginalEntity.getOrganization().getMeta().getHref(), retrievedUpdatedEntity.getOrganization().getMeta().getHref());
        assertEquals(retrievedOriginalEntity.getAgent().getMeta().getHref(), retrievedUpdatedEntity.getAgent().getMeta().getHref());
        assertEquals(retrievedOriginalEntity.getContract().getMeta().getHref(), retrievedUpdatedEntity.getContract().getMeta().getHref());
        assertEquals(retrievedOriginalEntity.getCreated().withNano(0), retrievedUpdatedEntity.getCreated().withNano(0));
        assertNotEquals(retrievedOriginalEntity.getUpdated().withNano(0), retrievedUpdatedEntity.getUpdated().withNano(0));
    }
}
