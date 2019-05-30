package com.lognex.api.entities.documents;

import com.lognex.api.entities.EntityTestBase;
import com.lognex.api.entities.StoreEntity;
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
import java.time.temporal.ChronoUnit;
import java.util.*;

import static com.lognex.api.utils.params.FilterParam.filterEq;
import static org.junit.Assert.*;

public class SalesReturnDocumentEntityTest extends EntityTestBase {
    @Test
    public void createTest() throws IOException, LognexApiException {
        SalesReturnDocumentEntity e = new SalesReturnDocumentEntity();
        e.setName("salesreturn_" + randomString(3) + "_" + new Date().getTime());
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

        ListEntity<StoreEntity> store = api.entity().store().get(filterEq("name", "Основной склад"));
        assertEquals(1, store.getRows().size());
        e.setStore(store.getRows().get(0));

        DemandDocumentEntity demand = new DemandDocumentEntity();
        demand.setName("demand_" + randomString(3) + "_" + new Date().getTime());
        demand.setDescription(randomString());
        demand.setOrganization(orgList.getRows().get(0));
        demand.setAgent(agent);
        demand.setStore(store.getRows().get(0));
        
        api.entity().demand().post(demand);
        e.setDemand(demand);

        api.entity().salesreturn().post(e);

        ListEntity<SalesReturnDocumentEntity> updatedEntitiesList = api.entity().salesreturn().get(filterEq("name", e.getName()));
        assertEquals(1, updatedEntitiesList.getRows().size());

        SalesReturnDocumentEntity retrievedEntity = updatedEntitiesList.getRows().get(0);
        assertEquals(e.getName(), retrievedEntity.getName());
        assertEquals(e.getDescription(), retrievedEntity.getDescription());
        assertEquals(e.getVatEnabled(), retrievedEntity.getVatEnabled());
        assertEquals(e.getVatIncluded(), retrievedEntity.getVatIncluded());
        assertEquals(e.getMoment(), retrievedEntity.getMoment());
        assertEquals(e.getOrganization().getMeta().getHref(), retrievedEntity.getOrganization().getMeta().getHref());
        assertEquals(e.getAgent().getMeta().getHref(), retrievedEntity.getAgent().getMeta().getHref());
        assertEquals(e.getStore().getMeta().getHref(), retrievedEntity.getStore().getMeta().getHref());
        assertEquals(e.getDemand().getMeta().getHref(), retrievedEntity.getDemand().getMeta().getHref());
    }

    @Test
    public void getTest() throws IOException, LognexApiException {
        SalesReturnDocumentEntity e = createSimpleDocumentSalesReturn();

        SalesReturnDocumentEntity retrievedEntity = api.entity().salesreturn().get(e.getId());
        getAsserts(e, retrievedEntity);

        retrievedEntity = api.entity().salesreturn().get(e);
        getAsserts(e, retrievedEntity);
    }

    @Test
    public void putTest() throws IOException, LognexApiException, InterruptedException {
        SalesReturnDocumentEntity e = createSimpleDocumentSalesReturn();

        SalesReturnDocumentEntity retrievedOriginalEntity = api.entity().salesreturn().get(e.getId());
        String name = "salesreturn_" + randomString(3) + "_" + new Date().getTime();
        e.setName(name);
        api.entity().salesreturn().put(e.getId(), e);
        putAsserts(e, retrievedOriginalEntity, name);

        retrievedOriginalEntity.set(e);

        name = "salesreturn_" + randomString(3) + "_" + new Date().getTime();
        e.setName(name);
        api.entity().salesreturn().put(e);
        putAsserts(e, retrievedOriginalEntity, name);
    }

    @Test
    public void deleteTest() throws IOException, LognexApiException {
        SalesReturnDocumentEntity e = createSimpleDocumentSalesReturn();

        ListEntity<SalesReturnDocumentEntity> entitiesList = api.entity().salesreturn().get(filterEq("name", e.getName()));
        assertEquals((Integer) 1, entitiesList.getMeta().getSize());

        api.entity().salesreturn().delete(e.getId());

        entitiesList = api.entity().salesreturn().get(filterEq("name", e.getName()));
        assertEquals((Integer) 0, entitiesList.getMeta().getSize());
    }

