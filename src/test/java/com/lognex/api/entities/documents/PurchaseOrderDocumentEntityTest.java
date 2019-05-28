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
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

import static com.lognex.api.utils.params.FilterParam.filterEq;
import static org.junit.Assert.*;

public class PurchaseOrderDocumentEntityTest extends EntityTestBase {
    @Test
    public void createTest() throws IOException, LognexApiException {
        PurchaseOrderDocumentEntity e = new PurchaseOrderDocumentEntity();
        e.setName("purchaseorder_" + randomString(3) + "_" + new Date().getTime());
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

        api.entity().purchaseorder().post(e);

        ListEntity<PurchaseOrderDocumentEntity> updatedEntitiesList = api.entity().purchaseorder().get(filterEq("name", e.getName()));
        assertEquals(1, updatedEntitiesList.getRows().size());

        PurchaseOrderDocumentEntity retrievedEntity = updatedEntitiesList.getRows().get(0);
        assertEquals(e.getName(), retrievedEntity.getName());
        assertEquals(e.getDescription(), retrievedEntity.getDescription());
        assertEquals(e.getVatEnabled(), retrievedEntity.getVatEnabled());
        assertEquals(e.getVatIncluded(), retrievedEntity.getVatIncluded());
        assertEquals(e.getMoment(), retrievedEntity.getMoment());
        assertEquals(e.getOrganization().getMeta().getHref(), retrievedEntity.getOrganization().getMeta().getHref());
        assertEquals(e.getAgent().getMeta().getHref(), retrievedEntity.getAgent().getMeta().getHref());
        assertEquals(e.getStore().getMeta().getHref(), retrievedEntity.getStore().getMeta().getHref());
    }

    @Test
    public void getTest() throws IOException, LognexApiException {
        PurchaseOrderDocumentEntity e = createSimpleDocumentPurchaseOrder();

        PurchaseOrderDocumentEntity retrievedEntity = api.entity().purchaseorder().get(e.getId());
        getAsserts(e, retrievedEntity);

        retrievedEntity = api.entity().purchaseorder().get(e);
        getAsserts(e, retrievedEntity);
    }

    @Test
    public void putTest() throws IOException, LognexApiException, InterruptedException {
        PurchaseOrderDocumentEntity e = createSimpleDocumentPurchaseOrder();

        PurchaseOrderDocumentEntity retrievedOriginalEntity = api.entity().purchaseorder().get(e.getId());
        String name = "purchaseorder_" + randomString(3) + "_" + new Date().getTime();
        e.setName(name);
        Thread.sleep(500);
        api.entity().purchaseorder().put(e.getId(), e);
        putAsserts(e, retrievedOriginalEntity, name);

        retrievedOriginalEntity.set(e);

        name = "purchaseorder_" + randomString(3) + "_" + new Date().getTime();
        e.setName(name);
        Thread.sleep(500);
        api.entity().purchaseorder().put(e);
        putAsserts(e, retrievedOriginalEntity, name);
    }

    @Test
    public void deleteTest() throws IOException, LognexApiException {
        PurchaseOrderDocumentEntity e = createSimpleDocumentPurchaseOrder();

        ListEntity<PurchaseOrderDocumentEntity> entitiesList = api.entity().purchaseorder().get(filterEq("name", e.getName()));
        assertEquals(1, entitiesList.getRows().size());

        api.entity().purchaseorder().delete(e.getId());

        entitiesList = api.entity().purchaseorder().get(filterEq("name", e.getName()));
        assertEquals(0, entitiesList.getRows().size());
    }

    @Test
    public void deleteByIdTest() throws IOException, LognexApiException {
        PurchaseOrderDocumentEntity e = createSimpleDocumentPurchaseOrder();

        ListEntity<PurchaseOrderDocumentEntity> entitiesList = api.entity().purchaseorder().get(filterEq("name", e.getName()));
        assertEquals(1, entitiesList.getRows().size());

        api.entity().purchaseorder().delete(e);

        entitiesList = api.entity().purchaseorder().get(filterEq("name", e.getName()));
        assertEquals(0, entitiesList.getRows().size());
    }

