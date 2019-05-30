package com.lognex.api.entities.documents;

import com.lognex.api.entities.ContractEntity;
import com.lognex.api.entities.EntityTestBase;
import com.lognex.api.entities.agents.CounterpartyEntity;
import com.lognex.api.entities.agents.OrganizationEntity;
import com.lognex.api.entities.products.ProductEntity;
import com.lognex.api.responses.ListEntity;
import com.lognex.api.responses.metadata.MetadataAttributeSharedStatesResponse;
import com.lognex.api.utils.LognexApiException;
import org.junit.Test;

import java.io.IOException;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
        api.entity().commissionreportout().put(e.getId(), e);
        putAsserts(e, retrievedOriginalEntity, name);

        retrievedOriginalEntity.set(e);

        name = "commissionreportout_" + randomString(3) + "_" + new Date().getTime();
        e.setName(name);
        api.entity().commissionreportout().put(e);
        putAsserts(e, retrievedOriginalEntity, name);
    }

    @Test
    public void deleteTest() throws IOException, LognexApiException {
        CommissionReportOutDocumentEntity e = createSimpleDocumentCommissionReportOut();

        ListEntity<CommissionReportOutDocumentEntity> entitiesList = api.entity().commissionreportout().get(filterEq("name", e.getName()));
        assertEquals((Integer) 1, entitiesList.getMeta().getSize());

        api.entity().commissionreportout().delete(e.getId());

        entitiesList = api.entity().commissionreportout().get(filterEq("name", e.getName()));
        assertEquals((Integer) 0, entitiesList.getMeta().getSize());
    }

    @Test
    public void deleteByIdTest() throws IOException, LognexApiException {
        CommissionReportOutDocumentEntity e = createSimpleDocumentCommissionReportOut();

        ListEntity<CommissionReportOutDocumentEntity> entitiesList = api.entity().commissionreportout().get(filterEq("name", e.getName()));
        assertEquals((Integer) 1, entitiesList.getMeta().getSize());

        api.entity().commissionreportout().delete(e);

        entitiesList = api.entity().commissionreportout().get(filterEq("name", e.getName()));
        assertEquals((Integer) 0, entitiesList.getMeta().getSize());
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
    }

    private void putAsserts(CommissionReportOutDocumentEntity e, CommissionReportOutDocumentEntity retrievedOriginalEntity, String name) throws IOException, LognexApiException {
        CommissionReportOutDocumentEntity retrievedUpdatedEntity = api.entity().commissionreportout().get(e.getId());

        assertNotEquals(retrievedOriginalEntity.getName(), retrievedUpdatedEntity.getName());
        assertEquals(name, retrievedUpdatedEntity.getName());
        assertEquals(retrievedOriginalEntity.getOrganization().getMeta().getHref(), retrievedUpdatedEntity.getOrganization().getMeta().getHref());
        assertEquals(retrievedOriginalEntity.getAgent().getMeta().getHref(), retrievedUpdatedEntity.getAgent().getMeta().getHref());
        assertEquals(retrievedOriginalEntity.getContract().getMeta().getHref(), retrievedUpdatedEntity.getContract().getMeta().getHref());
    }

    @Test
    public void createPositionByIdTest() throws IOException, LognexApiException {
        CommissionReportOutDocumentEntity e = createSimpleDocumentCommissionReportOut();

        ListEntity<DocumentPosition> originalPositions = api.entity().commissionreportout().getPositions(e.getId());

        DocumentPosition position = new DocumentPosition();

        ProductEntity product = new ProductEntity();
        product.setName(randomString());
        api.entity().product().post(product);

        position.setAssortment(product);
        position.setQuantity(randomDouble(1, 5, 3));

        api.entity().commissionreportout().postPosition(e.getId(), position);
        ListEntity<DocumentPosition> retrievedPositions = api.entity().commissionreportout().getPositions(e.getId());

        assertEquals(Integer.valueOf(originalPositions.getMeta().getSize() + 1), retrievedPositions.getMeta().getSize());
        assertTrue(retrievedPositions.
                getRows().
                stream().
                anyMatch(x -> ((ProductEntity) x.getAssortment()).getMeta().getHref().equals(product.getMeta().getHref()) &&
                        x.getQuantity().equals(position.getQuantity())
                )
        );
    }

    @Test
    public void createPositionByEntityTest() throws IOException, LognexApiException {
        CommissionReportOutDocumentEntity e = createSimpleDocumentCommissionReportOut();

        ListEntity<DocumentPosition> originalPositions = api.entity().commissionreportout().getPositions(e.getId());

        DocumentPosition position = new DocumentPosition();

        ProductEntity product = new ProductEntity();
        product.setName(randomString());
        api.entity().product().post(product);

        position.setAssortment(product);
        position.setQuantity(randomDouble(1, 5, 3));

        api.entity().commissionreportout().postPosition(e, position);
        ListEntity<DocumentPosition> retrievedPositions = api.entity().commissionreportout().getPositions(e);

        assertEquals(Integer.valueOf(originalPositions.getMeta().getSize() + 1), retrievedPositions.getMeta().getSize());
        assertTrue(retrievedPositions.
                getRows().
                stream().
                anyMatch(x -> ((ProductEntity) x.getAssortment()).getMeta().getHref().equals(product.getMeta().getHref()) &&
                        x.getQuantity().equals(position.getQuantity())
                )
        );
    }

    @Test
    public void createPositionsByIdTest() throws IOException, LognexApiException {
        CommissionReportOutDocumentEntity e = createSimpleDocumentCommissionReportOut();

        ListEntity<DocumentPosition> originalPositions = api.entity().commissionreportout().getPositions(e.getId());

        List<DocumentPosition> positions = new ArrayList<>();
        List<ProductEntity> products = new ArrayList<>();

        for (int i = 0; i < 2; i++) {
            DocumentPosition position = new DocumentPosition();

            ProductEntity product = new ProductEntity();
            product.setName(randomString());
            api.entity().product().post(product);
            products.add(product);

            position.setAssortment(product);
            position.setQuantity(randomDouble(1, 5, 3));

            positions.add(position);
        }

        api.entity().commissionreportout().postPositions(e.getId(), positions);
        ListEntity<DocumentPosition> retrievedPositions = api.entity().commissionreportout().getPositions(e.getId());

        assertEquals(Integer.valueOf(originalPositions.getMeta().getSize() + 2), retrievedPositions.getMeta().getSize());
        for (int i = 0; i < 2; i++) {
            ProductEntity product = products.get(i);
            DocumentPosition position = positions.get(i);

            assertTrue(retrievedPositions.
                    getRows().
                    stream().
                    anyMatch(x -> ((ProductEntity) x.getAssortment()).getMeta().getHref().equals(product.getMeta().getHref()) &&
                            x.getQuantity().equals(position.getQuantity())
                    )
            );
        }
    }

    @Test
    public void createPositionsByEntityTest() throws IOException, LognexApiException {
        CommissionReportOutDocumentEntity e = createSimpleDocumentCommissionReportOut();

        ListEntity<DocumentPosition> originalPositions = api.entity().commissionreportout().getPositions(e.getId());

        List<DocumentPosition> positions = new ArrayList<>();
        List<ProductEntity> products = new ArrayList<>();

        for (int i = 0; i < 2; i++) {
            DocumentPosition position = new DocumentPosition();

            ProductEntity product = new ProductEntity();
            product.setName(randomString());
            api.entity().product().post(product);
            products.add(product);

            position.setAssortment(product);
            position.setQuantity(randomDouble(1, 5, 3));

            positions.add(position);
        }

        api.entity().commissionreportout().postPositions(e, positions);
        ListEntity<DocumentPosition> retrievedPositions = api.entity().commissionreportout().getPositions(e);

        assertEquals(Integer.valueOf(originalPositions.getMeta().getSize() + 2), retrievedPositions.getMeta().getSize());
        for (int i = 0; i < 2; i++) {
            ProductEntity product = products.get(i);
            DocumentPosition position = positions.get(i);

            assertTrue(retrievedPositions.
                    getRows().
                    stream().
                    anyMatch(x -> ((ProductEntity) x.getAssortment()).getMeta().getHref().equals(product.getMeta().getHref()) &&
                            x.getQuantity().equals(position.getQuantity())
                    )
            );
        }
    }

    @Test
    public void getPositionTest() throws IOException, LognexApiException {
        CommissionReportOutDocumentEntity e = createSimpleDocumentCommissionReportOut();
        List<DocumentPosition> positions = createSimplePositions(e);

        DocumentPosition retrievedPosition = api.entity().commissionreportout().getPosition(e.getId(), positions.get(0).getId());
        getPositionAsserts(positions.get(0), retrievedPosition);

        retrievedPosition = api.entity().commissionreportout().getPosition(e, positions.get(0).getId());
        getPositionAsserts(positions.get(0), retrievedPosition);
    }

    @Test
    public void putPositionByIdsTest() throws IOException, LognexApiException {
        CommissionReportOutDocumentEntity e = createSimpleDocumentCommissionReportOut();
        List<DocumentPosition> positions = createSimplePositions(e);

        DocumentPosition p = positions.get(0);
        DocumentPosition retrievedPosition = api.entity().commissionreportout().getPosition(e.getId(), p.getId());

        DecimalFormat df = new DecimalFormat("#.###");
        double quantity = Double.valueOf(df.format(p.getQuantity() + randomDouble(1, 1, 3)));
        p.setQuantity(quantity);
        api.entity().commissionreportout().putPosition(e.getId(), p.getId(), p);

        putPositionAsserts(e, p, retrievedPosition, quantity);
    }

    @Test
    public void putPositionByEntityIdTest() throws IOException, LognexApiException {
        CommissionReportOutDocumentEntity e = createSimpleDocumentCommissionReportOut();
        List<DocumentPosition> positions = createSimplePositions(e);

        DocumentPosition p = positions.get(0);
        DocumentPosition retrievedPosition = api.entity().commissionreportout().getPosition(e.getId(), p.getId());

        DecimalFormat df = new DecimalFormat("#.###");
        double quantity = Double.valueOf(df.format(p.getQuantity() + randomDouble(1, 1, 3)));
        p.setQuantity(quantity);
        api.entity().commissionreportout().putPosition(e, p.getId(), p);

        putPositionAsserts(e, p, retrievedPosition, quantity);
    }

    @Test
    public void putPositionByEntitiesTest() throws IOException, LognexApiException {
        CommissionReportOutDocumentEntity e = createSimpleDocumentCommissionReportOut();
        List<DocumentPosition> positions = createSimplePositions(e);

        DocumentPosition p = positions.get(0);
        DocumentPosition retrievedPosition = api.entity().commissionreportout().getPosition(e.getId(), p.getId());

        DecimalFormat df = new DecimalFormat("#.###");
        double quantity = Double.valueOf(df.format(p.getQuantity() + randomDouble(1, 1, 3)));
        p.setQuantity(quantity);
        api.entity().commissionreportout().putPosition(e, p, p);

        putPositionAsserts(e, p, retrievedPosition, quantity);
    }

    @Test
    public void putPositionBySelfTest() throws IOException, LognexApiException {
        CommissionReportOutDocumentEntity e = createSimpleDocumentCommissionReportOut();
        List<DocumentPosition> positions = createSimplePositions(e);

        DocumentPosition p = positions.get(0);
        DocumentPosition retrievedPosition = api.entity().commissionreportout().getPosition(e.getId(), p.getId());

        DecimalFormat df = new DecimalFormat("#.###");
        double quantity = Double.valueOf(df.format(p.getQuantity() + randomDouble(1, 1, 3)));
        p.setQuantity(quantity);
        api.entity().commissionreportout().putPosition(e, p);

        putPositionAsserts(e, p, retrievedPosition, quantity);
    }

    @Test
    public void deletePositionByIdsTest() throws IOException, LognexApiException {
        CommissionReportOutDocumentEntity e = createSimpleDocumentCommissionReportOut();
        List<DocumentPosition> positions = createSimplePositions(e);

        ListEntity<DocumentPosition> positionsBefore = api.entity().commissionreportout().getPositions(e);

        api.entity().commissionreportout().delete(e.getId(), positions.get(0).getId());

        ListEntity<DocumentPosition> positionsAfter = api.entity().commissionreportout().getPositions(e);

        assertEquals(Integer.valueOf(positionsBefore.getMeta().getSize() - 1), positionsAfter.getMeta().getSize());
        assertFalse(positionsAfter.getRows().stream().
                anyMatch(x -> ((ProductEntity) positions.get(0).getAssortment()).getMeta().getHref().
                        equals(((ProductEntity) x.getAssortment()).getMeta().getHref()))
        );
    }

    @Test
    public void deletePositionByEntityIdTest() throws IOException, LognexApiException {
        CommissionReportOutDocumentEntity e = createSimpleDocumentCommissionReportOut();
        List<DocumentPosition> positions = createSimplePositions(e);

        ListEntity<DocumentPosition> positionsBefore = api.entity().commissionreportout().getPositions(e);

        api.entity().commissionreportout().delete(e, positions.get(0).getId());

        ListEntity<DocumentPosition> positionsAfter = api.entity().commissionreportout().getPositions(e);

        assertEquals(Integer.valueOf(positionsBefore.getMeta().getSize() - 1), positionsAfter.getMeta().getSize());
        assertFalse(positionsAfter.getRows().stream().
                anyMatch(x -> ((ProductEntity) positions.get(0).getAssortment()).getMeta().getHref().
                        equals(((ProductEntity) x.getAssortment()).getMeta().getHref()))
        );
    }

    @Test
    public void deletePositionByEntitiesTest() throws IOException, LognexApiException {
        CommissionReportOutDocumentEntity e = createSimpleDocumentCommissionReportOut();
        List<DocumentPosition> positions = createSimplePositions(e);

        ListEntity<DocumentPosition> positionsBefore = api.entity().commissionreportout().getPositions(e);

        api.entity().commissionreportout().delete(e, positions.get(0));

        ListEntity<DocumentPosition> positionsAfter = api.entity().commissionreportout().getPositions(e);

        assertEquals(Integer.valueOf(positionsBefore.getMeta().getSize() - 1), positionsAfter.getMeta().getSize());
        assertFalse(positionsAfter.getRows().stream().
                anyMatch(x -> ((ProductEntity) positions.get(0).getAssortment()).getMeta().getHref().
                        equals(((ProductEntity) x.getAssortment()).getMeta().getHref()))
        );
    }

    private List<DocumentPosition> createSimplePositions(CommissionReportOutDocumentEntity e) throws IOException, LognexApiException {
        List<DocumentPosition> positions = new ArrayList<>();

        for (int i = 0; i < 2; i++) {
            DocumentPosition position = new DocumentPosition();

            ProductEntity product = new ProductEntity();
            product.setName(randomString());
            api.entity().product().post(product);

            position.setAssortment(product);
            position.setQuantity(randomDouble(1, 5, 3));

            positions.add(position);
        }

        return api.entity().commissionreportout().postPositions(e, positions);
    }

    private void getPositionAsserts(DocumentPosition p, DocumentPosition retrievedPosition) {
        assertEquals(p.getMeta().getHref(), retrievedPosition.getMeta().getHref());
        assertEquals(((ProductEntity) p.getAssortment()).getMeta().getHref(),
                ((ProductEntity) retrievedPosition.getAssortment()).getMeta().getHref());
        assertEquals(p.getQuantity(), retrievedPosition.getQuantity());
    }

    private void putPositionAsserts(CommissionReportOutDocumentEntity e, DocumentPosition p, DocumentPosition retrievedOriginalPosition, Double quantity) throws IOException, LognexApiException {
        DocumentPosition retrievedUpdatedPosition = api.entity().commissionreportout().getPosition(e, p.getId());

        assertNotEquals(retrievedOriginalPosition.getQuantity(), retrievedUpdatedPosition.getQuantity());
        assertEquals(quantity, retrievedUpdatedPosition.getQuantity());
        assertEquals(((ProductEntity) retrievedOriginalPosition.getAssortment()).getMeta().getHref(),
                ((ProductEntity) retrievedUpdatedPosition.getAssortment()).getMeta().getHref());
    }
}
