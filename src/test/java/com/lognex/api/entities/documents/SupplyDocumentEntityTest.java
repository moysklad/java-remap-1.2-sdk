package com.lognex.api.entities.documents;

import com.lognex.api.entities.EntityTestBase;
import com.lognex.api.entities.GroupEntity;
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
import static com.lognex.api.utils.params.SearchParam.search;
import static org.junit.Assert.*;

public class SupplyDocumentEntityTest extends EntityTestBase {
    @Test
    public void createTest() throws IOException, LognexApiException {
        SupplyDocumentEntity e = new SupplyDocumentEntity();
        e.setName("supply_" + randomString(3) + "_" + new Date().getTime());
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

        api.entity().supply().post(e);

        ListEntity<SupplyDocumentEntity> updatedEntitiesList = api.entity().supply().get(filterEq("name", e.getName()));
        assertEquals(1, updatedEntitiesList.getRows().size());

        SupplyDocumentEntity retrievedEntity = updatedEntitiesList.getRows().get(0);
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
        SupplyDocumentEntity e = createSimpleDocumentSupply();

        SupplyDocumentEntity retrievedEntity = api.entity().supply().get(e.getId());
        getAsserts(e, retrievedEntity);

        retrievedEntity = api.entity().supply().get(e);
        getAsserts(e, retrievedEntity);
    }

    @Test
    public void putTest() throws IOException, LognexApiException, InterruptedException {
        SupplyDocumentEntity e = createSimpleDocumentSupply();

        SupplyDocumentEntity retrievedOriginalEntity = api.entity().supply().get(e.getId());
        String name = "supply_" + randomString(3) + "_" + new Date().getTime();
        e.setName(name);
        api.entity().supply().put(e.getId(), e);
        putAsserts(e, retrievedOriginalEntity, name);

        retrievedOriginalEntity.set(e);

        name = "supply_" + randomString(3) + "_" + new Date().getTime();
        e.setName(name);
        api.entity().supply().put(e);
        putAsserts(e, retrievedOriginalEntity, name);
    }

    @Test
    public void deleteTest() throws IOException, LognexApiException {
        SupplyDocumentEntity e = createSimpleDocumentSupply();

        ListEntity<SupplyDocumentEntity> entitiesList = api.entity().supply().get(filterEq("name", e.getName()));
        assertEquals(1, entitiesList.getRows().size());

        api.entity().supply().delete(e.getId());

        entitiesList = api.entity().supply().get(filterEq("name", e.getName()));
        assertEquals(0, entitiesList.getRows().size());
    }

    @Test
    public void deleteByIdTest() throws IOException, LognexApiException {
        SupplyDocumentEntity e = createSimpleDocumentSupply();

        ListEntity<SupplyDocumentEntity> entitiesList = api.entity().supply().get(filterEq("name", e.getName()));
        assertEquals(1, entitiesList.getRows().size());

        api.entity().supply().delete(e);

        entitiesList = api.entity().supply().get(filterEq("name", e.getName()));
        assertEquals(0, entitiesList.getRows().size());
    }

    @Test
    public void metadataTest() throws IOException, LognexApiException {
        MetadataAttributeSharedStatesResponse response = api.entity().supply().metadata().get();

        assertFalse(response.getCreateShared());
    }

    @Test
    public void newTest() throws IOException, LognexApiException {
        SupplyDocumentEntity e = api.entity().supply().newDocument();
        LocalDateTime time = LocalDateTime.now().withNano(0);

        assertEquals("", e.getName());
        assertTrue(e.getVatEnabled());
        assertTrue(e.getVatIncluded());
        assertEquals(Long.valueOf(0), e.getPayedSum());
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

        ListEntity<GroupEntity> group = api.entity().group().get(search("Основной"));
        assertEquals(1, group.getRows().size());
        assertEquals(e.getGroup().getMeta().getHref(), group.getRows().get(0).getMeta().getHref());
    }