    @Test
    public void deleteByIdTest() throws IOException, LognexApiException {
        SalesReturnDocumentEntity e = createSimpleDocumentSalesReturn();

        ListEntity<SalesReturnDocumentEntity> entitiesList = api.entity().salesreturn().get(filterEq("name", e.getName()));
        assertEquals((Integer) 1, entitiesList.getMeta().getSize());

        api.entity().salesreturn().delete(e);

        entitiesList = api.entity().salesreturn().get(filterEq("name", e.getName()));
        assertEquals((Integer) 0, entitiesList.getMeta().getSize());
    }

    @Test
    public void metadataTest() throws IOException, LognexApiException {
        MetadataAttributeSharedStatesResponse response = api.entity().salesreturn().metadata().get();

        assertFalse(response.getCreateShared());
    }

    @Test
    public void newTest() throws IOException, LognexApiException {
        SalesReturnDocumentEntity e = api.entity().salesreturn().newDocument();
        LocalDateTime time = LocalDateTime.now().withNano(0);

        assertEquals("", e.getName());
        assertTrue(e.getVatEnabled());
        assertTrue(e.getVatIncluded());
        assertEquals(Long.valueOf(0), e.getSum());
        assertFalse(e.getShared());
        assertTrue(e.getApplicable());
        assertTrue(ChronoUnit.MILLIS.between(time, e.getMoment()) < 1000);

        ListEntity<OrganizationEntity> orgList = api.entity().organization().get();
        Optional<OrganizationEntity> orgOptional = orgList.getRows().stream().
                min(Comparator.comparing(OrganizationEntity::getCreated));

        OrganizationEntity org = null;
        if (orgOptional.isPresent()) {
            org = orgOptional.get();
        } else {
            // Должно быть первое созданное юрлицо
            fail();
        }

        assertEquals(e.getOrganization().getMeta().getHref(), org.getMeta().getHref());

        ListEntity<StoreEntity> store = api.entity().store().get(filterEq("name", "Основной склад"));
        assertEquals(1, store.getRows().size());
        assertEquals(e.getStore().getMeta().getHref(), store.getRows().get(0).getMeta().getHref());
    }

    @Test
    public void newByDemandTest() throws IOException, LognexApiException {
        DemandDocumentEntity demand = new DemandDocumentEntity();
        demand.setName("demand_" + randomString(3) + "_" + new Date().getTime());

        ListEntity<OrganizationEntity> orgList = api.entity().organization().get();
        assertNotEquals(0, orgList.getRows().size());
        demand.setOrganization(orgList.getRows().get(0));

        CounterpartyEntity agent = new CounterpartyEntity();
        agent.setName(randomString());
        api.entity().counterparty().post(agent);
        demand.setAgent(agent);

        ListEntity<StoreEntity> store = api.entity().store().get(filterEq("name", "Основной склад"));
        assertEquals(1, store.getRows().size());
        demand.setStore(store.getRows().get(0));

        api.entity().demand().post(demand);

        SalesReturnDocumentEntity e = api.entity().salesreturn().newDocument("demand", demand);
        LocalDateTime time = LocalDateTime.now().withNano(0);

        assertEquals("", e.getName());
        assertEquals(demand.getVatEnabled(), e.getVatEnabled());
        assertEquals(demand.getVatIncluded(), e.getVatIncluded());
        assertEquals(demand.getPayedSum(), e.getPayedSum());
        assertEquals(demand.getSum(), e.getSum());
        assertFalse(e.getShared());
        assertTrue(e.getApplicable());
        assertTrue(ChronoUnit.MILLIS.between(time, e.getMoment()) < 1000);
        assertEquals(demand.getMeta().getHref(), e.getDemand().getMeta().getHref());
        assertEquals(demand.getAgent().getMeta().getHref(), e.getAgent().getMeta().getHref());
        assertEquals(demand.getStore().getMeta().getHref(), e.getStore().getMeta().getHref());
        assertEquals(demand.getOrganization().getMeta().getHref(), e.getOrganization().getMeta().getHref());
    }