    @Test
    public void metadataTest() throws IOException, LognexApiException {
        MetadataAttributeSharedStatesResponse response = api.entity().purchaseorder().metadata().get();

        assertFalse(response.getCreateShared());
    }

    @Test
    public void newTest() throws IOException, LognexApiException {
        PurchaseOrderDocumentEntity e = api.entity().purchaseorder().newDocument();
        LocalDateTime time = LocalDateTime.now().withNano(0);

        assertEquals("", e.getName());
        assertTrue(e.getVatEnabled());
        assertTrue(e.getVatIncluded());
        assertEquals(Long.valueOf(0), e.getPayedSum());
        assertEquals(Long.valueOf(0), e.getShippedSum());
        assertEquals(Long.valueOf(0), e.getInvoicedSum());
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
    public void newByInternalOrderTest() throws IOException, LognexApiException {
        InternalOrderDocumentEntity internalOrder = new InternalOrderDocumentEntity();
        internalOrder.setName("internalorder_" + randomString(3) + "_" + new Date().getTime());
        internalOrder.setVatEnabled(true);
        internalOrder.setVatIncluded(true);

        ListEntity<OrganizationEntity> orgList = api.entity().organization().get();
        assertNotEquals(0, orgList.getRows().size());
        internalOrder.setOrganization(orgList.getRows().get(0));

        ListEntity<StoreEntity> store = api.entity().store().get(filterEq("name", "Основной склад"));
        assertEquals(1, store.getRows().size());
        internalOrder.setStore(store.getRows().get(0));

        api.entity().internalorder().post(internalOrder);

        PurchaseOrderDocumentEntity e = api.entity().purchaseorder().newDocument("internalOrder", internalOrder);
        LocalDateTime time = LocalDateTime.now().withNano(0);

        assertEquals("", e.getName());
        assertEquals(internalOrder.getSum(), e.getSum());
        assertEquals(internalOrder.getVatEnabled(), e.getVatEnabled());
        assertEquals(internalOrder.getVatIncluded(), e.getVatIncluded());
        assertFalse(e.getShared());
        assertTrue(e.getApplicable());
        assertTrue(ChronoUnit.MILLIS.between(time, e.getMoment()) < 1000);
        assertEquals(internalOrder.getMeta().getHref(), e.getInternalOrder().getMeta().getHref());
        assertEquals(internalOrder.getStore().getMeta().getHref(), e.getStore().getMeta().getHref());
        assertEquals(internalOrder.getGroup().getMeta().getHref(), e.getGroup().getMeta().getHref());
        assertEquals(internalOrder.getOrganization().getMeta().getHref(), e.getOrganization().getMeta().getHref());
    }

    @Test
    public void newByCustomerOrdersTest() throws IOException, LognexApiException {
        CustomerOrderDocumentEntity customerOrder = new CustomerOrderDocumentEntity();
        customerOrder.setName("customerorder_" + randomString(3) + "_" + new Date().getTime());

        ListEntity<OrganizationEntity> orgList = api.entity().organization().get();
        assertNotEquals(0, orgList.getRows().size());
        customerOrder.setOrganization(orgList.getRows().get(0));

        CounterpartyEntity agent = new CounterpartyEntity();
        agent.setName(randomString());
        api.entity().counterparty().post(agent);
        customerOrder.setAgent(agent);

        api.entity().customerorder().post(customerOrder);

        PurchaseOrderDocumentEntity e = api.entity().purchaseorder().newDocument("customerOrders", Collections.singletonList(customerOrder));
        LocalDateTime time = LocalDateTime.now().withNano(0);

        assertEquals("", e.getName());
        assertEquals(customerOrder.getVatEnabled(), e.getVatEnabled());
        assertEquals(customerOrder.getVatIncluded(), e.getVatIncluded());
        assertEquals(customerOrder.getPayedSum(), e.getPayedSum());
        assertEquals(customerOrder.getSum(), e.getSum());
        assertFalse(e.getShared());
        assertTrue(e.getApplicable());
        assertTrue(ChronoUnit.MILLIS.between(time, e.getMoment()) < 1000);
        assertEquals(1, e.getCustomerOrders().size());
        assertEquals(customerOrder.getMeta().getHref(), e.getCustomerOrders().get(0).getMeta().getHref());
        assertEquals(customerOrder.getGroup().getMeta().getHref(), e.getGroup().getMeta().getHref());
        assertEquals(customerOrder.getOrganization().getMeta().getHref(), e.getOrganization().getMeta().getHref());
    }

    private PurchaseOrderDocumentEntity createSimpleDocumentPurchaseOrder() throws IOException, LognexApiException {
        PurchaseOrderDocumentEntity e = new PurchaseOrderDocumentEntity();
        e.setName("purchaseorder_" + randomString(3) + "_" + new Date().getTime());
        e.setDescription(randomString());

        ListEntity<OrganizationEntity> orgList = api.entity().organization().get();
        assertNotEquals(0, orgList.getRows().size());
        e.setOrganization(orgList.getRows().get(0));

        CounterpartyEntity agent = new CounterpartyEntity();
        agent.setName(randomString());
        api.entity().counterparty().post(agent);
        e.setAgent(agent);

        api.entity().purchaseorder().post(e);

        return e;
    }

    private void getAsserts(PurchaseOrderDocumentEntity e, PurchaseOrderDocumentEntity retrievedEntity) {
        assertEquals(e.getName(), retrievedEntity.getName());
        assertEquals(e.getDescription(), retrievedEntity.getDescription());
        assertEquals(e.getOrganization().getMeta().getHref(), retrievedEntity.getOrganization().getMeta().getHref());
        assertEquals(e.getAgent().getMeta().getHref(), retrievedEntity.getAgent().getMeta().getHref());
    }

    private void putAsserts(PurchaseOrderDocumentEntity e, PurchaseOrderDocumentEntity retrievedOriginalEntity, String name) throws IOException, LognexApiException {
        PurchaseOrderDocumentEntity retrievedUpdatedEntity = api.entity().purchaseorder().get(e.getId());

        assertNotEquals(retrievedOriginalEntity.getName(), retrievedUpdatedEntity.getName());
        assertEquals(name, retrievedUpdatedEntity.getName());
        assertEquals(retrievedOriginalEntity.getDescription(), retrievedUpdatedEntity.getDescription());
        assertEquals(retrievedOriginalEntity.getOrganization().getMeta().getHref(), retrievedUpdatedEntity.getOrganization().getMeta().getHref());
        assertEquals(retrievedOriginalEntity.getAgent().getMeta().getHref(), retrievedUpdatedEntity.getAgent().getMeta().getHref());
    }

    @Test
    public void createPositionByIdTest() throws IOException, LognexApiException {
        PurchaseOrderDocumentEntity e = createSimpleDocumentPurchaseOrder();

        ListEntity<DocumentPosition> originalPositions = api.entity().purchaseorder().getPositions(e.getId());

        DocumentPosition position = new DocumentPosition();

        ProductEntity product = new ProductEntity();
        product.setName(randomString());
        api.entity().product().post(product);

        position.setAssortment(product);
        position.setQuantity(randomDouble(1, 5, 3));

        api.entity().purchaseorder().postPosition(e.getId(), position);
        ListEntity<DocumentPosition> retrievedPositions = api.entity().purchaseorder().getPositions(e.getId());

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
        PurchaseOrderDocumentEntity e = createSimpleDocumentPurchaseOrder();

        ListEntity<DocumentPosition> originalPositions = api.entity().purchaseorder().getPositions(e.getId());

        DocumentPosition position = new DocumentPosition();

        ProductEntity product = new ProductEntity();
        product.setName(randomString());
        api.entity().product().post(product);

        position.setAssortment(product);
        position.setQuantity(randomDouble(1, 5, 3));

        api.entity().purchaseorder().postPosition(e, position);
        ListEntity<DocumentPosition> retrievedPositions = api.entity().purchaseorder().getPositions(e);

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
        PurchaseOrderDocumentEntity e = createSimpleDocumentPurchaseOrder();

        ListEntity<DocumentPosition> originalPositions = api.entity().purchaseorder().getPositions(e.getId());

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

        api.entity().purchaseorder().postPositions(e.getId(), positions);
        ListEntity<DocumentPosition> retrievedPositions = api.entity().purchaseorder().getPositions(e.getId());

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
        PurchaseOrderDocumentEntity e = createSimpleDocumentPurchaseOrder();

        ListEntity<DocumentPosition> originalPositions = api.entity().purchaseorder().getPositions(e.getId());

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

        api.entity().purchaseorder().postPositions(e, positions);
        ListEntity<DocumentPosition> retrievedPositions = api.entity().purchaseorder().getPositions(e);

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
        PurchaseOrderDocumentEntity e = createSimpleDocumentPurchaseOrder();
        List<DocumentPosition> positions = createSimplePositions(e);

        DocumentPosition retrievedPosition = api.entity().purchaseorder().getPosition(e.getId(), positions.get(0).getId());
        getPositionAsserts(positions.get(0), retrievedPosition);

        retrievedPosition = api.entity().purchaseorder().getPosition(e, positions.get(0).getId());
        getPositionAsserts(positions.get(0), retrievedPosition);
    }

    @Test
    public void putPositionByIdsTest() throws IOException, LognexApiException {
        PurchaseOrderDocumentEntity e = createSimpleDocumentPurchaseOrder();
        List<DocumentPosition> positions = createSimplePositions(e);

        DocumentPosition p = positions.get(0);
        DocumentPosition retrievedPosition = api.entity().purchaseorder().getPosition(e.getId(), p.getId());

        double quantity = p.getQuantity() + randomDouble(1, 1, 2);
        p.setQuantity(quantity);
        api.entity().purchaseorder().putPosition(e.getId(), p.getId(), p);

        putPositionAsserts(e, p, retrievedPosition, quantity);
    }

    @Test
    public void putPositionByEntityIdTest() throws IOException, LognexApiException {
        PurchaseOrderDocumentEntity e = createSimpleDocumentPurchaseOrder();
        List<DocumentPosition> positions = createSimplePositions(e);

        DocumentPosition p = positions.get(0);
        DocumentPosition retrievedPosition = api.entity().purchaseorder().getPosition(e.getId(), p.getId());

        double quantity = p.getQuantity() + randomDouble(1, 1, 2);
        p.setQuantity(quantity);
        api.entity().purchaseorder().putPosition(e, p.getId(), p);

        putPositionAsserts(e, p, retrievedPosition, quantity);
    }

    @Test
    public void putPositionByEntitiesTest() throws IOException, LognexApiException {
        PurchaseOrderDocumentEntity e = createSimpleDocumentPurchaseOrder();
        List<DocumentPosition> positions = createSimplePositions(e);

        DocumentPosition p = positions.get(0);
        DocumentPosition retrievedPosition = api.entity().purchaseorder().getPosition(e.getId(), p.getId());

        double quantity = p.getQuantity() + randomDouble(1, 1, 2);
        p.setQuantity(quantity);
        api.entity().purchaseorder().putPosition(e, p, p);

        putPositionAsserts(e, p, retrievedPosition, quantity);
    }

    @Test
    public void putPositionBySelfTest() throws IOException, LognexApiException {
        PurchaseOrderDocumentEntity e = createSimpleDocumentPurchaseOrder();
        List<DocumentPosition> positions = createSimplePositions(e);

        DocumentPosition p = positions.get(0);
        DocumentPosition retrievedPosition = api.entity().purchaseorder().getPosition(e.getId(), p.getId());

        Double quantity = p.getQuantity() + randomDouble(1, 1, 2);
        p.setQuantity(quantity);
        api.entity().purchaseorder().putPosition(e, p);

        putPositionAsserts(e, p, retrievedPosition, quantity);
    }

    @Test
    public void deletePositionByIdsTest() throws IOException, LognexApiException {
        PurchaseOrderDocumentEntity e = createSimpleDocumentPurchaseOrder();
        List<DocumentPosition> positions = createSimplePositions(e);

        ListEntity<DocumentPosition> positionsBefore = api.entity().purchaseorder().getPositions(e);

        api.entity().purchaseorder().delete(e.getId(), positions.get(0).getId());

        ListEntity<DocumentPosition> positionsAfter = api.entity().purchaseorder().getPositions(e);

        assertEquals(Integer.valueOf(positionsBefore.getMeta().getSize() - 1), positionsAfter.getMeta().getSize());
        assertFalse(positionsAfter.getRows().stream().
                anyMatch(x -> ((ProductEntity) positions.get(0).getAssortment()).getMeta().getHref().
                        equals(((ProductEntity) x.getAssortment()).getMeta().getHref()))
        );
    }

    @Test
    public void deletePositionByEntityIdTest() throws IOException, LognexApiException {
        PurchaseOrderDocumentEntity e = createSimpleDocumentPurchaseOrder();
        List<DocumentPosition> positions = createSimplePositions(e);

        ListEntity<DocumentPosition> positionsBefore = api.entity().purchaseorder().getPositions(e);

        api.entity().purchaseorder().delete(e, positions.get(0).getId());

        ListEntity<DocumentPosition> positionsAfter = api.entity().purchaseorder().getPositions(e);

        assertEquals(Integer.valueOf(positionsBefore.getMeta().getSize() - 1), positionsAfter.getMeta().getSize());
        assertFalse(positionsAfter.getRows().stream().
                anyMatch(x -> ((ProductEntity) positions.get(0).getAssortment()).getMeta().getHref().
                        equals(((ProductEntity) x.getAssortment()).getMeta().getHref()))
        );
    }

    @Test
    public void deletePositionByEntitiesTest() throws IOException, LognexApiException {
        PurchaseOrderDocumentEntity e = createSimpleDocumentPurchaseOrder();
        List<DocumentPosition> positions = createSimplePositions(e);

        ListEntity<DocumentPosition> positionsBefore = api.entity().purchaseorder().getPositions(e);

        api.entity().purchaseorder().delete(e, positions.get(0));

        ListEntity<DocumentPosition> positionsAfter = api.entity().purchaseorder().getPositions(e);

        assertEquals(Integer.valueOf(positionsBefore.getMeta().getSize() - 1), positionsAfter.getMeta().getSize());
        assertFalse(positionsAfter.getRows().stream().
                anyMatch(x -> ((ProductEntity) positions.get(0).getAssortment()).getMeta().getHref().
                        equals(((ProductEntity) x.getAssortment()).getMeta().getHref()))
        );
    }

    private List<DocumentPosition> createSimplePositions(PurchaseOrderDocumentEntity e) throws IOException, LognexApiException {
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

        return api.entity().purchaseorder().postPositions(e, positions);
    }

    private void getPositionAsserts(DocumentPosition p, DocumentPosition retrievedPosition) {
        assertEquals(p.getMeta().getHref(), retrievedPosition.getMeta().getHref());
        assertEquals(((ProductEntity) p.getAssortment()).getMeta().getHref(),
                ((ProductEntity) retrievedPosition.getAssortment()).getMeta().getHref());
        assertEquals(p.getQuantity(), retrievedPosition.getQuantity());
    }

    private void putPositionAsserts(PurchaseOrderDocumentEntity e, DocumentPosition p, DocumentPosition retrievedOriginalPosition, Double quantity) throws IOException, LognexApiException {
        DocumentPosition retrievedUpdatedPosition = api.entity().purchaseorder().getPosition(e, p.getId());

        assertNotEquals(retrievedOriginalPosition.getQuantity(), retrievedUpdatedPosition.getQuantity());
        assertEquals(quantity, retrievedUpdatedPosition.getQuantity());
        assertEquals(((ProductEntity) retrievedOriginalPosition.getAssortment()).getMeta().getHref(),
                ((ProductEntity) retrievedUpdatedPosition.getAssortment()).getMeta().getHref());
    }
}
