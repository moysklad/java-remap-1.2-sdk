package ru.moysklad.remap_1_2.entities.documents;

import ru.moysklad.remap_1_2.clients.EntityClientBase;
import ru.moysklad.remap_1_2.entities.*;
import ru.moysklad.remap_1_2.entities.agents.Counterparty;
import ru.moysklad.remap_1_2.entities.agents.Organization;
import ru.moysklad.remap_1_2.responses.ListEntity;
import ru.moysklad.remap_1_2.responses.metadata.MetadataAttributeSharedStatesResponse;
import ru.moysklad.remap_1_2.utils.ApiClientException;
import org.junit.Test;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Date;

import static ru.moysklad.remap_1_2.utils.params.FilterParam.filterEq;
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
        MetadataAttributeSharedStatesResponse<DocumentAttribute> response = api.entity().commissionreportout().metadata().get();

        assertFalse(response.getCreateShared());
    }

    @Test
    public void attributesTest() throws IOException, ApiClientException{
        ListEntity<Attribute> attributes = api.entity().commissionreportout().metadataAttributes();
        assertNotNull(attributes);
    }

    @Test
    public void createAttributeTest() throws IOException, ApiClientException {
        DocumentAttribute attribute = new DocumentAttribute();
        attribute.setType(DocumentAttribute.Type.textValue);
        String name = "field" + randomString(3) + "_" + new Date().getTime();
        attribute.setName(name);
        attribute.setRequired(false);
        attribute.setShow(true);
        attribute.setDescription("description");
        DocumentAttribute created = api.entity().commissionreportout().createMetadataAttribute(attribute);
        assertNotNull(created);
        assertEquals(name, created.getName());
        assertEquals(DocumentAttribute.Type.textValue, created.getType());
        assertFalse(created.getRequired());
        assertTrue(created.getShow());
        assertEquals("description", created.getDescription());
    }

    @Test
    public void updateAttributeTest() throws IOException, ApiClientException {
        DocumentAttribute attribute = new DocumentAttribute();
        attribute.setEntityType(Meta.Type.PRODUCT);
        attribute.setName("field" + randomString(3) + "_" + new Date().getTime());
        attribute.setRequired(true);
        attribute.setShow(true);
        DocumentAttribute created = api.entity().commissionreportout().createMetadataAttribute(attribute);

        String name = "field" + randomString(3) + "_" + new Date().getTime();
        created.setName(name);
        created.setRequired(false);
        created.setShow(false);
        DocumentAttribute updated = api.entity().commissionreportout().updateMetadataAttribute(created);
        assertNotNull(created);
        assertEquals(name, updated.getName());
        assertNull(updated.getType());
        assertEquals(Meta.Type.PRODUCT, updated.getEntityType());
        assertFalse(updated.getRequired());
        assertFalse(updated.getShow());
    }

    @Test
    public void deleteAttributeTest() throws IOException, ApiClientException{
        DocumentAttribute attribute = new DocumentAttribute();
        attribute.setEntityType(Meta.Type.PRODUCT);
        attribute.setName("field" + randomString(3) + "_" + new Date().getTime());
        attribute.setRequired(true);
        attribute.setShow(true);
        DocumentAttribute created = api.entity().commissionreportout().createMetadataAttribute(attribute);

        api.entity().commissionreportout().deleteMetadataAttribute(created);

        try {
            api.entity().commissionreportout().metadataAttributes(created.getId());
        } catch (ApiClientException e) {
            assertEquals(404, e.getStatusCode());
        }
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
    public EntityClientBase entityClient() {
        return api.entity().commissionreportout();
    }

    @Override
    public Class<? extends MetaEntity> entityClass() {
        return CommissionReportOut.class;
    }
}