    private SalesReturnDocumentEntity createSimpleDocumentSalesReturn() throws IOException, LognexApiException {
        SalesReturnDocumentEntity e = new SalesReturnDocumentEntity();
        e.setName("salesreturn_" + randomString(3) + "_" + new Date().getTime());

        ListEntity<OrganizationEntity> orgList = api.entity().organization().get();
        assertNotEquals(0, orgList.getRows().size());
        e.setOrganization(orgList.getRows().get(0));

        CounterpartyEntity agent = new CounterpartyEntity();
        agent.setName(randomString());
        api.entity().counterparty().post(agent);
        e.setAgent(agent);

        ListEntity<StoreEntity> store = api.entity().store().get(filterEq("name", "Основной склад"));
        assertEquals(1, store.getRows().size());
        e.setStore(store.getRows().get(0));

        api.entity().salesreturn().post(e);

        return e;
    }

    private void getAsserts(SalesReturnDocumentEntity e, SalesReturnDocumentEntity retrievedEntity) {
        assertEquals(e.getName(), retrievedEntity.getName());
        assertEquals(e.getOrganization().getMeta().getHref(), retrievedEntity.getOrganization().getMeta().getHref());
        assertEquals(e.getAgent().getMeta().getHref(), retrievedEntity.getAgent().getMeta().getHref());
        assertEquals(e.getStore().getMeta().getHref(), retrievedEntity.getStore().getMeta().getHref());
    }

    private void putAsserts(SalesReturnDocumentEntity e, SalesReturnDocumentEntity retrievedOriginalEntity, String name) throws IOException, LognexApiException {
        SalesReturnDocumentEntity retrievedUpdatedEntity = api.entity().salesreturn().get(e.getId());

        assertNotEquals(retrievedOriginalEntity.getName(), retrievedUpdatedEntity.getName());
        assertEquals(name, retrievedUpdatedEntity.getName());
        assertEquals(retrievedOriginalEntity.getOrganization().getMeta().getHref(), retrievedUpdatedEntity.getOrganization().getMeta().getHref());
        assertEquals(retrievedOriginalEntity.getAgent().getMeta().getHref(), retrievedUpdatedEntity.getAgent().getMeta().getHref());
        assertEquals(retrievedOriginalEntity.getStore().getMeta().getHref(), retrievedUpdatedEntity.getStore().getMeta().getHref());
    }

    @Test
    public void createPositionByIdTest() throws IOException, LognexApiException {
        SalesReturnDocumentEntity e = createSimpleDocumentSalesReturn();

        ListEntity<DocumentPosition> originalPositions = api.entity().salesreturn().getPositions(e.getId());

        DocumentPosition position = new DocumentPosition();

        ProductEntity product = new ProductEntity();
        product.setName(randomString());
        api.entity().product().post(product);

        position.setAssortment(product);
        position.setQuantity(randomDouble(1, 5, 3));

        api.entity().salesreturn().postPosition(e.getId(), position);
        ListEntity<DocumentPosition> retrievedPositions = api.entity().salesreturn().getPositions(e.getId());

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
        SalesReturnDocumentEntity e = createSimpleDocumentSalesReturn();

        ListEntity<DocumentPosition> originalPositions = api.entity().salesreturn().getPositions(e.getId());

        DocumentPosition position = new DocumentPosition();

        ProductEntity product = new ProductEntity();
        product.setName(randomString());
        api.entity().product().post(product);

        position.setAssortment(product);
        position.setQuantity(randomDouble(1, 5, 3));

        api.entity().salesreturn().postPosition(e, position);
        ListEntity<DocumentPosition> retrievedPositions = api.entity().salesreturn().getPositions(e);

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
        SalesReturnDocumentEntity e = createSimpleDocumentSalesReturn();

        ListEntity<DocumentPosition> originalPositions = api.entity().salesreturn().getPositions(e.getId());

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

        api.entity().salesreturn().postPositions(e.getId(), positions);
        ListEntity<DocumentPosition> retrievedPositions = api.entity().salesreturn().getPositions(e.getId());

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
        SalesReturnDocumentEntity e = createSimpleDocumentSalesReturn();

        ListEntity<DocumentPosition> originalPositions = api.entity().salesreturn().getPositions(e.getId());

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

        api.entity().salesreturn().postPositions(e, positions);
        ListEntity<DocumentPosition> retrievedPositions = api.entity().salesreturn().getPositions(e);

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
        SalesReturnDocumentEntity e = createSimpleDocumentSalesReturn();
        List<DocumentPosition> positions = createSimplePositions(e);

        DocumentPosition retrievedPosition = api.entity().salesreturn().getPosition(e.getId(), positions.get(0).getId());
        getPositionAsserts(positions.get(0), retrievedPosition);

        retrievedPosition = api.entity().salesreturn().getPosition(e, positions.get(0).getId());
        getPositionAsserts(positions.get(0), retrievedPosition);
    }