    @Test
    public void newByPurchaseOrderTest() throws IOException, LognexApiException {
        PurchaseOrderDocumentEntity purchaseOrder = new PurchaseOrderDocumentEntity();
        purchaseOrder.setName("purchaseorder_" + randomString(3) + "_" + new Date().getTime());
        purchaseOrder.setDescription(randomString());
        purchaseOrder.setVatEnabled(true);
        purchaseOrder.setVatIncluded(true);

        ListEntity<OrganizationEntity> orgList = api.entity().organization().get();
        assertNotEquals(0, orgList.getRows().size());
        purchaseOrder.setOrganization(orgList.getRows().get(0));

        CounterpartyEntity agent = new CounterpartyEntity();
        agent.setName(randomString());
        api.entity().counterparty().post(agent);
        purchaseOrder.setAgent(agent);
        purchaseOrder.setMoment(LocalDateTime.now().withNano(0));

        api.entity().purchaseorder().post(purchaseOrder);

        SupplyDocumentEntity e = api.entity().supply().newDocument("purchaseOrder", purchaseOrder);
        LocalDateTime time = LocalDateTime.now().withNano(0);

        assertEquals("", e.getName());
        assertEquals(purchaseOrder.getVatEnabled(), e.getVatEnabled());
        assertEquals(purchaseOrder.getVatIncluded(), e.getVatIncluded());
        assertEquals(purchaseOrder.getPayedSum(), e.getPayedSum());
        assertEquals(purchaseOrder.getSum(), e.getSum());
        assertFalse(e.getShared());
        assertTrue(e.getApplicable());
        assertTrue(ChronoUnit.MILLIS.between(time, e.getMoment()) < 1000);
        assertEquals(purchaseOrder.getMeta().getHref(), e.getPurchaseOrder().getMeta().getHref());
        assertEquals(purchaseOrder.getAgent().getMeta().getHref(), e.getAgent().getMeta().getHref());
        assertEquals(purchaseOrder.getGroup().getMeta().getHref(), e.getGroup().getMeta().getHref());
        assertEquals(purchaseOrder.getOrganization().getMeta().getHref(), e.getOrganization().getMeta().getHref());
    }

    @Test
    public void newByInvoicesInTest() throws IOException, LognexApiException {
        InvoiceInDocumentEntity invoiceIn = new InvoiceInDocumentEntity();
        invoiceIn.setName("invoiceout_" + randomString(3) + "_" + new Date().getTime());
        invoiceIn.setVatEnabled(true);
        invoiceIn.setVatIncluded(true);

        ListEntity<OrganizationEntity> orgList = api.entity().organization().get();
        assertNotEquals(0, orgList.getRows().size());
        invoiceIn.setOrganization(orgList.getRows().get(0));

        CounterpartyEntity agent = new CounterpartyEntity();
        agent.setName(randomString());
        api.entity().counterparty().post(agent);
        invoiceIn.setAgent(agent);

        api.entity().invoicein().post(invoiceIn);

        SupplyDocumentEntity e = api.entity().supply().newDocument("invoicesIn", Collections.singletonList(invoiceIn));
        LocalDateTime time = LocalDateTime.now().withNano(0);

        assertEquals("", e.getName());
        assertEquals(invoiceIn.getVatEnabled(), e.getVatEnabled());
        assertEquals(invoiceIn.getVatIncluded(), e.getVatIncluded());
        assertEquals(invoiceIn.getPayedSum(), e.getPayedSum());
        assertEquals(invoiceIn.getSum(), e.getSum());
        assertFalse(e.getShared());
        assertTrue(e.getApplicable());
        assertTrue(ChronoUnit.MILLIS.between(time, e.getMoment()) < 1000);
        assertEquals(1, e.getInvoicesIn().size());
        assertEquals(invoiceIn.getMeta().getHref(), e.getInvoicesIn().get(0).getMeta().getHref());
        assertEquals(invoiceIn.getAgent().getMeta().getHref(), e.getAgent().getMeta().getHref());
        assertEquals(invoiceIn.getGroup().getMeta().getHref(), e.getGroup().getMeta().getHref());
        assertEquals(invoiceIn.getOrganization().getMeta().getHref(), e.getOrganization().getMeta().getHref());
    }

    private SupplyDocumentEntity createSimpleDocumentSupply() throws IOException, LognexApiException {
        SupplyDocumentEntity e = new SupplyDocumentEntity();
        e.setName("supply_" + randomString(3) + "_" + new Date().getTime());
        e.setDescription(randomString());

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

        api.entity().supply().post(e);

        return e;
    }

    private void getAsserts(SupplyDocumentEntity e, SupplyDocumentEntity retrievedEntity) {
        assertEquals(e.getName(), retrievedEntity.getName());
        assertEquals(e.getDescription(), retrievedEntity.getDescription());
        assertEquals(e.getOrganization().getMeta().getHref(), retrievedEntity.getOrganization().getMeta().getHref());
        assertEquals(e.getAgent().getMeta().getHref(), retrievedEntity.getAgent().getMeta().getHref());
        assertEquals(e.getStore().getMeta().getHref(), retrievedEntity.getStore().getMeta().getHref());
    }

    private void putAsserts(SupplyDocumentEntity e, SupplyDocumentEntity retrievedOriginalEntity, String name) throws IOException, LognexApiException {
        SupplyDocumentEntity retrievedUpdatedEntity = api.entity().supply().get(e.getId());

        assertNotEquals(retrievedOriginalEntity.getName(), retrievedUpdatedEntity.getName());
        assertEquals(name, retrievedUpdatedEntity.getName());
        assertEquals(retrievedOriginalEntity.getDescription(), retrievedUpdatedEntity.getDescription());
        assertEquals(retrievedOriginalEntity.getOrganization().getMeta().getHref(), retrievedUpdatedEntity.getOrganization().getMeta().getHref());
        assertEquals(retrievedOriginalEntity.getAgent().getMeta().getHref(), retrievedUpdatedEntity.getAgent().getMeta().getHref());
        assertEquals(retrievedOriginalEntity.getStore().getMeta().getHref(), retrievedUpdatedEntity.getStore().getMeta().getHref());
    }

