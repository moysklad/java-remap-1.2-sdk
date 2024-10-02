package ru.moysklad.remap_1_2.entities.documents;

import ru.moysklad.remap_1_2.clients.EntityClientBase;
import ru.moysklad.remap_1_2.entities.*;
import ru.moysklad.remap_1_2.entities.agents.Counterparty;
import ru.moysklad.remap_1_2.entities.agents.Organization;
import ru.moysklad.remap_1_2.entities.documents.positions.CommissionReportDocumentPosition;
import ru.moysklad.remap_1_2.responses.ListEntity;
import ru.moysklad.remap_1_2.responses.metadata.MetadataAttributeSharedStatesResponse;
import ru.moysklad.remap_1_2.utils.ApiClientException;
import org.junit.Test;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Date;

import static ru.moysklad.remap_1_2.utils.params.ExpandParam.expand;
import static ru.moysklad.remap_1_2.utils.params.FilterParam.filterEq;
import static ru.moysklad.remap_1_2.utils.params.LimitParam.limit;
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
        MetadataAttributeSharedStatesResponse<DocumentAttribute> response = api.entity().commissionreportin().metadata().get();

        assertFalse(response.getCreateShared());
    }

    @Test
    public void attributesTest() throws IOException, ApiClientException{
        ListEntity<Attribute> attributes = api.entity().commissionreportin().metadataAttributes();
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
        DocumentAttribute created = api.entity().commissionreportin().createMetadataAttribute(attribute);
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
        DocumentAttribute created = api.entity().commissionreportin().createMetadataAttribute(attribute);

        String name = "field" + randomString(3) + "_" + new Date().getTime();
        created.setName(name);
        created.setRequired(false);
        created.setShow(false);
        DocumentAttribute updated = api.entity().commissionreportin().updateMetadataAttribute(created);
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
        DocumentAttribute created = api.entity().commissionreportin().createMetadataAttribute(attribute);

        api.entity().commissionreportin().deleteMetadataAttribute(created);

        try {
            api.entity().commissionreportin().metadataAttributes(created.getId());
        } catch (ApiClientException e) {
            assertEquals(404, e.getStatusCode());
        }
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
    public EntityClientBase entityClient() {
        return api.entity().commissionreportin();
    }

    @Override
    public Class<? extends MetaEntity> entityClass() {
        return CommissionReportIn.class;
    }
}