    @Test
    public void putPositionByIdsTest() throws IOException, LognexApiException {
        SalesReturnDocumentEntity e = createSimpleDocumentSalesReturn();
        List<DocumentPosition> positions = createSimplePositions(e);

        DocumentPosition p = positions.get(0);
        DocumentPosition retrievedPosition = api.entity().salesreturn().getPosition(e.getId(), p.getId());

        DecimalFormat df = new DecimalFormat("#.###");
        double quantity = Double.valueOf(df.format(p.getQuantity() + randomDouble(1, 1, 3)));
        p.setQuantity(quantity);
        api.entity().salesreturn().putPosition(e.getId(), p.getId(), p);

        putPositionAsserts(e, p, retrievedPosition, quantity);
    }

    @Test
    public void putPositionByEntityIdTest() throws IOException, LognexApiException {
        SalesReturnDocumentEntity e = createSimpleDocumentSalesReturn();
        List<DocumentPosition> positions = createSimplePositions(e);

        DocumentPosition p = positions.get(0);
        DocumentPosition retrievedPosition = api.entity().salesreturn().getPosition(e.getId(), p.getId());

        DecimalFormat df = new DecimalFormat("#.###");
        double quantity = Double.valueOf(df.format(p.getQuantity() + randomDouble(1, 1, 3)));
        p.setQuantity(quantity);
        api.entity().salesreturn().putPosition(e, p.getId(), p);

        putPositionAsserts(e, p, retrievedPosition, quantity);
    }

    @Test
    public void putPositionByEntitiesTest() throws IOException, LognexApiException {
        SalesReturnDocumentEntity e = createSimpleDocumentSalesReturn();
        List<DocumentPosition> positions = createSimplePositions(e);

        DocumentPosition p = positions.get(0);
        DocumentPosition retrievedPosition = api.entity().salesreturn().getPosition(e.getId(), p.getId());

        DecimalFormat df = new DecimalFormat("#.###");
        double quantity = Double.valueOf(df.format(p.getQuantity() + randomDouble(1, 1, 3)));
        p.setQuantity(quantity);
        api.entity().salesreturn().putPosition(e, p, p);

        putPositionAsserts(e, p, retrievedPosition, quantity);
    }

    @Test
    public void putPositionBySelfTest() throws IOException, LognexApiException {
        SalesReturnDocumentEntity e = createSimpleDocumentSalesReturn();
        List<DocumentPosition> positions = createSimplePositions(e);

        DocumentPosition p = positions.get(0);
        DocumentPosition retrievedPosition = api.entity().salesreturn().getPosition(e.getId(), p.getId());

        DecimalFormat df = new DecimalFormat("#.###");
        double quantity = Double.valueOf(df.format(p.getQuantity() + randomDouble(1, 1, 3)));
        p.setQuantity(quantity);
        api.entity().salesreturn().putPosition(e, p);

        putPositionAsserts(e, p, retrievedPosition, quantity);
    }