    @Test
    public void createPositionByIdTest() throws IOException, LognexApiException {
        SupplyDocumentEntity e = createSimpleDocumentSupply();

        ListEntity<DocumentPosition> originalPositions = api.entity().supply().getPositions(e.getId());

        DocumentPosition position = new DocumentPosition();

        ProductEntity product = new ProductEntity();
        product.setName(randomString());
        api.entity().product().post(product);

        position.setAssortment(product);
        position.setQuantity(randomDouble(1, 5, 3));

        api.entity().supply().postPosition(e.getId(), position);
        ListEntity<DocumentPosition> retrievedPositions = api.entity().supply().getPositions(e.getId());

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
        SupplyDocumentEntity e = createSimpleDocumentSupply();

        ListEntity<DocumentPosition> originalPositions = api.entity().supply().getPositions(e.getId());

        DocumentPosition position = new DocumentPosition();

        ProductEntity product = new ProductEntity();
        product.setName(randomString());
        api.entity().product().post(product);

        position.setAssortment(product);
        position.setQuantity(randomDouble(1, 5, 3));

        api.entity().supply().postPosition(e, position);
        ListEntity<DocumentPosition> retrievedPositions = api.entity().supply().getPositions(e);

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
        SupplyDocumentEntity e = createSimpleDocumentSupply();

        ListEntity<DocumentPosition> originalPositions = api.entity().supply().getPositions(e.getId());

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

        api.entity().supply().postPositions(e.getId(), positions);
        ListEntity<DocumentPosition> retrievedPositions = api.entity().supply().getPositions(e.getId());

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
        SupplyDocumentEntity e = createSimpleDocumentSupply();

        ListEntity<DocumentPosition> originalPositions = api.entity().supply().getPositions(e.getId());

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

        api.entity().supply().postPositions(e, positions);
        ListEntity<DocumentPosition> retrievedPositions = api.entity().supply().getPositions(e);

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
        SupplyDocumentEntity e = createSimpleDocumentSupply();
        List<DocumentPosition> positions = createSimplePositions(e);

        DocumentPosition retrievedPosition = api.entity().supply().getPosition(e.getId(), positions.get(0).getId());
        getPositionAsserts(positions.get(0), retrievedPosition);

        retrievedPosition = api.entity().supply().getPosition(e, positions.get(0).getId());
        getPositionAsserts(positions.get(0), retrievedPosition);
    }

    @Test
    public void putPositionByIdsTest() throws IOException, LognexApiException {
        SupplyDocumentEntity e = createSimpleDocumentSupply();
        List<DocumentPosition> positions = createSimplePositions(e);

        DocumentPosition p = positions.get(0);
        DocumentPosition retrievedPosition = api.entity().supply().getPosition(e.getId(), p.getId());

        DecimalFormat df = new DecimalFormat("#.###");
        double quantity = Double.valueOf(df.format(p.getQuantity() + randomDouble(1, 1, 3)));
        p.setQuantity(quantity);
        api.entity().supply().putPosition(e.getId(), p.getId(), p);

        putPositionAsserts(e, p, retrievedPosition, quantity);
    }

    @Test
    public void putPositionByEntityIdTest() throws IOException, LognexApiException {
        SupplyDocumentEntity e = createSimpleDocumentSupply();
        List<DocumentPosition> positions = createSimplePositions(e);

        DocumentPosition p = positions.get(0);
        DocumentPosition retrievedPosition = api.entity().supply().getPosition(e.getId(), p.getId());

        DecimalFormat df = new DecimalFormat("#.###");
        double quantity = Double.valueOf(df.format(p.getQuantity() + randomDouble(1, 1, 3)));
        p.setQuantity(quantity);
        api.entity().supply().putPosition(e, p.getId(), p);

        putPositionAsserts(e, p, retrievedPosition, quantity);
    }

    @Test
    public void putPositionByEntitiesTest() throws IOException, LognexApiException {
        SupplyDocumentEntity e = createSimpleDocumentSupply();
        List<DocumentPosition> positions = createSimplePositions(e);

        DocumentPosition p = positions.get(0);
        DocumentPosition retrievedPosition = api.entity().supply().getPosition(e.getId(), p.getId());

        DecimalFormat df = new DecimalFormat("#.###");
        double quantity = Double.valueOf(df.format(p.getQuantity() + randomDouble(1, 1, 3)));
        p.setQuantity(quantity);
        api.entity().supply().putPosition(e, p, p);

        putPositionAsserts(e, p, retrievedPosition, quantity);
    }

