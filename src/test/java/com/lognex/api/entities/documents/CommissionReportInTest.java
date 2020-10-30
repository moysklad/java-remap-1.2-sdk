package com.lognex.api.entities.documents;

import com.lognex.api.clients.EntityClientBase;
import com.lognex.api.clients.documents.DocumentMetadataClient;
import com.lognex.api.entities.Attribute;
import com.lognex.api.entities.Contract;
import com.lognex.api.entities.MetaEntity;
import com.lognex.api.entities.agents.Counterparty;
import com.lognex.api.entities.agents.Organization;
import com.lognex.api.entities.documents.positions.CommissionReportDocumentPosition;
import com.lognex.api.responses.ListEntity;
import com.lognex.api.responses.metadata.MetadataAttributeSharedStatesResponse;
import com.lognex.api.utils.ApiClientException;
import org.junit.Test;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Date;

import static com.lognex.api.utils.params.ExpandParam.expand;
import static com.lognex.api.utils.params.FilterParam.filterEq;
import static com.lognex.api.utils.params.LimitParam.limit;
import static org.junit.Assert.*;

public class CommissionReportInTest extends DocumentWithPositionsTestBase {
    @Test
    public void createTest() throws IOException, ApiClientException {
        CommissionReportIn commissionReportIn = new CommissionReportIn();
        commissionReportIn.setName("commissionreportin_" + randomString(3) + "_" + new Date().getTime());
        commissionReportIn.setDescription(randomString());
        commissionReportIn.setVatEnabled(true);
        commissionReportIn.setVatIncluded(true);
        commissionReportIn.setMoment(LocalDateTime.now());
        Counterparty agent = simpleEntityManager.createSimple(Counterparty.class);
        commissionReportIn.setAgent(agent);
        Organization organization = simpleEntityManager.getOwnOrganization();
        commissionReportIn.setOrganization(organization);

        CommissionReportDocumentPosition position = new CommissionReportDocumentPosition();
        position.setAssortment(simpleEntityManager.createSimpleProduct());
        position.setQuantity(1.);
        position.setReward(17.);
        commissionReportIn.setPositions(new ListEntity<>(position));

        Contract contract = new Contract();
        contract.setName(randomString());
        contract.setOwnAgent(organization);
        contract.setAgent(agent);
        contract.setContractType(Contract.Type.commission);
        api.entity().contract().create(contract);
        commissionReportIn.setContract(contract);

        commissionReportIn.setCommissionPeriodStart(LocalDateTime.now());
        commissionReportIn.setCommissionPeriodEnd(LocalDateTime.now().plusNanos(50));

        api.entity().commissionreportin().create(commissionReportIn);

        ListEntity<CommissionReportIn> updatedEntitiesList = api.entity().commissionreportin().get(
                filterEq("name", commissionReportIn.getName()),
                expand("positions"),
                limit(5)
        );
        assertEquals(1, updatedEntitiesList.getRows().size());

        CommissionReportIn retrievedEntity = updatedEntitiesList.getRows().get(0);
        assertEquals(commissionReportIn.getName(), retrievedEntity.getName());
        assertEquals(commissionReportIn.getDescription(), retrievedEntity.getDescription());
        assertEquals(commissionReportIn.getVatEnabled(), retrievedEntity.getVatEnabled());
        assertEquals(commissionReportIn.getVatIncluded(), retrievedEntity.getVatIncluded());
        assertEquals(commissionReportIn.getMoment(), retrievedEntity.getMoment());
        assertEquals(commissionReportIn.getOrganization().getMeta().getHref(), retrievedEntity.getOrganization().getMeta().getHref());
        assertEquals(commissionReportIn.getAgent().getMeta().getHref(), retrievedEntity.getAgent().getMeta().getHref());
        assertEquals(commissionReportIn.getContract().getMeta().getHref(), retrievedEntity.getContract().getMeta().getHref());
        assertEquals(1, retrievedEntity.getPositions().getRows().size());
        assertEquals(position.getReward(), retrievedEntity.getPositions().getRows().get(0).getReward());
    }

    @Test
    public void metadataTest() throws IOException, ApiClientException {
        DocumentMetadataClient<MetadataAttributeSharedStatesResponse> metadata = api.entity().commissionreportin().metadata();
        MetadataAttributeSharedStatesResponse response = metadata.get();

        assertFalse(response.getCreateShared());

        ListEntity<Attribute> attributes = metadata.attributes();
        assertNotNull(attributes);
    }

    @Override
    protected void getAsserts(MetaEntity originalEntity, MetaEntity retrievedEntity) {
        CommissionReportIn originalCommissionReportIn = (CommissionReportIn) originalEntity;
        CommissionReportIn retrievedCommissionReportIn = (CommissionReportIn) retrievedEntity;

        assertEquals(originalCommissionReportIn.getName(), retrievedCommissionReportIn.getName());
        assertEquals(originalCommissionReportIn.getOrganization().getMeta().getHref(), retrievedCommissionReportIn.getOrganization().getMeta().getHref());
        assertEquals(originalCommissionReportIn.getAgent().getMeta().getHref(), retrievedCommissionReportIn.getAgent().getMeta().getHref());
        assertEquals(originalCommissionReportIn.getContract().getMeta().getHref(), retrievedCommissionReportIn.getContract().getMeta().getHref());
    }

    @Override
    protected void putAsserts(MetaEntity originalEntity, MetaEntity updatedEntity, Object changedField) {
        CommissionReportIn originalCommissionReportIn = (CommissionReportIn) originalEntity;
        CommissionReportIn updatedCommissionReportIn = (CommissionReportIn) updatedEntity;

        assertNotEquals(originalCommissionReportIn.getName(), updatedCommissionReportIn.getName());
        assertEquals(changedField, updatedCommissionReportIn.getName());
        assertEquals(originalCommissionReportIn.getOrganization().getMeta().getHref(), updatedCommissionReportIn.getOrganization().getMeta().getHref());
        assertEquals(originalCommissionReportIn.getAgent().getMeta().getHref(), updatedCommissionReportIn.getAgent().getMeta().getHref());
        assertEquals(originalCommissionReportIn.getContract().getMeta().getHref(), updatedCommissionReportIn.getContract().getMeta().getHref());
    }

    @Override
    protected EntityClientBase entityClient() {
        return api.entity().commissionreportin();
    }

    @Override
    protected Class<? extends MetaEntity> entityClass() {
        return CommissionReportIn.class;
    }
}