    @Test
    public void deletePositionByIdsTest() throws IOException, LognexApiException {
        SalesReturnDocumentEntity e = createSimpleDocumentSalesReturn();
        List<DocumentPosition> positions = createSimplePositions(e);

        ListEntity<DocumentPosition> positionsBefore = api.entity().salesreturn().getPositions(e);

        api.entity().salesreturn().delete(e.getId(), positions.get(0).getId());

        ListEntity<DocumentPosition> positionsAfter = api.entity().salesreturn().getPositions(e);

        assertEquals(Integer.valueOf(positionsBefore.getMeta().getSize() - 1), positionsAfter.getMeta().getSize());
        assertFalse(positionsAfter.getRows().stream().
                anyMatch(x -> ((ProductEntity) positions.get(0).getAssortment()).getMeta().getHref().
                        equals(((ProductEntity) x.getAssortment()).getMeta().getHref()))
        );
    }

    @Test
    public void deletePositionByEntityIdTest() throws IOException, LognexApiException {
        SalesReturnDocumentEntity e = createSimpleDocumentSalesReturn();
        List<DocumentPosition> positions = createSimplePositions(e);

        ListEntity<DocumentPosition> positionsBefore = api.entity().salesreturn().getPositions(e);

        api.entity().salesreturn().delete(e, positions.get(0).getId());

        ListEntity<DocumentPosition> positionsAfter = api.entity().salesreturn().getPositions(e);

        assertEquals(Integer.valueOf(positionsBefore.getMeta().getSize() - 1), positionsAfter.getMeta().getSize());
        assertFalse(positionsAfter.getRows().stream().
                anyMatch(x -> ((ProductEntity) positions.get(0).getAssortment()).getMeta().getHref().
                        equals(((ProductEntity) x.getAssortment()).getMeta().getHref()))
        );
    }

    @Test
    public void deletePositionByEntitiesTest() throws IOException, LognexApiException {
        SalesReturnDocumentEntity e = createSimpleDocumentSalesReturn();
        List<DocumentPosition> positions = createSimplePositions(e);

        ListEntity<DocumentPosition> positionsBefore = api.entity().salesreturn().getPositions(e);

        api.entity().salesreturn().delete(e, positions.get(0));

        ListEntity<DocumentPosition> positionsAfter = api.entity().salesreturn().getPositions(e);

        assertEquals(Integer.valueOf(positionsBefore.getMeta().getSize() - 1), positionsAfter.getMeta().getSize());
        assertFalse(positionsAfter.getRows().stream().
                anyMatch(x -> ((ProductEntity) positions.get(0).getAssortment()).getMeta().getHref().
                        equals(((ProductEntity) x.getAssortment()).getMeta().getHref()))
        );
    }

    private List<DocumentPosition> createSimplePositions(SalesReturnDocumentEntity e) throws IOException, LognexApiException {
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

        return api.entity().salesreturn().postPositions(e, positions);
    }

    private void getPositionAsserts(DocumentPosition p, DocumentPosition retrievedPosition) {
        assertEquals(p.getMeta().getHref(), retrievedPosition.getMeta().getHref());
        assertEquals(((ProductEntity) p.getAssortment()).getMeta().getHref(),
                ((ProductEntity) retrievedPosition.getAssortment()).getMeta().getHref());
        assertEquals(p.getQuantity(), retrievedPosition.getQuantity());
    }

    private void putPositionAsserts(SalesReturnDocumentEntity e, DocumentPosition p, DocumentPosition retrievedOriginalPosition, Double quantity) throws IOException, LognexApiException {
        DocumentPosition retrievedUpdatedPosition = api.entity().salesreturn().getPosition(e, p.getId());

        assertNotEquals(retrievedOriginalPosition.getQuantity(), retrievedUpdatedPosition.getQuantity());
        assertEquals(quantity, retrievedUpdatedPosition.getQuantity());
        assertEquals(((ProductEntity) retrievedOriginalPosition.getAssortment()).getMeta().getHref(),
                ((ProductEntity) retrievedUpdatedPosition.getAssortment()).getMeta().getHref());
    }
}