    @Test
    public void putPositionBySelfTest() throws IOException, LognexApiException {
        SupplyDocumentEntity e = createSimpleDocumentSupply();
        List<DocumentPosition> positions = createSimplePositions(e);

        DocumentPosition p = positions.get(0);
        DocumentPosition retrievedPosition = api.entity().supply().getPosition(e.getId(), p.getId());

        DecimalFormat df = new DecimalFormat("#.###");
        double quantity = Double.valueOf(df.format(p.getQuantity() + randomDouble(1, 1, 3)));
        p.setQuantity(quantity);
        api.entity().supply().putPosition(e, p);

        putPositionAsserts(e, p, retrievedPosition, quantity);
    }

    @Test
    public void deletePositionByIdsTest() throws IOException, LognexApiException {
        SupplyDocumentEntity e = createSimpleDocumentSupply();
        List<DocumentPosition> positions = createSimplePositions(e);

        ListEntity<DocumentPosition> positionsBefore = api.entity().supply().getPositions(e);

        api.entity().supply().delete(e.getId(), positions.get(0).getId());

        ListEntity<DocumentPosition> positionsAfter = api.entity().supply().getPositions(e);

        assertEquals(Integer.valueOf(positionsBefore.getMeta().getSize() - 1), positionsAfter.getMeta().getSize());
        assertFalse(positionsAfter.getRows().stream().
                anyMatch(x -> ((ProductEntity) positions.get(0).getAssortment()).getMeta().getHref().
                        equals(((ProductEntity) x.getAssortment()).getMeta().getHref()))
        );
    }

    @Test
    public void deletePositionByEntityIdTest() throws IOException, LognexApiException {
        SupplyDocumentEntity e = createSimpleDocumentSupply();
        List<DocumentPosition> positions = createSimplePositions(e);

        ListEntity<DocumentPosition> positionsBefore = api.entity().supply().getPositions(e);

        api.entity().supply().delete(e, positions.get(0).getId());

        ListEntity<DocumentPosition> positionsAfter = api.entity().supply().getPositions(e);

        assertEquals(Integer.valueOf(positionsBefore.getMeta().getSize() - 1), positionsAfter.getMeta().getSize());
        assertFalse(positionsAfter.getRows().stream().
                anyMatch(x -> ((ProductEntity) positions.get(0).getAssortment()).getMeta().getHref().
                        equals(((ProductEntity) x.getAssortment()).getMeta().getHref()))
        );
    }

    @Test
    public void deletePositionByEntitiesTest() throws IOException, LognexApiException {
        SupplyDocumentEntity e = createSimpleDocumentSupply();
        List<DocumentPosition> positions = createSimplePositions(e);

        ListEntity<DocumentPosition> positionsBefore = api.entity().supply().getPositions(e);

        api.entity().supply().delete(e, positions.get(0));

        ListEntity<DocumentPosition> positionsAfter = api.entity().supply().getPositions(e);

        assertEquals(Integer.valueOf(positionsBefore.getMeta().getSize() - 1), positionsAfter.getMeta().getSize());
        assertFalse(positionsAfter.getRows().stream().
                anyMatch(x -> ((ProductEntity) positions.get(0).getAssortment()).getMeta().getHref().
                        equals(((ProductEntity) x.getAssortment()).getMeta().getHref()))
        );
    }

    private List<DocumentPosition> createSimplePositions(SupplyDocumentEntity e) throws IOException, LognexApiException {
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

        return api.entity().supply().postPositions(e, positions);
    }

    private void getPositionAsserts(DocumentPosition p, DocumentPosition retrievedPosition) {
        assertEquals(p.getMeta().getHref(), retrievedPosition.getMeta().getHref());
        assertEquals(((ProductEntity) p.getAssortment()).getMeta().getHref(),
                ((ProductEntity) retrievedPosition.getAssortment()).getMeta().getHref());
        assertEquals(p.getQuantity(), retrievedPosition.getQuantity());
    }

    private void putPositionAsserts(SupplyDocumentEntity e, DocumentPosition p, DocumentPosition retrievedOriginalPosition, Double quantity) throws IOException, LognexApiException {
        DocumentPosition retrievedUpdatedPosition = api.entity().supply().getPosition(e, p.getId());

        assertNotEquals(retrievedOriginalPosition.getQuantity(), retrievedUpdatedPosition.getQuantity());
        assertEquals(quantity, retrievedUpdatedPosition.getQuantity());
        assertEquals(((ProductEntity) retrievedOriginalPosition.getAssortment()).getMeta().getHref(),
                ((ProductEntity) retrievedUpdatedPosition.getAssortment()).getMeta().getHref());
    }
}
